package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents economic systems and statistics.
 */
@Parcelize
data class Economy(
    val countryId: String,
    val gdp: Long, // in USD
    val gdpGrowth: Double, // percentage
    val gdpPerCapita: Double,
    val unemploymentRate: Double,
    val inflationRate: Double,
    val interestRate: Double,
    val exchangeRate: Double, // local currency to USD
    val governmentRevenue: Long,
    val governmentSpending: Long,
    val budgetDeficit: Long,
    val publicDebt: Long,
    val debtToGdpRatio: Double,
    val foreignReserves: Long,
    val tradeBalance: Long, // exports - imports
    val exports: Long,
    val imports: Long,
    val taxRates: TaxRates,
    val sectors: List<EconomicSector>,
    val tradePartners: List<TradePartner>,
    val lastUpdated: Long
) : Parcelable {
    
    val budgetBalance: Long
        get() = governmentRevenue - governmentSpending
    
    val hasBudgetSurplus: Boolean
        get() = budgetBalance > 0
    
    val economicHealthIndex: Double
        get() {
            var score = 50.0
            score += gdpGrowth * 2
            score -= unemploymentRate
            score -= inflationRate * 0.5
            score -= if (debtToGdpRatio > 60) (debtToGdpRatio - 60) * 0.3 else 0.0
            return score.coerceIn(0.0, 100.0)
        }
}

@Parcelize
data class TaxRates(
    val personalIncomeTax: List<TaxBracket>,
    val corporateTax: Double,
    val capitalGainsTax: Double,
    val salesTax: Double,
    val propertyTax: Double,
    val importTariff: Double,
    val exportTariff: Double,
    val wealthTax: Double
) : Parcelable

@Parcelize
data class TaxBracket(
    val minIncome: Long,
    val maxIncome: Long?,
    val rate: Double
) : Parcelable

@Parcelize
data class EconomicSector(
    val name: String,
    val gdpContribution: Double, // percentage
    val employment: Long,
    val growth: Double,
    val investmentLevel: Double, // 0.0 to 1.0
    val governmentSupport: Double // 0.0 to 1.0
) : Parcelable

@Parcelize
data class TradePartner(
    val countryId: String,
    val exportsTo: Long, // value in USD
    val importsFrom: Long,
    val tradeBalance: Long,
    val primaryExports: List<String>,
    val primaryImports: List<String>,
    val hasTradeAgreement: Boolean
) : Parcelable

/**
 * Represents the national budget.
 */
@Parcelize
data class Budget(
    val id: String,
    val fiscalYear: Int,
    val countryId: String,
    val totalRevenue: Long,
    val totalSpending: Long,
    val categories: List<BudgetCategory>,
    val proposedBy: String, // npcId or playerId
    val proposedDate: Long,
    val approvedBy: String?, // parliament body
    val approvedDate: Long?,
    val status: BudgetStatus,
    val amendments: List<BudgetAmendment>
) : Parcelable {
    
    val deficit: Long
        get() = totalSpending - totalRevenue
    
    val deficitPercentage: Double
        get() = if (totalRevenue > 0) (deficit.toDouble() / totalRevenue) * 100 else 0.0
    
    val isBalanced: Boolean
        get() = deficit <= 0
}

enum class BudgetStatus {
    DRAFT,
    PROPOSED,
    IN_COMMITTEE,
    DEBATING,
    VOTING,
    APPROVED,
    REJECTED,
    VETOED,
    ENACTED,
    EXECUTING,
    CLOSED
}

@Parcelize
data class BudgetCategory(
    val name: String,
    val allocatedAmount: Long,
    val spentAmount: Long,
    val percentage: Double,
    val lineItems: List<BudgetLineItem>
) : Parcelable {
    
    val remaining: Long
        get() = allocatedAmount - spentAmount
    
    val utilizationRate: Double
        get() = if (allocatedAmount > 0) spentAmount.toDouble() / allocatedAmount else 0.0
}

@Parcelize
data class BudgetLineItem(
    val id: String,
    val name: String,
    val description: String,
    val allocatedAmount: Long,
    val spentAmount: Long,
    val department: String?,
    val isMandatory: Boolean
) : Parcelable

@Parcelize
data class BudgetAmendment(
    val id: String,
    val proposedBy: String,
    val lineItemId: String,
    val originalAmount: Long,
    val proposedAmount: Long,
    val reason: String,
    val status: AmendmentStatus
) : Parcelable

/**
 * Represents a trade deal.
 */
@Parcelize
data class TradeDeal(
    val id: String,
    val name: String,
    val type: TradeDealType,
    val partners: List<String>, // countryIds
    val signedDate: Long,
    val effectiveDate: Long?,
    val expiryDate: Long?,
    val terms: List<TradeTerm>,
    val tariffReductions: List<TariffReduction>,
    val quotas: List<TradeQuota>,
    val disputes: List<TradeDispute>,
    val isActive: Boolean,
    val annualTradeValue: Long
) : Parcelable

enum class TradeDealType {
    FREE_TRADE_AREA,
    CUSTOMS_UNION,
    COMMON_MARKET,
    ECONOMIC_UNION,
    BILATERAL_PREFERENCE,
    SECTORAL_AGREEMENT
}

@Parcelize
data class TradeTerm(
    val id: String,
    val article: String,
    val text: String,
    val sector: String?
) : Parcelable

@Parcelize
data class TariffReduction(
    val productId: String,
    val productName: String,
    val originalTariff: Double,
    val reducedTariff: Double,
    val eliminationDate: Long?
) : Parcelable

@Parcelize
data class TradeQuota(
    val productId: String,
    val productName: String,
    val quotaAmount: Long,
    val tariffAboveQuota: Double
) : Parcelable

@Parcelize
data class TradeDispute(
    val id: String,
    val complainant: String, // countryId
    val respondent: String, // countryId
    val issue: String,
    val filedDate: Long,
    val status: DisputeStatus,
    val resolution: String?,
    val resolutionDate: Long?
) : Parcelable

enum class DisputeStatus {
    FILED,
    CONSULTATION,
    PANEL_ESTABLISHED,
    UNDER_REVIEW,
    DECIDED,
    APPEALED,
    RESOLVED,
    DISMISSED
}

/**
 * Represents oil and energy resources.
 */
@Parcelize
data class OilAndEnergy(
    val countryId: String,
    val oilReserves: Long, // barrels
    val oilProduction: Long, // barrels per day
    val oilConsumption: Long,
    val oilExports: Long,
    val oilPrice: Double, // per barrel
    val naturalGasReserves: Long, // cubic meters
    val naturalGasProduction: Long,
    val coalReserves: Long,
    val coalProduction: Long,
    val renewableCapacity: Long, // MW
    val renewablePercentage: Double,
    val energyIndependence: Double, // percentage self-sufficient
    val opecMember: Boolean,
    val refineries: List<Refinery>,
    val powerPlants: List<PowerPlant>
) : Parcelable

@Parcelize
data class Refinery(
    val id: String,
    val name: String,
    val location: String,
    val capacity: Long, // barrels per day
    val utilization: Double
) : Parcelable

@Parcelize
data class PowerPlant(
    val id: String,
    val name: String,
    val type: PowerPlantType,
    val capacity: Long, // MW
    val location: String,
    val isActive: Boolean,
    val constructionYear: Int
) : Parcelable

enum class PowerPlantType {
    COAL,
    NATURAL_GAS,
    NUCLEAR,
    HYDRO,
    SOLAR,
    WIND,
    GEOTHERMAL,
    BIOMASS
}
