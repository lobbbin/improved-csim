package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * COMPREHENSIVE Save System
 * Features: Multiple save slots, Auto-save with rollback, Ironman mode,
 * Cloud saves, Export/Import saves, Save compression and validation
 */

@Parcelize
data class SaveSystem(
    val saveSlots: List<SaveSlot>,
    val autoSaves: List<AutoSave>,
    val quickSave: QuickSave?,
    val ironmanMode: Boolean,
    val cloudSyncEnabled: Boolean,
    val lastSaveTime: Long,
    val totalPlayTime: Long,
    val saveSettings: SaveSettings
) : Parcelable

// Save Slot

@Parcelize
data class SaveSlot(
    val id: String,
    val slotNumber: Int,
    val name: String,
    val gameSession: GameSession,
    val thumbnail: String?, // Base64 encoded screenshot
    val playTime: Long, // milliseconds
    val saveTime: Long,
    val fileSize: Long,
    val isCorrupted: Boolean,
    val version: Int,
    val achievements: List<String>, // Achievement IDs unlocked
    val checksum: String,
    val tags: List<String>
) : Parcelable {
    
    val formattedPlayTime: String
        get() {
            val hours = playTime / 3_600_000
            val minutes = (playTime % 3_600_000) / 60_000
            return "${hours}h ${minutes}m"
        }
    
    val formattedSaveTime: String
        get() {
            val now = System.currentTimeMillis()
            val diff = now - saveTime
            return when {
                diff < 60_000 -> "Just now"
                diff < 3_600_000 -> "${diff / 60_000} minutes ago"
                diff < 86_400_000 -> "${diff / 3_600_000} hours ago"
                diff < 604_800_000 -> "${diff / 86_400_000} days ago"
                else -> "${diff / 604_800_000} weeks ago"
            }
        }
}

// Game Session (Complete game state)

@Parcelize
data class GameSession(
    val id: String,
    val name: String,
    val playerState: PlayerState,
    val worldState: WorldState,
    val gameDate: Long,
    val realTimeStart: Long,
    val totalPlayTime: Long,
    val version: Int,
    val isAutoSave: Boolean,
    val thumbnailPath: String?,
    
    // Extended state for new systems
    val militaryState: MilitaryState?,
    val educationState: EducationState?,
    val mediaState: MediaState?,
    val environmentState: EnvironmentState?,
    val religionState: ReligionState?,
    val crimeState: CrimeState?,
    val intelligenceState: IntelligenceState?,
    val electionState: ElectionState?,
    val achievementState: AchievementState?,
    val timeState: TimeState?,
    
    // Statistics
    val statistics: GameStatistics,
    
    // History
    val decisionHistory: List<DecisionRecord>,
    val eventHistory: List<EventRecord>,
    val lawHistory: List<LawRecord>,
    
    // Random seed for reproducibility
    val seed: Long
) : Parcelable

@Parcelize
data class MilitaryState(
    val armedForces: ArmedForces?,
    val activeWars: List<War>,
    val defenseBudget: Long,
    val nuclearArsenal: NuclearArsenal?,
    val militaryAlliances: List<String>
) : Parcelable

@Parcelize
data class EducationState(
    val nationalEducation: NationalEducation?,
    val researchProgress: Map<String, Int>,
    val universities: List<University>,
    val activePolicies: List<String>
) : Parcelable

@Parcelize
data class MediaState(
    val mediaLandscape: MediaLandscape?,
    val activeStories: List<String>,
    val pressFreedomIndex: Double
) : Parcelable

@Parcelize
data class EnvironmentState(
    val climateState: ClimateState?,
    val pollutionLevel: Double,
    val naturalDisasters: List<String>,
    val conservationAreas: List<String>
) : Parcelable

@Parcelize
data class ReligionState(
    val religiousDemographics: Map<String, Double>,
    val religiousPolicies: List<String>,
    val interfaithRelations: Map<String, Int>
) : Parcelable

@Parcelize
data class CrimeState(
    val crimeStatistics: CrimeStatistics?,
    val activeInvestigations: List<String>,
    val prisonPopulation: Int
) : Parcelable

@Parcelize
data class IntelligenceState(
    val intelligenceSystem: IntelligenceSystem?,
    val activeOperations: List<String>,
    val spyNetworks: List<String>
) : Parcelable

@Parcelize
data class ElectionState(
    val currentElection: Election?,
    val pollNumbers: Map<String, Double>,
    val campaignFinance: Long
) : Parcelable

@Parcelize
data class AchievementState(
    val unlockedAchievements: List<String>,
    val achievementPoints: Int,
    val rareAchievements: Int
) : Parcelable

@Parcelize
data class TimeState(
    val currentTime: GameTime,
    val timeSpeed: TimeSpeed,
    val upcomingEvents: List<String>
) : Parcelable

// Statistics

@Parcelize
data class GameStatistics(
    val totalDecisions: Int,
    val totalEvents: Int,
    val totalLaws: Int,
    val totalTreaties: Int,
    val totalWars: Int,
    val totalElections: Int,
    val highestApproval: Double,
    val lowestApproval: Double,
    val longestTerm: Int,
    val totalPlaytime: Long,
    val decisionsByType: Map<String, Int>,
    val eventsByType: Map<String, Int>,
    val countryRelationsHistory: Map<String, List<Int>>,
    val gdpHistory: List<Pair<Long, Long>>, // timestamp, gdp
    val populationHistory: List<Pair<Long, Long>>,
    val approvalHistory: List<Pair<Long, Double>>
) : Parcelable

// Records

@Parcelize
data class DecisionRecord(
    val decisionId: String,
    val eventId: String,
    val choice: String,
    val timestamp: Long,
    val consequences: List<String>,
    val publicReaction: ReactionType
) : Parcelable

@Parcelize
data class EventRecord(
    val eventId: String,
    val title: String,
    val type: EventType,
    val resolved: Boolean,
    val timestamp: Long,
    val outcome: String?
) : Parcelable

@Parcelize
data class LawRecord(
    val lawId: String,
    val title: String,
    val passedDate: Long,
    val votesFor: Int,
    val votesAgainst: Int,
    val vetoed: Boolean,
    val overridden: Boolean
) : Parcelable

enum class ReactionType {
    POSITIVE,
    NEGATIVE,
    NEUTRAL,
    MIXED,
    UNKNOWN
}

// Auto Save

@Parcelize
data class AutoSave(
    val id: String,
    val session: GameSession,
    val triggerType: AutoSaveTrigger,
    val saveTime: Long,
    val playTime: Long,
    val retainedUntil: Long
) : Parcelable

enum class AutoSaveTrigger {
    PERIODIC,       // Every X minutes
    BEFORE_EVENT,   // Before major event
    AFTER_EVENT,    // After event resolution
    BEFORE_DECISION,// Before player decision
    BEFORE_BATTLE,  // Before military engagement
    BEFORE_ELECTION,// Before election
    ON_ACHIEVEMENT, // When achievement unlocked
    ON_SCANDAL,     // When scandal occurs
    ON_CRISIS,      // During crisis
    SESSION_END     // When quitting
}

// Quick Save

@Parcelize
data class QuickSave(
    val id: String,
    val session: GameSession,
    val saveTime: Long,
    val playTime: Long
) : Parcelable

// Save Settings

@Parcelize
data class SaveSettings(
    val autoSaveEnabled: Boolean,
    val autoSaveInterval: Int, // minutes
    val maxAutoSaves: Int,
    val maxSaveSlots: Int,
    val compressionEnabled: Boolean,
    val encryptionEnabled: Boolean,
    val cloudSyncEnabled: Boolean,
    val ironmanMode: Boolean,
    val ironmanAutoSave: Boolean,
    val showSaveConfirmation: Boolean,
    val backupEnabled: Boolean,
    val backupInterval: Int, // hours
    val maxBackups: Int
) : Parcelable {
    
    companion object {
        val DEFAULT = SaveSettings(
            autoSaveEnabled = true,
            autoSaveInterval = 10,
            maxAutoSaves = 5,
            maxSaveSlots = 10,
            compressionEnabled = true,
            encryptionEnabled = false,
            cloudSyncEnabled = false,
            ironmanMode = false,
            ironmanAutoSave = true,
            showSaveConfirmation = true,
            backupEnabled = true,
            backupInterval = 24,
            maxBackups = 3
        )
    }
}

// Cloud Save

@Parcelize
data class CloudSave(
    val id: String,
    val sessionId: String,
    val userId: String,
    val uploadTime: Long,
    val downloadTime: Long?,
    val cloudProvider: CloudProvider,
    val syncStatus: SyncStatus,
    val fileSize: Long,
    val etag: String?,
    val conflictResolution: ConflictResolution?
) : Parcelable

enum class CloudProvider {
    GOOGLE_DRIVE,
    DROPBOX,
    ONE_DRIVE,
    ICLOUD,
    AWS_S3,
    CUSTOM_SERVER
}

enum class SyncStatus {
    SYNCED,
    PENDING_UPLOAD,
    PENDING_DOWNLOAD,
    CONFLICT,
    ERROR,
    DISABLED
}

@Parcelize
data class ConflictResolution(
    val localModified: Long,
    val cloudModified: Long,
    val resolution: ResolutionType,
    val resolvedTime: Long?,
    val backupCreated: Boolean
) : Parcelable

enum class ResolutionType {
    KEEP_LOCAL,
    KEEP_CLOUD,
    KEEP_BOTH,
    MANUAL_REQUIRED
}

// Export/Import

@Parcelize
data class SaveExport(
    val id: String,
    val session: GameSession,
    val exportFormat: ExportFormat,
    val exportTime: Long,
    val includeScreenshots: Boolean,
    val compressed: Boolean,
    val encrypted: Boolean,
    val passwordProtected: Boolean,
    val shareable: Boolean,
    val shareCode: String?
) : Parcelable

enum class ExportFormat {
    JSON,
    BINARY,
    COMPRESSED_JSON,
    ENCRYPTED_BINARY,
    SHARE_CODE
}

// Save Validation

@Parcelize
data class SaveValidation(
    val isValid: Boolean,
    val checksumValid: Boolean,
    val versionCompatible: Boolean,
    val dataIntact: Boolean,
    val errors: List<SaveError>,
    val warnings: List<SaveWarning>,
    val canRepair: Boolean
) : Parcelable

@Parcelize
data class SaveError(
    val code: String,
    val message: String,
    val severity: ErrorSeverity,
    val location: String?,
    val canRecover: Boolean
) : Parcelable

enum class ErrorSeverity {
    CRITICAL,   // Save cannot be loaded
    MAJOR,      // Significant data loss
    MINOR,      // Minor data issues
    WARNING     // Non-critical issues
}

@Parcelize
data class SaveWarning(
    val code: String,
    val message: String,
    val suggestion: String?
) : Parcelable

// Ironman Mode

@Parcelize
data class IronmanSession(
    val id: String,
    val session: GameSession,
    val startTime: Long,
    val checksumChain: List<String>, // Hash chain for integrity
    val lastCheckpoint: Long,
    val allowExitSave: Boolean,
    val achievementsEnabled: Boolean
) : Parcelable {
    
    /**
     * Ironman mode enforces:
     * - No manual saves (only auto-saves)
     * - Single save slot
     * - No save scumming
     * - Validates save integrity
     * - Special achievements only available in Ironman
     */
}

// Save Manager State

@Parcelize
data class SaveManagerState(
    val currentSlot: Int?,
    val loadedSession: GameSession?,
    val hasUnsavedChanges: Boolean,
    val lastAutoSave: Long,
    val lastBackup: Long,
    val storageUsed: Long,
    val storageAvailable: Long,
    val pendingOperations: List<SaveOperation>
) : Parcelable

@Parcelize
data class SaveOperation(
    val id: String,
    val type: SaveOperationType,
    val status: OperationStatus,
    val progress: Float,
    val startTime: Long,
    val error: String?
) : Parcelable

enum class SaveOperationType {
    SAVE,
    LOAD,
    AUTO_SAVE,
    QUICK_SAVE,
    EXPORT,
    IMPORT,
    CLOUD_UPLOAD,
    CLOUD_DOWNLOAD,
    DELETE,
    VALIDATE,
    REPAIR,
    BACKUP
}

enum class OperationStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    CANCELLED
}

// Save Templates (for new games)

@Parcelize
data class SaveTemplate(
    val id: String,
    val name: String,
    val description: String,
    val difficulty: Difficulty,
    val startingConditions: StartingConditions,
    val achievements: List<String>,
    val ironman: Boolean
) : Parcelable

@Parcelize
data class StartingConditions(
    val country: String?,
    val governmentType: GovernmentType?,
    val startingYear: Int,
    val startingWealth: Long,
    val startingApproval: Double,
    val customRules: List<String>
) : Parcelable

enum class Difficulty {
    EASY,
    NORMAL,
    HARD,
    VERY_HARD,
    IMPOSSIBLE,
    CUSTOM
}
