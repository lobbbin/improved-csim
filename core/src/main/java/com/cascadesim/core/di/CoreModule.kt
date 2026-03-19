package com.cascadesim.core.di

import android.content.Context
import androidx.room.Room
import com.cascadesim.core.data.local.CascadeDatabase
import com.cascadesim.core.data.local.dao.*
import com.cascadesim.core.data.repository.*
import com.cascadesim.core.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing core dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CascadeDatabase {
        return Room.databaseBuilder(
            context,
            CascadeDatabase::class.java,
            "cascade_sim_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    @Singleton
    fun provideCountryDao(database: CascadeDatabase): CountryDao {
        return database.countryDao()
    }
    
    @Provides
    @Singleton
    fun provideNPCDao(database: CascadeDatabase): NPCDao {
        return database.npcDao()
    }
    
    @Provides
    @Singleton
    fun provideGameEventDao(database: CascadeDatabase): GameEventDao {
        return database.gameEventDao()
    }
    
    @Provides
    @Singleton
    fun provideTaskDao(database: CascadeDatabase): TaskDao {
        return database.taskDao()
    }
    
    @Provides
    @Singleton
    fun providePlayerStateDao(database: CascadeDatabase): PlayerStateDao {
        return database.playerStateDao()
    }
    
    @Provides
    @Singleton
    fun provideGameSessionDao(database: CascadeDatabase): GameSessionDao {
        return database.gameSessionDao()
    }
    
    @Provides
    @Singleton
    fun provideMemoryDao(database: CascadeDatabase): MemoryDao {
        return database.memoryDao()
    }
    
    @Provides
    @Singleton
    fun provideCountryRepository(dao: CountryDao): CountryRepository {
        return CountryRepositoryImpl(dao)
    }
    
    @Provides
    @Singleton
    fun provideNPCRepository(npcDao: NPCDao, memoryDao: MemoryDao): NPCRepository {
        return NPCRepositoryImpl(npcDao, memoryDao)
    }
    
    @Provides
    @Singleton
    fun provideGameEventRepository(dao: GameEventDao): GameEventRepository {
        return GameEventRepositoryImpl(dao)
    }
    
    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(dao)
    }
    
    @Provides
    @Singleton
    fun providePlayerStateRepository(dao: PlayerStateDao): PlayerStateRepository {
        return PlayerStateRepositoryImpl(dao)
    }
    
    @Provides
    @Singleton
    fun provideGameSessionRepository(dao: GameSessionDao): GameSessionRepository {
        return GameSessionRepositoryImpl(dao)
    }
}
