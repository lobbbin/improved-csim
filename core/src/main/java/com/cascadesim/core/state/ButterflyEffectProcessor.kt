package com.cascadesim.core.state

import com.cascadesim.core.domain.model.*
import com.cascadesim.core.domain.repository.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Butterfly Effect Processor.
 * Handles the cascade of consequences from decisions - both macro-to-micro and micro-to-macro.
 */
@Singleton
class ButterflyEffectProcessor @Inject constructor(
    private val gameEventRepository: GameEventRepository,
    private val taskRepository: TaskRepository,
    private val npcRepository: NPCRepository,
    private val playerStateRepository: PlayerStateRepository,
    private val countryRepository: CountryRepository
) {
    /**
     * Processes the butterfly effect from a macro decision.
     * Macro decisions spawn micro tasks and ripple through the system.
     */
    suspend fun processMacroDecision(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState
    ): ButterflyEffect {
        val effects = mutableListOf<CascadeEffect>()
        val spawnedTasks = mutableListOf<Task>()
        val npcMemories = mutableListOf<NPCMemoryEffect>()
        val worldChanges = mutableListOf<WorldChange>()
        
        // 1. Generate immediate micro tasks
        val microTasks = generateMicroTasks(decision, event)
        microTasks.forEach { task ->
            taskRepository.saveTask(task)
            spawnedTasks.add(task)
        }
        effects.add(CascadeEffect(
            type = CascadeType.TASK_SPAWN,
            description = "Spawned ${microTasks.size} follow-up tasks",
            magnitude = microTasks.size.toDouble()
        ))
        
        // 2. Calculate NPC memory impacts
        val affectedNPCs = findAffectedNPCs(decision, playerState)
        affectedNPCs.forEach { npc ->
            val memoryWeight = calculateMemoryWeight(decision, npc, playerState)
            val memory = Memory(
                id = java.util.UUID.randomUUID().toString(),
                description = "Decision: ${decision.text}",
                relatedEntityId = decision.id,
                emotionalWeight = memoryWeight,
                importance = (event.severity.ordinal * 20 + 20),
                category = MemoryCategory.POLICY,
                timestamp = System.currentTimeMillis()
            )
            npcRepository.addMemory(npc.id, memory)
            npcMemories.add(NPCMemoryEffect(npc.id, memory))
        }
        
        // 3. Calculate world state changes
        val worldStateChanges = calculateWorldChanges(decision, event, playerState)
        worldChanges.addAll(worldStateChanges)
        
        // 4. Generate potential future events (delayed consequences)
        val futureEvents = generateFutureEvents(decision, event)
        futureEvents.forEach { futureEvent ->
            gameEventRepository.saveEvent(futureEvent)
            effects.add(CascadeEffect(
                type = CascadeType.EVENT_TRIGGER,
                description = "Future event queued: ${futureEvent.title}",
                magnitude = futureEvent.severity.ordinal.toDouble(),
                delayMs = calculateEventDelay(futureEvent)
            ))
        }
        
        return ButterflyEffect(
            sourceDecision = decision.id,
            sourceEvent = event.id,
            effects = effects,
            spawnedTasks = spawnedTasks,
            npcMemories = npcMemories,
            worldChanges = worldChanges,
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * Processes the butterfly effect from micro decisions accumulating.
     * Small decisions can accumulate to change the macro picture.
     */
    suspend fun processMicroAccumulation(
        task: Task,
        playerState: PlayerState
    ): MicroAccumulationResult {
        // Track the accumulation of micro decisions
        val pattern = detectPattern(task, playerState)
        
        return when (pattern) {
            DecisionPattern.CONSISTENT_APPROVAL -> {
                // Consistent approving builds trust but may miss issues
                MicroAccumulationResult(
                    macroEffect = MacroEffect.TRUST_INCREASE,
                    value = 0.5,
                    description = "Consistent approvals building institutional trust",
                    triggersEvent = false
                )
            }
            DecisionPattern.CONSISTENT_REJECTION -> {
                // Consistent rejection builds reputation for being tough
                MicroAccumulationResult(
                    macroEffect = MacroEffect.REPUTATION_TOUGH,
                    value = 0.3,
                    description = "Reputation for strict oversight growing",
                    triggersEvent = false
                )
            }
            DecisionPattern.ERRATIC -> {
                // Erratic decisions create instability
                MicroAccumulationResult(
                    macroEffect = MacroEffect.INSTABILITY,
                    value = -0.2,
                    description = "Erratic decision-making causing uncertainty",
                    triggersEvent = true
                )
            }
            DecisionPattern.CORRUPT_PATTERN -> {
                // Suspicious patterns may trigger scandal
                MicroAccumulationResult(
                    macroEffect = MacroEffect.SCANDAL_RISK,
                    value = -0.5,
                    description = "Suspicious decision patterns detected",
                    triggersEvent = true
                )
            }
            else -> MicroAccumulationResult(
                macroEffect = MacroEffect.NONE,
                value = 0.0,
                description = "No significant pattern detected",
                triggersEvent = false
            )
        }
    }
    
    /**
     * Calculates how a decision affects international relations.
     */
    suspend fun calculateDiplomaticRipple(
        decision: Decision,
        affectedCountries: List<String>
    ): Map<String, Int> {
        val relationChanges = mutableMapOf<String, Int>()
        
        affectedCountries.forEach { countryId ->
            val baseChange = when (decision.riskLevel) {
                RiskLevel.SAFE -> randomInt(1, 5)
                RiskLevel.LOW -> randomInt(-2, 5)
                RiskLevel.MODERATE -> randomInt(-5, 5)
                RiskLevel.HIGH -> randomInt(-10, 2)
                RiskLevel.EXTREME -> randomInt(-20, -5)
                RiskLevel.UNKNOWN -> randomInt(-5, 5)
            }
            
            relationChanges[countryId] = baseChange
        }
        
        // Butterfly effect: Decisions about one country affect its allies/enemies
        affectedCountries.forEach { countryId ->
            val country = countryRepository.getCountryById(countryId)
            country?.relations?.forEach { (relatedCountry, relation) ->
                if (relatedCountry !in affectedCountries) {
                    // Spill-over effect to allies/enemies
                    val spillOver = (relationChanges[countryId] ?: 0) * 
                        if (relation > 50) 0.3 else if (relation < -50) -0.3 else 0.1
                    relationChanges[relatedCountry] = (relationChanges[relatedCountry] ?: 0) + spillOver.toInt()
                }
            }
        }
        
        return relationChanges
    }
    
    /**
     * Generates the next phase of gameplay based on game state changes.
     * Handles the Executive -> Opposition -> Campaign branching.
     */
    suspend fun calculateGamePhaseTransition(
        playerState: PlayerState,
        worldState: WorldState
    ): PhaseTransition {
        return when (playerState.currentMode) {
            GameMode.EXECUTIVE -> {
                // Check for election loss or removal
                val electionApproaching = playerState.isElectionApproaching
                val approvalLow = playerState.approvalRating < 0.3
                val stabilityLow = worldState.countries.find { it.id == playerState.country }
                    ?.let { it.stabilityIndex < 0.3 } ?: false
                
                when {
                    approvalLow && stabilityLow -> PhaseTransition(
                        newMode = GameMode.OPPOSITION,
                        reason = "Lost power due to low approval and instability",
                        triggerEvent = "Political transition"
                    )
                    electionApproaching -> PhaseTransition(
                        newMode = GameMode.CAMPAIGN,
                        reason = "Election season beginning",
                        triggerEvent = "Election called"
                    )
                    else -> PhaseTransition(
                        newMode = GameMode.EXECUTIVE,
                        reason = "Continue governing",
                        triggerEvent = null
                    )
                }
            }
            GameMode.OPPOSITION -> {
                // Check for comeback opportunity
                val supportGrowing = playerState.approvalRating > 0.4
                val nextElection = worldState.currentMonth >= 10
                
                when {
                    supportGrowing && nextElection -> PhaseTransition(
                        newMode = GameMode.CAMPAIGN,
                        reason = "Support recovering, election opportunity",
                        triggerEvent = "Campaign launched"
                    )
                    else -> PhaseTransition(
                        newMode = GameMode.OPPOSITION,
                        reason = "Continue building support",
                        triggerEvent = null
                    )
                }
            }
            GameMode.CAMPAIGN -> {
                // Election results determine next phase
                val pollNumbers = playerState.approvalRating
                
                when {
                    pollNumbers > 0.5 -> PhaseTransition(
                        newMode = GameMode.TRANSITION,
                        reason = "Election victory!",
                        triggerEvent = "Election won"
                    )
                    else -> PhaseTransition(
                        newMode = GameMode.OPPOSITION,
                        reason = "Election defeat",
                        triggerEvent = "Election lost"
                    )
                }
            }
            GameMode.TRANSITION -> PhaseTransition(
                newMode = GameMode.EXECUTIVE,
                reason = "Taking office",
                triggerEvent = "Inauguration"
            )
            GameMode.EXILE -> {
                val canReturn = playerState.approvalRating > 0.3
                PhaseTransition(
                    newMode = if (canReturn) GameMode.OPPOSITION else GameMode.EXILE,
                    reason = if (canReturn) "Return possible" else "Still in exile",
                    triggerEvent = null
                )
            }
            GameMode.COALITION_PARTNER -> PhaseTransition(
                newMode = GameMode.COALITION_PARTNER,
                reason = "Coalition arrangement unchanged",
                triggerEvent = null
            )
        }
    }
    
    // Private helper functions
    
    private fun generateMicroTasks(decision: Decision, event: GameEvent): List<Task> {
        val tasks = mutableListOf<Task>()
        
        // Every major decision needs follow-up
        when (event.type) {
            EventType.CRISIS -> {
                tasks.add(Task(
                    id = java.util.UUID.randomUUID().toString(),
                    title = "Crisis Follow-up Report",
                    description = "Review the aftermath of the crisis response",
                    type = TaskType.REPORT_REVIEW,
                    scope = TaskScope.MICRO,
                    status = TaskStatus.PENDING,
                    priority = TaskPriority.HIGH,
                    parentId = null,
                    childIds = emptyList(),
                    relatedEventId = event.id,
                    relatedDecisionId = decision.id,
                    assignedTo = null,
                    department = null,
                    requirements = emptyList(),
                    rewards = listOf(TaskReward(RewardType.POLITICAL_CAPITAL, "Crisis handled well", 5.0, true)),
                    penalties = emptyList(),
                    deadline = System.currentTimeMillis() + 86_400_000L,
                    timeEstimate = 3_600_000L,
                    progress = 0.0f,
                    subtasks = emptyList(),
                    dependencies = emptyList(),
                    blockingTasks = emptyList(),
                    tags = listOf("crisis", "follow-up"),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    completedAt = null
                ))
            }
            EventType.DIPLOMATIC -> {
                tasks.add(Task(
                    id = java.util.UUID.randomUUID().toString(),
                    title = "Diplomatic Cable Response",
                    description = "Draft response to diplomatic communication",
                    type = TaskType.PRESS_STATEMENT,
                    scope = TaskScope.MICRO,
                    status = TaskStatus.PENDING,
                    priority = TaskPriority.NORMAL,
                    parentId = null,
                    childIds = emptyList(),
                    relatedEventId = event.id,
                    relatedDecisionId = decision.id,
                    assignedTo = null,
                    department = "Foreign Ministry",
                    requirements = emptyList(),
                    rewards = listOf(TaskReward(RewardType.REPUTATION, "Diplomacy handled", 2.0, true)),
                    penalties = emptyList(),
                    deadline = System.currentTimeMillis() + 172_800_000L,
                    timeEstimate = 7_200_000L,
                    progress = 0.0f,
                    subtasks = emptyList(),
                    dependencies = emptyList(),
                    blockingTasks = emptyList(),
                    tags = listOf("diplomacy"),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    completedAt = null
                ))
            }
            EventType.ECONOMIC -> {
                tasks.add(Task(
                    id = java.util.UUID.randomUUID().toString(),
                    title = "Budget Review",
                    description = "Review budget implications of economic decision",
                    type = TaskType.BUDGET_ALLOCATION,
                    scope = TaskScope.MESO,
                    status = TaskStatus.PENDING,
                    priority = TaskPriority.HIGH,
                    parentId = null,
                    childIds = emptyList(),
                    relatedEventId = event.id,
                    relatedDecisionId = decision.id,
                    assignedTo = null,
                    department = "Finance Ministry",
                    requirements = emptyList(),
                    rewards = listOf(TaskReward(RewardType.POLITICAL_CAPITAL, "Fiscal responsibility", 3.0, true)),
                    penalties = emptyList(),
                    deadline = System.currentTimeMillis() + 259_200_000L,
                    timeEstimate = 14_400_000L,
                    progress = 0.0f,
                    subtasks = emptyList(),
                    dependencies = emptyList(),
                    blockingTasks = emptyList(),
                    tags = listOf("economy", "budget"),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    completedAt = null
                ))
            }
            else -> { /* Generate generic follow-up task */ }
        }
        
        return tasks
    }
    
    private suspend fun findAffectedNPCs(decision: Decision, playerState: PlayerState): List<NPC> {
        // Get NPCs that would be affected by this decision
        return npcRepository.getLivingNPCs().first()
            .sortedByDescending { it.influence }
            .take(10)
    }
    
    private fun calculateMemoryWeight(decision: Decision, npc: NPC, playerState: PlayerState): Double {
        val relationshipScore = npc.relationships[playerState.id] ?: 50
        val decisionAlignment = when {
            decision.riskLevel == RiskLevel.SAFE -> 0.3
            decision.riskLevel == RiskLevel.EXTREME -> -0.5
            else -> 0.0
        }
        
        return (relationshipScore - 50) / 100.0 + decisionAlignment
    }
    
    private fun calculateWorldChanges(decision: Decision, event: GameEvent, playerState: PlayerState): List<WorldChange> {
        val changes = mutableListOf<WorldChange>()
        
        decision.costs.forEach { cost ->
            changes.add(WorldChange(
                targetType = cost.type.name,
                change = -cost.amount,
                description = "Cost: ${cost.type.name} decreased by ${cost.amount}"
            ))
        }
        
        return changes
    }
    
    private fun generateFutureEvents(decision: Decision, event: GameEvent): List<GameEvent> {
        val events = mutableListOf<GameEvent>()
        
        // High-risk decisions may trigger consequences
        if (decision.riskLevel == RiskLevel.HIGH || decision.riskLevel == RiskLevel.EXTREME) {
            events.add(GameEvent(
                id = java.util.UUID.randomUUID().toString(),
                title = "Consequences of ${decision.text.take(30)}",
                description = "The aftermath of your decision is being felt",
                type = EventType.POLITICAL,
                category = EventCategory.DOMESTIC,
                severity = EventSeverity.MINOR,
                involvedEntities = emptyList(),
                availableDecisions = emptyList(),
                consequences = emptyList(),
                triggeredBy = decision.id,
                cascadeChildren = emptyList(),
                memoryImpact = null,
                timeLimit = null,
                isActive = false, // Will activate later
                isResolved = false,
                resolvedDecision = null,
                createdAt = System.currentTimeMillis() + 7 * 86_400_000L, // 7 days later
                resolvedAt = null
            ))
        }
        
        return events
    }
    
    private fun calculateEventDelay(event: GameEvent): Long {
        return when (event.severity) {
            EventSeverity.TRIVIAL -> 14 * 86_400_000L // 14 days
            EventSeverity.MINOR -> 7 * 86_400_000L // 7 days
            EventSeverity.MODERATE -> 3 * 86_400_000L // 3 days
            EventSeverity.MAJOR -> 86_400_000L // 1 day
            EventSeverity.CRITICAL -> 43_200_000L // 12 hours
            EventSeverity.CATASTROPHIC -> 21_600_000L // 6 hours
        }
    }
    
    private fun detectPattern(task: Task, playerState: PlayerState): DecisionPattern {
        // Simplified pattern detection - in reality would analyze recent decisions
        return DecisionPattern.entries.toTypedArray().random()
    }
    
    private fun randomInt(min: Int, max: Int): Int = (min..max).random()
}

/**
 * Data classes for butterfly effect tracking.
 */
data class ButterflyEffect(
    val sourceDecision: String,
    val sourceEvent: String,
    val effects: List<CascadeEffect>,
    val spawnedTasks: List<Task>,
    val npcMemories: List<NPCMemoryEffect>,
    val worldChanges: List<WorldChange>,
    val timestamp: Long
)

data class CascadeEffect(
    val type: CascadeType,
    val description: String,
    val magnitude: Double,
    val delayMs: Long = 0L
)

enum class CascadeType {
    TASK_SPAWN,
    EVENT_TRIGGER,
    RELATION_CHANGE,
    APPROVAL_CHANGE,
    POLICY_CHANGE,
    NPC_REACTION
}

data class NPCMemoryEffect(
    val npcId: String,
    val memory: Memory
)

data class WorldChange(
    val targetType: String,
    val change: Double,
    val description: String
)

data class MicroAccumulationResult(
    val macroEffect: MacroEffect,
    val value: Double,
    val description: String,
    val triggersEvent: Boolean
)

enum class MacroEffect {
    TRUST_INCREASE,
    REPUTATION_TOUGH,
    INSTABILITY,
    SCANDAL_RISK,
    NONE
}

enum class DecisionPattern {
    CONSISTENT_APPROVAL,
    CONSISTENT_REJECTION,
    ERRATIC,
    CORRUPT_PATTERN,
    BALANCED
}

data class PhaseTransition(
    val newMode: GameMode,
    val reason: String,
    val triggerEvent: String?
)
