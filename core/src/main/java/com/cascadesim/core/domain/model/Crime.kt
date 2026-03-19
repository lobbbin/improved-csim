package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Crime and Justice System
 * Comprehensive criminal justice management including crime rates, police, prisons, and organized crime.
 */

@Parcelize
data class CrimeAndJusticeSystem(
    val countryId: String,
    val overallCrimeRate: Double, // per 100,000
    val safetyIndex: Double, // 0-100
    val policeForce: PoliceForce,
    val judiciary: Judiciary,
    val prisonSystem: PrisonSystem,
    val organizedCrime: OrganizedCrime,
    val drugTrade: DrugTrade,
    val humanTrafficking: HumanTrafficking,
    val cyberCrime: CyberCrime,
    val rehabilitationPrograms: List<RehabilitationProgram>,
    val deathPenalty: DeathPenalty?,
    val crimeStatistics: CrimeStatistics,
    val hotspots: List<CrimeHotspot>,
    val ongoingInvestigations: List<Investigation>,
    val majorCases: List<MajorCase>,
    val publicTrust: Double,
    val lastUpdated: Long
) : Parcelable {
    
    val justiceEffectiveness: Double
        get() = (policeForce.effectiveness * 0.3 + judiciary.independence * 0.3 + 
                prisonSystem.rehabilitationRate * 0.2 + (100 - corruption) * 0.2)
    
    val corruption: Double
        get() = (policeForce.corruption + judiciary.corruption + prisonSystem.corruption) / 3
}

// Crime Statistics

@Parcelize
data class CrimeStatistics(
    val totalCrimes: Long, // per year
    val crimesByType: Map<CrimeType, Long>,
    val crimesByRegion: Map<String, Long>,
    val clearanceRate: Double, // percentage solved
    val violentCrimeRate: Double,
    val propertyCrimeRate: Double,
    val whiteCollarCrimeRate: Double,
    val cyberCrimeRate: Double,
    val hateCrimeRate: Double,
    val organizedCrimeRate: Double,
    val juvenileCrimeRate: Double,
    val recidivismRate: Double,
    val victimizationRate: Double,
    val unreportedCrimes: Double, // estimated percentage
    val trendLastYear: Double, // percentage change
    val trendLastDecade: Double
) : Parcelable

enum class CrimeType {
    MURDER,
    MANSLAUGHTER,
    ASSAULT,
    ROBBERY,
    BURGLARY,
    THEFT,
    RAPE,
    SEXUAL_ASSAULT,
    KIDNAPPING,
    HUMAN_TRAFFICKING,
    DRUG_TRAFFICKING,
    DRUG_POSSESSION,
    FRAUD,
    EMBEZZLEMENT,
    MONEY_LAUNDERING,
    TAX_EVASION,
    BRIBERY,
    CORRUPTION,
    CYBERCRIME,
    IDENTITY_THEFT,
    ARSON,
    VANDALISM,
    ILLEGAL_GAMBLING,
    PROSTITUTION,
    ILLEGAL_IMMIGRATION,
    WEAPONS_TRAFFICKING,
    TERRORISM,
    ORGANIZED_CRIME,
    HATE_CRIME,
    DOMESTIC_VIOLENCE,
    CHILD_ABUSE,
    ELDER_ABUSE,
    ANIMAL_CRUELTY,
    ENVIRONMENTAL_CRIME,
    COUNTERFEITING,
    PIRACY
}

// Police Force

@Parcelize
data class PoliceForce(
    val totalOfficers: Int,
    val officersPerCapita: Double, // per 100,000
    val budget: Long,
    val departments: List<PoliceDepartment>,
    val equipment: PoliceEquipment,
    val training: PoliceTraining,
    val effectiveness: Double, // 0-100
    val corruption: Double, // 0-100
    val brutality: Double,
    val communityTrust: Double,
    val responseTime: Int, // average minutes
    val clearanceRate: Double,
    val diversityIndex: Double,
    val unionStrength: Double,
    val oversightMechanisms: List<OversightMechanism>,
    val controversies: List<PoliceControversy>,
    val specialUnits: List<SpecialUnit>,
    val internalAffairs: InternalAffairs?
) : Parcelable

@Parcelize
data class PoliceDepartment(
    val id: String,
    val name: String,
    val jurisdiction: String,
    val officers: Int,
    val vehicles: Int,
    val stations: Int,
    val chief: String?, // NPC ID
    val budget: Long,
    val communityPolicing: Double,
    val effectiveness: Double
) : Parcelable

@Parcelize
data class PoliceEquipment(
    val firearms: Int,
    val vehicles: Int,
    val aircraft: Int,
    val watercraft: Int,
    val surveillanceEquipment: Int,
    val bodyCameras: Int,
    val nonLethalWeapons: Int,
    val armoredVehicles: Int,
    val militaryEquipment: Boolean
) : Parcelable

@Parcelize
data class PoliceTraining(
    val academyDuration: Int, // months
    val ongoingTraining: Int, // hours per year
    val deEscalationTraining: Boolean,
    val communityPolicingTraining: Boolean,
    val humanRightsTraining: Boolean,
    val mentalHealthTraining: Boolean,
    val certificationRequired: Boolean
) : Parcelable

@Parcelize
data class OversightMechanism(
    val id: String,
    val name: String,
    val type: OversightType,
    val independence: Double,
    val casesInvestigated: Int,
    val substantiatedComplaints: Int,
    val disciplinaryActions: Int
) : Parcelable

enum class OversightType {
    INTERNAL_AFFAIRS,
    CIVILIAN_REVIEW_BOARD,
    INDEPENDENT_COMMISSION,
    INSPECTOR_GENERAL,
    OMBUDSMAN,
    COURT_OVERSIGHT,
    MEDIA_OVERSIGHT
}

@Parcelize
data class PoliceControversy(
    val id: String,
    val title: String,
    val date: Long,
    val description: String,
    val type: ControversyType,
    val casualties: Int,
    val legalOutcome: String?,
    val publicReaction: Double,
    val reforms: List<String>
) : Parcelable

enum class ControversyType {
    EXCESSIVE_FORCE,
    WRONGFUL_DEATH,
    RACIAL_PROFILING,
    CORRUPTION,
    PLANTED_EVIDENCE,
    COERCED_CONFESSION,
    COVERUP,
    SEXUAL_MISCONDUCT,
    DOMESTIC_VIOLENCE,
    ILLEGAL_SURVEILLANCE
}

@Parcelize
data class SpecialUnit(
    val id: String,
    val name: String,
    val type: SpecialUnitType,
    val personnel: Int,
    val budget: Long,
    val effectiveness: Double,
    val controversies: Int
) : Parcelable

enum class SpecialUnitType {
    NARCOTICS,
    ANTI_TERRORISM,
    CYBER_CRIME,
    ORGANIZED_CRIME,
    HOMICIDE,
    VICE,
    SWAT,
    K9,
    TRAFFIC,
    INTELLIGENCE,
    GANG_UNIT,
    HATE_CRIMES,
    HUMAN_TRAFFICKING,
    FINANCIAL_CRIMES
}

@Parcelize
data class InternalAffairs(
    val id: String,
    val name: String,
    val investigators: Int,
    val casesPerYear: Int,
    val substantiatedRate: Double,
    val independence: Double
) : Parcelable

// Judiciary

@Parcelize
data class Judiciary(
    val independence: Double, // 0-100
    val corruption: Double,
    val efficiency: Double,
    val courts: List<Court>,
    val judges: Int,
    val appointments: AppointmentProcess,
    val casesPending: Long,
    val casesPerYear: Long,
    val averageCaseLength: Int, // days
    val backlog: Double,
    val legalAid: LegalAid,
    val barAssociation: BarAssociation?,
    val legalSystem: LegalSystem,
    val sentencingGuidelines: SentencingGuidelines,
    val appealsProcess: AppealsProcess
) : Parcelable

@Parcelize
data class Court(
    val id: String,
    val name: String,
    val type: CourtType,
    val location: String,
    val judges: Int,
    val jurisdiction: String,
    val casesPerYear: Long,
    val backlog: Long
) : Parcelable

enum class CourtType {
    SUPREME_COURT,
    APPELLATE_COURT,
    DISTRICT_COURT,
    TRIAL_COURT,
    FAMILY_COURT,
    JUVENILE_COURT,
    TAX_COURT,
    BANKRUPTCY_COURT,
    MILITARY_COURT,
    RELIGIOUS_COURT,
    ADMINISTRATIVE_COURT,
    CONSTITUTIONAL_COURT,
    LABOR_COURT,
    ENVIRONMENTAL_COURT,
    INTERNATIONAL_COURT
}

@Parcelize
data class AppointmentProcess(
    val type: AppointmentType,
    val tenure: Int, // years, -1 for life
    val confirmationRequired: Boolean,
    val politicalInfluence: Double,
    val diversityRequirement: Boolean
) : Parcelable

enum class AppointmentType {
    ELECTED,
    APPOINTED_BY_EXECUTIVE,
    APPOINTED_BY_LEGISLATURE,
    APPOINTED_BY_JUDICIARY,
    MERIT_SELECTION,
    MIXED_SYSTEM
}

@Parcelize
data class LegalAid(
    val budget: Long,
    val lawyers: Int,
    val casesHandled: Long,
    val eligibilityThreshold: Long, // income
    val quality: Double
) : Parcelable

@Parcelize
data class BarAssociation(
    val id: String,
    val name: String,
    val members: Int,
    val licensingAuthority: Boolean,
    val disciplinaryPower: Boolean,
    val politicalInfluence: Double
) : Parcelable

@Parcelize
data class LegalSystem(
    val type: LegalSystemType,
    val commonLawElements: Boolean,
    val civilLawElements: Boolean,
    val religiousLawElements: Boolean,
    val customaryLawElements: Boolean
) : Parcelable

enum class LegalSystemType {
    COMMON_LAW,
    CIVIL_LAW,
    RELIGIOUS_LAW,
    CUSTOMARY_LAW,
    MIXED_SYSTEM,
    SOCIALIST_LAW,
    TRIBAL_LAW
}

@Parcelize
data class SentencingGuidelines(
    val mandatory: Boolean,
    val discretion: Double,
    val minimumSentences: Map<CrimeType, Int>, // months
    val maximumSentences: Map<CrimeType, Int>,
    val deathPenalty: Boolean,
    val lifeImprisonment: Boolean,
    val paroleEligibility: Map<CrimeType, Double> // percentage of sentence
) : Parcelable

@Parcelize
data class AppealsProcess(
    val levels: Int,
    val timeLimit: Int, // days to file
    val grounds: List<String>,
    val successRate: Double,
    val averageDuration: Int // days
) : Parcelable

// Prison System

@Parcelize
data class PrisonSystem(
    val totalFacilities: Int,
    val totalCapacity: Int,
    val totalInmates: Int,
    val occupancyRate: Double,
    val privatePrisons: Int,
    val budget: Long,
    val costPerInmate: Long,
    val staff: Int,
    val rehabilitationRate: Double,
    val recidivismRate: Double,
    val corruption: Double,
    val violence: Double,
    val facilities: List<Prison>,
    val conditions: PrisonConditions,
    val programs: List<PrisonProgram>,
    val inmateDemographics: InmateDemographics,
    val pretrialDetainees: Int,
    val politicalPrisoners: Int,
    val deathRowInmates: Int,
    val annualExecutions: Int,
    val escapeRate: Double
) : Parcelable

@Parcelize
data class Prison(
    val id: String,
    val name: String,
    val type: PrisonType,
    val location: String,
    val capacity: Int,
    val currentInmates: Int,
    val securityLevel: SecurityLevel,
    val staff: Int,
    val warden: String?, // NPC ID
    val budget: Long,
    val overcrowding: Double,
    val violence: Double,
    val contraband: Double,
    val rehabilitationPrograms: List<String>,
    val privateOperator: String?,
    val controversies: List<String>
) : Parcelable

enum class PrisonType {
    MAXIMUM_SECURITY,
    MEDIUM_SECURITY,
    MINIMUM_SECURITY,
    SUPERMAX,
    FEDERAL,
    STATE,
    COUNTY,
    JUVENILE,
    WOMENS,
    MILITARY,
    IMMIGRATION_DETENTION,
    PSYCHIATRIC
}

enum class SecurityLevel {
    MINIMUM,
    LOW,
    MEDIUM,
    HIGH,
    MAXIMUM,
    SUPERMAX
}

@Parcelize
data class PrisonConditions(
    val overcrowding: Double,
    val sanitation: Double,
    val healthcare: Double,
    val nutrition: Double,
    val violence: Double,
    val gangs: Double,
    val drugUse: Double,
    val rehabilitation: Double,
    val familyContact: Double,
    val legalAccess: Double,
    val humanRightsViolations: List<String>
) : Parcelable

@Parcelize
data class PrisonProgram(
    val id: String,
    val name: String,
    val type: ProgramType,
    val participants: Int,
    val successRate: Double,
    val budget: Long
) : Parcelable

enum class ProgramType {
    EDUCATION,
    VOCATIONAL_TRAINING,
    DRUG_REHABILITATION,
    MENTAL_HEALTH,
    ANGER_MANAGEMENT,
    RELIGIOUS,
    WORK_PROGRAM,
    ARTS_PROGRAM,
    COLLEGE_COURSES,
    LIFE_SKILLS
}

@Parcelize
data class InmateDemographics(
    val total: Int,
    val byGender: Map<String, Int>,
    val byAge: Map<String, Int>,
    val byRace: Map<String, Int>,
    val byCrime: Map<CrimeType, Int>,
    val bySentence: Map<String, Int>, // sentence length ranges
    val foreignNationals: Int
) : Parcelable

// Organized Crime

@Parcelize
data class OrganizedCrime(
    val overallThreat: Double, // 0-1
    val syndicates: List<CriminalSyndicate>,
    val gangs: List<Gang>,
    val influence: CrimeInfluence,
    val annualRevenue: Long,
    val internationalConnections: List<String>,
    val lawEnforcementProgress: Double,
    val moneyLaunderingScale: Long,
    val corruptionReach: Double
) : Parcelable

@Parcelize
data class CriminalSyndicate(
    val id: String,
    val name: String,
    val type: SyndicateType,
    val leader: String?, // NPC ID
    val members: Int,
    val revenue: Long,
    val territories: List<String>,
    val activities: List<CriminalActivity>,
    val alliances: List<String>,
    val rivals: List<String>,
    val lawEnforcementStatus: LawEnforcementStatus,
    val history: String,
    val notoriety: Int // 0-100
) : Parcelable

enum class SyndicateType {
    MAFIA,
    CARTEL,
    TRIAD,
    YAKUZA,
    VORY,
    MOTORCYCLE_GANG,
    PRISON_GANG,
    DRUG_CARTEL,
    HUMAN_TRAFFICKING_NETWORK,
    CYBER_CRIME_RING,
    TERRORISM_FINANCING,
    ARMS_TRAFFICKING,
    WILDLIFE_TRAFFICKING,
    ORGAN_TRAFFICKING
}

@Parcelize
data class CriminalActivity(
    val id: String,
    val type: String,
    val revenue: Long,
    val scale: String,
    val victims: Long
) : Parcelable

enum class LawEnforcementStatus {
    UNKNOWN_LOCATION,
    UNDER_INVESTIGATION,
    UNDER_SURVEILLANCE,
    INDICTED,
    WANTED,
    CAPTURED,
    IMPRISONED,
    DEPORTED,
    DECEASED
}

@Parcelize
data class Gang(
    val id: String,
    val name: String,
    val type: GangType,
    val members: Int,
    val territory: String,
    val leader: String?,
    val activities: List<String>,
    val violence: Double,
    val lawEnforcementStatus: String
) : Parcelable

enum class GangType {
    STREET_GANG,
    PRISON_GANG,
    OUTLAW_MOTORCYCLE,
    DRUG_GANG,
    TERRITORIAL_GANG,
    YOUTH_GANG,
    ETHNIC_GANG,
    NEO_NAZI,
    WHITE_SUPREMACIST
}

@Parcelize
data class CrimeInfluence(
    val politicalCorruption: Double,
    val lawEnforcementInfiltration: Double,
    val judiciaryInfiltration: Double,
    val businessInfiltration: Double,
    val mediaControl: Double,
    val communityControl: Double
) : Parcelable

// Drug Trade

@Parcelize
data class DrugTrade(
    val scale: Long, // USD annual value
    val production: DrugProduction,
    val trafficking: DrugTrafficking,
    val consumption: DrugConsumption,
    val drugPolicy: DrugPolicy,
    val enforcementEfforts: List<EnforcementEffort>,
    val drugRelatedDeaths: Int,
    val addictionRate: Double,
    val treatmentAvailability: Double
) : Parcelable

@Parcelize
data class DrugProduction(
    val domesticCultivation: Map<String, Int>, // drug -> hectares
    val domesticManufacturing: Map<String, Long>, // drug -> kg
    val precursorProduction: List<String>,
    val exportValue: Long
) : Parcelable

@Parcelize
data class DrugTrafficking(
    val transitRoutes: List<TraffickingRoute>,
    val borderSeizures: Map<String, Long>, // drug -> kg
    val domesticSeizures: Map<String, Long>,
    val arrestRate: Double,
    val majorOperations: List<String>
) : Parcelable

@Parcelize
data class TraffickingRoute(
    val id: String,
    val origin: String,
    val destination: String,
    val drugs: List<String>,
    val volume: Long,
    val status: String
) : Parcelable

@Parcelize
data class DrugConsumption(
    val usageRates: Map<String, Double>, // drug -> percentage of population
    val addictionRates: Map<String, Double>,
    val treatmentSeeking: Map<String, Int>,
    val overdoseDeaths: Map<String, Int>
) : Parcelable

@Parcelize
data class DrugPolicy(
    val approach: DrugPolicyApproach,
    val decriminalizedDrugs: List<String>,
    val legalizedDrugs: List<String>,
    val mandatoryMinimums: Boolean,
    val harmReduction: Boolean,
    val treatmentOverIncarceration: Boolean
) : Parcelable

enum class DrugPolicyApproach {
    PROHIBITION,
    DECRIMINALIZATION,
    LEGALIZATION,
    HARM_REDUCTION,
    TREATMENT_FOCUSED,
    WAR_ON_DRUGS,
    PORTUGAL_MODEL
}

@Parcelize
data class EnforcementEffort(
    val id: String,
    val name: String,
    val budget: Long,
    val personnel: Int,
    val arrests: Int,
    val seizures: Long,
    val convictions: Int,
    val impact: Double
) : Parcelable

// Human Trafficking

@Parcelize
data class HumanTrafficking(
    val prevalence: Double, // 0-1
    val victims: Long,
    val traffickingTypes: Map<TraffickingType, Long>,
    val originCountries: List<String>,
    val destinationCountries: List<String>,
    val networks: List<TraffickingNetwork>,
    val rescueOperations: Int,
    val convictions: Int,
    val victimServices: VictimServices,
    val governmentRating: TraffickingRating
) : Parcelable

enum class TraffickingType {
    SEX_TRAFFICKING,
    FORCED_LABOR,
    DOMESTIC_SERVITUDE,
    CHILD_SOLDIERS,
    ORGAN_TRAFFICKING,
    CHILD_BEGGING,
    FORCED_MARRIAGE,
    FORCED_CRIMINALITY
}

@Parcelize
data class TraffickingNetwork(
    val id: String,
    val name: String,
    val leaders: List<String>,
    val members: Int,
    val routes: List<String>,
    val victimsTrafficked: Long,
    val status: String
) : Parcelable

@Parcelize
data class VictimServices(
    val shelters: Int,
    val capacity: Int,
    val funding: Long,
    val victimsServed: Long,
    val rehabilitationPrograms: List<String>
) : Parcelable

enum class TraffickingRating {
    TIER_1, // Fully compliant
    TIER_2, // Making efforts
    TIER_2_WATCH_LIST, // Needs improvement
    TIER_3 // Non-compliant
}

// Cyber Crime

@Parcelize
data class CyberCrime(
    val threatLevel: Double,
    val incidentsPerYear: Long,
    val economicImpact: Long,
    val crimeTypes: Map<CyberCrimeType, Long>,
    val domesticActors: Int,
    val foreignActors: List<String>,
    val capabilities: CyberCapabilities,
    val defenses: CyberDefenses,
    val cyberUnit: CyberCrimeUnit?,
    val internationalCooperation: List<String>
) : Parcelable

enum class CyberCrimeType {
    IDENTITY_THEFT,
    FINANCIAL_FRAUD,
    RANSOMWARE,
    DATA_BREACH,
    PHISHING,
    MALWARE,
    DDOS_ATTACK,
    CYBER_ESPIONAGE,
    CYBER_TERRORISM,
    CHILD_EXPLOITATION,
    DARK_WEB_MARKETS,
    CRYPTO_FRAUD,
    BUSINESS_EMAIL_COMPROMISE
}

@Parcelize
data class CyberCapabilities(
    val hackingSkills: Double,
    val infrastructure: Double,
    val funding: Long,
    val personnel: Int
) : Parcelable

@Parcelize
data class CyberDefenses(
    val cybersecurityBudget: Long,
    val personnel: Int,
    val incidents: Long,
    val responseTime: Int, // hours
    val training: Double,
    val legislation: Boolean
) : Parcelable

@Parcelize
data class CyberCrimeUnit(
    val id: String,
    val name: String,
    val personnel: Int,
    val budget: Long,
    val casesSolved: Int,
    val internationalCases: Int
) : Parcelable

// Rehabilitation

@Parcelize
data class RehabilitationProgram(
    val id: String,
    val name: String,
    val type: RehabilitationType,
    val budget: Long,
    val participants: Int,
    val successRate: Double,
    val waitlist: Int,
    val duration: Int // months
) : Parcelable

enum class RehabilitationType {
    DRUG_TREATMENT,
    VOCATIONAL_TRAINING,
    EDUCATION,
    COUNSELING,
    JOB_PLACEMENT,
    HOUSING_ASSISTANCE,
    MENTORSHIP,
    RESTORATIVE_JUSTICE
}

// Death Penalty

@Parcelize
data class DeathPenalty(
    val legal: Boolean,
    val methods: List<String>,
    val eligibleCrimes: List<CrimeType>,
    val currentInmates: Int,
    val executionsThisYear: Int,
    val totalExecutions: Int,
    val exonerations: Int,
    val publicSupport: Double,
    val internationalPressure: Double,
    val moratorium: Boolean
) : Parcelable

// Crime Hotspot

@Parcelize
data class CrimeHotspot(
    val id: String,
    val location: String,
    val primaryCrimes: List<CrimeType>,
    val rate: Double,
    val policePresence: Double,
    val socioeconomic: Double,
    val interventions: List<String>
) : Parcelable

// Investigation

@Parcelize
data class Investigation(
    val id: String,
    val title: String,
    val type: CrimeType,
    val startDate: Long,
    val status: InvestigationStatus,
    val detectives: Int,
    val suspects: List<String>,
    val evidence: List<String>,
    val budget: Long
) : Parcelable

enum class InvestigationStatus {
    ONGOING,
    SUSPENDED,
    CLOSED_UNSOLVED,
    ARRESTS_MADE,
    PROSECUTION,
    CONVICTION,
    ACQUITTAL
}

// Major Case

@Parcelize
data class MajorCase(
    val id: String,
    val title: String,
    val description: String,
    val date: Long,
    val type: CrimeType,
    val victims: Int,
    val perpetrator: String?,
    val status: CaseStatus,
    val mediaCoverage: Double,
    val socialImpact: Double,
    val legalOutcome: String?
) : Parcelable

enum class CaseStatus {
    INVESTIGATION,
    TRIAL,
    APPEAL,
    CONVICTED,
    ACQUITTED,
    UNSOLVED,
    COLD_CASE
}
