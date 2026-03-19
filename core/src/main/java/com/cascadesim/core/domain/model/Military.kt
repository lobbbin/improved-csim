package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Military and Defense System
 * Comprehensive military management including army, navy, air force, intelligence, and nuclear capabilities.
 */

@Parcelize
data class Military(
    val countryId: String,
    val totalPersonnel: Int,
    val activePersonnel: Int,
    val reservePersonnel: Int,
    val paramilitaryPersonnel: Int,
    val militaryBudget: Long, // Annual budget in USD
    val budgetPercentOfGdp: Double,
    val overallStrength: Int, // 0-1000 composite score
    val readinessLevel: ReadinessLevel,
    val morale: Double, // 0.0-1.0
    val professionalism: Double, // 0.0-1.0
    val corruption: Double, // 0.0-1.0
    val army: Army,
    val navy: Navy,
    val airForce: AirForce,
    val specialForces: SpecialForces,
    val nuclearCapability: NuclearCapability?,
    val intelligenceAgencies: List<IntelligenceAgency>,
    val defenseContractors: List<DefenseContractor>,
    val militaryAlliances: List<MilitaryAlliance>,
    val ongoingOperations: List<MilitaryOperation>,
    val bases: List<MilitaryBase>,
    val equipment: MilitaryEquipment,
    val doctrine: MilitaryDoctrine,
    val lastUpdated: Long
) : Parcelable {
    
    val totalCombatPower: Double
        get() = army.combatPower + navy.combatPower + airForce.combatPower + 
                (specialForces.combatPower * 2)
    
    val nuclearDeterrent: Boolean
        get() = nuclearCapability != null && nuclearCapability.warheads > 0
    
    val powerProjection: Double
        get() = (navy.combatPower * 0.4 + airForce.combatPower * 0.3 + 
                army.combatPower * 0.2 + specialForces.combatPower * 0.1) *
                (if (navy.aircraftCarriers > 0) 1.5 else 1.0)
    
    val defenseSpendingPerSoldier: Long
        get() = if (totalPersonnel > 0) militaryBudget / totalPersonnel else 0L
}

enum class ReadinessLevel {
    STAND_DOWN, // Peacetime minimal readiness
    NORMAL, // Standard operational readiness
    ELEVATED, // Heightened alert
    HIGH_ALERT, // Near-combat readiness
    MAXIMUM, // Full combat readiness
    MOBILIZED // Total war footing
}

@Parcelize
data class Army(
    val totalPersonnel: Int,
    val combatPersonnel: Int,
    val supportPersonnel: Int,
    val tanks: Int,
    val armoredVehicles: Int,
    val selfPropelledArtillery: Int,
    val towedArtillery: Int,
    val multipleLaunchRocketSystems: Int,
    val helicopters: Int,
    val attackHelicopters: Int,
    val divisions: List<Division>,
    val brigades: List<Brigade>,
    val combatPower: Double,
    val trainingLevel: Double,
    val equipmentQuality: Double
) : Parcelable

@Parcelize
data class Division(
    val id: String,
    val name: String,
    val type: DivisionType,
    val personnel: Int,
    val location: String,
    val commander: String?, // NPC ID
    val readiness: Double,
    val equipmentLevel: Double,
    val morale: Double
) : Parcelable

enum class DivisionType {
    INFANTRY,
    ARMORED,
    MECHANIZED,
    AIRBORNE,
    MOUNTAIN,
    ARTILLERY,
    AIR_ASSAULT,
    MARINE,
    SPECIAL_FORCES,
    LOGISTICS,
    ENGINEER,
    AIR_DEFENSE,
    RECONNAISSANCE,
    SIGNAL,
    MEDICAL,
    MILITARY_POLICE
}

@Parcelize
data class Brigade(
    val id: String,
    val name: String,
    val type: BrigadeType,
    val personnel: Int,
    val divisionId: String?,
    val location: String,
    val commander: String?,
    val readiness: Double,
    val equipmentLevel: Double
) : Parcelable

enum class BrigadeType {
    COMBAT,
    COMBAT_SUPPORT,
    COMBAT_SERVICE_SUPPORT,
    AVIATION,
    AIR_DEFENSE,
    ENGINEER,
    ARTILLERY,
    RECONNAISSANCE,
    SIGNAL,
    MAINTENANCE,
    MEDICAL,
    SUPPLY,
    MILITARY_INTELLIGENCE
}

@Parcelize
data class Navy(
    val totalPersonnel: Int,
    val ships: Int,
    val submarines: Int,
    val aircraftCarriers: Int,
    val destroyers: Int,
    val frigates: Int,
    val corvettes: Int,
    val patrolVessels: Int,
    val amphibiousShips: Int,
    val mineWarfareShips: Int,
    val auxiliaryShips: Int,
    val navalAircraft: Int,
    val fleets: List<Fleet>,
    val combatPower: Double,
    val trainingLevel: Double,
    val equipmentQuality: Double,
    val sealiftCapability: Double,
    val powerProjection: Double
) : Parcelable

@Parcelize
data class Fleet(
    val id: String,
    val name: String,
    val areaOfOperation: String,
    val flagshipId: String?,
    val ships: List<Ship>,
    val admiral: String?, // NPC ID
    val homePort: String,
    val operationalStatus: FleetStatus
) : Parcelable

enum class FleetStatus {
    ACTIVE,
    TRAINING,
    MAINTENANCE,
    DEPLOYED,
    STANDBY,
    DECOMMISSIONED
}

@Parcelize
data class Ship(
    val id: String,
    val name: String,
    val hullNumber: String,
    val type: ShipType,
    val displacement: Int, // tons
    val speed: Int, // knots
    val range: Int, // nautical miles
    val crew: Int,
    val commissioned: Int, // year
    val armament: List<WeaponSystem>,
    val aircraft: Int, // if carrier
    val missiles: Int,
    val radarSystems: List<String>,
    val sonarSystems: List<String>,
    val electronicWarfare: List<String>,
    val currentLocation: String?,
    val status: ShipStatus,
    val commander: String? // NPC ID
) : Parcelable

enum class ShipType {
    AIRCRAFT_CARRIER,
    LIGHT_CARRIER,
    HELICOPTER_CARRIER,
    DESTROYER,
    FRIGATE,
    CORVETTE,
    SUBMARINE_SSN,
    SUBMARINE_SSB,
    SUBMARINE_SSGN,
    AMPHIBIOUS_ASSAULT,
    LANDING_SHIP,
    PATROL_CRAFT,
    MINE_COUNTERMEASURE,
    REPLENISHMENT,
    HOSPITAL_SHIP,
    COMMAND_SHIP,
    RESEARCH_VESSEL,
    TUGBOAT,
    TENDER
}

enum class ShipStatus {
    ACTIVE,
    IN_RESERVE,
    UNDER_MAINTENANCE,
    UNDER_REPAIR,
    REFITTING,
    DEPLOYED,
    IN_TRANSIT,
    IN_PORT,
    SUNK,
    DECOMMISSIONED
}

@Parcelize
data class AirForce(
    val totalPersonnel: Int,
    val totalAircraft: Int,
    val fighterAircraft: Int,
    val attackAircraft: Int,
    val transportAircraft: Int,
    val trainerAircraft: Int,
    val helicopters: Int,
    val attackHelicopters: Int,
    val specialMissionAircraft: Int,
    val tankers: Int,
    val bombers: Int,
    val drones: Int,
    val airWings: List<AirWing>,
    val combatPower: Double,
    val trainingLevel: Double,
    val equipmentQuality: Double,
    val airDefenseSystems: List<AirDefenseSystem>,
    val spaceCapabilities: List<SpaceCapability>?
) : Parcelable

@Parcelize
data class AirWing(
    val id: String,
    val name: String,
    val baseId: String,
    val aircraft: List<Aircraft>,
    val commander: String?,
    val readiness: Double
) : Parcelable

@Parcelize
data class Aircraft(
    val id: String,
    val name: String,
    val tailNumber: String,
    val type: AircraftType,
    val manufacturer: String,
    val model: String,
    val generation: Int, // fighter generations 1-6
    val maxSpeed: Int, // km/h
    val range: Int, // km
    val serviceCeiling: Int, // meters
    val armament: List<WeaponSystem>,
    val avionics: List<String>,
    val stealthCapability: Double,
    val commissioned: Int,
    val status: AircraftStatus,
    val flightHours: Int,
    val lastMaintenance: Long
) : Parcelable

enum class AircraftType {
    AIR_SUPERIORITY_FIGHTER,
    MULTIROLE_FIGHTER,
    INTERCEPTOR,
    STRIKE_FIGHTER,
    GROUND_ATTACK,
    CLOSE_AIR_SUPPORT,
    STRATEGIC_BOMBER,
    TACTICAL_BOMBER,
    ELECTRONIC_WARFARE,
    AIRBORNE_EARLY_WARNING,
    RECONNAISSANCE,
    MARITIME_PATROL,
    SEARCH_AND_RESCUE,
    TRANSPORT,
    TANKER,
    TRAINER,
    VIP_TRANSPORT,
    ATTACK_HELICOPTER,
    TRANSPORT_HELICOPTER,
    ANTI_SUBMARINE_HELICOPTER,
    UAV,
    UCAV
}

enum class AircraftStatus {
    ACTIVE,
    IN_RESERVE,
    UNDER_MAINTENANCE,
    GROUNDED,
    DEPLOYED,
    SCRAPPED,
    LOST
}

@Parcelize
data class SpecialForces(
    val totalPersonnel: Int,
    val units: List<SpecialForcesUnit>,
    val capabilities: List<SpecialOperationsCapability>,
    val combatPower: Double,
    val trainingLevel: Double,
    val operationalTempo: Double
) : Parcelable

@Parcelize
data class SpecialForcesUnit(
    val id: String,
    val name: String,
    val type: SpecialForcesType,
    val personnel: Int,
    val commander: String?,
    val specialization: List<String>,
    val missions: List<SpecialMission>,
    val successRate: Double,
    val readiness: Double
) : Parcelable

enum class SpecialForcesType {
    COUNTER_TERRORISM,
    SPECIAL_RECONNAISSANCE,
    DIRECT_ACTION,
    UNCONVENTIONAL_WARFARE,
    FOREIGN_INTERNAL_DEFENSE,
    COUNTER_PROLIFERATION,
    HOSTAGE_RESCUE,
    MARITIME_SPECIAL,
    AIRBORNE,
    MOUNTAIN,
    DESERT,
    JUNGLE,
    ARCTIC,
    URBAN,
    PSYCHOLOGICAL_OPERATIONS,
    CIVIL_AFFAIRS
}

@Parcelize
data class SpecialMission(
    val id: String,
    val codename: String,
    val type: MissionType,
    val status: MissionStatus,
    val location: String?,
    val objective: String,
    val startDate: Long?,
    val endDate: Long?,
    val participants: Int,
    val outcome: MissionOutcome?
) : Parcelable

enum class MissionType {
    RECONNAISSANCE,
    DIRECT_ACTION,
    COUNTER_TERRORISM,
    HOSTAGE_RESCUE,
    SABOTAGE,
    ASSASSINATION,
    EXTRACTION,
    TRAINING,
    ADVISORY,
    PEACEKEEPING,
    HUMANITARIAN
}

enum class MissionStatus {
    PLANNING,
    APPROVED,
    IN_PROGRESS,
    COMPLETED,
    ABORTED,
    FAILED,
    ONGOING,
    STANDBY
}

enum class MissionOutcome {
    SUCCESS,
    PARTIAL_SUCCESS,
    FAILURE,
    CATASTROPHIC_FAILURE,
    INCONCLUSIVE
}

@Parcelize
data class SpecialOperationsCapability(
    val name: String,
    val level: Double, // 0.0-1.0
    val lastUsed: Long?,
    val experiencePoints: Int
) : Parcelable

@Parcelize
data class NuclearCapability(
    val warheads: Int,
    val deliverySystems: List<NuclearDeliverySystem>,
    val triadComponents: NuclearTriad,
    val doctrine: NuclearDoctrine,
    val firstStrikeCapability: Boolean,
    val secondStrikeCapability: Boolean,
    val testingHistory: List<NuclearTest>,
    val treaties: List<String>, // Treaty IDs
    val safeguards: NuclearSafeguards,
    val fissileMaterial: FissileMaterialStockpile
) : Parcelable

@Parcelize
data class NuclearDeliverySystem(
    val id: String,
    val name: String,
    val type: DeliverySystemType,
    val range: Int, // km
    val payload: Int, // kg
    val warheads: Int,
    val accuracy: Double, // CEP in meters
    val quantity: Int,
    val status: DeliverySystemStatus
) : Parcelable

enum class DeliverySystemType {
    ICBM,
    SLBM,
    IRBM,
    SRBM,
    CRUISE_MISSILE,
    AIR_LAUNCHED_CRUISE_MISSILE,
    STRATEGIC_BOMBER,
    TACTICAL_BOMBER,
    ARTILLERY_SHELL,
    LAND_MINE,
    TORPEDO,
    DEPTH_CHARGE
}

enum class DeliverySystemStatus {
    OPERATIONAL,
    DEVELOPMENT,
    TESTING,
    PHASED_OUT,
    DECOMMISSIONED
}

@Parcelize
data class NuclearTriad(
    val landBased: Boolean,
    val seaBased: Boolean,
    val airBased: Boolean,
    val landBasedWarheads: Int,
    val seaBasedWarheads: Int,
    val airBasedWarheads: Int
) : Parcelable

enum class NuclearDoctrine {
    NO_FIRST_USE,
    FIRST_USE,
    AMBIGUOUS,
    MINIMUM_DETERRENCE,
    ASSURED_DESTRUCTION,
    COUNTER_FORCE,
    COUNTER_VALUE,
    FLEXIBLE_RESPONSE,
    DEESCALATION
}

@Parcelize
data class NuclearTest(
    val id: String,
    val date: Long,
    val location: String,
    val yield: Double, // kilotons
    val type: String,
    val success: Boolean
) : Parcelable

@Parcelize
data class NuclearSafeguards(
    val permissiveActionLinks: Boolean,
    val twoPersonRule: Boolean,
    val codeManagement: CodeManagementSecurity,
    val storageSecurity: Double,
    val transportationSecurity: Double,
    val personnelReliability: Double,
    val incidentHistory: List<NuclearIncident>
) : Parcelable

enum class CodeManagementSecurity {
    NONE,
    BASIC,
    ENHANCED,
    ADVANCED,
    MAXIMUM
}

@Parcelize
data class NuclearIncident(
    val id: String,
    val date: Long,
    val type: IncidentType,
    val severity: IncidentSeverity,
    val location: String,
    val description: String,
    val casualties: Int,
    val resolved: Boolean
) : Parcelable

enum class IncidentType {
    ACCIDENTAL_LAUNCH,
    UNAUTHORIZED_ACCESS,
    LOSS_OF_CONTROL,
    RADIATION_LEAK,
    HANDLING_ERROR,
    TRANSPORT_ACCIDENT,
    SECURITY_BREACH,
    SYSTEM_FAILURE
}

enum class IncidentSeverity {
    MINOR,
    MODERATE,
    SERIOUS,
    SEVERE,
    CATASTROPHIC
}

@Parcelize
data class FissileMaterialStockpile(
    val highlyEnrichedUranium: Int, // kg
    val weaponsGradePlutonium: Int, // kg
    val tritium: Int, // kg
    val productionCapacity: Int, // kg per year
    val lastAudit: Long
) : Parcelable

@Parcelize
data class IntelligenceAgency(
    val id: String,
    val name: String,
    val acronym: String,
    val type: IntelligenceType,
    val budget: Long,
    val personnel: Int,
    val director: String?, // NPC ID
    val capabilities: List<IntelligenceCapability>,
    val operations: List<IntelligenceOperation>,
    val foreignStations: List<IntelligenceStation>,
    val domesticBranches: List<AgencyBranch>,
    val reputation: Double,
    val effectiveness: Double,
    val scandals: List<IntelligenceScandal>,
    val allies: List<String>, // Agency IDs of allied agencies
    val adversaries: List<String>
) : Parcelable

enum class IntelligenceType {
    FOREIGN_INTELLIGENCE,
    DOMESTIC_INTELLIGENCE,
    MILILITARY_INTELLIGENCE,
    SIGNALS_INTELLIGENCE,
    HUMAN_INTELLIGENCE,
    GEOGRAPHIC_INTELLIGENCE,
    CYBER_INTELLIGENCE,
    COUNTER_INTELLIGENCE,
    ECONOMIC_INTELLIGENCE,
    TERRORISM_INTELLIGENCE
}

@Parcelize
data class IntelligenceCapability(
    val type: CapabilityType,
    val level: Double,
    val budget: Long,
    val personnel: Int,
    val assets: Int
) : Parcelable

enum class CapabilityType {
    HUMINT, // Human Intelligence
    SIGINT, // Signals Intelligence
    IMINT, // Imagery Intelligence
    MASINT, // Measurement and Signature Intelligence
    OSINT, // Open Source Intelligence
    GEOINT, // Geospatial Intelligence
    CYBERINT, // Cyber Intelligence
    FININT, // Financial Intelligence
    TECHINT, // Technical Intelligence
    ELINT, // Electronic Intelligence
    COMINT, // Communications Intelligence
    FISINT, // Foreign Instrumentation Signals Intelligence
    MEDINT, // Medical Intelligence
    LEGINT // Legal Intelligence
}

@Parcelize
data class IntelligenceOperation(
    val id: String,
    val codename: String,
    val type: OperationType,
    val status: OperationStatus,
    val targetCountry: String?,
    val targetOrganization: String?,
    val startDate: Long,
    val endDate: Long?,
    val assets: List<String>, // Agent/Asset IDs
    val objectives: List<String>,
    val outcomes: List<String>,
    val classification: ClassificationLevel,
    val casualties: Int,
    val budget: Long
) : Parcelable

enum class OperationType {
    ESPIONAGE,
    SABOTAGE,
    PROPAGANDA,
    COVERT_ACTION,
    REGIME_CHANGE,
    ASSASSINATION,
    KIDNAPPING,
    EXTRACTION,
    ELECTION_INTERFERENCE,
    ECONOMIC_WARFARE,
    CYBER_OPERATION,
    DISINFORMATION,
    PARAMILITARY,
    COUP_SUPPORT
}

enum class OperationStatus {
    PROPOSED,
    APPROVED,
    PLANNING,
    ACTIVE,
    SUSPENDED,
    COMPLETED,
    COMPROMISED,
    ABORTED,
    EXPOSED
}

enum class ClassificationLevel {
    UNCLASSIFIED,
    CONFIDENTIAL,
    SECRET,
    TOP_SECRET,
    TOP_SECRET_SCI, // Sensitive Compartmented Information
    SAP, // Special Access Program
    COSMIC_TOP_SECRET
}

@Parcelize
data class IntelligenceStation(
    val id: String,
    val location: String,
    val country: String,
    val type: StationType,
    val personnel: Int,
    val status: StationStatus,
    val cover: String?
) : Parcelable

enum class StationType {
    EMBASSY,
    CONSULATE,
    COMMERCIAL,
    MEDIA,
    CULTURAL,
    MILITARY,
    RESEARCH,
    NGO
}

enum class StationStatus {
    ACTIVE,
    DORMANT,
    BURNED,
    CLOSED,
    MONITORED,
    COMPROMISED
}

@Parcelize
data class AgencyBranch(
    val id: String,
    val name: String,
    val location: String,
    val personnel: Int,
    val function: String,
    val director: String?
) : Parcelable

@Parcelize
data class IntelligenceScandal(
    val id: String,
    val title: String,
    val date: Long,
    val description: String,
    val publicKnowledge: Boolean,
    val politicalFallout: Double,
    val reforms: List<String>
) : Parcelable

@Parcelize
data class DefenseContractor(
    val id: String,
    val name: String,
    val country: String,
    val headquarters: String,
    val revenue: Long,
    val employees: Int,
    val products: List<DefenseProduct>,
    val contracts: List<DefenseContract>,
    val lobbyingBudget: Long,
    val politicalConnections: List<String>, // NPC IDs
    val scandals: List<ContractorScandal>,
    val subsidiaries: List<String>
) : Parcelable

@Parcelize
data class DefenseProduct(
    val id: String,
    val name: String,
    val category: ProductCategory,
    val description: String,
    val unitCost: Long,
    val productionCapacity: Int,
    val exportApproved: Boolean,
    val exportMarkets: List<String>
) : Parcelable

enum class ProductCategory {
    AIRCRAFT,
    HELICOPTER,
    MISSILE,
    TANK,
    ARMORED_VEHICLE,
    ARTILLERY,
    SMALL_ARMS,
    NAVAL_VESSEL,
    SUBMARINE,
    RADAR,
    ELECTRONIC_WARFARE,
    SATELLITE,
    DRONE,
    AMMUNITION,
    BODY_ARMOR,
    COMMUNICATIONS,
    CYBER
}

@Parcelize
data class DefenseContract(
    val id: String,
    val name: String,
    val contractorId: String,
    val value: Long,
    val startDate: Long,
    val endDate: Long,
    val products: List<String>,
    val quantity: Int,
    val status: ContractStatus,
    val costOverruns: Long,
    val delays: Int // months
) : Parcelable

enum class ContractStatus {
    BID,
    NEGOTIATING,
    AWARDED,
    ACTIVE,
    DELIVERED,
    COMPLETED,
    CANCELLED,
    TERMINATED,
    UNDER_INVESTIGATION
}

@Parcelize
data class ContractorScandal(
    val id: String,
    val title: String,
    val date: Long,
    val type: ScandalType,
    val description: String,
    val fines: Long,
    val convictions: Int,
    val reforms: List<String>
) : Parcelable

enum class ScandalType {
    BRIBERY,
    FRAUD,
    COST_OVERRUN,
    QUALITY_ISSUES,
    EXPORT_VIOLATION,
    INSIDER_TRADING,
    ENVIRONMENTAL,
    LABOR_VIOLATION
}

@Parcelize
data class MilitaryAlliance(
    val id: String,
    val name: String,
    val acronym: String,
    val type: AllianceType,
    val foundingDate: Long,
    val headquarters: String,
    val members: List<String>, // Country IDs
    val observerCountries: List<String>,
    val treaty: TreatySummary,
    val jointCommand: Boolean,
    val integratedForces: List<String>,
    val annualExercises: List<MilitaryExercise>,
    val collectiveDefense: Boolean,
    val funding: Long,
    val isActive: Boolean
) : Parcelable

enum class AllianceType {
    DEFENSE_PACT,
    MUTUAL_DEFENSE,
    NON_AGGRESSION,
    MILITARY_COOPERATION,
    ARMS_CONTROL,
    INTELLIGENCE_SHARING,
    PEACEKEEPING,
    COUNTER_TERRORISM,
    NAVAL_ALLIANCE,
    AIR_DEFENSE_ALLIANCE
}

@Parcelize
data class TreatySummary(
    val name: String,
    val signedDate: Long,
    val effectiveDate: Long,
    val expiryDate: Long?,
    val articles: Int,
    val keyProvisions: List<String>
) : Parcelable

@Parcelize
data class MilitaryExercise(
    val id: String,
    val name: String,
    val type: ExerciseType,
    val location: String,
    val participants: List<String>, // Country IDs
    val personnel: Int,
    val equipment: List<String>,
    val startDate: Long,
    val endDate: Long,
    val objectives: List<String>,
    val outcome: ExerciseOutcome?
) : Parcelable

enum class ExerciseType {
    FIELD_TRAINING,
    NAVAL,
    AIR,
    AMPHIBIOUS,
    SPECIAL_OPERATIONS,
    CYBER,
    COUNTER_TERRORISM,
    PEACEKEEPING,
    HUMANITARIAN,
    NUCLEAR
}

enum class ExerciseOutcome {
    SUCCESSFUL,
    PARTIALLY_SUCCESSFUL,
    PROBLEMATIC,
    CANCELLED
}

@Parcelize
data class MilitaryOperation(
    val id: String,
    val name: String,
    val codename: String,
    val type: OperationType,
    val status: OperationStatus,
    val startDate: Long,
    val endDate: Long?,
    val location: String,
    val objective: String,
    val personnel: Int,
    val casualties: MilitaryCasualties,
    val equipmentLosses: EquipmentLosses,
    val cost: Long,
    val outcome: OperationOutcome?,
    val politicalSupport: Double,
    val internationalSupport: Double,
    val unMandate: Boolean,
    val rulesOfEngagement: RulesOfEngagement
) : Parcelable

enum class OperationOutcome {
    VICTORY,
    STRATEGIC_VICTORY,
    TACTICAL_VICTORY,
    STALEMATE,
    TACTICAL_DEFEAT,
    STRATEGIC_DEFEAT,
    DEFEAT,
    WITHDRAWAL,
    ONGOING
}

@Parcelize
data class MilitaryCasualties(
    val killed: Int,
    val wounded: Int,
    val missing: Int,
    val captured: Int,
    val civilianKilled: Int,
    val civilianWounded: Int
) : Parcelable {
    val total: Int get() = killed + wounded + missing + captured
    val totalMilitary: Int get() = killed + wounded + missing + captured
    val totalCivilian: Int get() = civilianKilled + civilianWounded
}

@Parcelize
data class EquipmentLosses(
    val tanks: Int,
    val aircraft: Int,
    val helicopters: Int,
    val ships: Int,
    val vehicles: Int,
    val artillery: Int,
    val other: Int
) : Parcelable {
    val total: Int get() = tanks + aircraft + helicopters + ships + vehicles + artillery + other
}

@Parcelize
data class RulesOfEngagement(
    val level: RoELevel,
    val restrictions: List<String>,
    val approvedTargets: List<String>,
    val prohibitedActions: List<String>,
    val approvalChain: List<String>,
    val escalationThreshold: Double
) : Parcelable

enum class RoELevel {
    PEACETIME,
    LOW_INTENSITY,
    MEDIUM_INTENSITY,
    HIGH_INTENSITY,
    TOTAL_WAR,
    UNRESTRICTED
}

@Parcelize
data class MilitaryBase(
    val id: String,
    val name: String,
    val type: BaseType,
    val location: String,
    val country: String, // Host country
    val owningCountry: String,
    val coordinates: Coordinates,
    val personnel: Int,
    val area: Int, // square km
    val facilities: List<BaseFacility>,
    val economicImpact: Long,
    val status: BaseStatus,
    val leaseAgreement: LeaseAgreement?,
    val environmentalIssues: List<String>
) : Parcelable

enum class BaseType {
    ARMY_POST,
    NAVAL_BASE,
    AIR_BASE,
    JOINT_BASE,
    LOGISTICS_HUB,
    TRAINING_CENTER,
    RESEARCH_FACILITY,
    MISSILE_SITE,
    RADAR_STATION,
    COMMUNICATION_HUB,
    STORAGE_DEPOT,
    HEADQUARTERS
}

@Parcelize
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) : Parcelable

@Parcelize
data class BaseFacility(
    val id: String,
    val name: String,
    val type: String,
    val capacity: Int,
    val status: String
) : Parcelable

enum class BaseStatus {
    ACTIVE,
    REDUCED,
    CLOSING,
    CLOSED,
    CONSTRUCTION,
    PLANNED
}

@Parcelize
data class LeaseAgreement(
    val startDate: Long,
    val endDate: Long,
    val annualCost: Long,
    val conditions: List<String>,
    val renewalOptions: Boolean
) : Parcelable

@Parcelize
data class MilitaryEquipment(
    val totalValue: Long,
    val categories: Map<String, EquipmentCategory>,
    val modernizationPrograms: List<ModernizationProgram>,
    val procurementQueue: List<ProcurementItem>,
    val exportContracts: List<ExportContract>,
    val maintenanceBacklog: Long,
    val averageAge: Double,
    val domesticProduction: Double // percentage
) : Parcelable

@Parcelize
data class EquipmentCategory(
    val name: String,
    val quantity: Int,
    val operationalRate: Double,
    val averageAge: Double,
    val value: Long
) : Parcelable

@Parcelize
data class ModernizationProgram(
    val id: String,
    val name: String,
    val category: String,
    val startDate: Long,
    val targetDate: Long,
    val budget: Long,
    val spent: Long,
    val progress: Double,
    val status: ProgramStatus
) : Parcelable

enum class ProgramStatus {
    PLANNING,
    APPROVED,
    FUNDED,
    IN_PROGRESS,
    DELAYED,
    OVER_BUDGET,
    COMPLETED,
    CANCELLED
}

@Parcelize
data class ProcurementItem(
    val id: String,
    val name: String,
    val quantity: Int,
    val unitCost: Long,
    val totalCost: Long,
    val status: ProcurementStatus,
    val estimatedDelivery: Long,
    val supplier: String?
) : Parcelable

enum class ProcurementStatus {
    REQUESTED,
    APPROVED,
    FUNDED,
    CONTRACTED,
    IN_PRODUCTION,
    DELIVERED,
    CANCELLED
}

@Parcelize
data class ExportContract(
    val id: String,
    val buyerCountry: String,
    val items: List<String>,
    val value: Long,
    val approvalStatus: ExportApprovalStatus,
    val deliveryDate: Long
) : Parcelable

enum class ExportApprovalStatus {
    PENDING,
    APPROVED,
    REJECTED,
    UNDER_REVIEW,
    DELAYED
}

@Parcelize
data class MilitaryDoctrine(
    val name: String,
    val primaryFocus: DoctrineFocus,
    val strategicGoals: List<String>,
    val operationalConcepts: List<String>,
    val forceStructure: String,
    val lastRevision: Long,
    val adherence: Double
) : Parcelable

enum class DoctrineFocus {
    TERRITORIAL_DEFENSE,
    POWER_PROJECTION,
    COUNTER_INSURGENCY,
    ASYMMETRIC_WARFARE,
    BLITZKRIEG,
    ATTRITION,
    MANEUVER,
    AIRLAND_BATTLE,
    HYBRID_WARFARE,
    NUCLEAR_DETERRENCE,
    MARITIME_SUPREMACY,
    AIR_SUPERIORITY
}

@Parcelize
data class WeaponSystem(
    val id: String,
    val name: String,
    val type: WeaponType,
    val caliber: String?,
    val range: Int?,
    val guidance: String?,
    val warhead: String?
) : Parcelable

enum class WeaponType {
    GUN,
    MISSILE,
    BOMB,
    TORPEDO,
    MINE,
    ROCKET,
    ARTILLERY_SHELL,
    SMALL_ARM
}

@Parcelize
data class AirDefenseSystem(
    val id: String,
    val name: String,
    val type: AirDefenseType,
    val range: Int,
    val altitude: Int,
    val missiles: Int,
    val radars: List<String>
) : Parcelable

enum class AirDefenseType {
    POINT_DEFENSE,
    SHORT_RANGE,
    MEDIUM_RANGE,
    LONG_RANGE,
    THEATER,
    NATIONAL,
    BALLISTIC_MISSILE_DEFENSE
}

@Parcelize
data class SpaceCapability(
    val id: String,
    val name: String,
    val type: SpaceCapabilityType,
    val launchDate: Long,
    val orbitType: String,
    val capabilities: List<String>,
    val status: String
) : Parcelable

enum class SpaceCapabilityType {
    RECONNAISSANCE_SATELLITE,
    COMMUNICATIONS_SATELLITE,
    NAVIGATION_SATELLITE,
    EARLY_WARNING,
    WEATHER,
    ANTI_SATELLITE,
    SPACE_STATION,
    LAUNCH_VEHICLE
}

// War and Combat System Models

@Parcelize
data class War(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long?,
    val participants: List<WarParticipant>,
    val theatres: List<Theatre>,
    val battles: List<Battle>,
    val status: WarStatus,
    val casualties: MilitaryCasualties,
    val economicCost: Long,
    val politicalImpact: WarPoliticalImpact,
    val internationalInvolvement: List<InternationalActor>,
    val peaceTreaties: List<PeaceTreaty>,
    val warCrimes: List<WarCrime>
) : Parcelable

@Parcelize
data class WarParticipant(
    val countryId: String,
    val side: WarSide,
    val entryDate: Long,
    val exitDate: Long?,
    val contribution: WarContribution,
    val warAims: List<String>
) : Parcelable

enum class WarSide {
    AGGRESSOR,
    DEFENDER,
    COALITION_A,
    COALITION_B,
    NEUTRAL_OBSERVER,
    PEACEKEEPER
}

@Parcelize
data class WarContribution(
    val personnel: Int,
    val equipment: List<String>,
    val funding: Long,
    val intelligence: Boolean,
    val logistics: Boolean
) : Parcelable

@Parcelize
data class Theatre(
    val id: String,
    val name: String,
    val location: String,
    val commander: String?,
    val fronts: List<Front>,
    val status: TheatreStatus
) : Parcelable

@Parcelize
data class Front(
    val id: String,
    val name: String,
    val location: String,
    val length: Int, // km
    val status: FrontStatus,
    val controllingSide: WarSide?
) : Parcelable

enum class TheatreStatus {
    ACTIVE,
    STALEMATE,
    ADVANCING,
    RETREATING,
    SECURE,
    CONTested
}

enum class FrontStatus {
    STATIC,
    MOBILE,
    BREAKTHROUGH,
    COLLAPSED,
    STABILIZED
}

@Parcelize
data class Battle(
    val id: String,
    val name: String,
    val theatreId: String,
    val startDate: Long,
    val endDate: Long?,
    val location: String,
    val participants: List<BattleParticipant>,
    val type: BattleType,
    val result: BattleResult?,
    val casualties: MilitaryCasualties,
    val terrain: TerrainType,
    val weather: WeatherCondition,
    val commanderDecisions: List<CommanderDecision>
) : Parcelable

@Parcelize
data class BattleParticipant(
    val countryId: String,
    val side: WarSide,
    val personnel: Int,
    val tanks: Int,
    val aircraft: Int,
    val artillery: Int,
    val morale: Double
) : Parcelable

enum class BattleType {
    LAND,
    NAVAL,
    AIR,
    AMPHIBIOUS,
    AIRBORNE,
    URBAN,
    DESERT,
    MOUNTAIN,
    JUNGLE,
    ARTIC,
    CYBER,
    SPACE
}

enum class BattleResult {
    DECISIVE_VICTORY,
    VICTORY,
    TACTICAL_VICTORY,
    DRAW,
    TACTICAL_DEFEAT,
    DEFEAT,
    DECISIVE_DEFEAT,
    PYRRHIC_VICTORY,
    STALEMATE
}

enum class WeatherCondition {
    CLEAR,
    CLOUDY,
    RAIN,
    HEAVY_RAIN,
    SNOW,
    BLIZZARD,
    FOG,
    SANDSTORM,
    DUST_STORM,
    EXTREME_HEAT,
    EXTREME_COLD
}

@Parcelize
data class CommanderDecision(
    val id: String,
    val commanderId: String,
    val decision: String,
    val outcome: String,
    val impact: Double
) : Parcelable

enum class WarStatus {
    ESCALATING,
    ACTIVE,
    DE_ESCALATING,
    STALEMATE,
    NEGOTIATING,
    CEASEFIRE,
    ENDED,
    FROZEN_CONFLICT
}

@Parcelize
data class WarPoliticalImpact(
    val governmentStability: Double,
    val publicSupport: Double,
    val internationalReputation: Double,
    val economicImpact: Double,
    val regimeChange: Boolean,
    val leadershipChanges: List<String>
) : Parcelable

@Parcelize
data class InternationalActor(
    val countryId: String,
    val role: InternationalRole,
    val contribution: String,
    val motivation: String
) : Parcelable

enum class InternationalRole {
    MEDIATOR,
    ARMS_SUPPLIER,
    FINANCIAL_SUPPORTER,
    VOLUNTEER_FORCE,
    MERCENARY,
    PEACEKEEPER,
    SANCTION_ENFORCER,
    HUMANITARIAN_AID,
    OBSERVER
}

@Parcelize
data class PeaceTreaty(
    val id: String,
    val name: String,
    val signedDate: Long,
    val effectiveDate: Long,
    val signatories: List<String>,
    val terms: List<TreatyTerm>,
    val territorialChanges: List<TerritorialChange>,
    val reparations: Long,
    val occupation: List<Occupation>,
    val guarantees: List<SecurityGuarantee>
) : Parcelable

@Parcelize
data class TreatyTerm(
    val id: String,
    val article: String,
    val text: String,
    val complianceDeadline: Long?,
    val verificationMechanism: String?
) : Parcelable

@Parcelize
data class TerritorialChange(
    val region: String,
    val fromCountry: String,
    val toCountry: String,
    val area: Int,
    val population: Long
) : Parcelable

@Parcelize
data class Occupation(
    val region: String,
    val occupier: String,
    val startDate: Long,
    val endDate: Long?,
    val administrationType: String
) : Parcelable

@Parcelize
data class SecurityGuarantee(
    val guarantor: String,
    val beneficiary: String,
    val type: String,
    val duration: Long
) : Parcelable

@Parcelize
data class WarCrime(
    val id: String,
    val type: WarCrimeType,
    val date: Long,
    val location: String,
    val perpetrator: String,
    val victims: Int,
    val investigated: Boolean,
    val prosecuted: Boolean
) : Parcelable

enum class WarCrimeType {
    GENOCIDE,
    CRIMES_AGAINST_HUMANITY,
    WAR_CRIMES,
    CRIMES_OF_AGGRESSION,
    ETHNIC_CLEANSING,
    MASSACRE,
    TORTURE,
    RAPE_AS_WEAPON,
    USE_OF_PROHIBITED_WEAPONS,
    TARGETING_CIVILIANS,
    DEPORTATION,
    STARVATION,
    MEDICAL_EXPERIMENTS
}

// Occupation and Resistance

@Parcelize
data class OccupationState(
    val id: String,
    val occupiedCountry: String,
    val occupier: String,
    val startDate: Long,
    val territories: List<OccupiedTerritory>,
    val resistance: ResistanceMovement,
    val collaboration: CollaborationGovernment?,
    val administration: OccupationAdministration,
    val internationalStatus: OccupationStatus
) : Parcelable

@Parcelize
data class OccupiedTerritory(
    val id: String,
    val name: String,
    val area: Int,
    val population: Long,
    val militaryPresence: Int,
    val controlLevel: Double,
    val resistanceActivity: Double
) : Parcelable

@Parcelize
data class ResistanceMovement(
    val id: String,
    val name: String,
    val leadership: List<String>, // NPC IDs
    val members: Int,
    val cells: List<ResistanceCell>,
    val tactics: List<ResistanceTactic>,
    val popularSupport: Double,
    val externalSupport: List<String>,
    val effectiveness: Double
) : Parcelable

@Parcelize
data class ResistanceCell(
    val id: String,
    val location: String,
    val members: Int,
    val activities: List<String>,
    val status: CellStatus
) : Parcelable

enum class CellStatus {
    ACTIVE,
    DORMANT,
    COMPROMISED,
    DESTROYED,
    INFILTRATED
}

enum class ResistanceTactic {
    SABOTAGE,
    ASSASSINATION,
    AMBUSH,
    BOMBING,
    PROPAGANDA,
    STRIKE,
    BOYCOTT,
    GUERRILLA_WARFARE,
    URBAN_WARFARE,
    CYBER_RESISTANCE
}

@Parcelize
data class CollaborationGovernment(
    val id: String,
    val name: String,
    val leader: String?,
    val ministers: List<String>,
    val legitimacy: Double,
    val popularSupport: Double
) : Parcelable

@Parcelize
data class OccupationAdministration(
    val type: AdministrationType,
    val governor: String?,
    val policies: List<String>,
    val restrictions: List<String>
) : Parcelable

enum class AdministrationType {
    MILITARY_GOVERNMENT,
    CIVIL_ADMINISTRATION,
    PUPPET_STATE,
    PROTECTORATE,
    TRUST_TERRITORY,
    ANNEXED,
    QUASI_STATE
}

enum class OccupationStatus {
    LEGAL_OCCUPATION,
    ILLEGAL_OCCUPATION,
    DISPUTED,
    DE_FACTO,
    RECOGNIZED,
    CONTESTED
}
