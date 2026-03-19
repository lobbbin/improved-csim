package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * MASSIVELY EXPANDED Elections System
 * Includes: Primaries, Electoral College, Gerrymandering, Coalition Formation,
 * Impeachment, Recall Elections, Referendums, Campaign Finance, PAC Management, etc.
 */

@Parcelize
data class ElectionsSystem(
    val countryId: String,
    val electionType: ElectionType,
    val votingSystem: VotingSystem,
    val electoralCollege: ElectoralCollege?,
    val primarySystem: PrimarySystem?,
    val campaignFinance: CampaignFinanceSystem,
    val redistricting: RedistrictingSystem,
    val voterRegistration: VoterRegistrationSystem,
    val activeElections: List<Election>,
    val completedElections: List<ElectionResult>,
    val upcomingElections: List<ScheduledElection>,
    val referendums: List<Referendum>,
    val recallPetitions: List<RecallPetition>,
    val impeachmentProceedings: List<ImpeachmentProceeding>,
    val politicalParties: List<ElectoralParty>,
    val electoralCommissions: List<ElectoralCommission>,
    val lastUpdated: Long
) : Parcelable

// Election Types

enum class ElectionType {
    PRESIDENTIAL,
    PARLIAMENTARY,
    CONGRESSIONAL,
    LOCAL,
    MUNICIPAL,
    REGIONAL,
    EUROPEAN,
    CONSTITUTIONAL_ASSEMBLY,
    RECALL,
    RUNOFF,
    SPECIAL,
    BYELECTION
}

enum class VotingSystem {
    FIRST_PAST_THE_POST,
    TWO_ROUND_SYSTEM,
    PROPORTIONAL_REPRESENTATION,
    MIXED_MEMBER_PROPORTIONAL,
    SINGLE_TRANSFERABLE_VOTE,
    INSTANT_RUNOFF,
    APPROVAL_VOTING,
    RANKED_CHOICE,
    PARTY_LIST_PR,
    PARALLEL_VOTING,
    LIMITED_VOTE,
    CUMULATIVE_VOTING,
    BORDA_COUNT
}

// Electoral College System

@Parcelize
data class ElectoralCollege(
    val totalElectors: Int,
    val votesToWin: Int,
    val stateAllocation: List<StateElectoralVotes>,
    val winnerTakesAll: Boolean,
    val faithlessElectorLaws: Boolean,
    val congressionalDistrictMethod: Boolean,
    val distributionHistory: List<ElectoralCollegeResult>
) : Parcelable

@Parcelize
data class StateElectoralVotes(
    val stateId: String,
    val stateName: String,
    val electors: Int,
    val population: Long,
    val safeParty: String?,
    val leaningParty: String?,
    val currentPolling: Map<String, Double>
) : Parcelable

@Parcelize
data class ElectoralCollegeResult(
    val electionId: String,
    val date: Long,
    val candidateResults: Map<String, Int>, // candidate -> electoral votes
    val stateResults: Map<String, String>, // state -> winning candidate
    val faithlessElectors: Int,
    val totalTurnout: Double
) : Parcelable

// Primary System

@Parcelize
data class PrimarySystem(
    val type: PrimaryType,
    val openPrimary: Boolean,
    val junglePrimary: Boolean,
    val superTuesday: Boolean,
    val earlyStates: List<String>,
    val delegateAllocation: DelegateAllocationType,
    val totalDelegates: Int,
    val superDelegates: Int,
    val superDelegatesRole: SuperDelegateRole,
    val caucuses: List<Caucus>,
    val primaries: List<Primary>,
    val conventionRules: ConventionRules
) : Parcelable

enum class PrimaryType {
    CLOSED,
    OPEN,
    SEMI_CLOSED,
    SEMI_OPEN,
    BLANKET,
    TOP_TWO
}

enum class DelegateAllocationType {
    PROPORTIONAL,
    WINNER_TAKE_ALL,
    HYBRID,
    THRESHOLD_PROPORTIONAL
}

enum class SuperDelegateRole {
    FULL_VOTING,
    FIRST_BALLOT_ONLY,
    TIEBREAKER,
    CEREMONIAL
}

@Parcelize
data class Caucus(
    val id: String,
    val stateId: String,
    val name: String,
    val date: Long,
    val participants: Int,
    val viabilityThreshold: Double,
    val rounds: Int,
    val alignmentProcess: String
) : Parcelable

@Parcelize
data class Primary(
    val id: String,
    val stateId: String,
    val name: String,
    val date: Long,
    val type: PrimaryType,
    val registeredVoters: Long,
    val earlyVoting: Boolean,
    val earlyVotingStart: Long?,
    val absenteeVoting: Boolean,
    val noExcuseAbsentee: Boolean
) : Parcelable

@Parcelize
data class ConventionRules(
    val totalDelegates: Int,
    val firstBallotWin: Int,
    val brokeredConventionThreshold: Int,
    val floorCrossingAllowed: Boolean,
    val superdelegateVotingRound: Int,
    val platformCommittee: Int,
    val rulesCommittee: Int
) : Parcelable

// Campaign Finance

@Parcelize
data class CampaignFinanceSystem(
    val contributionLimits: ContributionLimits,
    val publicFunding: PublicFunding?,
    val pacs: List<PAC>,
    val superPacs: List<SuperPAC>,
    val darkMoneyGroups: List<DarkMoneyGroup>,
    val disclosureRequirements: DisclosureRequirements,
    val enforcementAgency: String,
    val fines: List<CampaignFinanceViolation>,
    val totalSpending: Long,
    val spendingByCategory: Map<String, Long>
) : Parcelable

@Parcelize
data class ContributionLimits(
    val individualLimit: Long,
    val pacLimit: Long,
    val partyLimit: Long,
    val candidateLimit: Long,
    val primaryVsGeneral: Boolean,
    val indexedForInflation: Boolean,
    val aggregateLimit: Long?
) : Parcelable

@Parcelize
data class PublicFunding(
    val available: Boolean,
    val presidentialFund: Boolean,
    val matchingFunds: Boolean,
    val matchingRatio: Double,
    val spendingLimits: Long,
    val qualifyingThreshold: Double,
    val participatingCandidates: List<String>
) : Parcelable

@Parcelize
data class PAC(
    val id: String,
    val name: String,
    val type: PACType,
    val treasurer: String,
    val registrationDate: Long,
    val totalReceipts: Long,
    val totalDisbursements: Long,
    val cashOnHand: Long,
    val supportedCandidates: List<String>,
    val independentExpenditures: Long,
    val donors: Long
) : Parcelable

enum class PACType {
    CONNECTED,
    NONCONNECTED,
    LEADERSHIP,
    CANDIDATE_AUTHORIZED,
    SUPER,
    HYBRID
}

@Parcelize
data class SuperPAC(
    val id: String,
    val name: String,
    val treasurer: String,
    val registrationDate: Long,
    val totalReceipts: Long,
    val totalDisbursements: Long,
    val cashOnHand: Long,
    val independentExpenditures: Long,
    val topDonors: List<String>,
    val supportedCandidates: List<String>,
    val opposedCandidates: List<String>
) : Parcelable

@Parcelize
data class DarkMoneyGroup(
    val id: String,
    val name: String,
    val type: DarkMoneyType,
    val organization: String, // 501(c)(4), etc.
    val totalSpending: Long,
    val politicalActivity: Double,
    val donorDisclosure: Boolean
) : Parcelable

enum class DarkMoneyType {
    SOCIAL_WELFARE,
    TRADE_ASSOCIATION,
    LABOR_UNION,
    CHAMBER_OF_COMMERCE,
    OTHER_501C4,
    LLC,
    PARTNERSHIP
}

@Parcelize
data class DisclosureRequirements(
    val donorThreshold: Long,
    val frequency: DisclosureFrequency,
    val electronicFiling: Boolean,
    val realTimeReporting: Boolean,
    val itemizedExpenses: Boolean,
    val lastFourSSN: Boolean
) : Parcelable

enum class DisclosureFrequency {
    QUARTERLY,
    MONTHLY,
    WEEKLY_PRE_ELECTION,
    REAL_TIME
}

@Parcelize
data class CampaignFinanceViolation(
    val id: String,
    val committeeId: String,
    val type: ViolationType,
    val amount: Long,
    val fine: Long,
    val date: Long,
    val status: ViolationStatus
) : Parcelable

enum class ViolationType {
    EXCESS_CONTRIBUTION,
    PROHIBITED_CONTRIBUTION,
    FAILURE_TO_REPORT,
    LATE_FILING,
    RECORDKEEPING,
    COORDINATION,
    FOREIGN_CONTRIBUTION
}

enum class ViolationStatus {
    PENDING,
    INVESTIGATING,
    FINED,
    SETTLED,
    DISMISSED
}

// Redistricting / Gerrymandering

@Parcelize
data class RedistrictingSystem(
    val method: RedistrictingMethod,
    val commission: RedistrictingCommission?,
    val districtMap: List<ElectoralDistrict>,
    val gerrymanderingIndex: Double,
    val partisanBias: Double,
    val efficiencyGap: Double,
    val meanMedianDifference: Double,
    val seatsVotesCurve: List<SeatsVotesPoint>,
    val packingCrackingAnalysis: PackingCrackingAnalysis,
    val legalChallenges: List<RedistrictingLawsuit>,
    val lastRedistrictingYear: Int,
    val nextRedistrictingYear: Int
) : Parcelable

enum class RedistrictingMethod {
    LEGISLATIVE,
    ADVISORY_COMMISSION,
    CITIZEN_COMMISSION,
    POLITICIAN_COMMISSION,
    INDEPENDENT_COMMISSION,
    COURT_DRAWN,
    COMPUTER_GENERATED
}

@Parcelize
data class RedistrictingCommission(
    val id: String,
    val name: String,
    val members: Int,
    val partisanBalance: String,
    val selectionProcess: String,
    val chair: String?,
    val meetings: Int,
    val publicHearings: Int,
    val transparencyScore: Double
) : Parcelable

@Parcelize
data class ElectoralDistrict(
    val id: String,
    val name: String,
    val type: DistrictType,
    val population: Long,
    val idealPopulation: Long,
    val deviation: Double,
    val compactnessScore: Double,
    val incumbents: List<String>,
    val partisanLean: Double,
    val demographicComposition: Map<String, Double>,
    val boundaries: String?, // GeoJSON
    val communitiesOfInterest: List<String>,
    val votingRightsActCompliance: Boolean
) : Parcelable

enum class DistrictType {
    CONGRESSIONAL,
    STATE_SENATE,
    STATE_HOUSE,
    COUNTY,
    MUNICIPAL,
    SCHOOL_BOARD
}

@Parcelize
data class PackingCrackingAnalysis(
    val packedDistricts: Int,
    val crackedDistricts: Int,
    val competitiveDistricts: Int,
    val wastedVotesTotal: Long,
    val wastedVotesByParty: Map<String, Long>,
    val efficiencyGap: Double
) : Parcelable

@Parcelize
data class SeatsVotesPoint(
    val voteShare: Double,
    val seatShare: Double
) : Parcelable

@Parcelize
data class RedistrictingLawsuit(
    val id: String,
    val plaintiff: String,
    val defendant: String,
    val claims: List<String>,
    val filingDate: Long,
    val court: String,
    val status: LawsuitStatus,
    val outcome: String?,
    val remedy: String?
) : Parcelable

enum class LawsuitStatus {
    FILED,
    PENDING,
    HEARING,
    DECISION,
    APPEALED,
    SETTLED,
    DISMISSED
}

// Voter Registration

@Parcelize
data class VoterRegistrationSystem(
    val registrationMethod: RegistrationMethod,
    val registrationDeadline: Int, // days before election
    val sameDayRegistration: Boolean,
    val automaticRegistration: Boolean,
    val onlineRegistration: Boolean,
    val registrationPurges: Boolean,
    val purgeCriteria: List<String>,
    val idRequirements: IDRequirement,
    val registrationStats: RegistrationStats,
    val registrationDrives: List<RegistrationDrive>
) : Parcelable

enum class RegistrationMethod {
    SELF_REGISTRATION,
    AUTOMATIC,
    OPT_OUT,
    MOTOR_VOTER,
    AGENCY_BASED,
    ONLINE_ONLY
}

@Parcelize
data class IDRequirement(
    val required: Boolean,
    val acceptedIds: List<String>,
    val freeIdAvailable: Boolean,
    val strictness: IDStrictness,
    val exemptions: List<String>
) : Parcelable

enum class IDStrictness {
    NONE,
    NON_STRICT,
    STRICT,
    STRICT_PLUS
}

@Parcelize
data class RegistrationStats(
    val totalRegistered: Long,
    val eligiblePopulation: Long,
    val registrationRate: Double,
    val byAge: Map<String, Long>,
    val byGender: Map<String, Long>,
    val byRace: Map<String, Long>,
    val byParty: Map<String, Long>,
    val inactiveRegistrations: Long,
    val pendingApplications: Long
) : Parcelable

@Parcelize
data class RegistrationDrive(
    val id: String,
    val organization: String,
    val startDate: Long,
    val endDate: Long?,
    val registrationsCollected: Int,
    val approvedRegistrations: Int,
    val rejectedRegistrations: Int,
    val targetDemographics: List<String>
) : Parcelable

// Active Election

@Parcelize
data class Election(
    val id: String,
    val name: String,
    val type: ElectionType,
    val date: Long,
    val offices: List<ElectedOffice>,
    val candidates: List<Candidate>,
    val ballotMeasures: List<BallotMeasure>,
    val pollingPlaces: Int,
    val earlyVotingStart: Long?,
    val earlyVotingEnd: Long?,
    val absenteeDeadline: Long?,
    val turnoutProjection: Double,
    val currentPolling: List<PollResult>,
    val mediaCoverage: Double,
    val totalSpending: Long,
    val debates: List<Debate>,
    val scandals: List<ElectionScandal>,
    val endorsements: List<Endorsement>,
    val status: ElectionStatus
) : Parcelable

enum class ElectionStatus {
    ANNOUNCED,
    CAMPAIGNING,
    EARLY_VOTING,
    VOTING_DAY,
    COUNTING,
    CERTIFIED,
    RECOUNT,
    CONTESTED,
    FINAL
}

@Parcelize
data class ElectedOffice(
    val id: String,
    val title: String,
    val level: GovernmentLevel,
    val termLength: Int,
    val termLimit: Int?,
    val salary: Long,
    val responsibilities: List<String>,
    val qualifications: List<String>,
    val incumbents: List<String>
) : Parcelable

enum class GovernmentLevel {
    FEDERAL,
    STATE,
    COUNTY,
    MUNICIPAL,
    SPECIAL_DISTRICT
}

@Parcelize
data class Candidate(
    val id: String,
    val fullName: String,
    val party: String,
    val office: String,
    val incumbency: Boolean,
    val campaignSlogan: String,
    val platform: List<String>,
    val campaignFinance: CandidateFinance,
    val pollNumbers: List<PollResult>,
    val endorsements: List<Endorsement>,
    val controversies: List<String>,
    val experience: List<CandidateExperience>,
    val demographics: CandidateDemographics,
    val campaignEvents: List<CampaignEvent>,
    val advertisements: List<Advertisement>,
    val status: CandidateStatus
) : Parcelable

enum class CandidateStatus {
    ACTIVE,
    SUSPENDED,
    WITHDRAWN,
    DISQUALIFIED,
    DECEASED
}

@Parcelize
data class CandidateFinance(
    val totalRaised: Long,
    val totalSpent: Long,
    val cashOnHand: Long,
    val debts: Long,
    val smallDonors: Long, // < $200
    val largeDonors: Long,
    val pacMoney: Long,
    val selfFinanced: Long,
    val publicFunding: Long,
    val averageDonation: Double
) : Parcelable

@Parcelize
data class PollResult(
    val id: String,
    val pollster: String,
    val date: Long,
    val sampleSize: Int,
    val marginOfError: Double,
    val methodology: String,
    val results: Map<String, Double>, // candidate -> percentage
    val favorableRatings: Map<String, Double>,
    val unfavorableRatings: Map<String, Double>,
    val headToHead: Map<String, Map<String, Double>>?, // candidate vs candidate
    val crosstabs: Map<String, Map<String, Double>>?
) : Parcelable

@Parcelize
data class Endorsement(
    val id: String,
    val endorser: String,
    val type: EndorserType,
    val candidateId: String,
    val date: Long,
    val impact: Double,
    val visibility: Double
) : Parcelable

enum class EndorserType {
    NEWSPAPER,
    LABOR_UNION,
    POLITICAL_PARTY,
    SITTING_POLITICIAN,
    CELEBRITY,
    ORGANIZATION,
    RELIGIOUS_LEADER,
    COMMUNITY_LEADER
}

@Parcelize
data class CandidateExperience(
    val position: String,
    val organization: String,
    val startYear: Int,
    val endYear: Int?,
    val accomplishments: List<String>
) : Parcelable

@Parcelize
data class CandidateDemographics(
    val age: Int,
    val gender: String,
    val race: String,
    val religion: String?,
    val education: String,
    val occupation: String,
    val hometown: String,
    val maritalStatus: String?,
    val children: Int
) : Parcelable

@Parcelize
data class CampaignEvent(
    val id: String,
    val type: CampaignEventType,
    val title: String,
    val date: Long,
    val location: String,
    val attendees: Int?,
    val mediaCoverage: Double,
    val impact: Double
) : Parcelable

enum class CampaignEventType {
    RALLY,
    TOWN_HALL,
    FUNDRAISER,
    DEBATE,
    PRESS_CONFERENCE,
    CANVASSING,
    PHONE_BANK,
    AD_LAUNCH,
    SCANDAL,
    ENDORSEMENT_EVENT
}

@Parcelize
data class Advertisement(
    val id: String,
    val candidateId: String,
    val type: AdType,
    val title: String,
    val duration: Int?, // seconds for TV/radio
    val budget: Long,
    val airDates: List<Long>,
    val markets: List<String>,
    val sentiment: AdSentiment,
    val factCheckRating: String?,
    val controversies: List<String>
) : Parcelable

enum class AdType {
    TV,
    RADIO,
    DIGITAL,
    PRINT,
    BILLBOARD,
    DIRECT_MAIL,
    TEXT,
    SOCIAL_MEDIA
}

enum class AdSentiment {
    POSITIVE,
    CONTRAST,
    NEGATIVE,
    BIOGRAPHICAL,
    POLICY
}

@Parcelize
data class Debate(
    val id: String,
    val date: Long,
    val location: String,
    val host: String,
    val participants: List<String>,
    val moderators: List<String>,
    val topics: List<String>,
    val viewership: Long,
    val winner: String?, // poll-determined
    val memorableMoments: List<String>
) : Parcelable

@Parcelize
data class ElectionScandal(
    val id: String,
    val title: String,
    val description: String,
    val affectedCandidate: String,
    val severity: Double,
    val date: Long,
    val mediaCoverage: Double,
    val impact: Double
) : Parcelable

// Ballot Measures

@Parcelize
data class BallotMeasure(
    val id: String,
    val title: String,
    val summary: String,
    val fullText: String,
    val type: BallotMeasureType,
    val proponents: List<String>,
    val opponents: List<String>,
    val funding: BallotMeasureFinance,
    val polling: List<PollResult>,
    val endorsements: List<Endorsement>,
    val status: BallotMeasureStatus
) : Parcelable

enum class BallotMeasureType {
    INITIATIVE,
    REFERENDUM,
    CONSTITUTIONAL_AMENDMENT,
    BOND_MEASURE,
    TAX_LEVY,
    RECALL_QUESTION,
    ADVISORY_QUESTION
}

enum class BallotMeasureStatus {
    QUALIFIED,
    CAMPAIGNING,
    VOTING,
    PASSED,
    FAILED,
    CHALLENGED,
    STRUCK_DOWN
}

@Parcelize
data class BallotMeasureFinance(
    val yesCommittee: String,
    val noCommittee: String,
    val yesSpending: Long,
    val noSpending: Long,
    val topDonorsYes: List<String>,
    val topDonorsNo: List<String>
) : Parcelable

// Election Results

@Parcelize
data class ElectionResult(
    val electionId: String,
    val date: Long,
    val winners: Map<String, String>, // office -> candidate
    val results: List<RaceResult>,
    val turnout: TurnoutData,
    val certificationDate: Long?,
    val challenges: List<ElectionChallenge>,
    val recounts: List<Recount>,
    val historicalSignificance: String?
) : Parcelable

@Parcelize
data class RaceResult(
    val office: String,
    val candidates: Map<String, Long>, // candidate -> votes
    val totalVotes: Long,
    val winner: String?,
    val margin: Long,
    val marginPercentage: Double,
    val runoffRequired: Boolean,
    val calledBy: List<String>, // media outlets
    val calledAt: Long?
) : Parcelable

@Parcelize
data class TurnoutData(
    val totalVotes: Long,
    val eligibleVoters: Long,
    val registeredVoters: Long,
    val turnoutRate: Double,
    val byAge: Map<String, Double>,
    val byGender: Map<String, Double>,
    val byRace: Map<String, Double>,
    val byEducation: Map<String, Double>,
    val byParty: Map<String, Double>,
    val earlyVoting: Long,
    val absentee: Long,
    val provisional: Long
) : Parcelable

@Parcelize
data class ElectionChallenge(
    val id: String,
    val challenger: String,
    val grounds: String,
    val filingDate: Long,
    val court: String,
    val status: ChallengeStatus,
    val outcome: String?
) : Parcelable

enum class ChallengeStatus {
    FILED,
    HEARING,
    APPEALED,
    DECIDED,
    DISMISSED
}

@Parcelize
data class Recount(
    val id: String,
    val raceId: String,
    val requestedBy: String,
    val reason: String,
    val startDate: Long,
    val endDate: Long?,
    val originalResult: Map<String, Long>,
    val recountResult: Map<String, Long>,
    val changedVotes: Int,
    val cost: Long
) : Parcelable

// Scheduled Elections

@Parcelize
data class ScheduledElection(
    val id: String,
    val name: String,
    val type: ElectionType,
    val date: Long,
    val offices: List<String>,
    val filingDeadline: Long,
    val primaryDate: Long?,
    val filingFee: Long,
    val signatureRequirement: Int?
) : Parcelable

// Referendum

@Parcelize
data class Referendum(
    val id: String,
    val title: String,
    val question: String,
    val description: String,
    val proposedBy: String,
    val date: Long,
    val type: ReferendumType,
    val status: ReferendumStatus,
    val results: ReferendumResult?,
    val campaigning: List<ReferendumCampaign>,
    val legalityChallenge: LegalChallenge?
) : Parcelable

enum class ReferendumType {
    BINDING,
    ADVISORY,
    CONSTITUTIONAL,
    INDEPENDENCE,
    BREXIT_STYLE
}

enum class ReferendumStatus {
    PROPOSED,
    LEGISLATED,
    CAMPAIGNING,
    VOTING,
    PASSED,
    FAILED,
    IMPLEMENTED,
    CHALLENGED,
    STRUCK_DOWN
}

@Parcelize
data class ReferendumResult(
    val yesVotes: Long,
    val noVotes: Long,
    val turnout: Double,
    val byRegion: Map<String, Map<String, Long>>,
    val certified: Boolean,
    val implementationStatus: String?
) : Parcelable

@Parcelize
data class ReferendumCampaign(
    val side: String, // "yes" or "no"
    val committee: String,
    val spending: Long,
    val keyArguments: List<String>,
    val spokespersons: List<String>
) : Parcelable

// Recall Elections

@Parcelize
data class RecallPetition(
    val id: String,
    val targetOfficial: String,
    val targetOffice: String,
    val organizers: String,
    val reason: String,
    val startDate: Long,
    val signatureGoal: Long,
    val signaturesCollected: Long,
    val signaturesValidated: Long,
    val deadline: Long,
    val status: RecallStatus,
    val counterCampaign: String?,
    val recallElection: Election?
) : Parcelable

enum class RecallStatus {
    CIRCULATING,
    SUBMITTED,
    VERIFYING,
    QUALIFIED,
    ELECTION_SCHEDULED,
    ELECTION_HELD,
    RECALLED,
    FAILED,
    EXPIRED,
    LEGAL_CHALLENGE
}

// Impeachment

@Parcelize
data class ImpeachmentProceeding(
    val id: String,
    val targetOfficial: String,
    val targetOffice: String,
    val charges: List<ImpeachmentCharge>,
    val introductionDate: Long,
    val houseCommittee: String,
    val investigationStatus: InvestigationStatus,
    val committeeHearings: List<Hearing>,
    val houseVoteDate: Long?,
    val houseVoteResult: VoteResult?,
    val senateTrialDate: Long?,
    val senateTrialStatus: TrialStatus?,
    val senateVoteResult: VoteResult?,
    val chiefJusticePresiding: Boolean,
    val managers: List<String>,
    val defenseTeam: List<String>,
    val witnesses: List<String>,
    val evidence: List<Evidence>,
    val publicOpinion: Double // support for removal
) : Parcelable

@Parcelize
data class ImpeachmentCharge(
    val id: String,
    val article: String,
    val description: String,
    val evidence: List<String>,
    val legalBasis: String,
    val voteTally: VoteResult?
) : Parcelable

enum class InvestigationStatus {
    PRELIMINARY,
    INVESTIGATION,
    HEARINGS,
    COMMITTEE_VOTE,
    FLOOR_VOTE,
    SENATE_TRIAL,
    ACQUITTAL,
    CONVICTION
}

enum class TrialStatus {
    PENDING,
    OPENING_ARGUMENTS,
    WITNESS_TESTIMONY,
    CLOSING_ARGUMENTS,
    DELIBERATION,
    VERDICT
}

@Parcelize
data class Hearing(
    val id: String,
    val date: Long,
    val witnesses: List<Witness>,
    val questions: List<Question>,
    val duration: Int,
    val viewership: Long
) : Parcelable

@Parcelize
data class Witness(
    val id: String,
    val name: String,
    val title: String,
    val testimony: String,
    val evidence: List<String>,
    val credibility: Double,
    val party: String // called by which party
) : Parcelable

@Parcelize
data class Question(
    val id: String,
    val questioner: String,
    val question: String,
    val answer: String,
    val followUps: List<String>
) : Parcelable

@Parcelize
data class Evidence(
    val id: String,
    val type: EvidenceType,
    val description: String,
    val source: String,
    val dateObtained: Long,
    val admitted: Boolean
) : Parcelable

enum class EvidenceType {
    DOCUMENT,
    EMAIL,
    RECORDING,
    TESTIMONY,
    FINANCIAL_RECORD,
    COMMUNICATION,
    PHOTOGRAPH,
    VIDEO
}

@Parcelize
data class VoteResult(
    val yeas: Int,
    val nays: Int,
    val present: Int,
    val absent: Int,
    val passed: Boolean,
    val requiredThreshold: Int,
    val partyBreakdown: Map<String, VoteBreakdown>
) : Parcelable

@Parcelize
data class VoteBreakdown(
    val yeas: Int,
    val nays: Int,
    val present: Int,
    val absent: Int
) : Parcelable

// Political Parties (Electoral)

@Parcelize
data class ElectoralParty(
    val id: String,
    val name: String,
    val abbreviation: String,
    val color: String,
    val founded: Int,
    val ideology: String,
    val platform: PartyPlatform,
    val leadership: PartyLeadership,
    val membership: Long,
    val registeredVoters: Long,
    val officesHeld: Map<String, Int>, // level -> count
    val ballotAccess: Map<String, Boolean>, // state -> has access
    val nationalConvention: Convention?,
    val primaryCalendar: List<Primary>,
    val funding: PartyFunding,
    val internalDivisions: List<PartyFaction>,
    val coalitionAgreements: List<CoalitionAgreement>
) : Parcelable

@Parcelize
data class PartyPlatform(
    val economic: String,
    val social: String,
    val foreign: String,
    val environmental: String,
    val healthcare: String,
    val education: String,
    val immigration: String,
    val criminalJustice: String,
    val keyPolicies: List<String>
) : Parcelable

@Parcelize
data class PartyLeadership(
    val chair: String,
    val viceChair: String,
    val treasurer: String,
    val secretary: String,
    val nationalCommittee: Int,
    val executiveCommittee: List<String>
) : Parcelable

@Parcelize
data class Convention(
    val id: String,
    val date: Long,
    val location: String,
    val venue: String,
    val delegates: Int,
    val keynoteSpeaker: String?,
    val presidentialNominee: String?,
    val vicePresidentialNominee: String?,
    val platformVotes: Map<String, Boolean>,
    val balloonDrop: Boolean
) : Parcelable

@Parcelize
data class PartyFunding(
    val annualBudget: Long,
    val fundraisingYTD: Long,
    val federalFunding: Long,
    val stateFunding: Long,
    val smallDonors: Long,
    val majorDonors: Long,
    val debts: Long
) : Parcelable

@Parcelize
data class PartyFaction(
    val id: String,
    val name: String,
    val leader: String,
    val members: Int,
    val influence: Double,
    val issues: List<String>
) : Parcelable

// Coalition Government

@Parcelize
data class CoalitionAgreement(
    val id: String,
    val parties: List<String>,
    val signedDate: Long,
    val primeMinisterParty: String,
    val cabinetPositions: Map<String, String>, // position -> party
    val policyAgreements: List<String>,
    val confidenceSupply: Boolean,
    val duration: Int, // months
    val terminationClause: String,
    val status: CoalitionStatus
) : Parcelable

enum class CoalitionStatus {
    NEGOTIATING,
    ACTIVE,
    MINORITY_GOVERNMENT,
    CONFIDENCE_SUPPLY,
    COLLAPSED,
    DISSOLVED
}

// Electoral Commission

@Parcelize
data class ElectoralCommission(
    val id: String,
    val name: String,
    val type: CommissionType,
    val members: Int,
    val chair: String,
    val budget: Long,
    val responsibilities: List<String>,
    val investigations: List<ElectoralInvestigation>
) : Parcelable

enum class CommissionType {
    INDEPENDENT,
    BIPARTISAN,
    GOVERNMENT,
    JUDICIAL
}

@Parcelize
data class ElectoralInvestigation(
    val id: String,
    val subject: String,
    val allegations: List<String>,
    val openedDate: Long,
    val status: InvestigationStatus,
    val findings: String?,
    val sanctions: List<String>
) : Parcelable

// News Categories for expanded system

enum class NewsCategory {
    POLITICS,
    ECONOMY,
    INTERNATIONAL,
    MILITARY,
    SPORTS,
    CULTURE,
    SCANDAL,
    DISASTER,
    TECHNOLOGY,
    HEALTH,
    ENVIRONMENT,
    SOCIAL,
    CRIME,
    EDUCATION
}
