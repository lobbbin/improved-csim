package com.cascadesim.core.data.local.entity

import androidx.room.*
import com.cascadesim.core.domain.model.*
import kotlinx.coroutines.flow.Flow

/**
 * EXPANDED Database Entities for all new systems
 * Military, Education, Media, Environment, Religion, Crime, Intelligence, Elections, etc.
 */

// ==================== MILITARY ENTITIES ====================

@Entity(tableName = "armed_forces")
data class ArmedForcesEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val totalPersonnel: Int,
    val activePersonnel: Int,
    val reservePersonnel: Int,
    val paramilitaryPersonnel: Int,
    val budget: Long,
    val budgetPercentGDP: Double,
    val overallStrength: Int,
    val readinessLevel: Double,
    val morale: Double,
    val lastUpdated: Long
)

@Entity(tableName = "divisions")
data class DivisionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val branch: String,
    val personnel: Int,
    val equipmentJson: String,
    val trainingLevel: Double,
    val experienceLevel: Double,
    val morale: Double,
    val location: String,
    val status: String,
    val commander: String?,
    val formationDate: Long
)

@Entity(tableName = "wars")
data class WarEntity(
    @PrimaryKey val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long?,
    val attackersJson: String,
    val defendersJson: String,
    val status: String,
    val casualties: Int,
    val battlesFought: Int,
    val outcome: String?
)

@Entity(tableName = "nuclear_arsenal")
data class NuclearArsenalEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val totalWarheads: Int,
    val deployedWarheads: Int,
    val storageWarheads: Int,
    val retiredWarheads: Int,
    val deliverySystemsJson: String,
    val triadCapability: Boolean,
    val firstStrikePolicy: Boolean,
    val noFirstUsePolicy: Boolean
)

// ==================== EDUCATION ENTITIES ====================

@Entity(tableName = "education_systems")
data class EducationSystemEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val literacyRate: Double,
    val primaryEnrollment: Double,
    val secondaryEnrollment: Double,
    val tertiaryEnrollment: Double,
    val educationBudget: Long,
    val educationPercentGDP: Double,
    val teacherStudentRatio: Double,
    val averageYearsSchooling: Double,
    val pisaScore: Int?,
    val lastUpdated: Long
)

@Entity(tableName = "universities")
data class UniversityEntity(
    @PrimaryKey val id: String,
    val name: String,
    val countryId: String,
    val location: String,
    val founded: Int,
    val students: Int,
    val faculty: Int,
    val ranking: Int?,
    val endowment: Long,
    val researchOutput: Long,
    val specialtiesJson: String
)

@Entity(tableName = "research_projects")
data class ResearchProjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val field: String,
    val institutionId: String,
    val leadResearcher: String?,
    val budget: Long,
    val startDate: Long,
    val expectedEndDate: Long,
    val status: String,
    val progress: Double,
    val breakthroughProbability: Double
)

// ==================== MEDIA ENTITIES ====================

@Entity(tableName = "media_outlets")
data class MediaOutletEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val countryId: String,
    val ownerId: String?,
    val ideology: String,
    val credibility: Double,
    val reach: Long,
    val stateControlled: Boolean,
    val independenceScore: Double,
    val scandals: Int,
    val foundedYear: Int
)

@Entity(tableName = "news_stories")
data class NewsStoryEntity(
    @PrimaryKey val id: String,
    val headline: String,
    val content: String,
    val outletId: String,
    val category: String,
    val publishDate: Long,
    val importance: Int,
    val sentiment: Double,
    val relatedEventId: String?,
    val relatedCountryId: String?,
    val relatedNPCId: String?,
    val retracted: Boolean
)

@Entity(tableName = "propaganda_campaigns")
data class PropagandaCampaignEntity(
    @PrimaryKey val id: String,
    val name: String,
    val objective: String,
    val targetAudience: String,
    val startDate: Long,
    val endDate: Long?,
    val budget: Long,
    val methodsJson: String,
    val effectiveness: Double,
    val domestic: Boolean
)

// ==================== ENVIRONMENT ENTITIES ====================

@Entity(tableName = "environment_state")
data class EnvironmentStateEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val airQualityIndex: Double,
    val waterQualityIndex: Double,
    val forestCoverage: Double,
    val co2Emissions: Long,
    val renewableEnergyPercent: Double,
    val endangeredSpeciesCount: Int,
    val protectedAreasPercent: Double,
    val climateVulnerability: Double,
    val lastUpdated: Long
)

@Entity(tableName = "natural_disasters")
data class NaturalDisasterEntity(
    @PrimaryKey val id: String,
    val type: String,
    val severity: String,
    val location: String,
    val startDate: Long,
    val endDate: Long?,
    val casualties: Int,
    val economicDamage: Long,
    val responseStatus: String,
    val internationalAid: Boolean
)

@Entity(tableName = "pollution_records")
data class PollutionRecordEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val region: String,
    val pollutantType: String,
    val level: Double,
    val source: String,
    val healthImpact: Double,
    val recordedDate: Long
)

// ==================== RELIGION ENTITIES ====================

@Entity(tableName = "religious_demographics")
data class ReligiousDemographicsEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val religionJson: String, // Map of religion to percentage
    val secularPercentage: Double,
    val dominantReligion: String,
    val religiousFreedomIndex: Double,
    val stateReligion: String?,
    val lastUpdated: Long
)

@Entity(tableName = "religious_institutions")
data class ReligiousInstitutionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val religion: String,
    val countryId: String,
    val leaderId: String?,
    val followers: Long,
    val influence: Double,
    val wealth: Long,
    val politicalAlignment: String?,
    val foundedYear: Int
)

// ==================== CRIME ENTITIES ====================

@Entity(tableName = "crime_statistics")
data class CrimeStatisticsEntity(
    @PrimaryKey val id: String,
    val countryId: String,
    val overallCrimeRate: Double,
    val violentCrimeRate: Double,
    val propertyCrimeRate: Double,
    val homicideRate: Double,
    val clearanceRate: Double,
    val incarcerationRate: Double,
    val recidivismRate: Double,
    val year: Int,
    val recordedDate: Long
)

@Entity(tableName = "prisons")
data class PrisonEntity(
    @PrimaryKey val id: String,
    val name: String,
    val countryId: String,
    val location: String,
    val type: String,
    val capacity: Int,
    val currentInmates: Int,
    val staffCount: Int,
    val budget: Long,
    val conditionsScore: Double,
    val violenceRate: Double
)

@Entity(tableName = "criminal_organizations")
data class CriminalOrganizationEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val countryId: String,
    val leaderId: String?,
    val members: Int,
    val annualRevenue: Long,
    val activitiesJson: String,
    val influenceLevel: Double,
    val lawEnforcementStatus: String
)

// ==================== INTELLIGENCE ENTITIES ====================

@Entity(tableName = "intelligence_agencies")
data class IntelligenceAgencyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val countryId: String,
    val directorId: String?,
    val budget: Long,
    val personnel: Int,
    val capabilitiesJson: String,
    val reputationJson: String,
    val foundedYear: Int,
    val headquarters: String
)

@Entity(tableName = "spy_networks")
data class SpyNetworkEntity(
    @PrimaryKey val id: String,
    val name: String,
    val countryId: String,
    val targetCountryId: String,
    val type: String,
    val status: String,
    val assetsCount: Int,
    val intelligenceCollected: Long,
    val establishedDate: Long,
    val lastContact: Long?,
    val compromisedLevel: Double
)

@Entity(tableName = "covert_operations")
data class CovertOperationEntity(
    @PrimaryKey val id: String,
    val codename: String,
    val type: String,
    val targetCountryId: String?,
    val objective: String,
    val status: String,
    val authorizedBy: String?,
    val authorizationDate: Long,
    val budget: Long,
    val startDate: Long?,
    val endDate: Long?,
    val outcome: String?
)

// ==================== ELECTION ENTITIES ====================

@Entity(tableName = "elections")
data class ElectionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val countryId: String,
    val date: Long,
    val status: String,
    val turnout: Double,
    val resultsJson: String,
    val candidatesJson: String,
    val totalSpending: Long,
    val scandals: Int,
    val internationalObservers: Boolean
)

@Entity(tableName = "political_parties")
data class PoliticalPartyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val countryId: String,
    val ideology: String,
    val foundedYear: Int,
    val leaderId: String?,
    val membership: Long,
    val parliamentSeats: Int,
    val pollingPercentage: Double,
    val platformJson: String,
    val isActive: Boolean
)

@Entity(tableName = "candidates")
data class CandidateEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val electionId: String,
    val partyId: String?,
    val office: String,
    val incumbency: Boolean,
    val campaignFinanceJson: String,
    val pollNumbersJson: String,
    val status: String,
    val birthYear: Int,
    val gender: String
)

@Entity(tableName = "polls")
data class PollEntity(
    @PrimaryKey val id: String,
    val electionId: String,
    val pollster: String,
    val date: Long,
    val sampleSize: Int,
    val marginOfError: Double,
    val resultsJson: String,
    val methodology: String,
    val credibility: Double
)

// ==================== ACHIEVEMENT ENTITIES ====================

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val category: String,
    val type: String,
    val points: Int,
    val rarity: String,
    val requirementJson: String,
    val hidden: Boolean,
    val hint: String?
)

@Entity(tableName = "player_achievements")
data class PlayerAchievementEntity(
    @PrimaryKey val id: String,
    val achievementId: String,
    val playerId: String,
    val unlockedDate: Long,
    val gameId: String,
    val context: String,
    val progress: Double
)

// ==================== SAVE ENTITIES ====================

@Entity(tableName = "game_saves")
data class GameSaveEntity(
    @PrimaryKey val id: String,
    val slotNumber: Int,
    val name: String,
    val saveTime: Long,
    val playTime: Long,
    val fileSize: Long,
    val thumbnailPath: String?,
    val version: Int,
    val isCorrupted: Boolean,
    val isAutoSave: Boolean,
    val isIronman: Boolean,
    val checksum: String
)

// ==================== TIME SYSTEM ENTITIES ====================

@Entity(tableName = "scheduled_events")
data class ScheduledEventEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val scheduledDate: Long,
    val recurring: Boolean,
    val frequency: String?,
    val dataJson: String,
    val status: String
)

@Entity(tableName = "holidays")
data class HolidayEntity(
    @PrimaryKey val id: String,
    val name: String,
    val countryId: String,
    val month: Int,
    val day: Int,
    val type: String,
    val isPublicHoliday: Boolean,
    val traditionsJson: String
)

// ==================== EXPANDED DAOs ====================

@Dao
interface ArmedForcesDao {
    @Query("SELECT * FROM armed_forces WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<ArmedForcesEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forces: ArmedForcesEntity)
    
    @Update
    suspend fun update(forces: ArmedForcesEntity)
}

@Dao
interface DivisionDao {
    @Query("SELECT * FROM divisions")
    fun getAll(): Flow<List<DivisionEntity>>
    
    @Query("SELECT * FROM divisions WHERE id = :id")
    suspend fun getById(id: String): DivisionEntity?
    
    @Query("SELECT * FROM divisions WHERE status = :status")
    fun getByStatus(status: String): Flow<List<DivisionEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(division: DivisionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(divisions: List<DivisionEntity>)
    
    @Update
    suspend fun update(division: DivisionEntity)
    
    @Delete
    suspend fun delete(division: DivisionEntity)
}

@Dao
interface WarDao {
    @Query("SELECT * FROM wars WHERE status = 'ACTIVE'")
    fun getActiveWars(): Flow<List<WarEntity>>
    
    @Query("SELECT * FROM wars WHERE id = :id")
    suspend fun getById(id: String): WarEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(war: WarEntity)
    
    @Update
    suspend fun update(war: WarEntity)
}

@Dao
interface NuclearArsenalDao {
    @Query("SELECT * FROM nuclear_arsenal WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<NuclearArsenalEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(arsenal: NuclearArsenalEntity)
    
    @Update
    suspend fun update(arsenal: NuclearArsenalEntity)
}

@Dao
interface EducationSystemDao {
    @Query("SELECT * FROM education_systems WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<EducationSystemEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(system: EducationSystemEntity)
    
    @Update
    suspend fun update(system: EducationSystemEntity)
}

@Dao
interface UniversityDao {
    @Query("SELECT * FROM universities WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<List<UniversityEntity>>
    
    @Query("SELECT * FROM universities ORDER BY ranking ASC LIMIT :limit")
    fun getTopUniversities(limit: Int = 100): Flow<List<UniversityEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(university: UniversityEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(universities: List<UniversityEntity>)
}

@Dao
interface MediaOutletDao {
    @Query("SELECT * FROM media_outlets WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<List<MediaOutletEntity>>
    
    @Query("SELECT * FROM media_outlets WHERE type = :type")
    fun getByType(type: String): Flow<List<MediaOutletEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(outlet: MediaOutletEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(outlets: List<MediaOutletEntity>)
}

@Dao
interface NewsStoryDao {
    @Query("SELECT * FROM news_stories ORDER BY publishDate DESC LIMIT :limit")
    fun getRecent(limit: Int = 50): Flow<List<NewsStoryEntity>>
    
    @Query("SELECT * FROM news_stories WHERE relatedEventId = :eventId")
    fun getByEvent(eventId: String): Flow<List<NewsStoryEntity>>
    
    @Query("SELECT * FROM news_stories WHERE importance >= :minImportance ORDER BY publishDate DESC")
    fun getImportant(minImportance: Int = 7): Flow<List<NewsStoryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(story: NewsStoryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stories: List<NewsStoryEntity>)
}

@Dao
interface EnvironmentStateDao {
    @Query("SELECT * FROM environment_state WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<EnvironmentStateEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(state: EnvironmentStateEntity)
    
    @Update
    suspend fun update(state: EnvironmentStateEntity)
}

@Dao
interface NaturalDisasterDao {
    @Query("SELECT * FROM natural_disasters WHERE responseStatus = 'ONGOING'")
    fun getActiveDisasters(): Flow<List<NaturalDisasterEntity>>
    
    @Query("SELECT * FROM natural_disasters WHERE startDate >= :startDate AND startDate <= :endDate")
    fun getByDateRange(startDate: Long, endDate: Long): Flow<List<NaturalDisasterEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(disaster: NaturalDisasterEntity)
    
    @Update
    suspend fun update(disaster: NaturalDisasterEntity)
}

@Dao
interface CrimeStatisticsDao {
    @Query("SELECT * FROM crime_statistics WHERE countryId = :countryId ORDER BY year DESC LIMIT 1")
    fun getLatestByCountry(countryId: String): Flow<CrimeStatisticsEntity?>
    
    @Query("SELECT * FROM crime_statistics WHERE countryId = :countryId ORDER BY year DESC")
    fun getHistoryByCountry(countryId: String): Flow<List<CrimeStatisticsEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stats: CrimeStatisticsEntity)
}

@Dao
interface PrisonDao {
    @Query("SELECT * FROM prisons WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<List<PrisonEntity>>
    
    @Query("SELECT SUM(currentInmates) FROM prisons WHERE countryId = :countryId")
    fun getTotalPrisoners(countryId: String): Flow<Int?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prison: PrisonEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prisons: List<PrisonEntity>)
}

@Dao
interface IntelligenceAgencyDao {
    @Query("SELECT * FROM intelligence_agencies WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<IntelligenceAgencyEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agency: IntelligenceAgencyEntity)
    
    @Update
    suspend fun update(agency: IntelligenceAgencyEntity)
}

@Dao
interface SpyNetworkDao {
    @Query("SELECT * FROM spy_networks WHERE countryId = :countryId AND status = 'ACTIVE'")
    fun getActiveNetworks(countryId: String): Flow<List<SpyNetworkEntity>>
    
    @Query("SELECT * FROM spy_networks WHERE targetCountryId = :targetCountryId")
    fun getNetworksTargeting(targetCountryId: String): Flow<List<SpyNetworkEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(network: SpyNetworkEntity)
    
    @Update
    suspend fun update(network: SpyNetworkEntity)
}

@Dao
interface ElectionDao {
    @Query("SELECT * FROM elections WHERE countryId = :countryId ORDER BY date DESC")
    fun getByCountry(countryId: String): Flow<List<ElectionEntity>>
    
    @Query("SELECT * FROM elections WHERE status = 'VOTING_DAY' OR status = 'COUNTING'")
    fun getActiveElections(): Flow<List<ElectionEntity>>
    
    @Query("SELECT * FROM elections WHERE date >= :fromDate AND date <= :toDate")
    fun getUpcoming(fromDate: Long, toDate: Long): Flow<List<ElectionEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(election: ElectionEntity)
    
    @Update
    suspend fun update(election: ElectionEntity)
}

@Dao
interface PoliticalPartyDao {
    @Query("SELECT * FROM political_parties WHERE countryId = :countryId AND isActive = 1")
    fun getActiveByCountry(countryId: String): Flow<List<PoliticalPartyEntity>>
    
    @Query("SELECT * FROM political_parties WHERE id = :id")
    suspend fun getById(id: String): PoliticalPartyEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(party: PoliticalPartyEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(parties: List<PoliticalPartyEntity>)
    
    @Update
    suspend fun update(party: PoliticalPartyEntity)
}

@Dao
interface CandidateDao {
    @Query("SELECT * FROM candidates WHERE electionId = :electionId")
    fun getByElection(electionId: String): Flow<List<CandidateEntity>>
    
    @Query("SELECT * FROM candidates WHERE id = :id")
    suspend fun getById(id: String): CandidateEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(candidate: CandidateEntity)
    
    @Update
    suspend fun update(candidate: CandidateEntity)
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAll(): Flow<List<AchievementEntity>>
    
    @Query("SELECT * FROM achievements WHERE category = :category")
    fun getByCategory(category: String): Flow<List<AchievementEntity>>
    
    @Query("SELECT * FROM achievements WHERE hidden = 0")
    fun getVisible(): Flow<List<AchievementEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: AchievementEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(achievements: List<AchievementEntity>)
}

@Dao
interface PlayerAchievementDao {
    @Query("SELECT * FROM player_achievements WHERE playerId = :playerId")
    fun getByPlayer(playerId: String): Flow<List<PlayerAchievementEntity>>
    
    @Query("SELECT COUNT(*) FROM player_achievements WHERE playerId = :playerId")
    fun getCountByPlayer(playerId: String): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: PlayerAchievementEntity)
}

@Dao
interface GameSaveDao {
    @Query("SELECT * FROM game_saves WHERE isAutoSave = 0 ORDER BY saveTime DESC")
    fun getManualSaves(): Flow<List<GameSaveEntity>>
    
    @Query("SELECT * FROM game_saves WHERE isAutoSave = 1 ORDER BY saveTime DESC LIMIT :limit")
    fun getAutoSaves(limit: Int = 5): Flow<List<GameSaveEntity>>
    
    @Query("SELECT * FROM game_saves WHERE slotNumber = :slotNumber")
    suspend fun getBySlot(slotNumber: Int): GameSaveEntity?
    
    @Query("SELECT * FROM game_saves WHERE id = :id")
    suspend fun getById(id: String): GameSaveEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(save: GameSaveEntity)
    
    @Update
    suspend fun update(save: GameSaveEntity)
    
    @Delete
    suspend fun delete(save: GameSaveEntity)
    
    @Query("DELETE FROM game_saves WHERE id = :id")
    suspend fun deleteById(id: String)
}

@Dao
interface ScheduledEventDao {
    @Query("SELECT * FROM scheduled_events WHERE scheduledDate <= :date AND status = 'PENDING'")
    fun getDue(date: Long): Flow<List<ScheduledEventEntity>>
    
    @Query("SELECT * FROM scheduled_events WHERE scheduledDate >= :fromDate AND scheduledDate <= :toDate")
    fun getByDateRange(fromDate: Long, toDate: Long): Flow<List<ScheduledEventEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: ScheduledEventEntity)
    
    @Update
    suspend fun update(event: ScheduledEventEntity)
}

@Dao
interface HolidayDao {
    @Query("SELECT * FROM holidays WHERE countryId = :countryId")
    fun getByCountry(countryId: String): Flow<List<HolidayEntity>>
    
    @Query("SELECT * FROM holidays WHERE month = :month AND day = :day")
    fun getByDate(month: Int, day: Int): Flow<List<HolidayEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(holiday: HolidayEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(holidays: List<HolidayEntity>)
}
