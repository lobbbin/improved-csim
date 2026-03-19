package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * COMPREHENSIVE Intelligence & Espionage System
 * Includes: Spy Networks, Covert Operations, Cyber Warfare, Surveillance,
 * Counter-Intelligence, Assassination Plots, Coup Support, Disinformation
 */

@Parcelize
data class IntelligenceSystem(
    val countryId: String,
    val intelligenceAgency: IntelligenceAgency,
    val spyNetworks: List<SpyNetwork>,
    val covertOperations: List<CovertOperation>,
    val counterIntelligence: CounterIntelligence,
    val cyberCapabilities: CyberCapabilities,
    val surveillanceProgram: SurveillanceProgram,
    val intelligenceSharing: List<IntelligenceSharing>,
    val defectors: List<Defector>,
    val doubleAgents: List<DoubleAgent>,
    val activeThreats: List<IntelligenceThreat>,
    val intelligenceBudget: Long,
    val lastUpdated: Long
) : Parcelable

// Intelligence Agency

@Parcelize
data class IntelligenceAgency(
    val id: String,
    val name: String,
    val abbreviation: String,
    val director: String?, // NPC ID
    val deputyDirector: String?,
    val founded: Int,
    val headquarters: String,
    val budget: Long,
    val personnel: Int,
    val departments: List<IntelligenceDepartment>,
    val capabilities: Map<IntelligenceCapability, Int>, // capability -> rating (1-100)
    val reputation: AgencyReputation,
    val controversies: List<String>,
    val successes: List<String>,
    val failures: List<String>
) : Parcelable

@Parcelize
data class IntelligenceDepartment(
    val id: String,
    val name: String,
    val focus: DepartmentFocus,
    val personnel: Int,
    val budget: Long,
    val director: String?,
    val clearanceLevel: Int
) : Parcelable

enum class DepartmentFocus {
    HUMAN_INTELLIGENCE, // HUMINT
    SIGNALS_INTELLIGENCE, // SIGINT
    IMAGERY_INTELLIGENCE, // IMINT
    MEASUREMENT_INTELLIGENCE, // MASINT
    OPEN_SOURCE_INTELLIGENCE, // OSINT
    CYBER_OPERATIONS,
    COUNTER_INTELLIGENCE,
    COVERT_ACTION,
    ANALYTICAL,
    TECHNICAL_SERVICES,
    LOGISTICS,
    TRAINING
}

enum class IntelligenceCapability {
    HUMINT_COLLECTION,
    SIGINT_COLLECTION,
    IMAGERY_ANALYSIS,
    CRYPTOGRAPHY,
    CYBER_OFFENSE,
    CYBER_DEFENSE,
    COVERT_ACTION,
    COUNTER_INTELLIGENCE,
    SURVEILLANCE,
    ANALYSIS,
    DISINFORMATION,
    ASSASSINATION,
    SABOTAGE,
    PROPAGANDA,
    ECONOMIC_INTELLIGENCE,
    POLITICAL_INTELLIGENCE,
    MILITARY_INTELLIGENCE,
    SCIENTIFIC_INTELLIGENCE
}

@Parcelize
data class AgencyReputation(
    val domestic: Double, // 0-1
    val international: Double,
    val amongAllies: Double,
    val amongAdversaries: Double,
    val effectiveness: Double,
    val trustworthiness: Double
) : Parcelable

// Spy Networks

@Parcelize
data class SpyNetwork(
    val id: String,
    val name: String,
    val targetCountry: String,
    val type: NetworkType,
    val status: NetworkStatus,
    val assets: List<Asset>,
    val safeHouses: List<SafeHouse>,
    val communicationMethods: List<CommunicationMethod>,
    val coverOrganizations: List<String>,
    val establishedDate: Long,
    val lastContact: Long?,
    val intelligenceCollected: Long,
    val compromisedLevel: Double, // 0-1
    val leader: String?, // Agent ID
    val budget: Long
) : Parcelable

enum class NetworkType {
    DIPLOMATIC,
    COMMERCIAL,
    JOURNALIST,
    ACADEMIC,
    CULTURAL,
    ILLEGAL, // Non-official cover
    MILITARY,
    CYBER
}

enum class NetworkStatus {
    ACTIVE,
    DORMANT,
    COMPROMISED,
    ROLLED_UP,
    SUSPENDED,
    EXFILTRATING
}

@Parcelize
data class Asset(
    val id: String,
    val codename: String,
    val realName: String?,
    val nationality: String,
    val position: String,
    val accessLevel: Int, // 1-10
    val recruitment: Recruitment,
    val handler: String?, // Agent ID
    val motivation: String,
    val payments: Long,
    val intelligenceProvided: Long,
    val reliability: Double,
    val risk: Double,
    val status: AssetStatus,
    val lastContact: Long?,
    val compromiseRisk: Double
) : Parcelable

@Parcelize
data class Recruitment(
    val method: RecruitmentMethod,
    val date: Long,
    val caseOfficer: String,
    val motivation: RecruitmentMotivation,
    val vettingComplete: Boolean
) : Parcelable

enum class RecruitmentMethod {
    MONEY,
    IDEOLOGY,
    COMPROMISE, // Kompromat
    EGO,
    COERCION,
    PATRIOTISM,
    REVENGE,
    ADVENTURE,
    BLACKMAIL
}

enum class RecruitmentMotivation {
    FINANCIAL,
    IDEOLOGICAL,
    PERSONAL_GRUDGE,
    BLACKMAIL,
    PATRIOTIC,
    EGOTISTICAL,
    FORCED,
    UNKNOWN
}

enum class AssetStatus {
    ACTIVE,
    INACTIVE,
    UNDER_INVESTIGATION,
    ARRESTED,
    EXECUTED,
    DEFECTED,
    DOUBLED,
    COMPROMISED,
    EXFILTRATED
}

@Parcelize
data class SafeHouse(
    val id: String,
    val location: String,
    val country: String,
    val type: SafeHouseType,
    val capacity: Int,
    val securityLevel: Int,
    val currentOccupants: Int,
    val lastUsed: Long?
) : Parcelable

enum class SafeHouseType {
    APARTMENT,
    HOUSE,
    COMMERCIAL,
    INDUSTRIAL,
    EMBASSY,
    CONSULATE
}

@Parcelize
data class CommunicationMethod(
    val id: String,
    val type: CommunicationType,
    val security: Int,
    val lastUsed: Long?,
    val compromised: Boolean
) : Parcelable

enum class CommunicationType {
    DEAD_DROP,
    BURST_TRANSMISSION,
    SATELLITE_PHONE,
    ONE_TIME_PAD,
    STEGANOGRAPHY,
    NUMBER_STATION,
    INTERNET,
    DIPLOMATIC_POUCH,
    HUMAN_COURIER
}

// Covert Operations

@Parcelize
data class CovertOperation(
    val id: String,
    val name: String,
    val codename: String,
    val type: CovertOperationType,
    val objective: String,
    val targetCountry: String?,
    val targetOrganization: String?,
    val status: OperationStatus,
    val authorizedBy: String?, // NPC ID
    val authorizationDate: Long,
    val planning: OperationPlanning,
    val execution: OperationExecution?,
    val outcome: OperationOutcome?,
    val involvedAgents: List<String>,
    val budget: Long,
    val plausibleDeniability: Double,
    val riskLevel: RiskLevel,
    val politicalRisk: Double,
    val legalRisk: Double
) : Parcelable

enum class CovertOperationType {
    PROPAGANDA,
    POLITICAL_ACTION,
    ECONOMIC_SABOTAGE,
    MILITARY_SUPPORT,
    REGIME_CHANGE,
    ASSASSINATION,
    KIDNAPPING,
    EXTRACTION,
    SABOTAGE,
    CYBER_ATTACK,
    DISINFORMATION,
    COUP_SUPPORT,
    INSURGENCY_SUPPORT,
    COUNTER_TERRORISM,
    NUCLEAR_PROLIFERATION,
    ARMS_TRAFFICKING,
    DRUG_TRAFFICKING,
    ELECTION_INTERFERENCE
}

enum class OperationStatus {
    PROPOSED,
    APPROVED,
    PLANNING,
    READY,
    EXECUTING,
    COMPLETED,
    FAILED,
    ABORTED,
    COMPROMISED,
    DENIED // Politically denied
}

@Parcelize
data class OperationPlanning(
    val startDate: Long?,
    val estimatedDuration: Int, // days
    val phases: List<OperationPhase>,
    val contingencies: List<String>,
    val requiredAssets: List<String>,
    val requiredEquipment: List<String>,
    val clearanceRequired: Int
) : Parcelable

@Parcelize
data class OperationPhase(
    val id: String,
    val name: String,
    val description: String,
    val duration: Int, // days
    val status: PhaseStatus,
    val tasks: List<String>
) : Parcelable

enum class PhaseStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    SKIPPED
}

@Parcelize
data class OperationExecution(
    val actualStartDate: Long,
    val actualEndDate: Long?,
    val events: List<OperationEvent>,
    val casualties: Int,
    val costs: Long,
    val intelligenceGained: Long?
) : Parcelable

@Parcelize
data class OperationEvent(
    val id: String,
    val timestamp: Long,
    val description: String,
    val type: EventType,
    val significance: Int // 1-10
) : Parcelable

@Parcelize
data class OperationOutcome(
    val success: Boolean,
    val partialSuccess: Boolean,
    val objectivesAchieved: List<String>,
    val objectivesFailed: List<String>,
    val consequences: List<String>,
    val politicalFallout: Double,
    val internationalReaction: Double,
    val mediaCoverage: Double,
    val lessonsLearned: List<String>
) : Parcelable

// Counter Intelligence

@Parcelize
data class CounterIntelligence(
    val domesticCI: DomesticCI,
    val foreignThreats: List<ForeignIntelligenceThreat>,
    val investigations: List<CIInvestigation>,
    val moleHunts: List<MoleHunt>,
    val defectors: List<Defector>,
    val doubleAgents: List<DoubleAgent>,
    val securityClearances: List<SecurityClearance>,
    val vettingQueue: List<VettingCase>
) : Parcelable

@Parcelize
data class DomesticCI(
    val personnel: Int,
    val budget: Long,
    val surveillanceOperations: Int,
    val activeInvestigations: Int,
    val arrestsThisYear: Int,
    val convictionsThisYear: Int,
    val thwartedOperations: Int
) : Parcelable

@Parcelize
data class ForeignIntelligenceThreat(
    val id: String,
    val country: String,
    val agency: String?,
    val threatLevel: Int, // 1-10
    val activities: List<String>,
    val knownOperatives: Int,
    val suspectedOperatives: Int,
    val targeting: List<String>
) : Parcelable

@Parcelize
data class CIInvestigation(
    val id: String,
    val name: String,
    val target: String, // Person or organization
    val type: InvestigationType,
    val openedDate: Long,
    val status: InvestigationStatus,
    val leadAgent: String?,
    val evidence: List<String>,
    val surveillance: Boolean,
    val wiretaps: Boolean,
    val suspects: List<String>
) : Parcelable

enum class InvestigationType {
    ESPIONAGE,
    TREASON,
    LEAK,
    SABOTAGE,
    TERRORISM,
    CORRUPTION,
    FOREIGN_INFLUENCE
}

enum class InvestigationStatus {
    PRELIMINARY,
    FULL_INVESTIGATION,
    SURVEILLANCE,
    ARREST_PENDING,
    PROSECUTION,
    CLOSED_NO_CHARGES,
    CLOSED_CONVICTION,
    CLOSED_ACQUITTAL
}

@Parcelize
data class MoleHunt(
    val id: String,
    val name: String,
    val suspectedMole: String?,
    val compromiseDescription: String,
    val startDate: Long,
    val status: MoleHuntStatus,
    val suspects: List<String>,
    val methods: List<String>,
    val evidenceFound: List<String>
) : Parcelable

enum class MoleHuntStatus {
    ONGOING,
    IDENTIFIED,
    ARRESTED,
    TURNED,
    CLEARED,
    ABANDONED
}

@Parcelize
data class Defector(
    val id: String,
    val name: String,
    val formerCountry: String,
    val formerAgency: String?,
    val formerPosition: String,
    val defectionDate: Long,
    val method: DefectionMethod,
    val value: IntelligenceValue,
    val debriefingStatus: DebriefingStatus,
    val newIdentity: String?,
    val resettlement: String?,
    val protectionLevel: Int,
    val active: Boolean
) : Parcelable

enum class DefectionMethod {
    WALK_IN,
    RECRUITED,
    EXTRACTION,
    EMBASSY_DEFECT,
    THIRD_COUNTRY
}

enum class IntelligenceValue {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

enum class DebriefingStatus {
    ONGOING,
    COMPLETED,
    DISCONTINUED,
    COMPROMISED
}

@Parcelize
data class DoubleAgent(
    val id: String,
    val codename: String,
    val realName: String,
    val originalAllegiance: String,
    val turnedBy: String, // Agency
    val turnedDate: Long,
    val handlerCountry: String,
    val handlerAgency: String?,
    val feedingDisinformation: Boolean,
    val credibility: Double,
    val lastContact: Long?,
    val status: DoubleAgentStatus
) : Parcelable

enum class DoubleAgentStatus {
    ACTIVE,
    SUSPECTED,
    BURNED,
    EXTRACTED,
    CAPTURED,
    KILLED
}

@Parcelize
data class SecurityClearance(
    val id: String,
    val holderId: String,
    val holderName: String,
    val level: ClearanceLevel,
    val grantedDate: Long,
    val expirationDate: Long?,
    val adjudicationStatus: AdjudicationStatus,
    val issues: List<String>
) : Parcelable

enum class ClearanceLevel {
    CONFIDENTIAL,
    SECRET,
    TOP_SECRET,
    TOP_SECRET_SCI,
    SPECIAL_ACCESS_PROGRAM
}

enum class AdjudicationStatus {
    PENDING,
    GRANTED,
    DENIED,
    REVOKED,
    SUSPENDED,
    UNDER_REVIEW
}

@Parcelize
data class VettingCase(
    val id: String,
    val subjectId: String,
    val subjectName: String,
    val position: String,
    val startedDate: Long,
    val status: VettingStatus,
    val issues: List<String>,
    val interviewer: String?
) : Parcelable

enum class VettingStatus {
    BACKGROUND_CHECK,
    INTERVIEWS,
    POLYGRAPH,
    ADJUDICATION,
    GRANTED,
    DENIED
}

// Cyber Capabilities

@Parcelize
data class CyberCapabilities(
    val offensiveCapabilities: CyberOffensive,
    val defensiveCapabilities: CyberDefensive,
    val cyberUnits: List<CyberUnit>,
    val malwareArsenal: List<MalwareTool>,
    val zeroDayVulnerabilities: Int,
    val botnetSize: Long,
    val targetInfrastructure: List<String>,
    val activeOperations: List<CyberOperation>
) : Parcelable

@Parcelize
data class CyberOffensive(
    val rating: Int, // 1-100
    val capabilities: List<CyberOffensiveCapability>,
    val successfulOperations: Int,
    val detectedOperations: Int
) : Parcelable

enum class CyberOffensiveCapability {
    NETWORK_INTRUSION,
    DATA_EXFILTRATION,
    SYSTEM_DISRUPTION,
    DESTRUCTIVE_ATTACK,
    RANSOMWARE,
    SUPPLY_CHAIN_ATTACK,
    INDUSTRIAL_CONTROL_SYSTEMS,
    CRITICAL_INFRASTRUCTURE,
    FINANCIAL_SYSTEMS,
    ELECTION_SYSTEMS
}

@Parcelize
data class CyberDefensive(
    val rating: Int,
    val capabilities: List<CyberDefensiveCapability>,
    val incidentsThisYear: Int,
    val preventedAttacks: Int,
    val averageResponseTime: Int // hours
) : Parcelable

enum class CyberDefensiveCapability {
    INTRUSION_DETECTION,
    MALWARE_ANALYSIS,
    THREAT_INTELLIGENCE,
    INCIDENT_RESPONSE,
    FORENSICS,
    NETWORK_DEFENSE,
    ENDPOINT_PROTECTION,
    ZERO_TRUST
}

@Parcelize
data class CyberUnit(
    val id: String,
    val name: String,
    val personnel: Int,
    val budget: Long,
    val mission: String,
    val capabilities: List<String>,
    val operations: Int
) : Parcelable

@Parcelize
data class MalwareTool(
    val id: String,
    val name: String,
    val type: MalwareType,
    val platform: String,
    val sophistication: Int,
    val deploymentCount: Int,
    val successRate: Double
) : Parcelable

enum class MalwareType {
    TROJAN,
    WORM,
    RANSOMWARE,
    SPYWARE,
    ROOTKIT,
    BACKDOOR,
    KEYLOGGER,
    SCREENSCRAPER,
    WIPER,
    STUXNET_STYLE
}

@Parcelize
data class CyberOperation(
    val id: String,
    val codename: String,
    val type: CyberOperationType,
    val target: String,
    val objective: String,
    val status: OperationStatus,
    val startDate: Long?,
    val endDate: Long?,
    val outcome: String?
) : Parcelable

enum class CyberOperationType {
    ESPIONAGE,
    SABOTAGE,
    DATA_THEFT,
    INFLUENCE_OPERATION,
    INFRASTRUCTURE_ATTACK,
    FINANCIAL_THEFT
}

// Surveillance Program

@Parcelize
data class SurveillanceProgram(
    val id: String,
    val name: String,
    val type: SurveillanceType,
    val legal: Boolean,
    val authorization: String?,
    val targets: List<SurveillanceTarget>,
    val methods: List<SurveillanceMethod>,
    val budget: Long,
    val dataCollected: Long, // terabytes
    val retention: Int, // days
    val oversight: String?,
    val controversies: List<String>,
    val politicalSupport: Double
) : Parcelable

enum class SurveillanceType {
    MASS_SURVEILLANCE,
    TARGETED_SURVEILLANCE,
    FOREIGN_INTELLIGENCE,
    DOMESTIC_TERRORISM,
    CRIMINAL_INVESTIGATION,
    BORDER_SURVEILLANCE
}

@Parcelize
data class SurveillanceTarget(
    val id: String,
    val type: TargetType,
    val identifier: String,
    val justification: String,
    val startDate: Long,
    val endDate: Long?,
    val collectedData: Long
) : Parcelable

enum class TargetType {
    INDIVIDUAL,
    ORGANIZATION,
    COMMUNICATION_NETWORK,
    GEOGRAPHIC_AREA,
    DIGITAL_SERVICE
}

@Parcelize
data class SurveillanceMethod(
    val id: String,
    val name: String,
    val type: MethodType,
    val legalBasis: String?,
    val effectiveness: Double
) : Parcelable

enum class MethodType {
    WIRETAP,
    EMAIL_INTERCEPTION,
    METADATA_COLLECTION,
    VIDEO_SURVEILLANCE,
    GPS_TRACKING,
    FINANCIAL_MONITORING,
    SOCIAL_MEDIA_MONITORING,
    BIOMETRIC_COLLECTION,
    FACIAL_RECOGNITION,
    LICENSE_PLATE_READERS
}

// Intelligence Sharing

@Parcelize
data class IntelligenceSharing(
    val id: String,
    val partnerCountry: String,
    val agreementType: SharingAgreementType,
    val establishedDate: Long,
    val classificationLevel: ClearanceLevel,
    val categories: List<String>,
    val reciprocal: Boolean,
    val restrictions: List<String>,
    val annualExchanges: Int,
    val reliability: Double
) : Parcelable

enum class SharingAgreementType {
    FIVE_EYES,
    BILATERAL,
    MULTILATERAL,
    NATO,
    EU_INTELLIGENCE,
    AD_HOC,
    LIMITED
}

// Intelligence Threats

@Parcelize
data class IntelligenceThreat(
    val id: String,
    val source: String, // Country or organization
    val type: ThreatType,
    val severity: Int, // 1-10
    val description: String,
    val indicators: List<String>,
    val mitigationStatus: MitigationStatus,
    val discoveredDate: Long,
    val lastActivityDate: Long?
) : Parcelable

enum class ThreatType {
    ESPIONAGE,
    CYBER_ATTACK,
    TERRORISM,
    PROLIFERATION,
    INSIDER_THREAT,
    FOREIGN_INFLUENCE,
    DISINFORMATION,
    ASSASSINATION
}

enum class MitigationStatus {
    MONITORING,
    ACTIVE_RESPONSE,
    CONTAINED,
    NEUTRALIZED,
    IGNORED
}

// Agent

@Parcelize
data class Agent(
    val id: String,
    val codename: String,
    val realName: String,
    val nationality: String,
    val recruitment: Recruitment,
    val training: List<AgentTraining>,
    val specializations: List<String>,
    val cover: Cover?,
    val languages: List<String>,
    val status: AgentStatus,
    val activeOperations: List<String>,
    val completedOperations: Int,
    val handler: String?,
    val location: String?,
    val risk: Double
) : Parcelable

@Parcelize
data class AgentTraining(
    val id: String,
    val name: String,
    val completedDate: Long,
    val score: Double
) : Parcelable

@Parcelize
data class Cover(
    val id: String,
    val type: CoverType,
    val identity: String,
    val occupation: String,
    val employer: String,
    val backstory: String,
    val documents: List<String>,
    val depth: Int, // years established
    val official: Boolean // Official vs non-official cover
) : Parcelable

enum class CoverType {
    OFFICIAL,
    NON_OFFICIAL, // NOC
    DIPLOMATIC,
    MILITARY,
    COMMERCIAL,
    ACADEMIC,
    JOURNALIST,
    NGO,
    TOURIST
}

enum class AgentStatus {
    ACTIVE,
    INACTIVE,
    UNDERCOVER,
    COMPROMISED,
    CAPTURED,
    KILLED,
    RETIRED,
    EXTRACTED
}
