package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Non-Player Character with personality, memories, and relationships.
 */
@Parcelize
data class NPC(
    val id: String,
    val fullName: String,
    val title: String,
    val age: Int,
    val gender: Gender,
    val nationality: String, // countryId
    val role: NPCRole,
    val party: String?, // partyId if applicable
    val portraitSeed: Long, // for procedural portrait generation
    val personality: Personality,
    val skills: Map<Skill, Int>, // skill -> level (1-100)
    val traits: List<Trait>,
    val memories: List<Memory>,
    val relationships: Map<String, Int>, // npcId or playerId -> relationship (-100 to 100)
    val currentPositions: List<Position>,
    val pastPositions: List<Position>,
    val scandals: List<Scandal>,
    val achievements: List<Achievement>,
    val wealth: Long,
    val approvalRating: Double,
    val influence: Int, // 0-100
    val corruptionLevel: Double, // 0.0 to 1.0
    val isAlive: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable {
    
    val effectiveInfluence: Int
        get() = (influence * (1 - corruptionLevel * 0.3)).toInt().coerceIn(0, 100)
    
    val canBeAppointed: Boolean
        get() = isAlive && corruptionLevel < 0.8 && !currentPositions.any { it.isFullTime }
}

enum class Gender {
    MALE,
    FEMALE,
    NON_BINARY
}

enum class NPCRole {
    POLITICIAN,
    DIPLOMAT,
    BUSINESS_LEADER,
    MILITARY_OFFICER,
    JUDGE,
    CIVIL_SERVANT,
    ACTIVIST,
    JOURNALIST,
    RELIGIOUS_LEADER,
    ACADEMIC,
    ATHLETE,
    CELEBRITY,
    LABOR_LEADER,
    CRIMINAL
}

@Parcelize
data class Personality(
    val openness: Int, // 0-100: creativity, curiosity
    val conscientiousness: Int, // 0-100: organization, dependability
    val extraversion: Int, // 0-100: sociability, assertiveness
    val agreeableness: Int, // 0-100: cooperation, trust
    val neuroticism: Int // 0-100: emotional instability
) : Parcelable {
    
    val politicalOrientation: PoliticalOrientation
        get() = when {
            openness > 70 && agreeableness < 40 -> PoliticalOrientation.PROGRESSIVE
            openness < 30 && conscientiousness > 70 -> PoliticalOrientation.CONSERVATIVE
            conscientiousness > 70 && agreeableness > 60 -> PoliticalOrientation.MODERATE
            neuroticism > 70 -> PoliticalOrientation.POPULIST
            else -> PoliticalOrientation.CENTRIST
        }
    
    val leadershipStyle: LeadershipStyle
        get() = when {
            extraversion > 70 && conscientiousness > 60 -> LeadershipStyle.AUTHORITARIAN
            agreeableness > 70 && extraversion > 50 -> LeadershipStyle.DEMOCRATIC
            openness > 70 && conscientiousness < 40 -> LeadershipStyle.VISIONARY
            neuroticism > 60 -> LeadershipStyle.PARANOID
            else -> LeadershipStyle.PRAGMATIC
        }
}

enum class PoliticalOrientation {
    FAR_LEFT,
    PROGRESSIVE,
    LEFT,
    CENTRIST,
    MODERATE,
    CONSERVATIVE,
    RIGHT,
    FAR_RIGHT,
    POPULIST,
    LIBERTARIAN
}

enum class LeadershipStyle {
    AUTHORITARIAN,
    DEMOCRATIC,
    VISIONARY,
    PRAGMATIC,
    PARANOID,
    HANDS_OFF,
    MICRO_MANAGER
}

enum class Skill {
    ORATORY,
    DIPLOMACY,
    ECONOMICS,
    MILITARY_STRATEGY,
    LAW,
    ADMINISTRATION,
    CORRUPTION,
    MEDIA_RELATIONS,
    NEGOTIATION,
    CHARISMA,
    INTELLIGENCE,
    LOYALTY,
    POLITICAL_SAVVY,
    CRISIS_MANAGEMENT
}

@Parcelize
data class Trait(
    val name: String,
    val type: TraitType,
    val intensity: Double // 0.0 to 1.0
) : Parcelable

enum class TraitType {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}

@Parcelize
data class Memory(
    val id: String,
    val description: String,
    val relatedEntityId: String, // playerId, npcId, countryId, or eventId
    val emotionalWeight: Double, // -1.0 to 1.0 (negative to positive)
    val importance: Int, // 0-100
    val category: MemoryCategory,
    val timestamp: Long
) : Parcelable

enum class MemoryCategory {
    INTERACTION,
    BETRAYAL,
    ALLIANCE,
    SCANDAL,
    ACHIEVEMENT,
    CONFLICT,
    NEGOTIATION,
    APPOINTMENT,
    ELECTION,
    POLICY
}

@Parcelize
data class Position(
    val title: String,
    val organization: String,
    val startDate: Long,
    val endDate: Long?,
    val isFullTime: Boolean,
    val salary: Long
) : Parcelable

@Parcelize
data class Scandal(
    val id: String,
    val title: String,
    val description: String,
    val severity: Int, // 0-100
    val isPublic: Boolean,
    val discoveredDate: Long?,
    val implicatedNPCs: List<String>
) : Parcelable

@Parcelize
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val prestige: Int, // 0-100
    val date: Long
) : Parcelable
