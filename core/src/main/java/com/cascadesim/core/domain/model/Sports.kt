package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents sports and cultural systems.
 */
@Parcelize
data class SportsSystem(
    val countryId: String,
    val nationalSport: String,
    val sportsBudget: Long,
    val olympicCommittee: String, // npcId of chair
    val nationalTeams: List<NationalTeam>,
    val sportsFacilities: Int,
    val professionalLeagues: List<ProfessionalLeague>,
    val activeScandals: List<SportsScandal>,
    val upcomingEvents: List<SportsEvent>,
    val athleteDevelopment: AthleteDevelopment,
    val internationalRankings: Map<String, Int> // sport -> ranking
) : Parcelable

@Parcelize
data class NationalTeam(
    val id: String,
    val sport: String,
    val coach: String?, // npcId
    val captain: String?, // npcId
    val ranking: Int,
    val recentResults: List<MatchResult>,
    val squadSize: Int,
    val selectionCriteria: SelectionCriteria,
    val nextMatch: String?,
    val worldCupQualification: Boolean,
    val olympicQualification: Boolean
) : Parcelable

@Parcelize
data class MatchResult(
    val opponent: String, // countryId
    val date: Long,
    val homeScore: Int?,
    val awayScore: Int?,
    val outcome: MatchOutcome
) : Parcelable

enum class MatchOutcome {
    WIN,
    DRAW,
    LOSS,
    PENDING
}

@Parcelize
data class SelectionCriteria(
    val meritBased: Boolean,
    val regionalQuota: Double?, // percentage
    val ageRestriction: IntRange?,
    val citizenshipRequirement: Boolean
) : Parcelable

@Parcelize
data class ProfessionalLeague(
    val id: String,
    val sport: String,
    val name: String,
    val teams: Int,
    val viewership: Long,
    val revenue: Long,
    val corruption: Double // 0.0 to 1.0
) : Parcelable

@Parcelize
data class SportsScandal(
    val id: String,
    val type: ScandalType,
    val description: String,
    val involvedParties: List<String>, // npcIds, teamIds
    val discoveryDate: Long,
    val severity: Int, // 0-100
    val publicReaction: Double, // -1.0 to 1.0
    val resolved: Boolean,
    val resolution: String?
) : Parcelable

enum class ScandalType {
    DOPING,
    MATCH_FIXING,
    CORRUPTION,
    VIOLENCE,
    FINANCIAL_FRAUD,
    DISCRIMINATION,
    SEXUAL_MISCONDUCT
}

@Parcelize
data class SportsEvent(
    val id: String,
    val name: String,
    val type: SportsEventType,
    val sport: String?,
    val hostCity: String,
    val hostCountry: String,
    val startDate: Long,
    val endDate: Long,
    val participatingCountries: Int,
    val estimatedViewers: Long,
    val budget: Long,
    val status: EventStatus
) : Parcelable

enum class SportsEventType {
    OLYMPICS,
    WORLD_CUP,
    CONTINENTAL_CHAMPIONSHIP,
    INTERNATIONAL_TOURNAMENT,
    NATIONAL_CHAMPIONSHIP,
    EXHIBITION
}

enum class EventStatus {
    BID_SUBMITTED,
    BID_ACCEPTED,
    PREPARING,
    ONGOING,
    COMPLETED,
    CANCELLED
}

@Parcelize
data class AthleteDevelopment(
    val youthPrograms: Int,
    val trainingCenters: Int,
    val scholarships: Int,
    val annualBudget: Long,
    val successRate: Double // percentage reaching professional level
) : Parcelable

/**
 * Represents cultural institutions and heritage.
 */
@Parcelize
data class Culture(
    val countryId: String,
    val culturalBudget: Long,
    val museums: Int,
    val theaters: Int,
    val libraries: Int,
    val worldHeritageSites: Int,
    val nationalHolidays: List<NationalHoliday>,
    val culturalExports: List<CulturalExport>,
    val mediaFreedom: Double, // 0.0 to 1.0
    val pressFreedom: Double,
    val culturalDiplomacy: List<CulturalAgreement>,
    val artsEducation: Double // funding as percentage of education budget
) : Parcelable

@Parcelize
data class NationalHoliday(
    val name: String,
    val date: String, // MM-DD format
    val description: String,
    val type: HolidayType
) : Parcelable

enum class HolidayType {
    NATIONAL,
    RELIGIOUS,
    HISTORICAL,
    CULTURAL
}

@Parcelize
data class CulturalExport(
    val type: CulturalType,
    val description: String,
    val globalReach: Int, // 0-100
    val economicValue: Long
) : Parcelable

enum class CulturalType {
    MUSIC,
    FILM,
    LITERATURE,
    CUISINE,
    FASHION,
    ART,
    DIGITAL_MEDIA
}

@Parcelize
data class CulturalAgreement(
    val id: String,
    val partnerCountry: String,
    val type: String,
    val signedDate: Long,
    val activities: List<String>
) : Parcelable
