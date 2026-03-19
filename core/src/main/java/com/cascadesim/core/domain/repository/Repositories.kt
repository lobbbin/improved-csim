package com.cascadesim.core.domain.repository

import com.cascadesim.core.domain.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Country data.
 */
interface CountryRepository {
    fun getAllCountries(): Flow<List<Country>>
    suspend fun getCountryById(id: String): Country?
    suspend fun saveCountry(country: Country)
    suspend fun saveCountries(countries: List<Country>)
    suspend fun updateCountry(country: Country)
    suspend fun deleteCountry(country: Country)
}

/**
 * Repository interface for NPC data.
 */
interface NPCRepository {
    fun getAllNPCs(): Flow<List<NPC>>
    suspend fun getNPCById(id: String): NPC?
    fun getNPCsByCountry(countryId: String): Flow<List<NPC>>
    fun getLivingNPCs(): Flow<List<NPC>>
    suspend fun saveNPC(npc: NPC)
    suspend fun saveNPCs(npcs: List<NPC>)
    suspend fun updateNPC(npc: NPC)
    suspend fun deleteNPC(npc: NPC)
    
    // Memory operations
    suspend fun addMemory(npcId: String, memory: Memory)
    suspend fun getMemories(npcId: String): List<Memory>
    suspend fun getMemoriesAbout(npcId: String, entityId: String): List<Memory>
}

/**
 * Repository interface for GameEvent data.
 */
interface GameEventRepository {
    fun getAllEvents(): Flow<List<GameEvent>>
    suspend fun getEventById(id: String): GameEvent?
    fun getActiveEvents(): Flow<List<GameEvent>>
    fun getUnresolvedEvents(): Flow<List<GameEvent>>
    suspend fun saveEvent(event: GameEvent)
    suspend fun saveEvents(events: List<GameEvent>)
    suspend fun updateEvent(event: GameEvent)
    suspend fun deleteEvent(event: GameEvent)
    
    // Decision handling
    suspend fun resolveEvent(eventId: String, decisionId: String)
}

/**
 * Repository interface for Task data.
 */
interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    fun getTasksByStatus(status: TaskStatus): Flow<List<Task>>
    fun getRootTasks(): Flow<List<Task>>
    fun getChildTasks(parentId: String): Flow<List<Task>>
    fun getOverdueTasks(): Flow<List<Task>>
    suspend fun saveTask(task: Task)
    suspend fun saveTasks(tasks: List<Task>)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    
    // Task spawning
    suspend fun spawnChildTask(parentId: String, task: Task)
    suspend fun completeTask(taskId: String)
    suspend fun failTask(taskId: String, reason: String)
}

/**
 * Repository interface for PlayerState data.
 */
interface PlayerStateRepository {
    suspend fun getPlayerState(): PlayerState?
    suspend fun savePlayerState(state: PlayerState)
    suspend fun updatePlayerState(state: PlayerState)
    
    // Decision recording
    suspend fun recordDecision(decision: DecisionRecord)
    suspend fun getDecisionHistory(): List<DecisionRecord>
}

/**
 * Repository interface for GameSession data.
 */
interface GameSessionRepository {
    fun getAllSessions(): Flow<List<GameSession>>
    suspend fun getSessionById(id: String): GameSession?
    suspend fun saveSession(session: GameSession)
    suspend fun deleteSession(session: GameSession)
    suspend fun createAutoSave(session: GameSession)
    suspend fun getLatestAutoSave(): GameSession?
}
