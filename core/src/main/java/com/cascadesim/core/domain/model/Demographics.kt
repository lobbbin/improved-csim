package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents demographics and population data.
 */
@Parcelize
data class Demographics(
    val countryId: String,
    val totalPopulation: Long,
    val populationGrowthRate: Double,
    val birthRate: Double, // per 1000
    val deathRate: Double, // per 1000
    val netMigration: Long, // annual
    val ageDistribution: List<AgeGroup>,
    val genderDistribution: GenderDistribution,
    val urbanPopulation: Long,
    val ruralPopulation: Long,
    val urbanizationRate: Double,
    val literacyRate: Double,
    val lifeExpectancy: Double,
    val infantMortality: Double, // per 1000 births
    val medianAge: Double,
    val ethnicGroups: List<EthnicGroup>,
    val religions: List<ReligionGroup>,
    val languages: List<LanguageGroup>,
    val educationLevels: List<EducationLevel>,
    val incomeDistribution: IncomeDistribution,
    val happinessIndex: Double,
    val lastCensus: Long
) : Parcelable {
    
    val populationDensity: Double
        get() = 0.0 // Would need area from country
    
    val isAgingPopulation: Boolean
        get() = medianAge > 40
    
    val workingAgePopulation: Long
        get() = ageDistribution.filter { it.ageRange.first in 15..64 }.sumOf { it.count }
    
    val dependencyRatio: Double
        get() {
            val dependents = ageDistribution.filter { it.ageRange.first < 15 || it.ageRange.first >= 65 }.sumOf { it.count }
            return if (workingAgePopulation > 0) dependents.toDouble() / workingAgePopulation else 0.0
        }
}

@Parcelize
data class AgeGroup(
    val ageMin: Int,
    val ageMax: Int,
    val count: Long,
    val percentage: Double,
    val maleCount: Long,
    val femaleCount: Long
) : Parcelable {
    val ageRange: IntRange get() = ageMin..ageMax
}

@Parcelize
data class GenderDistribution(
    val male: Long,
    val female: Long,
    val other: Long,
    val ratio: Double // males per 100 females
) : Parcelable

@Parcelize
data class EthnicGroup(
    val name: String,
    val count: Long,
    val percentage: Double,
    val regions: List<String>
) : Parcelable

@Parcelize
data class ReligionGroup(
    val name: String,
    val count: Long,
    val percentage: Double
) : Parcelable

@Parcelize
data class LanguageGroup(
    val name: String,
    val count: Long,
    val percentage: Double,
    val isOfficial: Boolean
) : Parcelable

@Parcelize
data class EducationLevel(
    val level: String,
    val count: Long,
    val percentage: Double
) : Parcelable

@Parcelize
data class IncomeDistribution(
    val povertyRate: Double,
    val giniCoefficient: Double,
    val incomeBrackets: List<IncomeBracket>
) : Parcelable

@Parcelize
data class IncomeBracket(
    val incomeMin: Long,
    val incomeMax: Long,
    val count: Long,
    val percentage: Double
) : Parcelable {
    val range: ClosedRange<Long> get() = incomeMin..incomeMax
}

/**
 * Represents immigration and border control.
 */
@Parcelize
data class ImmigrationPolicy(
    val countryId: String,
    val openBorders: Boolean,
    val visaRequirements: Map<String, VisaRequirement>, // countryId -> requirement
    val refugeeQuota: Int,
    val asylumSeekers: Int,
    val annualImmigration: Long,
    val annualEmigration: Long,
    val deportations: Long,
    val citizenshipRequirements: CitizenshipRequirements,
    val borderSecurityLevel: Double, // 0.0 to 1.0
    val illegalImmigrationEstimate: Long,
    val integrationPrograms: List<IntegrationProgram>
) : Parcelable

@Parcelize
data class VisaRequirement(
    val countryId: String,
    val visaType: VisaType,
    val fee: Long,
    val processingTime: Long, // days
    val validity: Long, // days
    val restrictions: List<String>
) : Parcelable

enum class VisaType {
    VISA_FREE,
    VISA_ON_ARRIVAL,
    E_VISA,
    STANDARD_VISA,
    RESTRICTED,
    BANNED
}

@Parcelize
data class CitizenshipRequirements(
    val residencyYears: Int,
    val languageRequired: Boolean,
    val testRequired: Boolean,
    val dualCitizenshipAllowed: Boolean,
    val investmentPath: Boolean,
    val investmentAmount: Long?
) : Parcelable

@Parcelize
data class IntegrationProgram(
    val id: String,
    val name: String,
    val description: String,
    val budget: Long,
    val participants: Int,
    val successRate: Double
) : Parcelable

/**
 * Represents health and social welfare.
 */
@Parcelize
data class HealthSystem(
    val countryId: String,
    val healthcareType: HealthcareType,
    val healthcareSpending: Long,
    val healthcareSpendingPerCapita: Double,
    val healthcareSpendingPercentageGdp: Double,
    val hospitals: Int,
    val hospitalBeds: Int,
    val doctors: Int,
    val nurses: Int,
    val universalCoverage: Boolean,
    val vaccinationRate: Double,
    val healthCampaigns: List<HealthCampaign>,
    val diseaseOutbreaks: List<DiseaseOutbreak>,
    val mentalHealthServices: Double, // availability 0.0 to 1.0
    val healthInsuranceCoverage: Double
) : Parcelable

enum class HealthcareType {
    NATIONAL_HEALTH_SERVICE,
    SINGLE_PAYER,
    MULTI_PAYER,
    PRIVATE_INSURANCE,
    MIXED_SYSTEM,
    DEVELOPING_SYSTEM
}

@Parcelize
data class HealthCampaign(
    val id: String,
    val name: String,
    val description: String,
    val targetDisease: String,
    val startDate: Long,
    val endDate: Long?,
    val budget: Long,
    val reach: Int,
    val effectiveness: Double
) : Parcelable

@Parcelize
data class DiseaseOutbreak(
    val id: String,
    val disease: String,
    val startDate: Long,
    val endDate: Long?,
    val cases: Int,
    val deaths: Int,
    val regions: List<String>,
    val containmentLevel: Double
) : Parcelable

/**
 * Represents social tensions and issues.
 */
@Parcelize
data class SocialTensions(
    val countryId: String,
    val overallTension: Double, // 0.0 to 1.0
    val ethnicTensions: List<EthnicTension>,
    val religiousTensions: List<ReligiousTension>,
    val classTensions: Double,
    val politicalPolarization: Double,
    val regionalSeparatism: List<SeparatistMovement>,
    val activeProtests: List<Protest>,
    val civilUnrestRisk: Double,
    val lastUpdated: Long
) : Parcelable

@Parcelize
data class EthnicTension(
    val groupA: String,
    val groupB: String,
    val tensionLevel: Double, // 0.0 to 1.0
    val mainIssues: List<String>,
    val recentIncidents: Int
) : Parcelable

@Parcelize
data class ReligiousTension(
    val groupA: String,
    val groupB: String,
    val tensionLevel: Double,
    val mainIssues: List<String>,
    val isViolent: Boolean
) : Parcelable

@Parcelize
data class SeparatistMovement(
    val id: String,
    val name: String,
    val region: String,
    val support: Double, // percentage in region
    val violent: Boolean,
    val demands: List<String>,
    val negotiations: Boolean
) : Parcelable

@Parcelize
data class Protest(
    val id: String,
    val cause: String,
    val startDate: Long,
    val endDate: Long?,
    val participants: Int,
    val locations: List<String>,
    val violence: Boolean,
    val governmentResponse: String
) : Parcelable
