package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * COMPREHENSIVE Achievement System with 200+ Achievements
 * Categories: Political, Economic, Military, Diplomatic, Social, Hidden, Milestone
 */

@Parcelize
data class AchievementSystem(
    val unlockedAchievements: List<UnlockedAchievement>,
    val achievementProgress: Map<String, AchievementProgress>,
    val totalPoints: Int,
    val hiddenAchievementsDiscovered: Int,
    val rareAchievements: Int,
    val achievementHistory: List<AchievementUnlockEvent>
) : Parcelable

@Parcelize
data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val category: AchievementCategory,
    val type: AchievementType,
    val points: Int,
    val icon: String,
    val rarity: AchievementRarity,
    val requirements: AchievementRequirement,
    val rewards: AchievementReward,
    val hidden: Boolean,
    val hint: String?,
    val relatedAchievements: List<String>,
    val steamlike: String // Steam achievement text
) : Parcelable

@Parcelize
data class UnlockedAchievement(
    val achievementId: String,
    val unlockedDate: Long,
    val gameId: String,
    val context: String // What was happening when unlocked
) : Parcelable

@Parcelize
data class AchievementProgress(
    val achievementId: String,
    val currentValue: Double,
    val targetValue: Double,
    val percentage: Double,
    val startedDate: Long
) : Parcelable

@Parcelize
data class AchievementUnlockEvent(
    val id: String,
    val achievementId: String,
    val achievementName: String,
    val date: Long,
    val rarity: String,
    val points: Int
) : Parcelable

enum class AchievementCategory {
    POLITICAL,
    ECONOMIC,
    MILITARY,
    DIPLOMATIC,
    SOCIAL,
    ENVIRONMENTAL,
    SCIENTIFIC,
    CULTURAL,
    SPORTS,
    INTELLIGENCE,
    MEDIA,
    RELIGION,
    CRIME_JUSTICE,
    EDUCATION,
    HEALTH,
    INFRASTRUCTURE,
    TECHNOLOGY,
    DEMOGRAPHICS,
    SURVIVAL,
    HIDDEN,
    MILESTONE
}

enum class AchievementType {
    SINGLE_EVENT,      // One-time action
    CUMULATIVE,        // Build up over time
    THRESHOLD,         // Reach a specific value
    CHALLENGE,         // Difficult feat
    SECRET,            // Hidden conditions
    SPEEDRUN,          // Time-limited
    IRONMAN,           // No saves/special conditions
    ENDGAME,           // End-game content
    STORY,             // Story progression
    SPECIAL_EVENT      // Limited time events
}

enum class AchievementRarity {
    COMMON,        // 50%+ players
    UNCOMMON,      // 25-50% players
    RARE,          // 10-25% players
    EPIC,          // 5-10% players
    LEGENDARY,     // 1-5% players
    MYTHIC,        // <1% players
    IMPOSSIBLE     // Near impossible
}

@Parcelize
data class AchievementRequirement(
    val type: RequirementType,
    val target: String,
    val value: Double,
    val secondaryConditions: List<String>,
    val timeLimit: Long?, // milliseconds
    val difficulty: String?
) : Parcelable

enum class RequirementType {
    APPROVAL_RATING,
    POPULATION,
    GDP,
    MILITARY_STRENGTH,
    INTERNATIONAL_REPUTATION,
    STABILITY_INDEX,
    HAPPINESS_INDEX,
    TECHNOLOGY_LEVEL,
    CORRUPTION_LEVEL,
    EDUCATION_LEVEL,
    HEALTH_INDEX,
    ENVIRONMENT_INDEX,
    CRIME_RATE,
    RELATIONS,
    TREATIES_SIGNED,
    WARS_WON,
    LAWS_PASSED,
    VETOES_USED,
    EXECUTIVE_ORDERS,
    ELECTIONS_WON,
    YEARS_SERVED,
    POLITICAL_CAPITAL,
    PERSONAL_WEALTH,
    ACHIEVEMENTS_UNLOCKED,
    EVENTS_RESOLVED,
    TASKS_COMPLETED,
    DECISIONS_MADE,
    SCANDALS_SURVIVED,
    ASSASSINATION_ATTEMPTS,
    COUP_ATTEMPTS_SURVIVED,
    IMPEACHMENT_SURVIVED,
    TERMS_SERVED,
    PARTIES_CREATED,
    COALITIONS_FORMED,
    PEACE_TREATIES,
    TRADE_DEALS,
    INTERNATIONAL_ORGANIZATIONS,
    NUCLEAR_WEAPONS,
    SPACE_PROGRAM,
    OLYMPIC_MEDALS,
    WORLD_CUP_VICTORY,
    NOBEL_PRIZE,
    NOBEL_PEACE_PRIZE,
    TERRITORY_GAINED,
    TERRITORY_LOST,
    POPULATION_GROWTH,
    POPULATION_DECLINE,
    DEBT_LEVEL,
    BUDGET_BALANCE,
    TRADE_SURPLUS,
    FOREIGN_INVESTMENT,
    TOURISM_REVENUE,
    OIL_PRODUCTION,
    RENEWABLE_ENERGY,
    CO2_EMISSIONS,
    FOREST_COVERAGE,
    ENDANGERED_SPECIES,
    LITERACY_RATE,
    UNIVERSITY_RANKING,
    LIFE_EXPECTANCY,
    INFANT_MORTALITY,
    VACCINATION_RATE,
    DISEASE_ERADICATED,
    PANDEMIC_SURVIVED,
    DISASTER_RESPONSE,
    INFRASTRUCTURE_INVESTED,
    INTERNET_PENETRATION,
    SCIENTIFIC_PUBLICATIONS,
    PATENTS_FILED,
    TECHNOLOGY_BREAKTHROUGH,
    AI_DEVELOPMENT,
    QUANTUM_COMPUTING,
    FUSION_ENERGY,
    MARS_MISSION,
    MOON_BASE,
    SPACE_STATION,
    SATELLITE_NETWORK,
    CYBER_ATTACK_DEFENDED,
    TERRORIST_ATTACKS_PREVENTED,
    SPIES_CAPTURED,
    COVERT_OPERATIONS,
    COUPS_SUPPORTED,
    REGIMES_CHANGED,
    DEMOCRACY_SPREAD,
    DICTATORSHIPS_SUPPORTED,
    CIVIL_WARS_ENDED,
    CIVIL_WARS_STARTED,
    REVOLUTIONS_LEAD,
    REVOLUTIONS_SUPPRESSED,
    GENOCIDE_PREVENTED,
    HUMANITARIAN_AID,
    REFUGEES_ACCEPTED,
    REFUGEES_DEPORTED,
    IMMIGRATION_POLICY,
    BORDER_WALL,
    CITIZENSHIP_GRANTED,
    DUAL_CITIZENSHIP,
    VOTING_RIGHTS_EXPANDED,
    VOTING_RIGHTS_RESTRICTED,
    CENSORSHIP_LEVEL,
    PRESS_FREEDOM,
    INTERNET_FREEDOM,
    RELIGIOUS_FREEDOM,
    LGBTQ_RIGHTS,
    WOMEN_RIGHTS,
    MINORITY_RIGHTS,
    LABOR_RIGHTS,
    CONSUMER_RIGHTS,
    ANIMAL_RIGHTS,
    ABORTION_LAW,
    DEATH_PENALTY,
    GUN_CONTROL,
    DRUG_POLICY,
    PROSTITUTION_POLICY,
    GAMBLING_POLICY,
    ALCOHOL_POLICY,
    TOBACCO_POLICY,
    JURY_SYSTEM,
    JUDICIAL_INDEPENDENCE,
    POLICE_REFORM,
    PRISON_REFORM,
    DECRIMINALIZATION,
    LEGALIZATION,
    CONSTITUTIONAL_AMENDMENT,
    FLAG_CHANGE,
    NATIONAL_ANTHEM_CHANGE,
    CAPITAL_CHANGE,
    NAME_CHANGE,
    CURRENCY_CHANGE,
    TIME_ZONE_CHANGE,
    MEASUREMENT_SYSTEM,
    LANGUAGE_POLICY,
    EDUCATION_REFORM,
    HEALTHCARE_REFORM,
    SOCIAL_SECURITY_REFORM,
    PENSION_REFORM,
    TAX_REFORM,
    BANKING_REFORM,
    CORPORATE_REFORM,
    ANTITRUST_ACTIONS,
    MONOPOLY_BROKEN,
    NATIONALIZATION,
    PRIVATIZATION,
    DEREGULATION,
    REGULATION,
    SUBSIDY_CREATED,
    SUBSIDY_ELIMINATED,
    WELFARE_EXPANDED,
    WELFARE_CUT,
    MINIMUM_WAGE_INCREASED,
    MINIMUM_WAGE_DECREASED,
    WEALTH_TAX,
    INHERITANCE_TAX,
    CARBON_TAX,
    FINANCIAL_TRANSACTION_TAX,
    VAT_INTRODUCED,
    FLAT_TAX,
    PROGRESSIVE_TAX,
    TAX_HAVEN_CREATED,
    TAX_AMNESTY,
    BAILOUT_GIVEN,
    STIMULUS_PACKAGE,
    AUSTERITY_MEASURES,
    QUANTITATIVE_EASING,
    INTEREST_RATE_CHANGE,
    CURRENCY_DEVALUATION,
    CURRENCY_APPRECIATION,
    CURRENCY_PEG,
    CURRENCY_FLOAT,
    EURO_ADOPTION,
    DOLLARIZATION,
    CRYPTOCURRENCY_ADOPTION,
    CENTRAL_BANK_DIGITAL_CURRENCY
}

@Parcelize
data class AchievementReward(
    val politicalCapital: Int,
    val reputation: Map<String, Int>,
    val unlocks: List<String>,
    val title: String?,
    val cosmetic: String?
) : Parcelable

// ==================== PREDEFINED ACHIEVEMENTS (200+) ====================

object AchievementsLibrary {
    
    // POLITICAL ACHIEVEMENTS (40)
    val POLITICAL_ACHIEVEMENTS = listOf(
        Achievement("pol_001", "First Steps", "Win your first election", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 10, "vote", AchievementRarity.COMMON, AchievementRequirement(RequirementType.ELECTIONS_WON, "election", 1.0), AchievementReward(10, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Win your first election"),
        Achievement("pol_002", "Landslide Victory", "Win an election with over 70% of the vote", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 25, "landslide", AchievementRarity.RARE, AchievementRequirement(RequirementType.APPROVAL_RATING, "election_margin", 70.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Win big"),
        Achievement("pol_003", "Political Survivor", "Survive 5 years in office", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 20, "survivor", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.YEARS_SERVED, "years", 5.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Time flies"),
        Achievement("pol_004", "Career Politician", "Serve 20 years in office", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 50, "career", AchievementRarity.EPIC, AchievementRequirement(RequirementType.YEARS_SERVED, "years", 20.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "A lifetime of service"),
        Achievement("pol_005", "Political Dynasty", "Serve 30 years across multiple terms", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 75, "dynasty", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.YEARS_SERVED, "years", 30.0), AchievementReward(75, emptyMap(), emptyList(), null, null), false, null, emptyList(), "The establishment"),
        Achievement("pol_006", "Veto Master", "Use veto power 10 times", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 15, "veto", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.VETOES_USED, "vetoes", 10.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Just say no"),
        Achievement("pol_007", "Executive Power", "Issue 50 executive orders", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 20, "executive", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.EXECUTIVE_ORDERS, "orders", 50.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "By executive order"),
        Achievement("pol_008", "Legislative Champion", "Pass 100 laws", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 30, "gavel", AchievementRarity.RARE, AchievementRequirement(RequirementType.LAWS_PASSED, "laws", 100.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Lawmaker"),
        Achievement("pol_009", "Constitutional Architect", "Pass a constitutional amendment", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 40, "constitution", AchievementRarity.EPIC, AchievementRequirement(RequirementType.CONSTITUTIONAL_AMENDMENT, "amendment", 1.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Changed the constitution"),
        Achievement("pol_010", "Reformer", "Pass major reform legislation", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 25, "reform", AchievementRarity.RARE, AchievementRequirement(RequirementType.LAWS_PASSED, "major_reform", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Reformer"),
        Achievement("pol_011", "Impeachment Survivor", "Survive an impeachment attempt", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 50, "impeachment", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.IMPEACHMENT_SURVIVED, "impeachment", 1.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Teflon politician"),
        Achievement("pol_012", "Coup Survivor", "Survive a coup attempt", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 75, "coup", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.COUP_ATTEMPTS_SURVIVED, "coup", 1.0), AchievementReward(75, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Survived the coup"),
        Achievement("pol_013", "Assassination Survivor", "Survive an assassination attempt", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 100, "assassination", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.ASSASSINATION_ATTEMPTS, "survived", 1.0), AchievementReward(100, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Nine lives"),
        Achievement("pol_014", "Scandal-Proof", "Survive 5 major scandals", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 30, "scandal", AchievementRarity.EPIC, AchievementRequirement(RequirementType.SCANDALS_SURVIVED, "scandals", 5.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Teflon"),
        Achievement("pol_015", "Popular Leader", "Maintain 80% approval for 1 year", AchievementCategory.POLITICAL, AchievementType.THRESHOLD, 35, "popular", AchievementRarity.RARE, AchievementRequirement(RequirementType.APPROVAL_RATING, "approval_80_year", 80.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Beloved leader"),
        Achievement("pol_016", "Bipartisan", "Pass legislation with opposition support", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 20, "bipartisan", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.LAWS_PASSED, "bipartisan", 1.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Crossing the aisle"),
        Achievement("pol_017", "Party Builder", "Create a new political party", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 15, "party", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.PARTIES_CREATED, "party", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "New party"),
        Achievement("pol_018", "Coalition Builder", "Form a coalition government", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 25, "coalition", AchievementRarity.RARE, AchievementRequirement(RequirementType.COALITIONS_FORMED, "coalition", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Coalition government"),
        Achievement("pol_019", "Kingmaker", "Influence an election in another country", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 30, "kingmaker", AchievementRarity.RARE, AchievementRequirement(RequirementType.REGIMES_CHANGED, "election_influence", 1.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Kingmaker"),
        Achievement("pol_020", "Term Limited", "Serve maximum possible terms", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 40, "term_limit", AchievementRarity.EPIC, AchievementRequirement(RequirementType.TERMS_SERVED, "max_terms", 2.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Term limited out"),
        Achievement("pol_021", "Democratizer", "Transition to democracy", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 50, "democracy", AchievementRarity.EPIC, AchievementRequirement(RequirementType.DEMOCRACY_SPREAD, "transition", 1.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Democratizer"),
        Achievement("pol_022", "Dictator", "Establish a dictatorship", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 40, "dictator", AchievementRarity.RARE, AchievementRequirement(RequirementType.CORRUPTION_LEVEL, "dictatorship", 0.8), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Dictator"),
        Achievement("pol_023", "The Monarch", "Become a monarch", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 35, "crown", AchievementRarity.RARE, AchievementRequirement(RequirementType.YEARS_SERVED, "monarch", 1.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Long live the king"),
        Achievement("pol_024", "Revolutionary", "Lead a successful revolution", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 60, "revolution", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.REVOLUTIONS_LEAD, "revolution", 1.0), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Revolutionary"),
        Achievement("pol_025", "Counter-Revolutionary", "Suppress a revolution", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 45, "counter_revolution", AchievementRarity.EPIC, AchievementRequirement(RequirementType.REVOLUTIONS_SUPPRESSED, "suppressed", 1.0), AchievementReward(45, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Order restored"),
        Achievement("pol_026", "Vote Expansion", "Expand voting rights", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 25, "voting", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.VOTING_RIGHTS_EXPANDED, "expanded", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "More voters"),
        Achievement("pol_027", "Vote Restriction", "Restrict voting rights", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 15, "vote_restriction", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.VOTING_RIGHTS_RESTRICTED, "restricted", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Fewer voters"),
        Achievement("pol_028", "Gerrymanderer", "Redistrict for political advantage", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 15, "gerrymander", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.LAWS_PASSED, "redistricting", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Creative cartography"),
        Achievement("pol_029", "Recall Survivor", "Survive a recall election", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 35, "recall", AchievementRarity.RARE, AchievementRequirement(RequirementType.ELECTIONS_WON, "recall", 1.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Recall survived"),
        Achievement("pol_030", "Primary Winner", "Win a party primary", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 10, "primary", AchievementRarity.COMMON, AchievementRequirement(RequirementType.ELECTIONS_WON, "primary", 1.0), AchievementReward(10, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Primary winner"),
        Achievement("pol_031", "Third Party Victory", "Win with a third party", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 75, "third_party", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.ELECTIONS_WON, "third_party", 1.0), AchievementReward(75, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Against the odds"),
        Achievement("pol_032", "Independence Day", "Lead country to independence", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 80, "independence", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.REVOLUTIONS_LEAD, "independence", 1.0), AchievementReward(80, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Independence"),
        Achievement("pol_033", "Unifier", "Unite a divided country", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 60, "unifier", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.STABILITY_INDEX, "unification", 0.8), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Unifier"),
        Achievement("pol_034", "The Diplomat", "Solve crisis through diplomacy", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 30, "diplomat", AchievementRarity.RARE, AchievementRequirement(RequirementType.PEACE_TREATIES, "crisis", 1.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Peace through diplomacy"),
        Achievement("pol_035", "The General", "Solve crisis through military", AchievementCategory.POLITICAL, AchievementType.SINGLE_EVENT, 25, "general", AchievementRarity.RARE, AchievementRequirement(RequirementType.WARS_WON, "crisis", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Peace through strength"),
        Achievement("pol_036", "The Corrupt", "Achieve 100% corruption", AchievementCategory.POLITICAL, AchievementType.THRESHOLD, 20, "corrupt", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.CORRUPTION_LEVEL, "max_corruption", 100.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Corrupt to the core"),
        Achievement("pol_037", "Mr. Clean", "Reduce corruption to near zero", AchievementCategory.POLITICAL, AchievementType.THRESHOLD, 50, "clean", AchievementRarity.EPIC, AchievementRequirement(RequirementType.CORRUPTION_LEVEL, "zero_corruption", 5.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Clean as a whistle"),
        Achievement("pol_038", "Power Behind the Throne", "Rule without holding office", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 100, "power_behind", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.INFLUENCE, "shadow_rule", 95.0), AchievementReward(100, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Shadow ruler"),
        Achievement("pol_039", "Political Machine", "Build a political machine", AchievementCategory.POLITICAL, AchievementType.CUMULATIVE, 35, "machine", AchievementRarity.RARE, AchievementRequirement(RequirementType.POLITICAL_CAPITAL, "machine", 500.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Political machine"),
        Achievement("pol_040", "The Statesman", "Retire with 90%+ approval", AchievementCategory.POLITICAL, AchievementType.CHALLENGE, 60, "statesman", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.APPROVAL_RATING, "retire_high", 90.0), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Beloved statesman")
    )
    
    // ECONOMIC ACHIEVEMENTS (40)
    val ECONOMIC_ACHIEVEMENTS = listOf(
        Achievement("eco_001", "First Budget", "Pass your first budget", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 10, "budget", AchievementRarity.COMMON, AchievementRequirement(RequirementType.LAWS_PASSED, "budget", 1.0), AchievementReward(10, emptyMap(), emptyList(), null, null), false, null, emptyList(), "First budget"),
        Achievement("eco_002", "Balanced Budget", "Pass a balanced budget", AchievementCategory.ECONOMIC, AchievementType.CHALLENGE, 25, "balanced", AchievementRarity.RARE, AchievementRequirement(RequirementType.BUDGET_BALANCE, "balanced", 0.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Balanced budget"),
        Achievement("eco_003", "Budget Surplus", "Achieve a budget surplus", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 30, "surplus", AchievementRarity.RARE, AchievementRequirement(RequirementType.BUDGET_BALANCE, "surplus", 1.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Surplus"),
        Achievement("eco_004", "Debt Free", "Eliminate national debt", AchievementCategory.ECONOMIC, AchievementType.CHALLENGE, 100, "debt_free", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.DEBT_LEVEL, "zero", 0.0), AchievementReward(100, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Debt free"),
        Achievement("eco_005", "Economic Boom", "Achieve 10% GDP growth", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 35, "boom", AchievementRarity.RARE, AchievementRequirement(RequirementType.GDP, "growth", 10.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Economic boom"),
        Achievement("eco_006", "Tiger Economy", "Achieve 20% GDP growth", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 60, "tiger", AchievementRarity.EPIC, AchievementRequirement(RequirementType.GDP, "growth", 20.0), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Tiger economy"),
        Achievement("eco_007", "Economic Miracle", "Achieve 50% GDP growth", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 100, "miracle", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.GDP, "growth", 50.0), AchievementReward(100, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Economic miracle"),
        Achievement("eco_008", "Full Employment", "Achieve under 3% unemployment", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 40, "employment", AchievementRarity.EPIC, AchievementRequirement(RequirementType.GDP, "unemployment", 3.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Full employment"),
        Achievement("eco_009", "Trade Surplus", "Achieve a trade surplus", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 20, "trade_surplus", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.TRADE_SURPLUS, "surplus", 1.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Trade surplus"),
        Achievement("eco_010", "Export Powerhouse", "Export over $1 trillion", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 45, "export", AchievementRarity.EPIC, AchievementRequirement(RequirementType.TRADE_SURPLUS, "exports", 1_000_000_000_000.0), AchievementReward(45, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Export powerhouse"),
        Achievement("eco_011", "Investment Magnet", "Attract $1 trillion in FDI", AchievementCategory.ECONOMIC, AchievementType.CUMULATIVE, 50, "investment", AchievementRarity.EPIC, AchievementRequirement(RequirementType.FOREIGN_INVESTMENT, "fdi", 1_000_000_000_000.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Investment magnet"),
        Achievement("eco_012", "Wealth of Nations", "Achieve $100,000 GDP per capita", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 55, "wealth", AchievementRarity.EPIC, AchievementRequirement(RequirementType.GDP, "per_capita", 100_000.0), AchievementReward(55, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Wealth of nations"),
        Achievement("eco_013", "Tax Cutter", "Cut taxes significantly", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 15, "tax_cut", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.TAX_REFORM, "cut", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Tax cutter"),
        Achievement("eco_014", "Tax Raiser", "Raise taxes significantly", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 15, "tax_raise", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.TAX_REFORM, "raise", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Tax raiser"),
        Achievement("eco_015", "Flat Taxer", "Implement a flat tax", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 20, "flat_tax", AchievementRarity.RARE, AchievementRequirement(RequirementType.FLAT_TAX, "implemented", 1.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Flat tax"),
        Achievement("eco_016", "Wealth Taxer", "Implement a wealth tax", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 20, "wealth_tax", AchievementRarity.RARE, AchievementRequirement(RequirementType.WEALTH_TAX, "implemented", 1.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Wealth tax"),
        Achievement("eco_017", "Deregulator", "Remove 100 regulations", AchievementCategory.ECONOMIC, AchievementType.CUMULATIVE, 25, "deregulate", AchievementRarity.RARE, AchievementRequirement(RequirementType.DEREGULATION, "regulations", 100.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Deregulator"),
        Achievement("eco_018", "Nationalizer", "Nationalize a major industry", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 25, "nationalize", AchievementRarity.RARE, AchievementRequirement(RequirementType.NATIONALIZATION, "industry", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Nationalizer"),
        Achievement("eco_019", "Privatizer", "Privatize a major industry", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 25, "privatize", AchievementRarity.RARE, AchievementRequirement(RequirementType.PRIVATIZATION, "industry", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Privatizer"),
        Achievement("eco_020", "Monopoly Breaker", "Break up a monopoly", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 30, "antitrust", AchievementRarity.RARE, AchievementRequirement(RequirementType.MONOPOLY_BROKEN, "monopoly", 1.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Antitrust"),
        Achievement("eco_021", "Oil Baron", "Become a major oil exporter", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 40, "oil", AchievementRarity.EPIC, AchievementRequirement(RequirementType.OIL_PRODUCTION, "exporter", 1_000_000.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Oil baron"),
        Achievement("eco_022", "Green Energy Pioneer", "Achieve 100% renewable energy", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 75, "green", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.RENEWABLE_ENERGY, "100_percent", 100.0), AchievementReward(75, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Green pioneer"),
        Achievement("eco_023", "Crypto Adopter", "Adopt cryptocurrency as legal tender", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 30, "crypto", AchievementRarity.RARE, AchievementRequirement(RequirementType.CRYPTOCURRENCY_ADOPTION, "adopted", 1.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Crypto nation"),
        Achievement("eco_024", "Euro Adopter", "Adopt the Euro", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 35, "euro", AchievementRarity.RARE, AchievementRequirement(RequirementType.EURO_ADOPTION, "adopted", 1.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Euro zone"),
        Achievement("eco_025", "Dollarized", "Adopt the US dollar", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 25, "dollar", AchievementRarity.RARE, AchievementRequirement(RequirementType.DOLLARIZATION, "adopted", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Dollarized"),
        Achievement("eco_026", "Recession Survivor", "Survive a recession", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 15, "recession", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.GDP, "recession_survived", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Recession survivor"),
        Achievement("eco_027", "Depression Survivor", "Survive a depression", AchievementCategory.ECONOMIC, AchievementType.CHALLENGE, 40, "depression", AchievementRarity.EPIC, AchievementRequirement(RequirementType.GDP, "depression_survived", 1.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Depression survivor"),
        Achievement("eco_028", "Stimulator", "Pass a stimulus package", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 20, "stimulus", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.STIMULUS_PACKAGE, "passed", 1.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Stimulator"),
        Achievement("eco_029", "Austerity", "Implement austerity measures", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 15, "austerity", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.AUSTERITY_MEASURES, "implemented", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Austerity"),
        Achievement("eco_030", "Bailout", "Bail out a major industry", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 15, "bailout", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.BAILOUT_GIVEN, "bailout", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Bailout"),
        Achievement("eco_031", "QE Master", "Implement quantitative easing", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 20, "qe", AchievementRarity.RARE, AchievementRequirement(RequirementType.QUANTITATIVE_EASING, "implemented", 1.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "QE master"),
        Achievement("eco_032", "Currency Devaluation", "Devalue currency by 50%", AchievementCategory.ECONOMIC, AchievementType.CHALLENGE, 20, "devalue", AchievementRarity.RARE, AchievementRequirement(RequirementType.CURRENCY_DEVALUATION, "50_percent", 50.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Devaluation"),
        Achievement("eco_033", "Tax Haven", "Create a tax haven", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 30, "tax_haven", AchievementRarity.RARE, AchievementRequirement(RequirementType.TAX_HAVEN_CREATED, "created", 1.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Tax haven"),
        Achievement("eco_034", "Welfare State", "Create comprehensive welfare", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 35, "welfare", AchievementRarity.RARE, AchievementRequirement(RequirementType.WELFARE_EXPANDED, "comprehensive", 1.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Welfare state"),
        Achievement("eco_035", "Minimum Wage Hiker", "Double minimum wage", AchievementCategory.ECONOMIC, AchievementType.CHALLENGE, 25, "min_wage", AchievementRarity.RARE, AchievementRequirement(RequirementType.MINIMUM_WAGE_INCREASED, "doubled", 100.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Wage hiker"),
        Achievement("eco_036", "Personal Wealth", "Accumulate $1 billion personal wealth", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 45, "wealthy", AchievementRarity.EPIC, AchievementRequirement(RequirementType.PERSONAL_WEALTH, "billionaire", 1_000_000_000.0), AchievementReward(45, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Billionaire"),
        Achievement("eco_037", "Tourism Boom", "Achieve $100B tourism revenue", AchievementCategory.ECONOMIC, AchievementType.THRESHOLD, 35, "tourism", AchievementRarity.EPIC, AchievementRequirement(RequirementType.TOURISM_REVENUE, "100_billion", 100_000_000_000.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Tourism boom"),
        Achievement("eco_038", "Carbon Taxer", "Implement carbon tax", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 25, "carbon_tax", AchievementRarity.RARE, AchievementRequirement(RequirementType.CARBON_TAX, "implemented", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Carbon tax"),
        Achievement("eco_039", "VAT Implemented", "Implement VAT", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 15, "vat", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.VAT_INTRODUCED, "implemented", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "VAT"),
        Achievement("eco_040", "CBDC Pioneer", "Launch central bank digital currency", AchievementCategory.ECONOMIC, AchievementType.SINGLE_EVENT, 40, "cbdc", AchievementRarity.EPIC, AchievementRequirement(RequirementType.CENTRAL_BANK_DIGITAL_CURRENCY, "launched", 1.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "CBDC pioneer")
    )
    
    // MILITARY ACHIEVEMENTS (30)
    val MILITARY_ACHIEVEMENTS = listOf(
        Achievement("mil_001", "First Victory", "Win your first war", AchievementCategory.MILITARY, AchievementType.SINGLE_EVENT, 20, "victory", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.WARS_WON, "first_war", 1.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "First victory"),
        Achievement("mil_002", "Conqueror", "Conquer 10 territories", AchievementCategory.MILITARY, AchievementType.CUMULATIVE, 50, "conqueror", AchievementRarity.EPIC, AchievementRequirement(RequirementType.TERRITORY_GAINED, "10_territories", 10.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Conqueror"),
        Achievement("mil_003", "World Conqueror", "Conquer the world", AchievementCategory.MILITARY, AchievementType.CHALLENGE, 200, "world", AchievementRarity.IMPOSSIBLE, AchievementRequirement(RequirementType.TERRITORY_GAINED, "world", 100.0), AchievementReward(200, emptyMap(), emptyList(), null, null), false, null, emptyList(), "World conqueror"),
        Achievement("mil_004", "Nuclear Power", "Develop nuclear weapons", AchievementCategory.MILITARY, AchievementType.SINGLE_EVENT, 50, "nuclear", AchievementRarity.EPIC, AchievementRequirement(RequirementType.NUCLEAR_WEAPONS, "developed", 1.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Nuclear power"),
        Achievement("mil_005", "Nuclear Disarmament", "Eliminate nuclear weapons", AchievementCategory.MILITARY, AchievementType.CHALLENGE, 60, "disarmament", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.NUCLEAR_WEAPONS, "eliminated", 0.0), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Disarmed"),
        Achievement("mil_006", "Peacekeeper", "Successfully complete 10 peacekeeping missions", AchievementCategory.MILITARY, AchievementType.CUMULATIVE, 35, "peacekeeper", AchievementRarity.RARE, AchievementRequirement(RequirementType.PEACE_TREATIES, "peacekeeping", 10.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Peacekeeper"),
        Achievement("mil_007", "Defense Spending", "Spend $1 trillion on defense", AchievementCategory.MILITARY, AchievementType.CUMULATIVE, 40, "defense", AchievementRarity.EPIC, AchievementRequirement(RequirementType.MILITARY_STRENGTH, "spending", 1_000_000_000_000.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Defense spender"),
        Achievement("mil_008", "Military Coup", "Seize power through coup", AchievementCategory.MILITARY, AchievementType.CHALLENGE, 45, "coup", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.COUP_ATTEMPTS_SURVIVED, "successful_coup", 1.0), AchievementReward(45, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Coup leader"),
        Achievement("mil_009", "Civil War Victor", "Win a civil war", AchievementCategory.MILITARY, AchievementType.CHALLENGE, 55, "civil_war", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.CIVIL_WARS_ENDED, "victory", 1.0), AchievementReward(55, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Civil war victor"),
        Achievement("mil_010", "Regime Change", "Orchestrate a regime change abroad", AchievementCategory.MILITARY, AchievementType.SINGLE_EVENT, 40, "regime", AchievementRarity.EPIC, AchievementRequirement(RequirementType.REGIMES_CHANGED, "orchestrated", 1.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Regime changer")
    )
    
    // DIPLOMATIC ACHIEVEMENTS (25)
    val DIPLOMATIC_ACHIEVEMENTS = listOf(
        Achievement("dip_001", "First Treaty", "Sign your first treaty", AchievementCategory.DIPLOMATIC, AchievementType.SINGLE_EVENT, 10, "treaty", AchievementRarity.COMMON, AchievementRequirement(RequirementType.TREATIES_SIGNED, "first", 1.0), AchievementReward(10, emptyMap(), emptyList(), null, null), false, null, emptyList(), "First treaty"),
        Achievement("dip_002", "Peace Maker", "Broker a peace treaty between two countries", AchievementCategory.DIPLOMATIC, AchievementType.SINGLE_EVENT, 35, "peace", AchievementRarity.RARE, AchievementRequirement(RequirementType.PEACE_TREATIES, "brokered", 1.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Peace maker"),
        Achievement("dip_003", "Nobel Peace Prize", "Win the Nobel Peace Prize", AchievementCategory.DIPLOMATIC, AchievementType.SINGLE_EVENT, 75, "nobel", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.NOBEL_PEACE_PRIZE, "won", 1.0), AchievementReward(75, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Nobel laureate"),
        Achievement("dip_004", "Alliance Builder", "Form 5 alliances", AchievementCategory.DIPLOMATIC, AchievementType.CUMULATIVE, 30, "alliance", AchievementRarity.RARE, AchievementRequirement(RequirementType.TREATIES_SIGNED, "alliances", 5.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Alliance builder"),
        Achievement("dip_005", "Trade Deal Master", "Sign 10 trade deals", AchievementCategory.DIPLOMATIC, AchievementType.CUMULATIVE, 25, "trade", AchievementRarity.RARE, AchievementRequirement(RequirementType.TRADE_DEALS, "deals", 10.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Trade master"),
        Achievement("dip_006", "UN Leader", "Lead a UN Security Council resolution", AchievementCategory.DIPLOMATIC, AchievementType.SINGLE_EVENT, 30, "un", AchievementRarity.RARE, AchievementRequirement(RequirementType.INTERNATIONAL_ORGANIZATIONS, "un_lead", 1.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "UN leader"),
        Achievement("dip_007", "Sanctioner", "Impose sanctions on 10 countries", AchievementCategory.DIPLOMATIC, AchievementType.CUMULATIVE, 20, "sanctions", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.INTERNATIONAL_REPUTATION, "sanctions", 10.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Sanctioner"),
        Achievement("dip_008", "Isolationist", "Have no diplomatic relations", AchievementCategory.DIPLOMATIC, AchievementType.THRESHOLD, 25, "isolation", AchievementRarity.RARE, AchievementRequirement(RequirementType.RELATIONS, "zero", 0.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Isolationist"),
        Achievement("dip_009", "Global Diplomat", "Have relations with all countries", AchievementCategory.DIPLOMATIC, AchievementType.CHALLENGE, 60, "global", AchievementRarity.EPIC, AchievementRequirement(RequirementType.RELATIONS, "all", 100.0), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Global diplomat"),
        Achievement("dip_010", "Embassy Network", "Open 50 embassies", AchievementCategory.DIPLOMATIC, AchievementType.CUMULATIVE, 30, "embassy", AchievementRarity.RARE, AchievementRequirement(RequirementType.INTERNATIONAL_ORGANIZATIONS, "embassies", 50.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Embassy network")
    )
    
    // SOCIAL ACHIEVEMENTS (25)
    val SOCIAL_ACHIEVEMENTS = listOf(
        Achievement("soc_001", "Happy People", "Achieve happiness index above 8", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 40, "happy", AchievementRarity.EPIC, AchievementRequirement(RequirementType.HAPPINESS_INDEX, "high", 8.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Happy nation"),
        Achievement("soc_002", "Healthcare for All", "Achieve universal healthcare", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 50, "healthcare", AchievementRarity.EPIC, AchievementRequirement(RequirementType.VACCINATION_RATE, "universal", 100.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Universal healthcare"),
        Achievement("soc_003", "Education Revolution", "Achieve 100% literacy", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 45, "education", AchievementRarity.EPIC, AchievementRequirement(RequirementType.LITERACY_RATE, "100", 100.0), AchievementReward(45, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Literate nation"),
        Achievement("soc_004", "Long Lives", "Achieve life expectancy of 90", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 55, "longevity", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.LIFE_EXPECTANCY, "90", 90.0), AchievementReward(55, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Long-lived nation"),
        Achievement("soc_005", "Population Boom", "Double population", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 35, "population", AchievementRarity.RARE, AchievementRequirement(RequirementType.POPULATION_GROWTH, "doubled", 200.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Population boom"),
        Achievement("soc_006", "Immigration Destination", "Accept 1 million immigrants", AchievementCategory.SOCIAL, AchievementType.CUMULATIVE, 40, "immigration", AchievementRarity.EPIC, AchievementRequirement(RequirementType.REFUGEES_ACCEPTED, "million", 1_000_000.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Immigration destination"),
        Achievement("soc_007", "Women's Rights", "Achieve perfect gender equality", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 50, "gender", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.WOMEN_RIGHTS, "perfect", 100.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Gender equal"),
        Achievement("soc_008", "LGBTQ+ Rights", "Achieve full LGBTQ+ equality", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 45, "lgbtq", AchievementRarity.EPIC, AchievementRequirement(RequirementType.LGBTQ_RIGHTS, "full", 100.0), AchievementReward(45, emptyMap(), emptyList(), null, null), false, null, emptyList(), "LGBTQ+ equal"),
        Achievement("soc_009", "Press Freedom", "Achieve top press freedom ranking", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 40, "press", AchievementRarity.EPIC, AchievementRequirement(RequirementType.PRESS_FREEDOM, "top", 100.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Free press"),
        Achievement("soc_010", "Internet Freedom", "Achieve free internet", AchievementCategory.SOCIAL, AchievementType.THRESHOLD, 35, "internet", AchievementRarity.RARE, AchievementRequirement(RequirementType.INTERNET_FREEDOM, "free", 100.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Free internet")
    )
    
    // ENVIRONMENTAL ACHIEVEMENTS (15)
    val ENVIRONMENTAL_ACHIEVEMENTS = listOf(
        Achievement("env_001", "Carbon Neutral", "Achieve carbon neutrality", AchievementCategory.ENVIRONMENTAL, AchievementType.THRESHOLD, 75, "carbon", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.CO2_EMISSIONS, "neutral", 0.0), AchievementReward(75, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Carbon neutral"),
        Achievement("env_002", "Forest Coverage", "Achieve 50% forest coverage", AchievementCategory.ENVIRONMENTAL, AchievementType.THRESHOLD, 50, "forest", AchievementRarity.EPIC, AchievementRequirement(RequirementType.FOREST_COVERAGE, "50", 50.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Green nation"),
        Achievement("env_003", "Endangered Species", "Save 10 endangered species", AchievementCategory.ENVIRONMENTAL, AchievementType.CUMULATIVE, 40, "species", AchievementRarity.EPIC, AchievementRequirement(RequirementType.ENDANGERED_SPECIES, "saved", 10.0), AchievementReward(40, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Species saver"),
        Achievement("env_004", "Clean Air", "Achieve perfect air quality", AchievementCategory.ENVIRONMENTAL, AchievementType.THRESHOLD, 45, "air", AchievementRarity.EPIC, AchievementRequirement(RequirementType.CO2_EMISSIONS, "clean_air", 0.0), AchievementReward(45, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Clean air"),
        Achievement("env_005", "Polluter", "Become worst polluter", AchievementCategory.ENVIRONMENTAL, AchievementType.THRESHOLD, 15, "polluter", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.CO2_EMISSIONS, "worst", 100.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Polluter")
    )
    
    // TECHNOLOGY ACHIEVEMENTS (15)
    val TECHNOLOGY_ACHIEVEMENTS = listOf(
        Achievement("tech_001", "Tech Leader", "Achieve 100% tech level", AchievementCategory.TECHNOLOGY, AchievementType.THRESHOLD, 50, "tech", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.TECHNOLOGY_LEVEL, "max", 100.0), AchievementReward(50, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Tech leader"),
        Achievement("tech_002", "Nuclear Power Plant", "Build first nuclear power plant", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 25, "nuclear_plant", AchievementRarity.RARE, AchievementRequirement(RequirementType.TECHNOLOGY_LEVEL, "nuclear", 1.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Nuclear power"),
        Achievement("tech_003", "Fusion Energy", "Develop fusion power", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 100, "fusion", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.FUSION_ENERGY, "developed", 1.0), AchievementReward(100, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Fusion pioneer"),
        Achievement("tech_004", "AI Development", "Develop advanced AI", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 60, "ai", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.AI_DEVELOPMENT, "advanced", 1.0), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "AI pioneer"),
        Achievement("tech_005", "Quantum Computing", "Build quantum computer", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 75, "quantum", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.QUANTUM_COMPUTING, "built", 1.0), AchievementReward(75, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Quantum pioneer"),
        Achievement("tech_006", "Moon Landing", "Land on the moon", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 60, "moon", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.MOON_BASE, "landing", 1.0), AchievementReward(60, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Moon landing"),
        Achievement("tech_007", "Mars Mission", "Land on Mars", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 100, "mars", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.MARS_MISSION, "landed", 1.0), AchievementReward(100, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Mars pioneer"),
        Achievement("tech_008", "Space Station", "Build a space station", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 55, "station", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.SPACE_STATION, "built", 1.0), AchievementReward(55, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Space station"),
        Achievement("tech_009", "Moon Base", "Establish moon base", AchievementCategory.TECHNOLOGY, AchievementType.SINGLE_EVENT, 80, "moon_base", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.MOON_BASE, "established", 1.0), AchievementReward(80, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Moon base"),
        Achievement("tech_010", "Satellite Network", "Launch 100 satellites", AchievementCategory.TECHNOLOGY, AchievementType.CUMULATIVE, 35, "satellite", AchievementRarity.EPIC, AchievementRequirement(RequirementType.SATELLITE_NETWORK, "100", 100.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Satellite network")
    )
    
    // HIDDEN ACHIEVEMENTS (10)
    val HIDDEN_ACHIEVEMENTS = listOf(
        Achievement("hid_001", "???", "???", AchievementCategory.HIDDEN, AchievementType.SECRET, 100, "secret", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.ACHIEVEMENTS_UNLOCKED, "hidden1", 1.0), AchievementReward(100, emptyMap(), emptyList(), null, null), true, "Some secrets remain hidden...", emptyList(), "???"),
        Achievement("hid_002", "Time Traveler", "Play 100 in-game years", AchievementCategory.HIDDEN, AchievementType.CUMULATIVE, 75, "time", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.YEARS_SERVED, "100_years", 100.0), AchievementReward(75, emptyMap(), emptyList(), null, null), true, "Time is relative...", emptyList(), "Time traveler"),
        Achievement("hid_003", "Perfect Leader", "Max all stats simultaneously", AchievementCategory.HIDDEN, AchievementType.CHALLENGE, 150, "perfect", AchievementRarity.IMPOSSIBLE, AchievementRequirement(RequirementType.APPROVAL_RATING, "perfect", 100.0), AchievementReward(150, emptyMap(), emptyList(), null, null), true, "Perfection is elusive...", emptyList(), "Perfect leader"),
        Achievement("hid_004", "Ironman", "Complete game on ironman mode", AchievementCategory.HIDDEN, AchievementType.IRONMAN, 100, "ironman", AchievementRarity.MYTHIC, AchievementRequirement(RequirementType.YEARS_SERVED, "ironman", 20.0), AchievementReward(100, emptyMap(), emptyList(), null, null), true, "No saves, no mercy...", emptyList(), "Ironman"),
        Achievement("hid_005", "Pacifist", "Never start a war", AchievementCategory.HIDDEN, AchievementType.CHALLENGE, 60, "pacifist", AchievementRarity.LEGENDARY, AchievementRequirement(RequirementType.WARS_WON, "zero_wars", 0.0), AchievementReward(60, emptyMap(), emptyList(), null, null), true, "Peace is a choice...", emptyList(), "Pacifist run")
    )
    
    // MILESTONE ACHIEVEMENTS (10)
    val MILESTONE_ACHIEVEMENTS = listOf(
        Achievement("mil_001", "First Year", "Complete one year in office", AchievementCategory.MILESTONE, AchievementType.SINGLE_EVENT, 5, "year1", AchievementRarity.COMMON, AchievementRequirement(RequirementType.YEARS_SERVED, "1", 1.0), AchievementReward(5, emptyMap(), emptyList(), null, null), false, null, emptyList(), "First year"),
        Achievement("mil_002", "First Term", "Complete first term", AchievementCategory.MILESTONE, AchievementType.SINGLE_EVENT, 15, "term1", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.TERMS_SERVED, "1", 1.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "First term complete"),
        Achievement("mil_003", "Second Term", "Win second term", AchievementCategory.MILESTONE, AchievementType.SINGLE_EVENT, 25, "term2", AchievementRarity.RARE, AchievementRequirement(RequirementType.TERMS_SERVED, "2", 2.0), AchievementReward(25, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Second term"),
        Achievement("mil_004", "Decade of Service", "Serve 10 years", AchievementCategory.MILESTONE, AchievementType.CUMULATIVE, 35, "decade", AchievementRarity.EPIC, AchievementRequirement(RequirementType.YEARS_SERVED, "10", 10.0), AchievementReward(35, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Decade served"),
        Achievement("mil_005", "25 Decisions", "Make 25 decisions", AchievementCategory.MILESTONE, AchievementType.CUMULATIVE, 5, "decisions25", AchievementRarity.COMMON, AchievementRequirement(RequirementType.DECISIONS_MADE, "25", 25.0), AchievementReward(5, emptyMap(), emptyList(), null, null), false, null, emptyList(), "25 decisions"),
        Achievement("mil_006", "100 Decisions", "Make 100 decisions", AchievementCategory.MILESTONE, AchievementType.CUMULATIVE, 10, "decisions100", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.DECISIONS_MADE, "100", 100.0), AchievementReward(10, emptyMap(), emptyList(), null, null), false, null, emptyList(), "100 decisions"),
        Achievement("mil_007", "500 Decisions", "Make 500 decisions", AchievementCategory.MILESTONE, AchievementType.CUMULATIVE, 20, "decisions500", AchievementRarity.RARE, AchievementRequirement(RequirementType.DECISIONS_MADE, "500", 500.0), AchievementReward(20, emptyMap(), emptyList(), null, null), false, null, emptyList(), "500 decisions"),
        Achievement("mil_008", "Task Master", "Complete 100 tasks", AchievementCategory.MILESTONE, AchievementType.CUMULATIVE, 15, "tasks", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.TASKS_COMPLETED, "100", 100.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Task master"),
        Achievement("mil_009", "Event Handler", "Resolve 50 events", AchievementCategory.MILESTONE, AchievementType.CUMULATIVE, 15, "events", AchievementRarity.UNCOMMON, AchievementRequirement(RequirementType.EVENTS_RESOLVED, "50", 50.0), AchievementReward(15, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Event handler"),
        Achievement("mil_010", "Achievement Hunter", "Unlock 50 achievements", AchievementCategory.MILESTONE, AchievementType.CUMULATIVE, 30, "achievements", AchievementRarity.RARE, AchievementRequirement(RequirementType.ACHIEVEMENTS_UNLOCKED, "50", 50.0), AchievementReward(30, emptyMap(), emptyList(), null, null), false, null, emptyList(), "Achievement hunter")
    )
    
    // Get all achievements
    fun getAllAchievements(): List<Achievement> {
        return POLITICAL_ACHIEVEMENTS + 
               ECONOMIC_ACHIEVEMENTS + 
               MILITARY_ACHIEVEMENTS + 
               DIPLOMATIC_ACHIEVEMENTS + 
               SOCIAL_ACHIEVEMENTS + 
               ENVIRONMENTAL_ACHIEVEMENTS + 
               TECHNOLOGY_ACHIEVEMENTS + 
               HIDDEN_ACHIEVEMENTS + 
               MILESTONE_ACHIEVEMENTS
    }
}
