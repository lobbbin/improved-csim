package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Religion and Ideology System
 * Comprehensive religious and ideological management including faiths, institutions, and conflicts.
 */

@Parcelize
data class ReligionSystem(
    val countryId: String,
    val dominantReligion: String,
    val religiousDiversity: Double, // 0-1
    val secularism: Double, // 0-1
    val religiousFreedom: Double, // 0-100
    val religions: List<ReligionDemographics>,
    val religiousInstitutions: List<ReligiousInstitution>,
    val religiousLeaders: List<ReligiousLeader>,
    val religiousLaws: ReligiousLaws,
    val religiousTensions: List<ReligiousTension>,
    val religiousConflicts: List<ReligiousConflict>,
    val cults: List<Cult>,
    val extremism: ReligiousExtremism,
    val interfaithInitiatives: List<InterfaithInitiative>,
    val stateReligion: StateReligion?,
    val lastUpdated: Long
) : Parcelable {
    
    val totalBelievers: Long
        get() = religions.sumOf { it.adherents }
    
    val religiousTensionLevel: Double
        get() = religiousTensions.sumOf { it.intensity } / religiousTensions.size.coerceAtLeast(1)
    
    val dominantPercentage: Double
        get() = religions.maxByOrNull { it.percentage }?.percentage ?: 0.0
}

@Parcelize
data class ReligionDemographics(
    val religionId: String,
    val name: String,
    val type: ReligionType,
    val adherents: Long,
    val percentage: Double,
    val growthRate: Double, // annual
    val urbanPercentage: Double,
    val ruralPercentage: Double,
    val averageAge: Double,
    val educationLevel: Double,
    val incomeLevel: Double,
    val regions: Map<String, Double>, // region -> percentage
    val sects: List<Sect>,
    val practices: List<String>,
    val conversionRate: Double
) : Parcelable

enum class ReligionType {
    ABRAHAMIC,
    DHARMIC,
    EAST_ASIAN,
    INDIGENOUS,
    NEW_RELIGIOUS_MOVEMENT,
    IRRELIGIOUS,
    SPIRITUAL_BUT_NOT_RELIGIOUS,
    SYNCRETIC,
    MYSTICAL
}

@Parcelize
data class Sect(
    val id: String,
    val name: String,
    val parentReligion: String,
    val adherents: Long,
    val percentage: Double,
    val distinctiveBeliefs: List<String>,
    val tensions: List<String>, // other sects with tensions
    val leadership: String?, // NPC ID
    val foundingDate: Long?,
    val headquarters: String?
) : Parcelable

// Religious Institutions

@Parcelize
data class ReligiousInstitution(
    val id: String,
    val name: String,
    val religion: String,
    val type: ReligiousInstitutionType,
    val established: Int,
    val headquarters: String,
    val leader: String?, // NPC ID
    val members: Long,
    val clergy: Int,
    val properties: Int,
    val wealth: Long,
    val politicalInfluence: Double,
    val socialServices: List<SocialService>,
    val educationalInstitutions: Int,
    val healthcareInstitutions: Int,
    val mediaOutlets: List<String>,
    val taxStatus: TaxStatus,
    val scandals: List<String>,
    val diplomaticRelations: List<String>, // countries with diplomatic ties
    val internationalAffiliations: List<String>
) : Parcelable

enum class ReligiousInstitutionType {
    CHURCH,
    MOSQUE,
    TEMPLE,
    SYNAGOGUE,
    GURDWARA,
    SHRINE,
    MONASTERY,
    SEMINARY,
    RELIGIOUS_ORDER,
    RELIGIOUS_PARTY,
    RELIGIOUS_CHARITY,
    MISSIONARY_ORGANIZATION,
    RELIGIOUS_COUNCIL,
    THEOLOGICAL_SCHOOL
}

@Parcelize
data class SocialService(
    val id: String,
    val type: String,
    val beneficiaries: Long,
    val budget: Long
) : Parcelable

enum class TaxStatus {
    TAX_EXEMPT,
    TAXED,
    PARTIAL_EXEMPTION,
    GOVERNMENT_SUBSIDIZED,
    SPECIAL_STATUS
}

// Religious Leaders

@Parcelize
data class ReligiousLeader(
    val id: String, // NPC ID
    val name: String,
    val title: String,
    val religion: String,
    val institution: String?,
    val influence: Int, // 0-100
    val followers: Long,
    val politicalAlignment: PoliticalOrientation?,
    val teachings: List<String>,
    val controversies: List<String>,
    val age: Int,
    val inExile: Boolean,
    val imprisoned: Boolean
) : Parcelable

// Religious Laws

@Parcelize
data class ReligiousLaws(
    val establishment: EstablishmentType,
    val blasphemyLaws: Boolean,
    val apostasyLaws: Boolean,
    val proselytizingRestrictions: List<String>,
    val religiousCourts: Boolean,
    val religiousCourtsJurisdiction: List<String>,
    val religiousEducationInSchools: ReligiousEducationPolicy,
    val religiousHolidays: List<ReligiousHoliday>,
    val dietaryLaws: List<String>,
    val dressCodes: List<String>,
    val marriageLaws: ReligiousMarriageLaws,
    val burialLaws: List<String>,
    val conversionLaws: ConversionLawType,
    val interfaithMarriage: InterfaithMarriagePolicy,
    val buildingRestrictions: List<String>
) : Parcelable

enum class EstablishmentType {
    STATE_RELIGION,
    PREFERRED_RELIGION,
    SEPARATION_OF_CHURCH_AND_STATE,
    SECULAR_STATE,
    THEOCRACY,
    MULTIPLE_STATE_RELIGIONS,
    NO_POLICY
}

enum class ReligiousEducationPolicy {
    NONE,
    OPTIONAL,
    COMPULSORY,
    PARENTAL_CHOICE,
    STATE_CONTROLLED,
    RELIGIOUS_SCHOOLS_ONLY
}

@Parcelize
data class ReligiousHoliday(
    val id: String,
    val name: String,
    val religion: String,
    val date: String, // flexible format
    val isNationalHoliday: Boolean,
    val duration: Int, // days
    val restrictions: List<String>
) : Parcelable

@Parcelize
data class ReligiousMarriageLaws(
    val religiousOfficiants: Boolean,
    val civilRegistrationRequired: Boolean,
    val polygamyAllowed: Boolean,
    val divorceAllowed: Boolean,
    val minimumAge: Int,
    val consentRequired: Boolean
) : Parcelable

enum class ConversionLawType {
    FREE,
    RESTRICTED,
    PROHIBITED,
    PUNISHABLE,
    REQUIRE_PERMISSION,
    DISCOURAGED
}

enum class InterfaithMarriagePolicy {
    ALLOWED,
    RESTRICTED,
    PROHIBITED,
    REQUIRES_CONVERSION,
    CIVIL_ONLY,
    NO_POLICY
}

// Religious Tensions

@Parcelize
data class ReligiousTension(
    val id: String,
    val religion1: String,
    val religion2: String,
    val intensity: Double, // 0-1
    val causes: List<String>,
    val affectedRegions: List<String>,
    val historicalOrigins: String?,
    val recentIncidents: Int,
    val mediationEfforts: List<String>,
    val potentialForViolence: Double
) : Parcelable

// Religious Conflicts

@Parcelize
data class ReligiousConflict(
    val id: String,
    val title: String,
    val startDate: Long,
    val endDate: Long?,
    val religions: List<String>,
    val type: ConflictType,
    val description: String,
    val casualties: Int,
    val displaced: Long,
    val economicImpact: Long,
    val internationalInvolvement: List<String>,
    val status: ConflictStatus,
    val resolutionEfforts: List<ResolutionEffort>
) : Parcelable

enum class ConflictType {
    INTERFAITH_VIOLENCE,
    SECTARIAN_VIOLENCE,
    STATE_REPRESSION,
    INSURGENCY,
    TERRORISM,
    DISCRIMINATION,
    PERSECUTION,
    POGROM,
    HOLY_WAR,
    CIVIL_WAR
}

enum class ConflictStatus {
    ESCALATING,
    ONGOING,
    STALEMATE,
    DE_ESCALATING,
    RESOLVED,
    FROZEN,
    RECURRING
}

@Parcelize
data class ResolutionEffort(
    val id: String,
    val date: Long,
    val mediator: String,
    val type: String,
    val outcome: String?
) : Parcelable

// Cults and Sects

@Parcelize
data class Cult(
    val id: String,
    val name: String,
    val leader: String?, // NPC ID
    val membership: Int,
    val beliefs: List<String>,
    val practices: List<String>,
    val establishmentDate: Long?,
    val location: String,
    val wealth: Long,
    val legalStatus: CultLegalStatus,
    val allegations: List<String>,
    val investigations: List<Investigation>,
    val publicPerception: Double, // -1 to 1
    val governmentMonitoring: Boolean,
    val defections: Int, // per year
    val recruitmentMethods: List<String>
) : Parcelable

enum class CultLegalStatus {
    LEGAL_RECOGNIZED,
    LEGAL_UNRECOGNIZED,
    UNDER_INVESTIGATION,
    RESTRICTED,
    BANNED,
    UNDERGROUND
}

@Parcelize
data class Investigation(
    val id: String,
    val agency: String,
    val startDate: Long,
    val endDate: Long?,
    val findings: String?,
    val charges: List<String>
) : Parcelable

// Religious Extremism

@Parcelize
data class ReligiousExtremism(
    val overallThreat: Double, // 0-1
    val activeGroups: List<ExtremistGroup>,
    val radicalizationRate: Double, // per year
    val foreignInfluence: Double,
    val domesticFactors: List<String>,
    val counterExtremismPrograms: List<CounterExtremismProgram>,
    val deRadicalizationCenters: Int,
    val atRiskPopulation: Long,
    val foreignFighters: Int,
    val returnees: Int,
    val propagandaReach: Long
) : Parcelable

@Parcelize
data class ExtremistGroup(
    val id: String,
    val name: String,
    val ideology: String,
    val members: Int,
    val leadership: List<String>, // NPC IDs
    val foundingDate: Long?,
    val originCountry: String?,
    val activities: List<ExtremistActivity>,
    val funding: Long,
    val designation: TerrorDesignation?,
    val capabilities: List<String>,
    val targets: List<String>,
    val propaganda: PropagandaCapability
) : Parcelable

enum class TerrorDesignation {
    DOMESTIC_TERRORIST,
    INTERNATIONAL_TERRORIST,
    FOREIGN_TERRORIST_ORGANIZATION,
    SANCTIONED_ENTITY,
    NOT_DESIGNATED,
    UNDER_REVIEW
}

@Parcelize
data class ExtremistActivity(
    val id: String,
    val type: ActivityType,
    val date: Long,
    val location: String,
    val casualties: Int,
    val description: String,
    val status: String
) : Parcelable

enum class ActivityType {
    TERROR_ATTACK,
    ASSASSINATION,
    BOMBING,
    KIDNAPPING,
    RECRUITMENT,
    PROPAGANDA,
    FUNDRAISING,
    TRAINING,
    PLANNING,
    RADICALIZATION
}

@Parcelize
data class PropagandaCapability(
    val onlinePresence: Double,
    val mediaProduction: Double,
    val recruitmentReach: Long,
    val languages: List<String>,
    val platforms: List<String>
) : Parcelable

@Parcelize
data class CounterExtremismProgram(
    val id: String,
    val name: String,
    val approach: String,
    val budget: Long,
    val participants: Int,
    val successRate: Double,
    val startDate: Long
) : Parcelable

// Interfaith Initiatives

@Parcelize
data class InterfaithInitiative(
    val id: String,
    val name: String,
    val religions: List<String>,
    val startDate: Long,
    val objectives: List<String>,
    val activities: List<String>,
    val participants: Long,
    val effectiveness: Double,
    val funding: Long,
    val governmentSupport: Boolean
) : Parcelable

// State Religion

@Parcelize
data class StateReligion(
    val id: String,
    val name: String,
    val establishedDate: Long,
    val legalFramework: String,
    val privileges: List<String>,
    val obligations: List<String>,
    val governmentFunding: Long,
    val advisoryRole: Boolean,
    val headOfFaith: String?, // NPC ID
    val politicalInfluence: Double
) : Parcelable

// Religious Buildings

@Parcelize
data class ReligiousBuilding(
    val id: String,
    val name: String,
    val type: ReligiousInstitutionType,
    val religion: String,
    val location: String,
    val capacity: Int,
    val built: Int,
    val historicalSignificance: Double,
    val pilgrimageSite: Boolean,
    val annualVisitors: Long?,
    val damage: Double, // 0-1
    val protected: Boolean
) : Parcelable

// Religious Events

@Parcelize
data class ReligiousEvent(
    val id: String,
    val title: String,
    val religion: String,
    val type: ReligiousEventType,
    val date: Long,
    val location: String,
    val participants: Long,
    val description: String,
    val mediaCoverage: Double,
    val politicalSignificance: Double
) : Parcelable

enum class ReligiousEventType {
    PILGRIMAGE,
    FESTIVAL,
    CEREMONY,
    CONSECRATION,
    FUNERAL,
    WEDDING,
    ORDINATION,
    COUNCIL,
    SCHISM,
    REFORMATION,
    MIRACLE_CLAIM,
    APPARITION
}

// Faith-based Diplomacy

@Parcelize
data class FaithBasedDiplomacy(
    val religiousAmbassadors: List<ReligiousAmbassador>,
    val interfaithCouncils: List<InterfaithCouncil>,
    val religiousMediation: List<ReligiousMediation>,
    val holySiteAgreements: List<HolySiteAgreement>
) : Parcelable

@Parcelize
data class ReligiousAmbassador(
    val id: String,
    val npcId: String,
    val religion: String,
    val hostCountry: String,
    val startDate: Long,
    val influence: Double
) : Parcelable

@Parcelize
data class InterfaithCouncil(
    val id: String,
    val name: String,
    val religions: List<String>,
    val established: Int,
    val achievements: List<String>
) : Parcelable

@Parcelize
data class ReligiousMediation(
    val id: String,
    val conflict: String,
    val mediator: String,
    val date: Long,
    val outcome: String?
) : Parcelable

@Parcelize
data class HolySiteAgreement(
    val id: String,
    val site: String,
    val parties: List<String>,
    val date: Long,
    val terms: List<String>,
    val status: String
) : Parcelable
