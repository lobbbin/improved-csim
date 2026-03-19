package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a ministry/department in the government.
 */
@Parcelize
data class Ministry(
    val id: String,
    val name: String,
    val shortName: String,
    val description: String,
    val budget: Long,
    val staffCount: Int,
    val minister: String?, // npcId
    val deputyMinister: String?, // npcId
    val departments: List<Department>,
    val efficiency: Double, // 0.0 to 1.0
    val corruptionLevel: Double, // 0.0 to 1.0
    val publicSatisfaction: Double, // 0.0 to 1.0
    val activeProjects: List<String>, // projectIds
    val pendingAppointments: List<String>, // positionIds
    val recentScandals: List<String>,
    val keyPerformanceIndicators: List<KPI>,
    val isActive: Boolean
) : Parcelable {
    
    val effectiveBudget: Long
        get() = (budget * efficiency * (1 - corruptionLevel * 0.3)).toLong()
    
    val isUnderstaffed: Boolean
        get() = staffCount < departments.sumOf { it.requiredStaff } * 0.8
    
    val overallPerformance: Double
        get() = (efficiency + (1 - corruptionLevel) + publicSatisfaction) / 3
}

@Parcelize
data class Department(
    val id: String,
    val name: String,
    val head: String?, // npcId
    val staffCount: Int,
    val requiredStaff: Int,
    val budget: Long,
    val responsibilities: List<String>,
    val efficiency: Double
) : Parcelable

@Parcelize
data class KPI(
    val name: String,
    val currentValue: Double,
    val targetValue: Double,
    val unit: String,
    val trend: Trend
) : Parcelable {
    
    val progress: Double
        get() = if (targetValue != 0.0) currentValue / targetValue else 0.0
    
    val isOnTrack: Boolean
        get() = progress >= 0.9 && trend != Trend.DECLINING
}

enum class Trend {
    IMPROVING,
    STABLE,
    DECLINING
}

/**
 * Represents an appointment position in government.
 */
@Parcelize
data class Appointment(
    val id: String,
    val title: String,
    val department: String,
    val level: AppointmentLevel,
    val appointee: String?, // npcId
    val termStart: Long?,
    val termEnd: Long?,
    val salary: Long,
    val responsibilities: List<String>,
    val requiredSkills: List<Skill>,
    val confirmationRequired: Boolean,
    val confirmedBy: String?, // parliament committee or body
    val confirmationStatus: ConfirmationStatus,
    val performanceRating: Double?, // 0.0 to 1.0
    val appointedBy: String?, // playerId or npcId
    val isActive: Boolean
) : Parcelable

enum class AppointmentLevel {
    MINISTER,
    DEPUTY_MINISTER,
    DIRECTOR_GENERAL,
    DIRECTOR,
    AMBASSADOR,
    JUDGE,
    CHIEF_EXECUTIVE,
    BOARD_MEMBER
}

enum class ConfirmationStatus {
    PENDING,
    IN_HEARING,
    CONFIRMED,
    REJECTED,
    RECALLED,
    RESIGNED
}

/**
 * Represents a purchase order in government procurement.
 */
@Parcelize
data class PurchaseOrder(
    val id: String,
    val ministry: String, // ministryId
    val department: String?,
    val title: String,
    val description: String,
    val category: PurchaseCategory,
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double,
    val vendor: String?,
    val vendorReputation: Double, // 0.0 to 1.0
    val status: PurchaseStatus,
    val requestedBy: String, // npcId
    val approvedBy: String?, // npcId or playerId
    val approvalDate: Long?,
    val deliveryDate: Long?,
    val urgency: Urgency,
    val justification: String,
    val isCompetitivelyBid: Boolean,
    val bidDetails: BidDetails?,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable

enum class PurchaseCategory {
    EQUIPMENT,
    VEHICLES,
    CONSTRUCTION,
    CONSULTING,
    SUPPLIES,
    SERVICES,
    TECHNOLOGY,
    INFRASTRUCTURE,
    DEFENSE
}

enum class PurchaseStatus {
    DRAFT,
    SUBMITTED,
    UNDER_REVIEW,
    APPROVED,
    REJECTED,
    ORDERED,
    DELIVERED,
    COMPLETED,
    CANCELLED
}

enum class Urgency {
    ROUTINE,
    PRIORITY,
    URGENT,
    EMERGENCY
}

@Parcelize
data class BidDetails(
    val bidNumber: String,
    val openDate: Long,
    val closeDate: Long,
    val bidders: List<Bidder>,
    val winningBidder: String?,
    val winningBid: Double?,
    val lowestBid: Double?,
    val evaluationCriteria: List<EvaluationCriterion>
) : Parcelable

@Parcelize
data class Bidder(
    val id: String,
    val name: String,
    val bidAmount: Double,
    val proposalScore: Double,
    val isQualified: Boolean,
    val disqualificationReason: String?
) : Parcelable

@Parcelize
data class EvaluationCriterion(
    val name: String,
    val weight: Double,
    val description: String
) : Parcelable
