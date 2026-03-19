package com.cascadesim.core.state

import com.cascadesim.core.domain.model.*
import com.cascadesim.core.domain.repository.*
import com.cascadesim.core.generator.ProceduralGenerator
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central game state manager.
 * Handles game initialization, state transitions, and core game logic.
 */
@Singleton
class GameStateManager @Inject constructor(
    private val countryRepository: CountryRepository,
    private val npcRepository: NPCRepository,
    private val gameEventRepository: GameEventRepository,
    private val taskRepository: TaskRepository,
    private val playerStateRepository: PlayerStateRepository,
    private val gameSessionRepository: GameSessionRepository
) {
    private val generator = ProceduralGenerator()
    
    private val _currentSession = MutableStateFlow<GameSession?>(null)
    val currentSession: StateFlow<GameSession?> = _currentSession.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _gameTime = MutableStateFlow(GameTime())
    val gameTime: StateFlow<GameTime> = _gameTime.asStateFlow()
    
    /**
     * Starts a new game with the specified player configuration.
     */
    suspend fun startNewGame(
        playerName: String,
        countryName: String,
        governmentType: GovernmentType
    ): Result<GameSession> {
        _isLoading.value = true
        
        return try {
            // Generate world
            val worldState = generator.generateWorld(countryCount = 15)
            
            // Select or create player's country
            val playerCountry = worldState.countries.first().let { country ->
                country.copy(
                    name = countryName,
                    governmentType = governmentType,
                    id = "player_country"
                )
            }
            
            // Create player state
            val playerState = createPlayerState(playerName, playerCountry, governmentType)
            
            // Update world with player country
            val updatedCountries = listOf(playerCountry) + 
                worldState.countries.filter { it.id != playerCountry.id }
            
            val session = GameSession(
                id = java.util.UUID.randomUUID().toString(),
                name = "Save ${System.currentTimeMillis()}",
                playerState = playerState,
                worldState = worldState.copy(countries = updatedCountries),
                gameDate = System.currentTimeMillis(),
                realTimeStart = System.currentTimeMillis(),
                totalPlayTime = 0L,
                version = 1,
                isAutoSave = false,
                thumbnailPath = null
            )
            
            // Save to repositories
            countryRepository.saveCountries(updatedCountries)
            playerStateRepository.savePlayerState(playerState)
            gameSessionRepository.saveSession(session)
            
            _currentSession.value = session
            _gameTime.value = GameTime(
                year = worldState.currentYear,
                month = worldState.currentMonth,
                day = worldState.currentDay
            )
            
            Result.success(session)
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * Loads an existing game session.
     */
    suspend fun loadGame(sessionId: String): Result<GameSession> {
        _isLoading.value = true
        
        return try {
            val session = gameSessionRepository.getSessionById(sessionId)
            if (session != null) {
                _currentSession.value = session
                _gameTime.value = GameTime(
                    year = session.worldState.currentYear,
                    month = session.worldState.currentMonth,
                    day = session.worldState.currentDay
                )
                Result.success(session)
            } else {
                Result.failure(Exception("Session not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * Saves the current game.
     */
    suspend fun saveGame(isAutoSave: Boolean = false): Result<Unit> {
        val session = _currentSession.value ?: return Result.failure(Exception("No active session"))
        
        return try {
            val updatedSession = session.copy(
                totalPlayTime = session.totalPlayTime + 
                    (System.currentTimeMillis() - session.realTimeStart),
                isAutoSave = isAutoSave
            )
            
            if (isAutoSave) {
                gameSessionRepository.createAutoSave(updatedSession)
            } else {
                gameSessionRepository.saveSession(updatedSession)
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Advances game time by one tick.
     */
    suspend fun advanceTime() {
        val currentTime = _gameTime.value
        val newDay = currentTime.day + 1
        val newMonth = if (newDay > 30) {
            currentTime.month + 1
        } else {
            currentTime.month
        }
        val newYear = if (newMonth > 12) {
            currentTime.year + 1
        } else {
            currentTime.year
        }
        
        _gameTime.value = currentTime.copy(
            day = ((newDay - 1) % 30) + 1,
            month = ((newMonth - 1) % 12) + 1,
            year = newYear
        )
        
        // Process daily events
        processDailyEvents()
    }
    
    /**
     * Processes a player decision on an event.
     */
    suspend fun processDecision(eventId: String, decisionId: String): Result<DecisionResult> {
        val event = gameEventRepository.getEventById(eventId)
            ?: return Result.failure(Exception("Event not found"))
        
        val decision = event.availableDecisions.find { it.id == decisionId }
            ?: return Result.failure(Exception("Decision not found"))
        
        // Check requirements
        val playerState = playerStateRepository.getPlayerState()
            ?: return Result.failure(Exception("No player state"))
        
        // Apply costs
        decision.costs.forEach { cost ->
            applyCost(playerState, cost)
        }
        
        // Resolve event
        gameEventRepository.resolveEvent(eventId, decisionId)
        
        // Apply outcomes
        val outcomes = applyOutcomes(decision.outcomes, event, playerState)
        
        // Spawn cascade tasks
        val spawnedTasks = spawnCascadeTasks(event, decision)
        
        // Update player decision history
        playerStateRepository.recordDecision(
            DecisionRecord(
                decisionId = decisionId,
                eventId = eventId,
                choice = decision.text,
                timestamp = System.currentTimeMillis(),
                consequences = outcomes.map { it.description },
                publicReaction = ReactionType.NEUTRAL
            )
        )
        
        return Result.success(
            DecisionResult(
                success = true,
                outcomes = outcomes,
                spawnedTasks = spawnedTasks,
                approvalChange = calculateApprovalChange(decision)
            )
        )
    }
    
    /**
     * Completes a task.
     */
    suspend fun completeTask(taskId: String): Result<TaskResult> {
        val task = taskRepository.getTaskById(taskId)
            ?: return Result.failure(Exception("Task not found"))
        
        // Apply rewards
        task.rewards.forEach { reward ->
            applyReward(reward)
        }
        
        taskRepository.completeTask(taskId)
        
        // Spawn child tasks if any
        val childTasks = mutableListOf<Task>()
        // Would check task template for child tasks
        
        return Result.success(
            TaskResult(
                success = true,
                rewards = task.rewards,
                spawnedChildren = childTasks
            )
        )
    }
    
    /**
     * Gets all active events for the current session.
     */
    fun getActiveEvents(): Flow<List<GameEvent>> = gameEventRepository.getActiveEvents()
    
    /**
     * Gets all pending tasks.
     */
    fun getPendingTasks(): Flow<List<Task>> = taskRepository.getTasksByStatus(TaskStatus.PENDING)
    
    /**
     * Gets overdue tasks.
     */
    fun getOverdueTasks(): Flow<List<Task>> = taskRepository.getOverdueTasks()
    
    // Private helper functions
    
    private fun createPlayerState(
        playerName: String,
        country: Country,
        governmentType: GovernmentType
    ): PlayerState {
        val now = System.currentTimeMillis()
        val position = when (governmentType) {
            GovernmentType.PRESIDENTIAL_DEMOCRACY -> PlayerPosition(
                title = "President",
                power = 85,
                responsibilities = listOf("Executive authority", "Commander in Chief", "Foreign policy"),
                limitations = listOf("Term limits", "Congressional oversight"),
                canAppoint = listOf("Cabinet", "Ambassadors", "Judges"),
                canVeto = true,
                canPardon = true,
                canDeclareWar = false,
                canDissolveParliament = false
            )
            GovernmentType.PARLIAMENTARY_DEMOCRACY -> PlayerPosition(
                title = "Prime Minister",
                power = 70,
                responsibilities = listOf("Head of government", "Policy leadership", "Cabinet management"),
                limitations = listOf("Parliamentary confidence", "Coalition agreements"),
                canAppoint = listOf("Cabinet"),
                canVeto = false,
                canPardon = false,
                canDeclareWar = false,
                canDissolveParliament = true
            )
            GovernmentType.ABSOLUTE_MONARCHY -> PlayerPosition(
                title = "Monarch",
                power = 100,
                responsibilities = listOf("Absolute authority", "All state matters"),
                limitations = emptyList(),
                canAppoint = listOf("All positions"),
                canVeto = true,
                canPardon = true,
                canDeclareWar = true,
                canDissolveParliament = true
            )
            GovernmentType.DICTATORSHIP -> PlayerPosition(
                title = "Supreme Leader",
                power = 95,
                responsibilities = listOf("Total control", "Military command"),
                limitations = listOf("Potential coup risk"),
                canAppoint = listOf("All positions"),
                canVeto = true,
                canPardon = true,
                canDeclareWar = true,
                canDissolveParliament = true
            )
            else -> PlayerPosition(
                title = "Head of State",
                power = 60,
                responsibilities = listOf("State leadership"),
                limitations = listOf("Constitutional limits"),
                canAppoint = listOf("Ministers"),
                canVeto = false,
                canPardon = false,
                canDeclareWar = false,
                canDissolveParliament = false
            )
        }
        
        return PlayerState(
            id = "player",
            name = playerName,
            country = country.id,
            currentMode = GameMode.EXECUTIVE,
            governmentType = governmentType,
            position = position,
            politicalCapital = 100,
            approvalRating = 0.55,
            personalWealth = 500_000L,
            party = null,
            coalitionPartners = emptyList(),
            opposition = emptyList(),
            achievements = emptyList(),
            reputation = mapOf(
                ReputationType.DOMESTIC to 50,
                ReputationType.INTERNATIONAL to 50,
                ReputationType.ECONOMIC to 50,
                ReputationType.DIPLOMATIC to 50
            ),
            traits = emptyList(),
            decisionsHistory = emptyList(),
            currentTermStart = now,
            termLength = 157_680_000_000L, // 5 years in milliseconds
            electionCycle = 157_680_000_000L,
            vetoes = 0,
            executiveOrders = 0,
            lawsPassed = 0,
            treatiesSigned = 0,
            createdAt = now,
            updatedAt = now
        )
    }
    
    private suspend fun processDailyEvents() {
        // Check for random events
        if (kotlin.random.Random.nextDouble() < 0.1) { // 10% chance per day
            val event = generator.generateEvent()
            gameEventRepository.saveEvent(event)
        }
        
        // Check for task deadlines
        taskRepository.getOverdueTasks().first().forEach { task ->
            taskRepository.failTask(task.id, "Deadline expired")
        }
    }
    
    private suspend fun applyCost(playerState: PlayerState, cost: Cost) {
        when (cost.type) {
            CostType.POLITICAL_CAPITAL -> {
                playerStateRepository.updatePlayerState(
                    playerState.copy(
                        politicalCapital = (playerState.politicalCapital - cost.amount.toInt()).coerceAtLeast(0)
                    )
                )
            }
            CostType.APPROVAL_RATING -> {
                playerStateRepository.updatePlayerState(
                    playerState.copy(
                        approvalRating = (playerState.approvalRating - cost.amount / 100.0).coerceIn(0.0, 1.0)
                    )
                )
            }
            else -> { /* Handle other cost types */ }
        }
    }
    
    private fun applyOutcomes(
        outcomes: List<Outcome>,
        event: GameEvent,
        playerState: PlayerState
    ): List<OutcomeResult> {
        return outcomes.map { outcome ->
            if (kotlin.random.Random.nextDouble() < outcome.probability) {
                OutcomeResult(
                    type = outcome.type,
                    value = outcome.value,
                    description = "${outcome.type.name} changed by ${outcome.value}",
                    success = true
                )
            } else {
                OutcomeResult(
                    type = outcome.type,
                    value = 0.0,
                    description = "Outcome did not occur",
                    success = false
                )
            }
        }
    }
    
    private suspend fun spawnCascadeTasks(event: GameEvent, decision: Decision): List<Task> {
        val tasks = mutableListOf<Task>()
        
        // Macro decisions spawn micro tasks
        if (event.type in listOf(EventType.CRISIS, EventType.DIPLOMATIC, EventType.POLITICAL)) {
            val followUpTask = generator.generateTask(type = TaskType.DOCUMENT_APPROVAL)
            taskRepository.saveTask(followUpTask)
            tasks.add(followUpTask)
        }
        
        return tasks
    }
    
    private fun calculateApprovalChange(decision: Decision): Double {
        return when (decision.riskLevel) {
            RiskLevel.SAFE -> 0.02
            RiskLevel.LOW -> 0.01
            RiskLevel.MODERATE -> 0.0
            RiskLevel.HIGH -> -0.02
            RiskLevel.EXTREME -> -0.05
            RiskLevel.UNKNOWN -> 0.0
        }
    }
    
    private suspend fun applyReward(reward: TaskReward) {
        val playerState = playerStateRepository.getPlayerState() ?: return
        
        when (reward.type) {
            RewardType.APPROVAL_RATING -> {
                playerStateRepository.updatePlayerState(
                    playerState.copy(
                        approvalRating = (playerState.approvalRating + reward.value / 100.0).coerceIn(0.0, 1.0)
                    )
                )
            }
            RewardType.POLITICAL_CAPITAL -> {
                playerStateRepository.updatePlayerState(
                    playerState.copy(
                        politicalCapital = (playerState.politicalCapital + reward.value.toInt()).coerceAtMost(200)
                    )
                )
            }
            else -> { /* Handle other reward types */ }
        }
    }
}

/**
 * Data class for game time tracking.
 */
data class GameTime(
    val year: Int = 2024,
    val month: Int = 1,
    val day: Int = 1,
    val hour: Int = 8,
    val speed: TimeSpeed = TimeSpeed.NORMAL
) {
    val formattedDate: String
        get() = "$day/$month/$year"
    
    val dayOfYear: Int
        get() = (month - 1) * 30 + day
}

/**
 * Result of a decision.
 */
data class DecisionResult(
    val success: Boolean,
    val outcomes: List<OutcomeResult>,
    val spawnedTasks: List<Task>,
    val approvalChange: Double
)

/**
 * Result of an outcome application.
 */
data class OutcomeResult(
    val type: OutcomeType,
    val value: Double,
    val description: String,
    val success: Boolean
)

/**
 * Result of task completion.
 */
data class TaskResult(
    val success: Boolean,
    val rewards: List<TaskReward>,
    val spawnedChildren: List<Task>
)
