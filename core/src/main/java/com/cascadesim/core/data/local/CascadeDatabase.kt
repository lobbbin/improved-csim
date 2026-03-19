package com.cascadesim.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cascadesim.core.data.local.dao.*
import com.cascadesim.core.data.local.entity.*

@Database(
    entities = [
        CountryEntity::class,
        NPCEntity::class,
        GameEventEntity::class,
        TaskEntity::class,
        PlayerStateEntity::class,
        GameSessionEntity::class,
        MemoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CascadeDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun npcDao(): NPCDao
    abstract fun gameEventDao(): GameEventDao
    abstract fun taskDao(): TaskDao
    abstract fun playerStateDao(): PlayerStateDao
    abstract fun gameSessionDao(): GameSessionDao
    abstract fun memoryDao(): MemoryDao
}
