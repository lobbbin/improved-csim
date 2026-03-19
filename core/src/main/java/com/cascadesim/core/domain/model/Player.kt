package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents the player's state in the game.
 */
@Parcelize
data class PlayerState(
    val id: String,
    val name: String,
    val country: String, // countryId
    val currentMode: GameMode,
    val governmentType: GovernmentType,
    val position: PlayerPosition,
    val politicalCapital: Int,
    val approvalRating: Double,
    val personalWealth: Long,
    val party: String?, // partyId
    val coalitionPartners: List<String>, // partyIds
    val opposition: List<String>, // partyIds
    val achievements: List<PlayerAchievement>,
    val reputation: Map<ReputationType, Int>,
    val traits: List<PlayerTrait>,
    val decisionsHistory: List<DecisionRecord>,
    val currentTermStart: Long,
    val termLength: Long, // milliseconds
    val electionCycle: Long, // milliseconds between elections
    val vetoes: Int,
    val executiveOrders: Int,
    val lawsPassed: Int,
    val treatiesSigned: Int,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable {
    
    val termProgress: Float
        get() = if (termLength > 0) {
            ((System.currentTimeMillis() - currentTermStart).toFloat() / termLength).coerceIn(0f, 1f)
        } else 0f
    
    val daysRemainingInTerm: Long
        get() = ((currentTermStart + termLength) - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
    
    val isElectionApproaching: Boolean
        get() = daysRemainingInTerm < 90
    
    val overallReputation: Int
        get() = if (reputation.isNotEmpty()) reputation.values.average().toInt() else 50
}

enum class GameMode {
    EXECUTIVE, // Player is in power, running the government
    OPPOSITION, // Player lost election, leading opposition
    CAMPAIGN, // Player is actively campaigning for election
    COALITION_PARTNER, // Player's party is junior coalition member
    TRANSITION, // Brief period between election and taking office
    EXILE // Player was overthrown, in exile
}

@Parcelize
data class PlayerPosition(
    val title: String,
    val power: Int, // 0-100
    val responsibilities: List<String>,
    val limitations: List<String>,
    val canAppoint: List<String>, // positions player can appoint
    val canVeto: Boolean,
    val canPardon: Boolean,
    val canDeclareWar: Boolean,
    val canDissolveParliament: Boolean
) : Parcelable

@Parcelize
data class PlayerAchievement(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val unlockedAt: Long,
    val rarity: AchievementRarity
) : Parcelable

enum class AchievementRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    LEGENDARY
}

enum class ReputationType {
    DOMESTIC,
    INTERNATIONAL,
    ECONOMIC,
    MILITARY,
    DIPLOMATIC,
    HUMAN_RIGHTS,
    ENVIRONMENTAL,
    RELIGIOUS
}

@Parcelize
data class PlayerTrait(
    val name: String,
    val description: String,
    val effects: List<TraitEffect>,
    val isPositive: Boolean
) : Parcelable

@Parcelize
data class TraitEffect(
    val targetType: String,
    val modifier: Double
) : Parcelable

@Parcelize
data class DecisionRecord(
    val decisionId: String,
    val eventId: String?,
    val choice: String, // decision text
    val timestamp: Long,
    val consequences: List<String>,
    val publicReaction: ReactionType
) : Parcelable

enum class ReactionType {
    OVERWHELMINGLY_POSITIVE,
    POSITIVE,
    MIXED_POSITIVE,
    NEUTRAL,
    MIXED_NEGATIVE,
    NEGATIVE,
    OVERWHELMINGLY_NEGATIVE,
    CONTROVERSIAL,
    IGNORED
}

/**
 * Represents a complete game session with save/load capability.
 */
@Parcelize
data class GameSession(
    val id: String,
    val name: String,
    val playerState: PlayerState,
    val worldState: WorldState,
    val gameDate: Long, // in-game timestamp
    val realTimeStart: Long,
    val totalPlayTime: Long, // milliseconds
    val version: Int,
    val isAutoSave: Boolean,
    val thumbnailPath: String?
) : Parcelable

/**
 * Represents the state of the world in the game.
 */
@Parcelize
data class WorldState(
    val countries: List<Country>,
    val activeNPCs: List<NPC>,
    val activeEvents: List<GameEvent>,
    val pendingTasks: List<Task>,
    val globalTensions: Map<String, Int>, // regionId -> tension level
    val economicConditions: EconomicConditions,
    val activeTreaties: List<String>, // treatyIds
    val currentYear: Int,
    val currentMonth: Int,
    val currentDay: Int,
    val timeSpeed: TimeSpeed
) : Parcelable

enum class TimeSpeed {
    PAUSED,
    SLOW, // 1 day = 10 seconds real time
    NORMAL, // 1 day = 5 seconds real time
    FAST, // 1 day = 2 seconds real time
    VERY_FAST // 1 day = 1 second real time
}

@Parcelize
data class EconomicConditions(
    val globalGrowthRate: Double,
    val inflationRate: Double,
    val unemploymentRate: Double,
    val oilPrice: Double,
    val tradeTension: Int, // 0-100
    val stockMarketIndex: Double,
    val cryptoValue: Double
) : Parcelable
