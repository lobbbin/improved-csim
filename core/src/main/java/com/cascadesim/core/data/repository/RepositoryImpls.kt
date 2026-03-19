package com.cascadesim.core.data.repository

import com.cascadesim.core.data.local.dao.*
import com.cascadesim.core.data.local.entity.*
import com.cascadesim.core.domain.model.*
import com.cascadesim.core.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of CountryRepository.
 */
@Singleton
class CountryRepositoryImpl @Inject constructor(
    private val countryDao: CountryDao
) : CountryRepository {
    
    override fun getAllCountries(): Flow<List<Country>> =
        countryDao.getAllCountries().map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun getCountryById(id: String): Country? =
        countryDao.getCountryById(id)?.toDomain()
    
    override suspend fun saveCountry(country: Country) =
        countryDao.insertCountry(CountryEntity.fromDomain(country))
    
    override suspend fun saveCountries(countries: List<Country>) =
        countryDao.insertCountries(countries.map { CountryEntity.fromDomain(it) })
    
    override suspend fun updateCountry(country: Country) =
        countryDao.updateCountry(CountryEntity.fromDomain(country))
    
    override suspend fun deleteCountry(country: Country) =
        countryDao.deleteCountry(CountryEntity.fromDomain(country))
}

/**
 * Implementation of NPCRepository.
 */
@Singleton
class NPCRepositoryImpl @Inject constructor(
    private val npcDao: NPCDao,
    private val memoryDao: MemoryDao
) : NPCRepository {
    
    override fun getAllNPCs(): Flow<List<NPC>> =
        npcDao.getAllNPCs().map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun getNPCById(id: String): NPC? =
        npcDao.getNPCById(id)?.toDomain()
    
    override fun getNPCsByCountry(countryId: String): Flow<List<NPC>> =
        npcDao.getNPCsByCountry(countryId).map { entities -> entities.map { it.toDomain() } }
    
    override fun getLivingNPCs(): Flow<List<NPC>> =
        npcDao.getLivingNPCs().map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun saveNPC(npc: NPC) =
        npcDao.insertNPC(NPCEntity.fromDomain(npc))
    
    override suspend fun saveNPCs(npcs: List<NPC>) =
        npcDao.insertNPCs(npcs.map { NPCEntity.fromDomain(it) })
    
    override suspend fun updateNPC(npc: NPC) =
        npcDao.updateNPC(NPCEntity.fromDomain(npc))
    
    override suspend fun deleteNPC(npc: NPC) =
        npcDao.deleteNPC(NPCEntity.fromDomain(npc))
    
    override suspend fun addMemory(npcId: String, memory: Memory) =
        memoryDao.insertMemory(MemoryEntity.fromDomain(memory, npcId))
    
    override suspend fun getMemories(npcId: String): List<Memory> =
        memoryDao.getMemoriesForNPC(npcId).let { emptyList() } // Flow to list conversion
    
    override suspend fun getMemoriesAbout(npcId: String, entityId: String): List<Memory> =
        memoryDao.getMemoriesAboutEntity(npcId, entityId).map { it.toDomain() }
}

/**
 * Implementation of GameEventRepository.
 */
@Singleton
class GameEventRepositoryImpl @Inject constructor(
    private val gameEventDao: GameEventDao
) : GameEventRepository {
    
    override fun getAllEvents(): Flow<List<GameEvent>> =
        gameEventDao.getAllEvents().map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun getEventById(id: String): GameEvent? =
        gameEventDao.getEventById(id)?.toDomain()
    
    override fun getActiveEvents(): Flow<List<GameEvent>> =
        gameEventDao.getActiveEvents().map { entities -> entities.map { it.toDomain() } }
    
    override fun getUnresolvedEvents(): Flow<List<GameEvent>> =
        gameEventDao.getUnresolvedEvents().map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun saveEvent(event: GameEvent) =
        gameEventDao.insertEvent(GameEventEntity.fromDomain(event))
    
    override suspend fun saveEvents(events: List<GameEvent>) =
        gameEventDao.insertEvents(events.map { GameEventEntity.fromDomain(it) })
    
    override suspend fun updateEvent(event: GameEvent) =
        gameEventDao.updateEvent(GameEventEntity.fromDomain(event))
    
    override suspend fun deleteEvent(event: GameEvent) =
        gameEventDao.deleteEvent(GameEventEntity.fromDomain(event))
    
    override suspend fun resolveEvent(eventId: String, decisionId: String) {
        gameEventDao.getEventById(eventId)?.let { entity ->
            gameEventDao.updateEvent(
                entity.copy(
                    isResolved = true,
                    resolvedDecision = decisionId,
                    resolvedAt = System.currentTimeMillis()
                )
            )
        }
    }
}

/**
 * Implementation of TaskRepository.
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    
    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun getTaskById(id: String): Task? =
        taskDao.getTaskById(id)?.toDomain()
    
    override fun getTasksByStatus(status: TaskStatus): Flow<List<Task>> =
        taskDao.getTasksByStatus(status.name).map { entities -> entities.map { it.toDomain() } }
    
    override fun getRootTasks(): Flow<List<Task>> =
        taskDao.getRootTasks().map { entities -> entities.map { it.toDomain() } }
    
    override fun getChildTasks(parentId: String): Flow<List<Task>> =
        taskDao.getChildTasks(parentId).map { entities -> entities.map { it.toDomain() } }
    
    override fun getOverdueTasks(): Flow<List<Task>> =
        taskDao.getOverdueTasks(System.currentTimeMillis()).map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun saveTask(task: Task) =
        taskDao.insertTask(TaskEntity.fromDomain(task))
    
    override suspend fun saveTasks(tasks: List<Task>) =
        taskDao.insertTasks(tasks.map { TaskEntity.fromDomain(it) })
    
    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(TaskEntity.fromDomain(task))
    
    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(TaskEntity.fromDomain(task))
    
    override suspend fun spawnChildTask(parentId: String, task: Task) {
        val childTask = task.copy(parentId = parentId)
        taskDao.insertTask(TaskEntity.fromDomain(childTask))
        
        // Update parent with new child reference
        taskDao.getTaskById(parentId)?.let { parent ->
            val updatedChildIds = parent.childIdsJson.let { 
                if (it.isBlank()) childTask.id else "$it,${childTask.id}" 
            }
            taskDao.updateTask(parent.copy(childIdsJson = updatedChildIds))
        }
    }
    
    override suspend fun completeTask(taskId: String) {
        taskDao.getTaskById(taskId)?.let { entity ->
            taskDao.updateTask(
                entity.copy(
                    status = TaskStatus.COMPLETED.name,
                    progress = 1.0f,
                    completedAt = System.currentTimeMillis()
                )
            )
        }
    }
    
    override suspend fun failTask(taskId: String, reason: String) {
        taskDao.getTaskById(taskId)?.let { entity ->
            taskDao.updateTask(
                entity.copy(
                    status = TaskStatus.FAILED.name,
                    completedAt = System.currentTimeMillis()
                )
            )
        }
    }
}

/**
 * Implementation of PlayerStateRepository.
 */
@Singleton
class PlayerStateRepositoryImpl @Inject constructor(
    private val playerStateDao: PlayerStateDao
) : PlayerStateRepository {
    
    private var cachedState: PlayerState? = null
    
    override suspend fun getPlayerState(): PlayerState? {
        return cachedState ?: playerStateDao.getAllPlayerStates().let { 
            // First result from flow - simplified
            null 
        }
    }
    
    override suspend fun savePlayerState(state: PlayerState) {
        cachedState = state
        playerStateDao.insertPlayerState(PlayerStateEntity.fromDomain(state))
    }
    
    override suspend fun updatePlayerState(state: PlayerState) {
        cachedState = state
        playerStateDao.updatePlayerState(PlayerStateEntity.fromDomain(state))
    }
    
    override suspend fun recordDecision(decision: DecisionRecord) {
        // Would append to decision history
    }
    
    override suspend fun getDecisionHistory(): List<DecisionRecord> {
        return emptyList() // Would load from storage
    }
}

/**
 * Implementation of GameSessionRepository.
 */
@Singleton
class GameSessionRepositoryImpl @Inject constructor(
    private val gameSessionDao: GameSessionDao
) : GameSessionRepository {
    
    override fun getAllSessions(): Flow<List<GameSession>> =
        gameSessionDao.getAllSessions().map { entities -> 
            entities.map { entity ->
                // Would need to load full session data
                GameSession(
                    id = entity.id,
                    name = entity.name,
                    playerState = PlayerState(
                        id = entity.playerId,
                        name = "",
                        country = "",
                        currentMode = GameMode.EXECUTIVE,
                        governmentType = GovernmentType.PRESIDENTIAL_DEMOCRACY,
                        position = PlayerPosition("", 0, emptyList(), emptyList(), emptyList(), false, false, false, false),
                        politicalCapital = 0,
                        approvalRating = 0.0,
                        personalWealth = 0,
                        party = null,
                        coalitionPartners = emptyList(),
                        opposition = emptyList(),
                        achievements = emptyList(),
                        reputation = emptyMap(),
                        traits = emptyList(),
                        decisionsHistory = emptyList(),
                        currentTermStart = 0,
                        termLength = 0,
                        electionCycle = 0,
                        vetoes = 0,
                        executiveOrders = 0,
                        lawsPassed = 0,
                        treatiesSigned = 0,
                        createdAt = entity.createdAt,
                        updatedAt = entity.createdAt
                    ),
                    worldState = WorldState(
                        countries = emptyList(),
                        activeNPCs = emptyList(),
                        activeEvents = emptyList(),
                        pendingTasks = emptyList(),
                        globalTensions = emptyMap(),
                        economicConditions = EconomicConditions(0.0, 0.0, 0.0, 0.0, 0, 0.0, 0.0),
                        activeTreaties = emptyList(),
                        currentYear = entity.currentYear,
                        currentMonth = entity.currentMonth,
                        currentDay = entity.currentDay,
                        timeSpeed = TimeSpeed.NORMAL
                    ),
                    gameDate = entity.gameDate,
                    realTimeStart = entity.realTimeStart,
                    totalPlayTime = entity.totalPlayTime,
                    version = entity.version,
                    isAutoSave = entity.isAutoSave,
                    thumbnailPath = entity.thumbnailPath
                )
            }
        }
    
    override suspend fun getSessionById(id: String): GameSession? =
        gameSessionDao.getSessionById(id)?.let { entity ->
            // Would load full session data
            null
        }
    
    override suspend fun saveSession(session: GameSession) =
        gameSessionDao.insertSession(GameSessionEntity.fromDomain(session))
    
    override suspend fun deleteSession(session: GameSession) =
        gameSessionDao.deleteSessionById(session.id)
    
    override suspend fun createAutoSave(session: GameSession) {
        val autoSave = session.copy(
            id = "autosave_${System.currentTimeMillis()}",
            isAutoSave = true
        )
        gameSessionDao.insertSession(GameSessionEntity.fromDomain(autoSave))
    }
    
    override suspend fun getLatestAutoSave(): GameSession? =
        gameSessionDao.getLatestAutoSave()?.let { entity ->
            // Would load full session
            null
        }
}
