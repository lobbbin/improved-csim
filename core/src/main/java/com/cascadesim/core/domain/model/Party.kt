package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a political party in the game.
 */
@Parcelize
data class Party(
    val id: String,
    val name: String,
    val shortName: String,
    val color: String, // hex color
    val ideology: Ideology,
    val orientation: PoliticalOrientation,
    val foundedYear: Int,
    val country: String, // countryId
    val leader: String, // npcId
    val members: List<String>, // npcIds
    val parliamentSeats: Int,
    val totalSeats: Int,
    val pollingPercentage: Double,
    val platform: Platform,
    val internalFactions: List<Faction>,
    val scandals: List<String>,
    val achievements: List<String>,
    val isRuling: Boolean,
    val coalitionRank: Int?, // position in coalition, null if not in coalition
    val isActive: Boolean
) : Parcelable {
    
    val parliamentPercentage: Double
        get() = if (totalSeats > 0) (parliamentSeats.toDouble() / totalSeats) * 100 else 0.0
    
    val hasMajority: Boolean
        get() = parliamentPercentage > 50
    
    val canFormGovernment: Boolean
        get() = parliamentPercentage > 40 || (parliamentPercentage > 25 && coalitionRank == 1)
}

enum class Ideology {
    SOCIALIST,
    SOCIAL_DEMOCRAT,
    PROGRESSIVE,
    LIBERAL,
    CENTRIST,
    CONSERVATIVE,
    NATIONAL_CONSERVATIVE,
    LIBERTARIAN,
    NATIONALIST,
    POPULIST,
    GREEN,
    COMMUNIST,
    ISLAMIST,
    CHRISTIAN_DEMOCRAT
}

@Parcelize
data class Platform(
    val economicPolicy: EconomicPolicy,
    val socialPolicy: SocialPolicy,
    val foreignPolicy: ForeignPolicy,
    val keyIssues: List<KeyIssue>,
    val promises: List<Promise>
) : Parcelable

enum class EconomicPolicy {
    STATE_CONTROLLED,
    MIXED_ECONOMY_LEFT,
    MIXED_ECONOMY_CENTER,
    MIXED_ECONOMY_RIGHT,
    FREE_MARKET,
    DISTRIBUTIST
}

enum class SocialPolicy {
    PROGRESSIVE,
    MODERATE_PROGRESSIVE,
    CENTRIST,
    MODERATE_CONSERVATIVE,
    CONSERVATIVE,
    TRADITIONALIST
}

enum class ForeignPolicy {
    ISOLATIONIST,
    NEUTRALIST,
    ENGAGEMENT,
    INTERVENTIONIST,
    IMPERIALIST
}

@Parcelize
data class KeyIssue(
    val name: String,
    val stance: String,
    val importance: Int // 0-100
) : Parcelable

@Parcelize
data class Promise(
    val id: String,
    val title: String,
    val description: String,
    val deadline: Long?, // in-game timestamp
    val isFulfilled: Boolean,
    val progress: Float // 0.0 to 1.0
) : Parcelable

@Parcelize
data class Faction(
    val id: String,
    val name: String,
    val leader: String, // npcId
    val members: List<String>, // npcIds
    val influence: Int, // 0-100
    val ideologyDeviation: String // how they differ from main party
) : Parcelable

/**
 * Represents an election event.
 */
@Parcelize
data class Election(
    val id: String,
    val country: String, // countryId
    val type: ElectionType,
    val date: Long,
    val candidates: List<Candidate>,
    val polls: List<Poll>,
    val results: ElectionResults?,
    val campaignEvents: List<CampaignEvent>,
    val issues: List<ElectionIssue>,
    val turnout: Double?, // percentage
    val isActive: Boolean,
    val isCompleted: Boolean
) : Parcelable

enum class ElectionType {
    PRESIDENTIAL,
    PARLIAMENTARY,
    LOCAL,
    REFERENDUM,
    PRIMARY,
    RUNOFF
}

@Parcelize
data class Candidate(
    val npcId: String,
    val partyId: String?,
    val runningMate: String?, // npcId for VP
    val campaignSlogan: String,
    val campaignBudget: Long,
    val pollNumbers: List<PollNumber>,
    val endorsements: List<String>, // npcIds
    val scandals: List<String>,
    val momentum: Double // -1.0 to 1.0
) : Parcelable

@Parcelize
data class PollNumber(
    val date: Long,
    val percentage: Double,
    val sampleSize: Int,
    val marginOfError: Double
) : Parcelable

@Parcelize
data class Poll(
    val id: String,
    val date: Long,
    val pollster: String,
    val results: Map<String, Double>, // candidateId or partyId -> percentage
    val sampleSize: Int,
    val marginOfError: Double,
    val methodology: String
) : Parcelable

@Parcelize
data class ElectionResults(
    val winner: String, // candidateId or partyId
    val winnerVotes: Long,
    val totalVotes: Long,
    val runnerUp: String?,
    val runnerUpVotes: Long?,
    val electoralVotes: Map<String, Int>?, // for electoral college systems
    val seatDistribution: Map<String, Int>?, // partyId -> seats for parliamentary
    val isRunoffNeeded: Boolean,
    val certifiedAt: Long
) : Parcelable {
    
    val winnerPercentage: Double
        get() = if (totalVotes > 0) (winnerVotes.toDouble() / totalVotes) * 100 else 0.0
}

@Parcelize
data class CampaignEvent(
    val id: String,
    val candidateId: String,
    val type: CampaignEventType,
    val title: String,
    val description: String,
    val location: String,
    val date: Long,
    val attendance: Int?,
    val mediaCoverage: Int, // 0-100
    val impact: CampaignImpact
) : Parcelable

enum class CampaignEventType {
    RALLY,
    SPEECH,
    DEBATE,
    TOWN_HALL,
    FUNDRAISER,
    PRESS_CONFERENCE,
    SCANDAL,
    ENDORSEMENT,
    AD_BLITZ,
    DOOR_TO_DOOR
}

@Parcelize
data class CampaignImpact(
    val pollChange: Double,
    val donationChange: Long,
    val mediaAttention: Int,
    val sentimentChange: Double
) : Parcelable

@Parcelize
data class ElectionIssue(
    val name: String,
    val importance: Int, // 0-100
    val candidatePositions: Map<String, String> // candidateId -> position
) : Parcelable
