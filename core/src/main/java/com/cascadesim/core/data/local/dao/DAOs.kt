package com.cascadesim.core.data.local.dao

import androidx.room.*
import com.cascadesim.core.data.local.entity.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Country entities.
 */
@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    fun getAllCountries(): Flow<List<CountryEntity>>
    
    @Query("SELECT * FROM countries WHERE id = :id")
    suspend fun getCountryById(id: String): CountryEntity?
    
    @Query("SELECT * FROM countries WHERE continent = :continent")
    fun getCountriesByContinent(continent: String): Flow<List<CountryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)
    
    @Update
    suspend fun updateCountry(country: CountryEntity)
    
    @Delete
    suspend fun deleteCountry(country: CountryEntity)
    
    @Query("DELETE FROM countries")
    suspend fun deleteAllCountries()
}

/**
 * DAO for NPC entities.
 */
@Dao
interface NPCDao {
    @Query("SELECT * FROM npcs")
    fun getAllNPCs(): Flow<List<NPCEntity>>
    
    @Query("SELECT * FROM npcs WHERE id = :id")
    suspend fun getNPCById(id: String): NPCEntity?
    
    @Query("SELECT * FROM npcs WHERE nationality = :countryId")
    fun getNPCsByCountry(countryId: String): Flow<List<NPCEntity>>
    
    @Query("SELECT * FROM npcs WHERE role = :role")
    fun getNPCsByRole(role: String): Flow<List<NPCEntity>>
    
    @Query("SELECT * FROM npcs WHERE party = :partyId")
    fun getNPCsByParty(partyId: String): Flow<List<NPCEntity>>
    
    @Query("SELECT * FROM npcs WHERE isAlive = 1")
    fun getLivingNPCs(): Flow<List<NPCEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNPC(npc: NPCEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNPCs(npcs: List<NPCEntity>)
    
    @Update
    suspend fun updateNPC(npc: NPCEntity)
    
    @Delete
    suspend fun deleteNPC(npc: NPCEntity)
    
    @Query("DELETE FROM npcs")
    suspend fun deleteAllNPCs()
}

/**
 * DAO for GameEvent entities.
 */
@Dao
interface GameEventDao {
    @Query("SELECT * FROM game_events")
    fun getAllEvents(): Flow<List<GameEventEntity>>
    
    @Query("SELECT * FROM game_events WHERE id = :id")
    suspend fun getEventById(id: String): GameEventEntity?
    
    @Query("SELECT * FROM game_events WHERE isActive = 1")
    fun getActiveEvents(): Flow<List<GameEventEntity>>
    
    @Query("SELECT * FROM game_events WHERE isResolved = 0")
    fun getUnresolvedEvents(): Flow<List<GameEventEntity>>
    
    @Query("SELECT * FROM game_events WHERE severity IN (:severities)")
    fun getEventsBySeverity(severities: List<String>): Flow<List<GameEventEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: GameEventEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<GameEventEntity>)
    
    @Update
    suspend fun updateEvent(event: GameEventEntity)
    
    @Delete
    suspend fun deleteEvent(event: GameEventEntity)
    
    @Query("DELETE FROM game_events")
    suspend fun deleteAllEvents()
}

/**
 * DAO for Task entities.
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity?
    
    @Query("SELECT * FROM tasks WHERE status = :status")
    fun getTasksByStatus(status: String): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE parentId IS NULL")
    fun getRootTasks(): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE parentId = :parentId")
    fun getChildTasks(parentId: String): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE assignedTo = :assigneeId")
    fun getTasksByAssignee(assigneeId: String): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE department = :department")
    fun getTasksByDepartment(department: String): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE deadline IS NOT NULL AND deadline < :timestamp AND status != 'COMPLETED'")
    fun getOverdueTasks(timestamp: Long): Flow<List<TaskEntity>>
    
    @Query("SELECT * FROM tasks WHERE priority = :priority")
    fun getTasksByPriority(priority: String): Flow<List<TaskEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)
    
    @Update
    suspend fun updateTask(task: TaskEntity)
    
    @Delete
    suspend fun deleteTask(task: TaskEntity)
    
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}

/**
 * DAO for PlayerState entities.
 */
@Dao
interface PlayerStateDao {
    @Query("SELECT * FROM player_states WHERE id = :id")
    suspend fun getPlayerStateById(id: String): PlayerStateEntity?
    
    @Query("SELECT * FROM player_states")
    fun getAllPlayerStates(): Flow<List<PlayerStateEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerState(state: PlayerStateEntity)
    
    @Update
    suspend fun updatePlayerState(state: PlayerStateEntity)
    
    @Delete
    suspend fun deletePlayerState(state: PlayerStateEntity)
    
    @Query("DELETE FROM player_states")
    suspend fun deleteAllPlayerStates()
}

/**
 * DAO for GameSession entities.
 */
@Dao
interface GameSessionDao {
    @Query("SELECT * FROM game_sessions ORDER BY createdAt DESC")
    fun getAllSessions(): Flow<List<GameSessionEntity>>
    
    @Query("SELECT * FROM game_sessions WHERE id = :id")
    suspend fun getSessionById(id: String): GameSessionEntity?
    
    @Query("SELECT * FROM game_sessions WHERE isAutoSave = 0 ORDER BY createdAt DESC")
    fun getManualSaves(): Flow<List<GameSessionEntity>>
    
    @Query("SELECT * FROM game_sessions WHERE isAutoSave = 1 ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestAutoSave(): GameSessionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: GameSessionEntity)
    
    @Update
    suspend fun updateSession(session: GameSessionEntity)
    
    @Delete
    suspend fun deleteSession(session: GameSessionEntity)
    
    @Query("DELETE FROM game_sessions WHERE id = :id")
    suspend fun deleteSessionById(id: String)
    
    @Query("DELETE FROM game_sessions")
    suspend fun deleteAllSessions()
}

/**
 * DAO for Memory entities.
 */
@Dao
interface MemoryDao {
    @Query("SELECT * FROM memories WHERE npcId = :npcId ORDER BY timestamp DESC")
    fun getMemoriesForNPC(npcId: String): Flow<List<MemoryEntity>>
    
    @Query("SELECT * FROM memories WHERE npcId = :npcId AND relatedEntityId = :entityId")
    suspend fun getMemoriesAboutEntity(npcId: String, entityId: String): List<MemoryEntity>
    
    @Query("SELECT * FROM memories WHERE npcId = :npcId AND emotionalWeight < 0 ORDER BY emotionalWeight ASC LIMIT :limit")
    suspend fun getNegativeMemories(npcId: String, limit: Int = 10): List<MemoryEntity>
    
    @Query("SELECT * FROM memories WHERE npcId = :npcId AND emotionalWeight > 0 ORDER BY emotionalWeight DESC LIMIT :limit")
    suspend fun getPositiveMemories(npcId: String, limit: Int = 10): List<MemoryEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemories(memories: List<MemoryEntity>)
    
    @Delete
    suspend fun deleteMemory(memory: MemoryEntity)
    
    @Query("DELETE FROM memories WHERE npcId = :npcId")
    suspend fun deleteMemoriesForNPC(npcId: String)
    
    @Query("DELETE FROM memories")
    suspend fun deleteAllMemories()
}
