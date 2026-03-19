package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a procedurally generated country in the game world.
 */
@Parcelize
data class Country(
    val id: String,
    val name: String,
    val officialName: String,
    val flagEmoji: String,
    val flagColors: List<String>,
    val continent: Continent,
    val governmentType: GovernmentType,
    val population: Long,
    val areaKm2: Long,
    val capitalCity: String,
    val majorCities: List<String>,
    val languages: List<String>,
    val currency: Currency,
    val resources: List<NaturalResource>,
    val terrain: List<TerrainType>,
    val climate: ClimateType,
    val gdpPerCapita: Double,
    val happinessIndex: Double,
    val stabilityIndex: Double,
    val militaryStrength: Int,
    val techLevel: Int,
    val relations: Map<String, Int>, // countryId -> relationship score (-100 to 100)
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable {
    
    val populationInMillions: Double
        get() = population / 1_000_000.0
    
    val gdpTotal: Double
        get() = gdpPerCapita * population
    
    val isNuclearPower: Boolean
        get() = militaryStrength > 800 && techLevel > 70
    
    val isDemocracy: Boolean
        get() = governmentType in listOf(
            GovernmentType.PARLIAMENTARY_DEMOCRACY,
            GovernmentType.PRESIDENTIAL_DEMOCRACY,
            GovernmentType.CONSTITUTIONAL_MONARCHY
        )
    
    val overallRating: Double
        get() = (happinessIndex + stabilityIndex + (techLevel / 10.0)) / 3
}

enum class Continent {
    AFRICA,
    ASIA,
    EUROPE,
    NORTH_AMERICA,
    SOUTH_AMERICA,
    OCEANIA,
    MIDDLE_EAST
}

enum class GovernmentType {
    PRESIDENTIAL_DEMOCRACY,
    PARLIAMENTARY_DEMOCRACY,
    CONSTITUTIONAL_MONARCHY,
    ABSOLUTE_MONARCHY,
    DICTATORSHIP,
    TECHNOCRACY,
    THEOCRACY,
    MILITARY_JUNTA,
    SINGLE_PARTY_STATE,
    OLIGARCHY
}

@Parcelize
data class Currency(
    val name: String,
    val code: String,
    val symbol: String,
    val exchangeRateToUsd: Double
) : Parcelable

@Parcelize
data class NaturalResource(
    val name: String,
    val type: ResourceType,
    val abundance: Double, // 0.0 to 1.0
    val extractionDifficulty: Double, // 0.0 to 1.0
    val annualProduction: Double
) : Parcelable

enum class ResourceType {
    OIL,
    NATURAL_GAS,
    COAL,
    IRON_ORE,
    GOLD,
    SILVER,
    COPPER,
    DIAMONDS,
    RARE_EARTH,
    URANIUM,
    TIMBER,
    FRESH_WATER,
    ARABLE_LAND,
    FISH,
    LITHIUM
}

enum class TerrainType {
    MOUNTAINS,
    HILLS,
    PLAINS,
    DESERT,
    RAINFOREST,
    COASTLINE,
    ISLANDS,
    TUNDRA,
    WETLANDS
}

enum class ClimateType {
    TROPICAL,
    SUBTROPICAL,
    TEMPERATE,
    CONTINENTAL,
    ARID,
    POLAR,
    MEDITERRANEAN
}
