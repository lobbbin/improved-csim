package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents diplomatic relations and treaties.
 */
@Parcelize
data class DiplomaticRelation(
    val id: String,
    val countryA: String, // countryId
    val countryB: String, // countryId
    val status: RelationStatus,
    val relationScore: Int, // -100 to 100
    val formalAlliance: Boolean,
    val allianceType: AllianceType?,
    val treaties: List<String>, // treatyIds
    val embassies: EmbassyStatus,
    val tradeVolume: Long, // annual trade in USD
    val travelAdvisory: TravelAdvisory?,
    val sanctions: List<Sanction>,
    val historicalEvents: List<DiplomaticEvent>,
    val currentIssues: List<DiplomaticIssue>,
    val lastUpdated: Long
) : Parcelable {
    
    val isPositive: Boolean
        get() = relationScore > 25
    
    val isNegative: Boolean
        get() = relationScore < -25
    
    val isNeutral: Boolean
        get() = relationScore in -25..25
    
    val hasFullRelations: Boolean
        get() = status == RelationStatus.FULL_DIPLOMATIC && embassies.mutual
}

enum class RelationStatus {
    NO_RELATIONS,
    HOSTILE,
    STRAINED,
    COLD,
    NEUTRAL,
    CORDIAL,
    FRIENDLY,
    ALLIED,
    FULL_DIPLOMATIC
}

enum class AllianceType {
    DEFENSE_PACT,
    NON_AGGRESSION_PACT,
    MUTUAL_DEFENSE,
    STRATEGIC_PARTNERSHIP,
    CUSTOMS_UNION,
    FREE_TRADE_AREA,
    COMMON_MARKET,
    POLITICAL_UNION
}

@Parcelize
data class EmbassyStatus(
    val hasEmbassyInA: Boolean, // B has embassy in A
    val hasEmbassyInB: Boolean, // A has embassy in B
    val mutual: Boolean,
    val ambassadorA: String?, // npcId
    val ambassadorB: String?,
    val staffLevelA: Int,
    val staffLevelB: Int
) : Parcelable

enum class TravelAdvisory {
    NO_RESTRICTIONS,
    EXERCISE_CAUTION,
    AVOID_NONESSENTIAL_TRAVEL,
    AVOID_ALL_TRAVEL,
    TRAVEL_BAN
}

@Parcelize
data class Sanction(
    val id: String,
    val imposedBy: String, // countryId
    val targetCountry: String, // countryId
    val type: SanctionType,
    val description: String,
    val imposedDate: Long,
    val expiryDate: Long?,
    val conditions: List<String>,
    val impact: Double // 0.0 to 1.0
) : Parcelable

enum class SanctionType {
    ECONOMIC,
    ARMS_EMBARGO,
    TRAVEL_BAN,
    ASSET_FREEZE,
    TRADE_RESTRICTION,
    FINANCIAL,
    DIPLOMATIC
}

@Parcelize
data class DiplomaticEvent(
    val id: String,
    val type: DiplomaticEventType,
    val description: String,
    val date: Long,
    val impact: Int // relation change
) : Parcelable

enum class DiplomaticEventType {
    TREATY_SIGNED,
    TREATY_BROKEN,
    SUMMIT,
    STATE_VISIT,
    INCIDENT,
    SANCTION_IMPOSED,
    SANCTION_LIFTED,
    WAR_DECLARED,
    PEACE_SIGNED,
    RECOGNITION_GRANTED,
    RECOGNITION_WITHDRAWN,
    AMBASSADOR_EXPELLED
}

@Parcelize
data class DiplomaticIssue(
    val id: String,
    val title: String,
    val description: String,
    val severity: IssueSeverity,
    val raisedDate: Long,
    val status: IssueStatus
) : Parcelable

enum class IssueSeverity {
    MINOR,
    MODERATE,
    MAJOR,
    CRITICAL
}

enum class IssueStatus {
    ACTIVE,
    NEGOTIATING,
    RESOLVED,
    ESCALATED
}

/**
 * Represents a treaty between countries.
 */
@Parcelize
data class Treaty(
    val id: String,
    val name: String,
    val type: TreatyType,
    val signatories: List<String>, // countryIds
    val description: String,
    val terms: List<TreatyTerm>,
    val signedDate: Long,
    val ratifiedBy: Map<String, Boolean>, // countryId -> ratified
    val effectiveDate: Long?,
    val expiryDate: Long?,
    val violations: List<TreatyViolation>,
    val isActive: Boolean,
    val canWithdraw: Boolean,
    val withdrawalNotice: Long? // required notice period in days
) : Parcelable

enum class TreatyType {
    BILATERAL_TRADE,
    MULTILATERAL_TRADE,
    DEFENSE_ALLIANCE,
    NON_AGGRESSION,
    PEACE,
    BOUNDARY,
    ENVIRONMENTAL,
    HUMAN_RIGHTS,
    ARMS_CONTROL,
    EXTRADITION,
    CULTURAL_EXCHANGE,
    SCIENTIFIC_COOPERATION
}

@Parcelize
data class TreatyTerm(
    val id: String,
    val article: String,
    val text: String,
    val obligations: List<String>,
    val verificationMethod: String
) : Parcelable

@Parcelize
data class TreatyViolation(
    val id: String,
    val violator: String, // countryId
    val termId: String,
    val description: String,
    val date: Long,
    val resolved: Boolean,
    val resolutionDate: Long?
) : Parcelable

/**
 * Represents an international organization.
 */
@Parcelize
data class InternationalOrganization(
    val id: String,
    val name: String,
    val shortName: String,
    val type: OrganizationType,
    val headquarters: String,
    val foundingDate: Long,
    val members: List<String>, // countryIds
    val observers: List<String>,
    val leadership: OrganizationLeadership,
    val budget: Long,
    val activeResolutions: List<Resolution>,
    val currentAgenda: List<String>
) : Parcelable

enum class OrganizationType {
    GLOBAL,
    REGIONAL,
    ECONOMIC,
    MILITARY,
    POLITICAL,
    TECHNICAL
}

@Parcelize
data class OrganizationLeadership(
    val secretaryGeneral: String?, // npcId
    val presidency: String?, // countryId holding presidency
    val presidencyTermEnd: Long
) : Parcelable

@Parcelize
data class Resolution(
    val id: String,
    val number: String,
    val title: String,
    val text: String,
    val proposedBy: String, // countryId
    val votes: Map<String, VoteType>, // countryId -> vote
    val passed: Boolean,
    val passedDate: Long?,
    val binding: Boolean
) : Parcelable
