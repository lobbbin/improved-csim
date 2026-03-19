package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a law/bill in the legislative system.
 */
@Parcelize
data class Bill(
    val id: String,
    val title: String,
    val shortTitle: String,
    val billNumber: String,
    val description: String,
    val sponsor: String, // npcId or playerId
    val coSponsors: List<String>, // npcIds
    val category: BillCategory,
    val type: BillType,
    val clauses: List<Clause>,
    val amendments: List<Amendment>,
    val status: BillStatus,
    val introducedDate: Long,
    val currentCommittee: String?,
    val votes: List<Vote>,
    val votingResults: VotingResults?,
    val signedBy: String?, // npcId or playerId
    val signedDate: Long?,
    val vetoedBy: String?,
    val vetoDate: Long?,
    val vetoReason: String?,
    val overrideAttempts: Int,
    val publicOpinion: Double, // -1.0 to 1.0
    val mediaCoverage: Int, // 0-100
    val constitutionality: Double, // 0.0 to 1.0 probability of being constitutional
    val impact: BillImpact,
    val effectiveDate: Long?,
    val expiryDate: Long?
) : Parcelable {
    
    val isLaw: Boolean
        get() = status == BillStatus.ENACTED
    
    val isPending: Boolean
        get() = status in listOf(BillStatus.INTRODUCED, BillStatus.IN_COMMITTEE, BillStatus.ON_FLOOR)
    
    val daysInLegislature: Int
        get() = ((System.currentTimeMillis() - introducedDate) / (1000 * 60 * 60 * 24)).toInt()
}

enum class BillCategory {
    BUDGET,
    TAXATION,
    HEALTHCARE,
    EDUCATION,
    DEFENSE,
    FOREIGN_POLICY,
    IMMIGRATION,
    CRIMINAL_JUSTICE,
    CIVIL_RIGHTS,
    ENVIRONMENT,
    LABOR,
    TRADE,
    INFRASTRUCTURE,
    TECHNOLOGY,
    SOCIAL_WELFARE,
    CONSTITUTIONAL
}

enum class BillType {
    ORDINARY,
    APPROPRIATION,
    REVENUE,
    CONSTITUTIONAL_AMENDMENT,
    TREATY,
    DECLARATION,
    CONFIRMATION,
    RESOLUTION
}

enum class BillStatus {
    DRAFT,
    INTRODUCED,
    IN_COMMITTEE,
    COMMITTEE_REPORTED,
    ON_FLOOR,
    PASSED_ONE_CHAMBER,
    PASSED_BOTH_CHAMBERS,
    SENT_TO_EXECUTIVE,
    ENACTED,
    VETOED,
    VETO_OVERRIDE_FAILED,
    FAILED,
    EXPIRED,
    STRUCK_DOWN
}

@Parcelize
data class Clause(
    val id: String,
    val number: String,
    val title: String,
    val text: String,
    val category: ClauseCategory,
    val budgetImpact: Long?,
    val affectedGroups: List<String>,
    val implementationNotes: String?
) : Parcelable

enum class ClauseCategory {
    DEFINITIONS,
    PURPOSE,
    PROVISIONS,
    PENALTIES,
    FUNDING,
    IMPLEMENTATION,
    OVERSIGHT,
    SUNSET
}

@Parcelize
data class Amendment(
    val id: String,
    val billId: String,
    val sponsor: String,
    val text: String,
    val replacesClause: String?, // clauseId
    val addsAfterClause: String?, // clauseId
    val status: AmendmentStatus,
    val votes: List<Vote>
) : Parcelable

enum class AmendmentStatus {
    PROPOSED,
    ACCEPTED,
    REJECTED,
    WITHDRAWN
}

@Parcelize
data class Vote(
    val voterId: String, // npcId or playerId
    val voterName: String,
    val party: String?, // partyId
    val vote: VoteType,
    val reason: String?,
    val timestamp: Long
) : Parcelable

enum class VoteType {
    YEA,
    NAY,
    ABSTAIN,
    PRESENT,
    ABSENT
}

@Parcelize
data class VotingResults(
    val totalMembers: Int,
    val yeas: Int,
    val nays: Int,
    val abstentions: Int,
    val absent: Int,
    val passed: Boolean,
    val requiredMajority: Double, // e.g., 0.5 for simple, 0.67 for supermajority
    val partyBreakdown: Map<String, PartyVoteBreakdown>
) : Parcelable {
    
    val actualMajority: Double
        get() = if (yeas + nays > 0) yeas.toDouble() / (yeas + nays) else 0.0
    
    val turnoutRate: Double
        get() = if (totalMembers > 0) (yeas + nays + abstentions).toDouble() / totalMembers else 0.0
}

@Parcelize
data class PartyVoteBreakdown(
    val partyId: String,
    val totalMembers: Int,
    val yeas: Int,
    val nays: Int,
    val abstentions: Int
) : Parcelable

@Parcelize
data class BillImpact(
    val economicImpact: Double, // -1.0 to 1.0 (negative to positive)
    val socialImpact: Double,
    val politicalImpact: Double,
    val internationalImpact: Double,
    val environmentalImpact: Double,
    val affectedSectors: List<String>,
    val affectedPopulations: List<AffectedPopulation>,
    val implementationCost: Long?,
    val annualRecurringCost: Long?
) : Parcelable

@Parcelize
data class AffectedPopulation(
    val group: String,
    val estimatedSize: Long,
    val impactType: ImpactType,
    val severity: Double // 0.0 to 1.0
) : Parcelable

enum class ImpactType {
    BENEFIT,
    HARM,
    MIXED,
    NEUTRAL
}

/**
 * Represents a court case in the judicial system.
 */
@Parcelize
data class CourtCase(
    val id: String,
    val title: String,
    val caseNumber: String,
    val type: CaseType,
    val court: String,
    val judge: String, // npcId
    val plaintiffs: List<String>, // npcIds or organizations
    val defendants: List<String>,
    val attorneys: Map<String, String>, // party -> attorneyId
    val charges: List<Charge>,
    val arguments: List<LegalArgument>,
    val evidence: List<Evidence>,
    val hearings: List<Hearing>,
    val status: CaseStatus,
    val verdict: Verdict?,
    val appeals: List<String>, // caseIds of appeals
    val constitutionalIssues: List<String>,
    val precedentSet: String?,
    val mediaInterest: Int, // 0-100
    val filedDate: Long,
    val resolvedDate: Long?
) : Parcelable

enum class CaseType {
    CRIMINAL,
    CIVIL,
    CONSTITUTIONAL,
    ADMINISTRATIVE,
    ELECTORAL,
    INTERNATIONAL
}

enum class CaseStatus {
    FILED,
    PRE_TRIAL,
    IN_TRIAL,
    UNDER_DELIBERATION,
    DECIDED,
    APPEALED,
    UPHELD,
    OVERTURNED,
    DISMISSED,
    SETTLED
}

@Parcelize
data class Charge(
    val id: String,
    val name: String,
    val description: String,
    val severity: ChargeSeverity,
    val potentialPenalty: String,
    val statuteReference: String
) : Parcelable

enum class ChargeSeverity {
    INFRACTION,
    MISDEMEANOR,
    FELONY,
    CAPITAL
}

@Parcelize
data class LegalArgument(
    val id: String,
    val party: String, // plaintiff or defendant
    val title: String,
    val description: String,
    val supportingPrecedents: List<String>,
    val counterArguments: List<String>
) : Parcelable

@Parcelize
data class Evidence(
    val id: String,
    val type: EvidenceType,
    val description: String,
    val submittedBy: String,
    val isAdmitted: Boolean,
    val weight: Double // 0.0 to 1.0
) : Parcelable

enum class EvidenceType {
    DOCUMENT,
    TESTIMONY,
    PHYSICAL,
    DIGITAL,
    EXPERT_OPINION,
    VIDEO,
    AUDIO
}

@Parcelize
data class Hearing(
    val id: String,
    val date: Long,
    val type: HearingType,
    val summary: String,
    val duration: Long, // milliseconds
    val attendees: List<String>
) : Parcelable

enum class HearingType {
    INITIAL,
    PRELIMINARY,
    EVIDENTIARY,
    ORAL_ARGUMENT,
    CLOSING,
    SENTENCING
}

@Parcelize
data class Verdict(
    val decision: VerdictDecision,
    val summary: String,
    val damages: Long?,
    val sentence: String?,
    val conditions: List<String>,
    val dissentingOpinions: List<String>,
    val date: Long
) : Parcelable

enum class VerdictDecision {
    GUILTY,
    NOT_GUILTY,
    LIABLE,
    NOT_LIABLE,
    UPHELD,
    OVERTURNED,
    DISMISSED,
    SETTLED,
    CONSTITUTIONAL,
    UNCONSTITUTIONAL
}
