package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents infrastructure systems.
 */
@Parcelize
data class Infrastructure(
    val countryId: String,
    val overallQuality: Double, // 0.0 to 1.0
    val transport: TransportInfrastructure,
    val energy: EnergyInfrastructure,
    val communications: CommunicationsInfrastructure,
    val water: WaterInfrastructure,
    val publicBuildings: PublicBuildings,
    val ongoingProjects: List<InfrastructureProject>,
    val investmentNeeded: Long,
    val annualInvestment: Long
) : Parcelable

@Parcelize
data class TransportInfrastructure(
    val roadsTotalKm: Long,
    val pavedRoadsKm: Long,
    val highwaysKm: Long,
    val roadQuality: Double, // 0.0 to 1.0
    val railwaysKm: Long,
    val railwayElectrified: Double, // percentage
    val airports: Int,
    val majorPorts: Int,
    val publicTransitSystems: List<PublicTransit>,
    val congestionLevel: Double, // 0.0 to 1.0
    val maintenanceBacklog: Long // USD needed
) : Parcelable {
    
    val roadPavedPercentage: Double
        get() = if (roadsTotalKm > 0) (pavedRoadsKm.toDouble() / roadsTotalKm) * 100 else 0.0
}

@Parcelize
data class PublicTransit(
    val id: String,
    val city: String,
    val type: TransitType,
    val lines: Int,
    val stations: Int,
    val dailyRidership: Long,
    val coverage: Double // 0.0 to 1.0
) : Parcelable

enum class TransitType {
    BUS,
    METRO,
    TRAM,
    LIGHT_RAIL,
    COMMUTER_RAIL,
    FERRY
}

@Parcelize
data class EnergyInfrastructure(
    val generationCapacity: Long, // MW
    val peakDemand: Long, // MW
    val reserveMargin: Double, // percentage
    val transmissionLines: Long, // km
    val distributionLosses: Double, // percentage
    val smartGridAdoption: Double, // 0.0 to 1.0
    val renewablePercentage: Double,
    val nuclearPlants: Int,
    val blackouts: Int, // annual count
    val electrificationRate: Double // percentage of population
) : Parcelable {
    
    val hasSufficientCapacity: Boolean
        get() = reserveMargin >= 15
}

@Parcelize
data class CommunicationsInfrastructure(
    val internetPenetration: Double, // percentage
    val broadbandSpeed: Double, // Mbps average
    val mobileSubscriptions: Long,
    val fiberCoverage: Double, // percentage of households
    val _5gCoverage: Double,
    val ruralConnectivity: Double,
    val dataCenters: Int
) : Parcelable

@Parcelize
data class WaterInfrastructure(
    val totalCapacity: Long, // cubic meters per day
    val treatmentPlants: Int,
    val pipedWaterAccess: Double, // percentage of population
    val sanitationAccess: Double,
    val waterQuality: Double, // 0.0 to 1.0
    val leaksPercentage: Double,
    val droughtRisk: Double // 0.0 to 1.0
) : Parcelable

@Parcelize
data class PublicBuildings(
    val schools: Int,
    val hospitals: Int,
    val governmentBuildings: Int,
    val sportsFacilities: Int,
    val culturalFacilities: Int,
    val averageCondition: Double // 0.0 to 1.0
) : Parcelable

/**
 * Represents an infrastructure construction project.
 */
@Parcelize
data class InfrastructureProject(
    val id: String,
    val name: String,
    val type: ProjectType,
    val description: String,
    val location: String,
    val estimatedCost: Long,
    val actualCost: Long?,
    val fundingSource: FundingSource,
    val contractor: String?,
    val startDate: Long,
    val estimatedCompletion: Long,
    val actualCompletion: Long?,
    val progress: Double, // 0.0 to 1.0
    val status: ProjectStatus,
    val jobsCreated: Int,
    val challenges: List<String>,
    val benefits: List<String>
) : Parcelable

enum class ProjectType {
    ROAD,
    BRIDGE,
    HIGHWAY,
    RAILWAY,
    AIRPORT,
    PORT,
    POWER_PLANT,
    GRID_EXPANSION,
    WATER_TREATMENT,
    DAM,
    TELECOMMUNICATIONS,
    PUBLIC_BUILDING,
    SPORTS_FACILITY,
    URBAN_DEVELOPMENT
}

enum class FundingSource {
    GOVERNMENT,
    PUBLIC_PRIVATE_PARTNERSHIP,
    FOREIGN_INVESTMENT,
    INTERNATIONAL_AID,
    MUNICIPAL_BONDS,
    MIXED
}

enum class ProjectStatus {
    PLANNING,
    TENDERING,
    APPROVED,
    GROUND_BROKEN,
    IN_PROGRESS,
    ON_HOLD,
    DELAYED,
    COMPLETED,
    CANCELLED
}
