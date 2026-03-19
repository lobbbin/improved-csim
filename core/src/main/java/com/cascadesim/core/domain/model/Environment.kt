package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Environment and Climate System
 * Comprehensive environmental management including pollution, climate change, and natural disasters.
 */

@Parcelize
data class EnvironmentSystem(
    val countryId: String,
    val overallQuality: Double, // 0-100
    val airQuality: AirQuality,
    val waterQuality: WaterQuality,
    val landQuality: LandQuality,
    val biodiversity: Biodiversity,
    val climateStatus: ClimateStatus,
    val pollution: PollutionTracking,
    val naturalResources: EnvironmentalResources,
    val protectedAreas: List<ProtectedArea>,
    val environmentalLaws: List<EnvironmentalLaw>,
    val internationalAgreements: List<String>, // treaty IDs
    val environmentalAgency: EnvironmentalAgency?,
    val disasters: List<NaturalDisaster>,
    val ongoingCrises: List<EnvironmentalCrisis>,
    val greenInitiatives: List<GreenInitiative>,
    val publicAwareness: Double,
    val lastUpdated: Long
) : Parcelable {
    
    val environmentalHealthIndex: Double
        get() = (airQuality.index * 0.3 + waterQuality.index * 0.3 + 
                landQuality.index * 0.2 + biodiversity.index * 0.2)
    
    val sustainabilityScore: Double
        get() = (environmentalHealthIndex * 0.4 + (100 - pollution.totalIndex) * 0.3 + 
                climateStatus.mitigationProgress * 0.3)
}

// Air Quality

@Parcelize
data class AirQuality(
    val index: Double, // 0-100
    val aqi: Int, // Air Quality Index
    val pm25Level: Double, // µg/m³
    val pm10Level: Double,
    val o3Level: Double, // ozone
    val no2Level: Double, // nitrogen dioxide
    val so2Level: Double, // sulfur dioxide
    val coLevel: Double, // carbon monoxide
    val co2Emissions: Long, // tons per year
    val methaneEmissions: Long,
    val industrialEmissions: Long,
    val vehicleEmissions: Long,
    val daysWithPoorAirQuality: Int, // per year
    val mostPollutedCities: List<PollutedCity>,
    val improvementTrend: Double
) : Parcelable

@Parcelize
data class PollutedCity(
    val cityId: String,
    val cityName: String,
    val aqi: Int,
    val mainPollutant: String,
    val populationExposed: Long
) : Parcelable

// Water Quality

@Parcelize
data class WaterQuality(
    val index: Double, // 0-100
    val freshwaterAvailability: Long, // cubic meters per capita
    val waterStress: Double, // 0-1
    val drinkingWaterAccess: Double, // percentage of population
    val sanitationAccess: Double,
    val surfaceWaterQuality: Double,
    val groundwaterQuality: Double,
    val oceanWaterQuality: Double,
    val industrialWaterPollution: Long, // tons of pollutants
    val agriculturalRunoff: Long,
    val wastewaterTreatmentRate: Double,
    val contaminatedSites: Int,
    val waterBorneDiseases: Int, // cases per year
    val majorRivers: List<River>,
    val lakes: List<Lake>,
    val aquifers: List<Aquifer>
) : Parcelable

@Parcelize
data class River(
    val id: String,
    val name: String,
    val length: Int, // km
    val qualityIndex: Double,
    val flowRate: Double,
    val dams: Int,
    val pollution: String,
    val transboundary: Boolean,
    val riparianCountries: List<String>
) : Parcelable

@Parcelize
data class Lake(
    val id: String,
    val name: String,
    val area: Int, // sq km
    val depth: Int, // meters
    val qualityIndex: Double,
    val pollution: String,
    val endangered: Boolean
) : Parcelable

@Parcelize
data class Aquifer(
    val id: String,
    val name: String,
    val area: Int, // sq km
    val waterVolume: Long, // cubic meters
    val rechargeRate: Long, // per year
    val extractionRate: Long,
    val qualityIndex: Double,
    val depletionRate: Double,
    val salinity: Double
) : Parcelable

// Land Quality

@Parcelize
data class LandQuality(
    val index: Double, // 0-100
    val totalLandArea: Long, // sq km
    val arableLand: Double, // percentage
    val forestCover: Double,
    val desertification: Double,
    val soilErosion: Double,
    val contaminatedLand: Double,
    val urbanSprawl: Double,
    val agriculturalLand: Double,
    val protectedLand: Double,
    val deforestationRate: Double, // percentage per year
    val reforestationRate: Double,
    val landDegradation: Double,
    val soilQuality: SoilQuality,
    val forests: List<Forest>,
    val deserts: List<Desert>
) : Parcelable

@Parcelize
data class SoilQuality(
    val averageFertility: Double,
    val organicContent: Double,
    val acidity: Double,
    val salinization: Double,
    val erosionRate: Double,
    val contamination: Double
) : Parcelable

@Parcelize
data class Forest(
    val id: String,
    val name: String,
    val area: Int, // sq km
    val type: ForestType,
    val biodiversityIndex: Double,
    val loggingActivity: Double,
    val protected: Boolean,
    val carbonSequestration: Long, // tons per year
    val fireRisk: Double
) : Parcelable

enum class ForestType {
    TROPICAL_RAINFOREST,
    TEMPERATE_RAINFOREST,
    TROPICAL_DRY_FOREST,
    TEMPERATE_DECIDUOUS,
    BOREAL_TAIGA,
    MEDITERRANEAN,
    MANGROVE,
    CLOUD_FOREST,
    MONSOON_FOREST,
    CONIFEROUS
}

@Parcelize
data class Desert(
    val id: String,
    val name: String,
    val area: Int, // sq km
    val type: String,
    val expansionRate: Double
) : Parcelable

// Biodiversity

@Parcelize
data class Biodiversity(
    val index: Double, // 0-100
    val speciesCount: Int,
    val endangeredSpecies: Int,
    val criticallyEndangered: Int,
    val extinctInCountry: Int,
    val protectedSpecies: Int,
    val ecosystems: List<Ecosystem>,
    val wildlifeCorridors: List<WildlifeCorridor>,
    val nationalParks: Int,
    val wildlifeSanctuaries: Int,
    val marineProtectedAreas: Int,
    val invasiveSpecies: List<InvasiveSpecies>,
    val conservationPrograms: List<ConservationProgram>,
    val wildlifeTrade: WildlifeTradeStatus
) : Parcelable

@Parcelize
data class Ecosystem(
    val id: String,
    val name: String,
    val type: EcosystemType,
    val area: Int, // sq km
    val healthIndex: Double,
    val threats: List<String>,
    val endemicSpecies: Int
) : Parcelable

enum class EcosystemType {
    TROPICAL_RAINFOREST,
    TEMPERATE_FOREST,
    BOREAL_FOREST,
    GRASSLAND,
    SAVANNA,
    DESERT,
    TUNDRA,
    WETLAND,
    MANGROVE,
    CORAL_REEF,
    KELP_FOREST,
    FRESHWATER,
    MOUNTAIN,
    COASTAL,
    PELAGIC
}

@Parcelize
data class WildlifeCorridor(
    val id: String,
    val name: String,
    val length: Int, // km
    val width: Int, // km
    val quality: Double,
    val species: List<String>
) : Parcelable

@Parcelize
data class InvasiveSpecies(
    val id: String,
    val name: String,
    val type: String,
    val origin: String,
    val introductionDate: Long?,
    val affectedArea: Int, // sq km
    val impact: String,
    val controlEfforts: List<String>
) : Parcelable

@Parcelize
data class ConservationProgram(
    val id: String,
    val name: String,
    val target: String,
    val budget: Long,
    val startDate: Long,
    val progress: Double,
    val success: Boolean
) : Parcelable

@Parcelize
data class WildlifeTradeStatus(
    val legalExports: Long, // USD value
    val estimatedIllegalTrade: Long,
    val seizures: Int, // per year
    val trafficking: Double,
    val speciesAffected: List<String>
) : Parcelable

// Climate Status

@Parcelize
data class ClimateStatus(
    val averageTemperature: Double, // Celsius
    val temperatureChange: Double, // degrees since baseline
    val precipitation: Double, // mm per year
    val precipitationChange: Double,
    val seaLevelRise: Double, // mm since baseline
    val extremeWeatherEvents: Int, // per year
    val greenhouseGasEmissions: Long, // tons CO2 equivalent
    val emissionRate: Double, // per capita
    val carbonFootprint: Double,
    val mitigationProgress: Double,
    val adaptationProgress: Double,
    val renewableEnergyPercentage: Double,
    val fossilFuelDependency: Double,
    val climateVulnerability: Double,
    val climateRisk: ClimateRisk,
    val mitigationPolicies: List<MitigationPolicy>,
    val adaptationMeasures: List<AdaptationMeasure>,
    val carbonTrading: CarbonMarket?
) : Parcelable

@Parcelize
data class ClimateRisk(
    val overall: Double,
    val coastal: Double,
    val agricultural: Double,
    val health: Double,
    val economic: Double,
    val extreme: Double,
    val water: Double,
    val food: Double
) : Parcelable

@Parcelize
data class MitigationPolicy(
    val id: String,
    val name: String,
    val type: MitigationType,
    val description: String,
    val startDate: Long,
    val targetDate: Long,
    val target: String,
    val progress: Double,
    val effectiveness: Double,
    val cost: Long
) : Parcelable

enum class MitigationType {
    RENEWABLE_ENERGY,
    ENERGY_EFFICIENCY,
    CARBON_TAX,
    CAP_AND_TRADE,
    ELECTRIC_VEHICLES,
    FOREST_CONSERVATION,
    METHANE_REDUCTION,
    INDUSTRIAL_EFFICIENCY,
    PUBLIC_TRANSPORT,
    BUILDING_CODES,
    PHASE_OUT_FOSSIL
}

@Parcelize
data class AdaptationMeasure(
    val id: String,
    val name: String,
    val type: AdaptationType,
    val description: String,
    val startDate: Long,
    val progress: Double,
    val budget: Long,
    val beneficiaries: Long
) : Parcelable

enum class AdaptationType {
    COASTAL_DEFENSE,
    FLOOD_CONTROL,
    DROUGHT_RESILIENCE,
    HEAT_WAVE_RESPONSE,
    AGRICULTURAL_ADAPTATION,
    WATER_MANAGEMENT,
    URBAN_GREENING,
    EARLY_WARNING,
    MIGRATION_PLANNING,
    INSURANCE_SCHEMES,
    INFRASTRUCTURE_RESILIENCE
}

@Parcelize
data class CarbonMarket(
    val id: String,
    val carbonPrice: Double, // per ton
    val tradedVolume: Long,
    val participants: Int,
    val revenue: Long,
    val linked: Boolean,
    val linkedMarkets: List<String>
) : Parcelable

// Pollution Tracking

@Parcelize
data class PollutionTracking(
    val totalIndex: Double, // 0-100 (lower is better)
    val air: AirPollution,
    val water: WaterPollution,
    val land: LandPollution,
    val noise: NoisePollution,
    val light: LightPollution,
    val radioactive: RadioactivePollution?,
    val majorPolluters: List<MajorPolluter>,
    val cleanupSites: List<CleanupSite>,
    val enforcementActions: Int
) : Parcelable

@Parcelize
data class AirPollution(
    val industrialSources: Long, // tons per year
    val transportSources: Long,
    val agriculturalSources: Long,
    val domesticSources: Long,
    val transboundary: Long,
    val totalEmissions: Long,
    val controlled: Double // percentage
) : Parcelable

@Parcelize
data class WaterPollution(
    val industrialDischarge: Long,
    val sewageDischarge: Long,
    val agriculturalRunoff: Long,
    val oilSpills: Int,
    val plasticPollution: Long, // tons
    val microplastics: Double, // particles per liter
    val treated: Double // percentage
) : Parcelable

@Parcelize
data class LandPollution(
    val illegalDumping: Int, // sites
    val hazardousWaste: Long, // tons
    val municipalWaste: Long,
    val recyclingRate: Double,
    val landfillCapacity: Double, // percentage full
    val contaminatedSites: Int
) : Parcelable

@Parcelize
data class NoisePollution(
    val urbanNoise: Double, // dB average
    val industrialNoise: Double,
    val transportationNoise: Double,
    val affectedPopulation: Long,
    val regulations: Boolean
) : Parcelable

@Parcelize
data class LightPollution(
    val affectedArea: Int, // sq km
    val affectedPopulation: Long,
    val energyWaste: Long, // kWh
    val darkSkyAreas: Int
) : Parcelable

@Parcelize
data class RadioactivePollution(
    val source: String,
    val level: Double,
    val affectedArea: Int,
    val affectedPopulation: Long,
    val cleanupStatus: String
) : Parcelable

@Parcelize
data class MajorPolluter(
    val id: String,
    val name: String,
    val type: String,
    val emissions: Long,
    val pollutants: List<String>,
    val fines: Long,
    val compliance: Boolean
) : Parcelable

@Parcelize
data class CleanupSite(
    val id: String,
    val name: String,
    val type: String,
    val pollutants: List<String>,
    val startDate: Long,
    val estimatedCompletion: Long,
    val progress: Double,
    val cost: Long
) : Parcelable

// Environmental Resources

@Parcelize
data class EnvironmentalResources(
    val freshwater: FreshwaterResources,
    val minerals: MineralResources,
    val forests: ForestResources,
    val fisheries: FisheryResources,
    val renewable: RenewableResources,
    val fossil: FossilResources
) : Parcelable

@Parcelize
data class FreshwaterResources(
    val totalRenewable: Long, // cubic meters per year
    val totalWithdrawal: Long,
    val perCapita: Double,
    val stressLevel: Double,
    val seasonalVariability: Double
) : Parcelable

@Parcelize
data class MineralResources(
    val reserves: Map<String, Long>, // mineral -> tons
    val production: Map<String, Long>,
    val exports: Map<String, Long>,
    val explorationActive: Boolean
) : Parcelable

@Parcelize
data class ForestResources(
    val totalArea: Int, // sq km
    val commercialForest: Int,
    val protectedForest: Int,
    val annualGrowth: Long, // cubic meters
    val annualHarvest: Long,
    val sustainable: Boolean
) : Parcelable

@Parcelize
data class FisheryResources(
    val totalCatch: Long, // tons per year
    val fishStocks: Map<String, Double>, // species -> health 0-1
    val overfished: List<String>,
    val aquaculture: Long,
    val exclusiveEconomicZone: Int, // sq km
    val fishingFleet: Int,
    val sustainable: Boolean
) : Parcelable

@Parcelize
data class RenewableResources(
    val solarPotential: Double, // kW per sq m
    val windPotential: Double,
    val hydroPotential: Long, // MW
    val geothermalPotential: Long,
    val biomassPotential: Long,
    val installedCapacity: Long,
    val production: Long // MWh per year
) : Parcelable

@Parcelize
data class FossilResources(
    val oilReserves: Long, // barrels
    val gasReserves: Long, // cubic meters
    val coalReserves: Long, // tons
    val remainingYears: Int,
    val production: Map<String, Long>
) : Parcelable

// Protected Areas

@Parcelize
data class ProtectedArea(
    val id: String,
    val name: String,
    val type: ProtectedAreaType,
    val area: Int, // sq km
    val established: Int,
    val management: String,
    val biodiversityIndex: Double,
    val visitorLimit: Int?,
    val annualVisitors: Long,
    val threats: List<String>,
    val unescoStatus: UNESCOTier?
) : Parcelable

enum class ProtectedAreaType {
    NATIONAL_PARK,
    WILDLIFE_SANCTUARY,
    NATURE_RESERVE,
    MARINE_PROTECTED_AREA,
    BIOSPHERE_RESERVE,
    WORLD_HERITAGE_SITE,
    RAMSAR_SITE,
    STRICT_NATURE_RESERVE,
    HABITAT_MANAGEMENT_AREA,
    PROTECTED_LANDSCAPE,
    RESOURCE_RESERVE
}

enum class UNESCOTier {
    WORLD_HERITAGE,
    BIOSPHERE_RESERVE,
    GEOPARK,
    TENTATIVE_LIST
}

// Environmental Laws

@Parcelize
data class EnvironmentalLaw(
    val id: String,
    val name: String,
    val type: EnvironmentalLawType,
    val passedDate: Long,
    val description: String,
    val enforcement: Double,
    val amendments: Int,
    val lastAmendment: Long?
) : Parcelable

enum class EnvironmentalLawType {
    AIR_QUALITY,
    WATER_QUALITY,
    WASTE_MANAGEMENT,
    BIODIVERSITY,
    FORESTRY,
    FISHERIES,
    MINING,
    IMPACT_ASSESSMENT,
    POLLUTION_CONTROL,
    CLIMATE_CHANGE,
    CHEMICAL_SAFETY,
    NOISE_CONTROL,
    LAND_USE,
    COASTAL_MANAGEMENT
}

// Environmental Agency

@Parcelize
data class EnvironmentalAgency(
    val id: String,
    val name: String,
    val budget: Long,
    val staff: Int,
    val director: String?, // NPC ID
    val independence: Double,
    val enforcementPowers: Boolean,
    val inspectionsPerYear: Int,
    val finesIssued: Long,
    val prosecutions: Int
) : Parcelable

// Natural Disasters

@Parcelize
data class NaturalDisaster(
    val id: String,
    val type: DisasterType,
    val name: String,
    val startDate: Long,
    val endDate: Long?,
    val location: String,
    val magnitude: Double,
    val affected: Long, // people
    val deaths: Int,
    val injured: Int,
    val displaced: Long,
    val economicDamage: Long,
    val infrastructureDamage: InfrastructureDamage,
    val response: DisasterResponse,
    val internationalAid: Long,
    val ongoing: Boolean
) : Parcelable

enum class DisasterType {
    EARTHQUAKE,
    VOLCANIC_ERUPTION,
    TSUNAMI,
    HURRICANE_TYPHOON,
    TORNADO,
    FLOOD,
    DROUGHT,
    WILDFIRE,
    LANDSLIDE,
    AVALANCHE,
    HEAT_WAVE,
    COLD_WAVE,
    FAMINE,
    PEST_INFESTATION,
    DISEASE_OUTBREAK,
    SANDSTORM
}

@Parcelize
data class InfrastructureDamage(
    val buildingsDestroyed: Int,
    val buildingsDamaged: Int,
    val roadsDestroyed: Int, // km
    val bridgesDestroyed: Int,
    val powerOutages: Long, // people affected
    val waterSupplyAffected: Long,
    val schoolsDamaged: Int,
    val hospitalsDamaged: Int
) : Parcelable

@Parcelize
data class DisasterResponse(
    val leadAgency: String,
    val personnelDeployed: Int,
    val equipmentDeployed: List<String>,
    val evacuationOrders: Boolean,
    val sheltersOpened: Int,
    val internationalAssistance: Boolean,
    val responseTime: Int, // hours
    val effectiveness: Double
) : Parcelable

// Environmental Crisis

@Parcelize
data class EnvironmentalCrisis(
    val id: String,
    val title: String,
    val type: CrisisType,
    val severity: Int, // 1-10
    val startDate: Long,
    val description: String,
    val affectedArea: Int, // sq km
    val affectedPopulation: Long,
    val causes: List<String>,
    val status: CrisisStatus,
    val response: List<String>,
    val internationalAttention: Boolean
) : Parcelable

enum class CrisisType {
    WATER_SCARCITY,
    AIR_EMERGENCY,
    TOXIC_SPILL,
    RADIATION_LEAK,
    DEFORESTATION_CRISIS,
    WILDLIFE_EMERGENCY,
    CORAL_BLEACHING,
    ALGAL_BLOOM,
    SMOG_EPISODE,
    SINKHOLE,
    DAM_FAILURE
}

enum class CrisisStatus {
    EMERGENT,
    ESCALATING,
    PEAK,
    STABILIZING,
    RESOLVING,
    RESOLVED,
    CHRONIC
}

// Green Initiatives

@Parcelize
data class GreenInitiative(
    val id: String,
    val name: String,
    val type: GreenInitiativeType,
    val description: String,
    val startDate: Long,
    val targetDate: Long,
    val budget: Long,
    val progress: Double,
    val impact: String,
    val stakeholders: List<String>
) : Parcelable

enum class GreenInitiativeType {
    RENEWABLE_ENERGY,
    ENERGY_EFFICIENCY,
    WASTE_REDUCTION,
    RECYCLING,
    REF0RESTATION,
    URBAN_GREENING,
    SUSTAINABLE_TRANSPORT,
    WATER_CONSERVATION,
    ORGANIC_FARMING,
    CLEAN_TECH,
    GREEN_BUILDING,
    CIRCULAR_ECONOMY,
    CARBON_CAPTURE,
    PLASTIC_BAN
}

// Environmental Protests

@Parcelize
data class EnvironmentalProtest(
    val id: String,
    val title: String,
    val date: Long,
    val location: String,
    val participants: Int,
    val cause: String,
    val demands: List<String>,
    val response: String,
    val outcome: String?,
    val arrests: Int
) : Parcelable
