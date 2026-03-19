package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a game event that can trigger decisions and consequences.
 */
@Parcelize
data class GameEvent(
    val id: String,
    val title: String,
    val description: String,
    val type: EventType,
    val category: EventCategory,
    val severity: EventSeverity,
    val involvedEntities: List<EventEntity>, // countries, NPCs, organizations
    val availableDecisions: List<Decision>,
    val consequences: List<Consequence>,
    val triggeredBy: String?, // eventId or decisionId that caused this
    val cascadeChildren: List<String>, // eventIds that can be triggered
    val memoryImpact: MemoryImpact?,
    val timeLimit: Long?, // milliseconds until auto-resolution
    val isActive: Boolean,
    val isResolved: Boolean,
    val resolvedDecision: String?, // decisionId chosen
    val createdAt: Long,
    val resolvedAt: Long?
) : Parcelable {
    
    val isExpired: Boolean
        get() = timeLimit != null && System.currentTimeMillis() > createdAt + timeLimit
    
    val hasCascadePotential: Boolean
        get() = cascadeChildren.isNotEmpty()
}

enum class EventType {
    CRISIS,
    OPPORTUNITY,
    SCANDAL,
    DIPLOMATIC,
    ECONOMIC,
    NATURAL_DISASTER,
    POLITICAL,
    MILITARY,
    SOCIAL,
    SPORTS,
    CULTURAL
}

enum class EventCategory {
    DOMESTIC,
    INTERNATIONAL,
    INTERNAL_PARTY,
    PERSONAL,
    REGIONAL,
    GLOBAL
}

enum class EventSeverity {
    TRIVIAL, // minor impact, optional response
    MINOR, // small impact, should respond
    MODERATE, // significant impact, must respond
    MAJOR, // large impact, urgent response needed
    CRITICAL, // game-changing impact, immediate response required
    CATASTROPHIC // potential game-over scenario
}

@Parcelize
data class EventEntity(
    val entityId: String,
    val entityType: EntityType,
    val role: String // how they're involved
) : Parcelable

enum class EntityType {
    COUNTRY,
    NPC,
    ORGANIZATION,
    PLAYER,
    REGION,
    RESOURCE
}

@Parcelize
data class Decision(
    val id: String,
    val text: String,
    val description: String,
    val requirements: List<DecisionRequirement>,
    val costs: List<Cost>,
    val outcomes: List<Outcome>,
    val aiReasoning: String?, // why an AI NPC might choose this
    val riskLevel: RiskLevel,
    val timeToImplement: Long, // milliseconds
    val approvalRequired: Boolean,
    val approvers: List<String> // npcIds or organizationIds
) : Parcelable

@Parcelize
data class DecisionRequirement(
    val type: RequirementType,
    val targetId: String?,
    val value: Double
) : Parcelable

enum class RequirementType {
    MIN_APPROVAL_RATING,
    MIN_TREASURY,
    MIN_MILITARY_STRENGTH,
    MIN_RELATION_WITH_COUNTRY,
    HAS_POLICY,
    HAS_MINISTER,
    MINISTER_APPROVAL,
    PARLIAMENT_SUPPORT,
    PARTY_SUPPORT,
    POPULAR_SUPPORT,
    RESOURCE_AVAILABLE,
    TECH_LEVEL
}

@Parcelize
data class Cost(
    val type: CostType,
    val amount: Double,
    val isRecurring: Boolean,
    val recurrencePeriod: Long? // milliseconds
) : Parcelable

enum class CostType {
    MONEY,
    POLITICAL_CAPITAL,
    APPROVAL_RATING,
    INTERNATIONAL_REPUTATION,
    MILITARY_STRENGTH,
    STABILITY,
    HAPPINESS
}

@Parcelize
data class Outcome(
    val type: OutcomeType,
    val targetId: String?,
    val value: Double,
    val probability: Double, // 0.0 to 1.0
    val condition: String?, // condition for this outcome to occur
    val delayMs: Long // delay before outcome takes effect
) : Parcelable

enum class OutcomeType {
    CHANGE_APPROVAL,
    CHANGE_TREASURY,
    CHANGE_RELATION,
    CHANGE_STABILITY,
    CHANGE_HAPPINESS,
    TRIGGER_EVENT,
    SPAWN_TASK,
    CHANGE_POLICY,
    APPOINT_NPC,
    REMOVE_NPC,
    CHANGE_RESOURCE,
    CHANGE_MILITARY_STRENGTH,
    CHANGE_INTERNATIONAL_REPUTATION
}

enum class RiskLevel {
    SAFE,
    LOW,
    MODERATE,
    HIGH,
    EXTREME,
    UNKNOWN
}

@Parcelize
data class Consequence(
    val id: String,
    val description: String,
    val type: ConsequenceType,
    val delayMs: Long,
    val probability: Double,
    val effects: List<Effect>
) : Parcelable

@Parcelize
data class Effect(
    val targetType: EffectTargetType,
    val targetId: String?,
    val operation: EffectOperation,
    val value: Double
) : Parcelable

enum class EffectTargetType {
    COUNTRY_STAT,
    NPC_STAT,
    PLAYER_STAT,
    GLOBAL_STAT,
    RELATION,
    RESOURCE
}

enum class EffectOperation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    SET
}

enum class ConsequenceType {
    IMMEDIATE,
    DELAYED,
    CONDITIONAL,
    CASCADING
}

@Parcelize
data class MemoryImpact(
    val description: String,
    val affectedNPCs: List<String>,
    val emotionalWeightMin: Double,
    val emotionalWeightMax: Double,
    val importanceMin: Int,
    val importanceMax: Int
) : Parcelable {
    val emotionalWeightRange: ClosedFloatingPointRange<Double> get() = emotionalWeightMin..emotionalWeightMax
    val importanceRange: IntRange get() = importanceMin..importanceMax
}
