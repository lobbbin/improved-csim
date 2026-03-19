package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a task in the game's node-based task system.
 * Tasks can spawn child tasks and have recursive relationships.
 */
@Parcelize
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val type: TaskType,
    val scope: TaskScope,
    val status: TaskStatus,
    val priority: TaskPriority,
    val parentId: String?, // null for root tasks
    val childIds: List<String>,
    val relatedEventId: String?,
    val relatedDecisionId: String?,
    val assignedTo: String?, // npcId or playerId
    val department: String?, // ministry/department responsible
    val requirements: List<TaskRequirement>,
    val rewards: List<TaskReward>,
    val penalties: List<TaskPenalty>,
    val deadline: Long?, // timestamp
    val timeEstimate: Long, // estimated completion time in ms
    val progress: Float, // 0.0 to 1.0
    val subtasks: List<Subtask>,
    val dependencies: List<String>, // taskIds that must complete first
    val blockingTasks: List<String>, // taskIds this blocks
    val tags: List<String>,
    val createdAt: Long,
    val updatedAt: Long,
    val completedAt: Long?
) : Parcelable {
    
    val isOverdue: Boolean
        get() = deadline != null && deadline < System.currentTimeMillis() && status != TaskStatus.COMPLETED
    
    val isBlocked: Boolean
        get() = dependencies.isNotEmpty() && dependencies.any { /* check if dependency is incomplete */ false }
    
    val canBeStarted: Boolean
        get() = status == TaskStatus.PENDING && !isBlocked && requirements.all { it.isMet }
    
    val completionPercentage: Int
        get() = (progress * 100).toInt()
    
    val hasChildren: Boolean
        get() = childIds.isNotEmpty()
}

enum class TaskType {
    // Macro tasks - major decisions and policy setting
    POLICY_DECISION,
    BUDGET_ALLOCATION,
    TREATY_NEGOTIATION,
    MILITARY_OPERATION,
    ELECTION_CAMPAIGN,
    CONSTITUTIONAL_REFORM,
    MAJOR_INFRASTRUCTURE,
    TRADE_DEAL,
    
    // Micro tasks - specific approvals and individual decisions
    DOCUMENT_APPROVAL,
    APPOINTMENT_DECISION,
    PURCHASE_ORDER,
    PRESS_STATEMENT,
    MEETING_ATTENDANCE,
    SPEECH_WRITING,
    RALLY_ORGANIZATION,
    INDIVIDUAL_NEGOTIATION,
    
    // Administrative tasks
    REPORT_REVIEW,
    DATA_ENTRY,
    CORRESPONDENCE,
    SCHEDULING,
    
    // Crisis tasks
    EMERGENCY_RESPONSE,
    DAMAGE_CONTROL,
    CRISIS_COMMUNICATION,
    
    // Opportunity tasks
    INVESTMENT_OPPORTUNITY,
    DIPLOMATIC_OPENING,
    STRATEGIC_INITIATIVE
}

enum class TaskScope {
    MICRO, // Individual decision, small impact
    MESO, // Department-level, moderate impact
    MACRO // National/international, major impact
}

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    BLOCKED,
    AWAITING_APPROVAL,
    COMPLETED,
    FAILED,
    CANCELLED,
    DEFERRED
}

enum class TaskPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT,
    CRITICAL
}

@Parcelize
data class TaskRequirement(
    val type: RequirementType,
    val description: String,
    val targetId: String?,
    val value: Double,
    val isMet: Boolean
) : Parcelable

@Parcelize
data class TaskReward(
    val type: RewardType,
    val description: String,
    val value: Double,
    val isGuaranteed: Boolean
) : Parcelable

enum class RewardType {
    APPROVAL_RATING,
    POLITICAL_CAPITAL,
    TREASURY,
    REPUTATION,
    INFLUENCE,
    HAPPINESS,
    STABILITY,
    PERSONAL_WEALTH,
    PARTY_SUPPORT
}

@Parcelize
data class TaskPenalty(
    val type: PenaltyType,
    val description: String,
    val value: Double,
    val probability: Double
) : Parcelable

enum class PenaltyType {
    APPROVAL_RATING,
    POLITICAL_CAPITAL,
    TREASURY,
    REPUTATION,
    STABILITY,
    HAPPINESS,
    SCANDAL_RISK,
    INTERNATIONAL_RELATIONS
}

@Parcelize
data class Subtask(
    val id: String,
    val title: String,
    val isCompleted: Boolean,
    val order: Int
) : Parcelable

/**
 * Task template for procedural generation of tasks.
 */
@Parcelize
data class TaskTemplate(
    val id: String,
    val name: String,
    val baseTitle: String,
    val baseDescription: String,
    val type: TaskType,
    val scope: TaskScope,
    val priorityRange: ClosedRange<TaskPriority>,
    val timeEstimateRange: ClosedRange<Long>,
    val requirementTemplates: List<RequirementTemplate>,
    val rewardTemplates: List<RewardTemplate>,
    val penaltyTemplates: List<PenaltyTemplate>,
    val childTaskTemplates: List<String>, // templateIds
    val category: TaskCategory,
    val tags: List<String>
) : Parcelable

enum class TaskCategory {
    GOVERNANCE,
    POLITICS,
    ECONOMY,
    DIPLOMACY,
    MILITARY,
    SOCIAL,
    INFRASTRUCTURE,
    PERSONAL,
    CRISIS
}

@Parcelize
data class RequirementTemplate(
    val type: RequirementType,
    val descriptionTemplate: String,
    val valueRange: ClosedRange<Double>
) : Parcelable

@Parcelize
data class RewardTemplate(
    val type: RewardType,
    val descriptionTemplate: String,
    val valueRange: ClosedRange<Double>,
    val guaranteedProbability: Double
) : Parcelable

@Parcelize
data class PenaltyTemplate(
    val type: PenaltyType,
    val descriptionTemplate: String,
    val valueRange: ClosedRange<Double>,
    val probabilityRange: ClosedRange<Double>
) : Parcelable
