package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * COMPREHENSIVE Achievement System
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
data class GameAchievement(
    val id: String,
    val name: String,
    val description: String,
    val category: AchievementCategory,
    val type: AchievementType,
    val points: Int,
    val icon: String,
    val rarity: GameAchievementRarity,
    val requirements: AchievementRequirement,
    val rewards: AchievementReward,
    val hidden: Boolean,
    val hint: String?,
    val relatedAchievements: List<String>,
    val steamlike: String
) : Parcelable

@Parcelize
data class UnlockedAchievement(
    val achievementId: String,
    val unlockedDate: Long,
    val gameId: String,
    val context: String
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
    POLITICAL, ECONOMIC, MILITARY, DIPLOMATIC, SOCIAL,
    ENVIRONMENTAL, SCIENTIFIC, CULTURAL, SPORTS, INTELLIGENCE,
    MEDIA, RELIGION, CRIME_JUSTICE, EDUCATION, HEALTH,
    INFRASTRUCTURE, TECHNOLOGY, DEMOGRAPHICS, SURVIVAL, HIDDEN, MILESTONE
}

enum class AchievementType {
    SINGLE_EVENT, CUMULATIVE, THRESHOLD, CHALLENGE, SECRET,
    SPEEDRUN, IRONMAN, ENDGAME, STORY, SPECIAL_EVENT
}

enum class GameAchievementRarity {
    COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTHIC, IMPOSSIBLE
}

@Parcelize
data class AchievementRequirement(
    val type: AchievementRequirementType,
    val target: String,
    val value: Double,
    val secondaryConditions: List<String> = emptyList(),
    val timeLimit: Long? = null,
    val difficulty: String? = null
) : Parcelable

enum class AchievementRequirementType {
    APPROVAL_RATING, POPULATION, GDP, MILITARY_STRENGTH, INTERNATIONAL_REPUTATION,
    STABILITY_INDEX, HAPPINESS_INDEX, TECHNOLOGY_LEVEL, CORRUPTION_LEVEL,
    EDUCATION_LEVEL, HEALTH_INDEX, ENVIRONMENT_INDEX, CRIME_RATE,
    RELATIONS, TREATIES_SIGNED, WARS_WON, LAWS_PASSED, VETOES_USED,
    EXECUTIVE_ORDERS, ELECTIONS_WON, YEARS_SERVED, POLITICAL_CAPITAL,
    PERSONAL_WEALTH, ACHIEVEMENTS_UNLOCKED, EVENTS_RESOLVED, TASKS_COMPLETED,
    DECISIONS_MADE, SCANDALS_SURVIVED, ASSASSINATION_ATTEMPTS, COUP_ATTEMPTS_SURVIVED,
    IMPEACHMENT_SURVIVED, TERMS_SERVED, PARTIES_CREATED, COALITIONS_FORMED,
    PEACE_TREATIES, TRADE_DEALS, INTERNATIONAL_ORGANIZATIONS, NUCLEAR_WEAPONS,
    SPACE_PROGRAM, OLYMPIC_MEDALS, WORLD_CUP_VICTORY, NOBEL_PRIZE,
    TERRITORY_GAINED, DEBT_LEVEL, BUDGET_BALANCE, TRADE_SURPLUS,
    FOREIGN_INVESTMENT, OIL_PRODUCTION, RENEWABLE_ENERGY, LITERACY_RATE,
    LIFE_EXPECTANCY, INFRASTRUCTURE_INVESTED, SCIENTIFIC_PUBLICATIONS,
    CYBER_ATTACK_DEFENDED, TERRORIST_ATTACKS_PREVENTED, SPIES_CAPTURED,
    COVERT_OPERATIONS, COUPS_SUPPORTED, REGIMES_CHANGED, DEMOCRACY_SPREAD,
    REVOLUTIONS_LEAD, REVOLUTIONS_SUPPRESSED, HUMANITARIAN_AID, REFUGEES_ACCEPTED,
    VOTING_RIGHTS_EXPANDED, VOTING_RIGHTS_RESTRICTED, PRESS_FREEDOM,
    RELIGIOUS_FREEDOM, WOMEN_RIGHTS, MINORITY_RIGHTS, CONSTITUTIONAL_AMENDMENT,
    TAX_REFORM, HEALTHCARE_REFORM, EDUCATION_REFORM, NATIONALIZATION, PRIVATIZATION,
    MONOPOLY_BROKEN, BAILOUT_GIVEN, STIMULUS_PACKAGE, CURRENCY_CHANGE, INFLUENCE
}

@Parcelize
data class AchievementReward(
    val politicalCapital: Int,
    val reputation: Map<String, Int>,
    val unlocks: List<String>,
    val title: String?,
    val cosmetic: String?
) : Parcelable

/**
 * Library of predefined achievements - simplified version
 */
object AchievementsLibrary {
    
    val ALL_ACHIEVEMENTS: List<GameAchievement> = listOf(
        // Political Achievements
        GameAchievement(
            id = "pol_001", name = "First Steps", description = "Win your first election",
            category = AchievementCategory.POLITICAL, type = AchievementType.SINGLE_EVENT,
            points = 10, icon = "vote", rarity = GameAchievementRarity.COMMON,
            requirements = AchievementRequirement(AchievementRequirementType.ELECTIONS_WON, "election", 1.0),
            rewards = AchievementReward(10, emptyMap(), emptyList(), null, null),
            hidden = false, hint = null, relatedAchievements = emptyList(), steamlike = "Win your first election"
        ),
        GameAchievement(
            id = "pol_002", name = "Landslide Victory", description = "Win with 70%+ of vote",
            category = AchievementCategory.POLITICAL, type = AchievementType.CHALLENGE,
            points = 25, icon = "landslide", rarity = GameAchievementRarity.RARE,
            requirements = AchievementRequirement(AchievementRequirementType.APPROVAL_RATING, "election", 70.0),
            rewards = AchievementReward(25, emptyMap(), emptyList(), null, null),
            hidden = false, hint = null, relatedAchievements = emptyList(), steamlike = "Win big"
        ),
        GameAchievement(
            id = "pol_003", name = "Political Survivor", description = "Survive 5 years in office",
            category = AchievementCategory.POLITICAL, type = AchievementType.CUMULATIVE,
            points = 20, icon = "survivor", rarity = GameAchievementRarity.UNCOMMON,
            requirements = AchievementRequirement(AchievementRequirementType.YEARS_SERVED, "years", 5.0),
            rewards = AchievementReward(20, emptyMap(), emptyList(), null, null),
            hidden = false, hint = null, relatedAchievements = emptyList(), steamlike = "Time flies"
        ),
        // Economic Achievements
        GameAchievement(
            id = "eco_001", name = "First Budget", description = "Pass your first budget",
            category = AchievementCategory.ECONOMIC, type = AchievementType.SINGLE_EVENT,
            points = 10, icon = "budget", rarity = GameAchievementRarity.COMMON,
            requirements = AchievementRequirement(AchievementRequirementType.LAWS_PASSED, "budget", 1.0),
            rewards = AchievementReward(10, emptyMap(), emptyList(), null, null),
            hidden = false, hint = null, relatedAchievements = emptyList(), steamlike = "First budget"
        ),
        GameAchievement(
            id = "eco_002", name = "Budget Surplus", description = "Achieve a budget surplus",
            category = AchievementCategory.ECONOMIC, type = AchievementType.THRESHOLD,
            points = 30, icon = "surplus", rarity = GameAchievementRarity.RARE,
            requirements = AchievementRequirement(AchievementRequirementType.BUDGET_BALANCE, "surplus", 1.0),
            rewards = AchievementReward(30, emptyMap(), emptyList(), null, null),
            hidden = false, hint = null, relatedAchievements = emptyList(), steamlike = "Surplus"
        ),
        // Military Achievements
        GameAchievement(
            id = "mil_001", name = "Commander in Chief", description = "Win your first war",
            category = AchievementCategory.MILITARY, type = AchievementType.SINGLE_EVENT,
            points = 30, icon = "medal", rarity = GameAchievementRarity.RARE,
            requirements = AchievementRequirement(AchievementRequirementType.WARS_WON, "war", 1.0),
            rewards = AchievementReward(30, emptyMap(), emptyList(), null, null),
            hidden = false, hint = null, relatedAchievements = emptyList(), steamlike = "Victory"
        ),
        // Diplomatic Achievements
        GameAchievement(
            id = "dip_001", name = "Peacemaker", description = "Sign your first peace treaty",
            category = AchievementCategory.DIPLOMATIC, type = AchievementType.SINGLE_EVENT,
            points = 20, icon = "dove", rarity = GameAchievementRarity.UNCOMMON,
            requirements = AchievementRequirement(AchievementRequirementType.PEACE_TREATIES, "treaty", 1.0),
            rewards = AchievementReward(20, emptyMap(), emptyList(), null, null),
            hidden = false, hint = null, relatedAchievements = emptyList(), steamlike = "Peace"
        )
    )
    
    fun getById(id: String): GameAchievement? = ALL_ACHIEVEMENTS.find { it.id == id }
    
    fun getByCategory(category: AchievementCategory): List<GameAchievement> =
        ALL_ACHIEVEMENTS.filter { it.category == category }
}