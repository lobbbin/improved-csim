package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Education and Research System
 * Comprehensive education management from primary schools to research institutions.
 */

@Parcelize
data class EducationSystem(
    val countryId: String,
    val literacyRate: Double,
    val averageYearsOfSchooling: Double,
    val educationBudget: Long,
    val budgetPercentOfGdp: Double,
    val primaryEducation: PrimaryEducation,
    val secondaryEducation: SecondaryEducation,
    val higherEducation: HigherEducation,
    val vocationalEducation: VocationalEducation,
    val researchInstitutions: List<ResearchInstitution>,
    val educationQuality: Double,
    val educationEquity: Double,
    val internationalRankings: EducationRankings,
    val currentIssues: List<EducationIssue>,
    val policies: EducationPolicies,
    val statistics: EducationStatistics,
    val lastUpdated: Long
) : Parcelable {
    
    val overallScore: Double
        get() = (literacyRate * 30 + educationQuality * 40 + educationEquity * 30)
    
    val enrollmentRate: Double
        get() = (primaryEducation.enrollmentRate + secondaryEducation.enrollmentRate + 
                higherEducation.enrollmentRate) / 3
    
    val teacherStudentRatio: Double
        get() = (primaryEducation.teacherStudentRatio + secondaryEducation.teacherStudentRatio) / 2
}

@Parcelize
data class PrimaryEducation(
    val totalSchools: Int,
    val totalStudents: Long,
    val totalTeachers: Int,
    val enrollmentRate: Double,
    val attendanceRate: Double,
    val completionRate: Double,
    val teacherStudentRatio: Double,
    val teacherQualificationRate: Double,
    val infrastructure: SchoolInfrastructure,
    val curriculum: Curriculum,
    val standardTests: StandardizedTests,
    val genderParity: Double,
    val ruralAccess: Double,
    val dropoutRate: Double,
    val repetitionRate: Double
) : Parcelable

@Parcelize
data class SecondaryEducation(
    val totalSchools: Int,
    val totalStudents: Long,
    val totalTeachers: Int,
    val enrollmentRate: Double,
    val attendanceRate: Double,
    val completionRate: Double,
    val teacherStudentRatio: Double,
    val teacherQualificationRate: Double,
    val infrastructure: SchoolInfrastructure,
    val curriculum: Curriculum,
    val standardTests: StandardizedTests,
    val genderParity: Double,
    val ruralAccess: Double,
    val dropoutRate: Double,
    val universityTrackPercentage: Double,
    val vocationalTrackPercentage: Double
) : Parcelable

@Parcelize
data class HigherEducation(
    val totalUniversities: Int,
    val publicUniversities: Int,
    val privateUniversities: Int,
    val totalStudents: Long,
    val totalFaculty: Int,
    val enrollmentRate: Double,
    val graduationRate: Double,
    val studentFacultyRatio: Double,
    val researchOutput: Long, // papers per year
    val internationalStudents: Long,
    val studentExchange: StudentExchange,
    val topUniversities: List<University>,
    val fieldsOfStudy: Map<String, Int>, // field -> student count
    val genderParity: Double,
    val tuitionCosts: TuitionCosts,
    val studentDebt: Long,
    val employmentRate: Double, // after graduation
    val averageStartingSalary: Long
) : Parcelable

@Parcelize
data class University(
    val id: String,
    val name: String,
    val location: String,
    val type: UniversityType,
    val founded: Int,
    val students: Long,
    val faculty: Int,
    val endowment: Long,
    val ranking: Int,
    val fields: List<String>,
    val researchExpenditure: Long,
    val notableAlumni: List<String>,
    val internationalRanking: Int,
    val tuition: Long,
    val acceptanceRate: Double,
    val reputation: Double,
    val chancellor: String?, // NPC ID
    val departments: List<Department>,
    val researchCenters: List<ResearchCenter>,
    val sportsProgram: UniversitySports?,
    val affiliations: List<String>
) : Parcelable

enum class UniversityType {
    PUBLIC_RESEARCH,
    PRIVATE_RESEARCH,
    LIBERAL_ARTS,
    TECHNICAL_INSTITUTE,
    COMMUNITY_COLLEGE,
    MILITARY_ACADEMY,
    RELIGIOUS,
    MEDICAL_CENTER,
    AGRICULTURAL,
    TEACHERS_COLLEGE,
    OPEN_UNIVERSITY,
    POLYTECHNIC
}

@Parcelize
data class Department(
    val id: String,
    val name: String,
    val faculty: Int,
    val students: Int,
    val researchOutput: Int,
    val ranking: Int,
    val head: String? // NPC ID
) : Parcelable

@Parcelize
data class ResearchCenter(
    val id: String,
    val name: String,
    val focus: String,
    val budget: Long,
    val researchers: Int,
    val director: String?
) : Parcelable

@Parcelize
data class UniversitySports(
    val division: String,
    val teams: List<String>,
    val championships: Int,
    val budget: Long,
    val revenue: Long
) : Parcelable

@Parcelize
data class VocationalEducation(
    val totalInstitutions: Int,
    val totalStudents: Long,
    val totalInstructors: Int,
    val enrollmentRate: Double,
    val completionRate: Double,
    val employmentRate: Double,
    val trades: List<VocationalTrade>,
    val apprenticeshipPrograms: List<Apprenticeship>,
    val industryPartnerships: List<IndustryPartnership>,
    val certificationPassRate: Double,
    val averageSalary: Long
) : Parcelable

@Parcelize
data class VocationalTrade(
    val id: String,
    val name: String,
    val students: Int,
    val demand: Double,
    val averageSalary: Long,
    val jobPlacementRate: Double
) : Parcelable

@Parcelize
data class Apprenticeship(
    val id: String,
    val trade: String,
    val duration: Int, // months
    val participants: Int,
    val completionRate: Double,
    val companies: List<String>
) : Parcelable

@Parcelize
data class IndustryPartnership(
    val id: String,
    val company: String,
    val program: String,
    val students: Int,
    val jobsCreated: Int
) : Parcelable

@Parcelize
data class SchoolInfrastructure(
    val totalClassrooms: Int,
    val adequateClassrooms: Double, // percentage
    val electricityAccess: Double,
    val internetAccess: Double,
    val computerAccess: Double,
    val libraryAccess: Double,
    val laboratoryAccess: Double,
    val sportsFacilities: Double,
    val cleanWater: Double,
    val sanitationFacilities: Double,
    val disabilityAccess: Double,
    val averageBuildingAge: Int,
    val maintenanceBudget: Long,
    val constructionProjects: List<ConstructionProject>
) : Parcelable

@Parcelize
data class ConstructionProject(
    val id: String,
    val type: String,
    val location: String,
    val budget: Long,
    val progress: Double,
    val startDate: Long,
    val expectedCompletion: Long
) : Parcelable

@Parcelize
data class Curriculum(
    val name: String,
    val lastRevision: Long,
    val subjects: List<Subject>,
    val ideologyInfluence: Double, // 0.0-1.0 ideological content
    val criticalThinking: Double,
    val stemEmphasis: Double,
    val artsEmphasis: Double,
    val physicalEducation: Double,
    val languageInstruction: List<LanguageInstruction>,
    val civicEducation: Double,
    val historyPresentation: HistoryPresentation,
    val sexEducation: SexEducationLevel,
    val religiousEducation: ReligiousEducationLevel
) : Parcelable

@Parcelize
data class Subject(
    val id: String,
    val name: String,
    val hoursPerWeek: Double,
    val compulsory: Boolean,
    val levels: List<String>
) : Parcelable

@Parcelize
data class LanguageInstruction(
    val language: String,
    val startGrade: Int,
    val hoursPerWeek: Double,
    val proficiencyTarget: String
) : Parcelable

enum class HistoryPresentation {
    OBJECTIVE,
    NATIONALISTIC,
    CRITICAL,
    REVISED,
    CENSORED,
    PROPAGANDISTIC,
    BALANCED
}

enum class SexEducationLevel {
    NONE,
    ABSTINENCE_ONLY,
    BASIC,
    COMPREHENSIVE,
    MEDICAL_FOCUS,
    VALUES_BASED
}

enum class ReligiousEducationLevel {
    NONE,
    OPTIONAL,
    COMPULSORY_PRIVATE,
    COMPULSORY_PUBLIC,
    MULTI_FAITH,
    DOMINANT_FAITH_ONLY
}

@Parcelize
data class StandardizedTests(
    val name: String,
    val subjects: List<String>,
    val averageScore: Double,
    val internationalComparison: Double,
    val passingRate: Double,
    val administeredBy: String,
    val frequency: Int, // times per year
    val stressLevel: Double,
    val cheatingRate: Double
) : Parcelable

@Parcelize
data class StudentExchange(
    val incomingStudents: Long,
    val outgoingStudents: Long,
    val partnerCountries: List<String>,
    val programs: List<ExchangeProgram>,
    val scholarships: Int,
    val visaRestrictions: List<String>
) : Parcelable

@Parcelize
data class ExchangeProgram(
    val id: String,
    val name: String,
    val partnerCountries: List<String>,
    val duration: Int, // months
    val participants: Int,
    val funding: Long
) : Parcelable

@Parcelize
data class TuitionCosts(
    val publicPrimary: Long,
    val publicSecondary: Long,
    val publicUniversity: Long,
    val privatePrimary: Long,
    val privateSecondary: Long,
    val privateUniversity: Long,
    val averageAnnualIncrease: Double,
    val scholarshipAvailability: Double,
    val financialAidBudget: Long
) : Parcelable

// Research System

@Parcelize
data class ResearchInstitution(
    val id: String,
    val name: String,
    val type: InstitutionType,
    val location: String,
    val budget: Long,
    val researchers: Int,
    val director: String?, // NPC ID
    val focusAreas: List<String>,
    val publications: Int, // per year
    val patents: Int, // per year
    val collaborations: List<String>, // other institution IDs
    val industryPartnerships: List<String>,
    val internationalRanking: Int,
    val nobelLaureates: Int,
    val researchOutput: List<ResearchOutput>,
    val facilities: List<ResearchFacility>
) : Parcelable

enum class InstitutionType {
    UNIVERSITY,
    GOVERNMENT_LAB,
    PRIVATE_RESEARCH,
    MEDICAL_CENTER,
    THINK_TANK,
    DEFENSE_LAB,
    AGRICULTURAL_RESEARCH,
    ENVIRONMENTAL_RESEARCH,
    SPACE_AGENCY,
    ENERGY_LAB,
    COMPUTING_CENTER
}

@Parcelize
data class ResearchOutput(
    val id: String,
    val title: String,
    val authors: List<String>,
    val field: String,
    val publicationDate: Long,
    val journal: String?,
    val citations: Int,
    val impact: Double,
    val patents: Int,
    val commercialized: Boolean
) : Parcelable

@Parcelize
data class ResearchFacility(
    val id: String,
    val name: String,
    val type: String,
    val cost: Long,
    val capacity: Int,
    val utilization: Double
) : Parcelable

// Technology Tree

@Parcelize
data class TechnologyTree(
    val categories: List<TechnologyCategory>,
    val totalTechs: Int,
    val researchedTechs: Int,
    val researchProgress: Map<String, Double>, // techId -> progress
    val researchQueue: List<ResearchQueueItem>,
    val technologyLevel: Double,
    val researchBudget: Long,
    val researchEfficiency: Double
) : Parcelable

@Parcelize
data class TechnologyCategory(
    val id: String,
    val name: String,
    val description: String,
    val technologies: List<Technology>,
    val progress: Double
) : Parcelable

@Parcelize
data class Technology(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val prerequisites: List<String>, // Technology IDs
    val researchCost: Long,
    val researchTime: Long, // milliseconds
    val researched: Boolean,
    val researchDate: Long?,
    val effects: List<TechnologyEffect>,
    val unlocks: List<String>,
    val tier: Int, // 1-10
    val era: TechnologyEra,
    val requiredResources: List<String>,
    val requiredInstitutions: List<String>,
    val militaryApplication: Boolean,
    val civilianApplication: Boolean,
    val dualUse: Boolean
) : Parcelable

enum class TechnologyEra {
    ANCIENT,
    CLASSICAL,
    MEDIEVAL,
    RENAISSANCE,
    INDUSTRIAL,
    MODERN,
    ATOMIC,
    INFORMATION,
    BIOTECH,
    NANOTECH,
    QUANTUM,
    POST_SINGULARITY
}

@Parcelize
data class TechnologyEffect(
    val id: String,
    val type: TechnologyEffectType,
    val target: String,
    val value: Double,
    val description: String
) : Parcelable

enum class TechnologyEffectType {
    PRODUCTIVITY_BOOST,
    MILITARY_STRENGTH,
    HEALTH_IMPROVEMENT,
    EDUCATION_BOOST,
    ECONOMIC_GROWTH,
    DIPLOMATIC_BONUS,
    ENVIRONMENTAL_EFFECT,
    POPULATION_GROWTH,
    RESOURCE_EFFICIENCY,
    RESEARCH_SPEED,
    UNLOCK_BUILDING,
    UNLOCK_UNIT,
    UNLOCK_POLICY,
    UNLOCK_RESOURCE
}

@Parcelize
data class ResearchQueueItem(
    val id: String,
    val technologyId: String,
    val progress: Double,
    val priority: Int,
    val assignedScientists: Int,
    val assignedFunding: Long,
    val startDate: Long,
    val estimatedCompletion: Long
) : Parcelable

// Scientific Breakthroughs

@Parcelize
data class ScientificBreakthrough(
    val id: String,
    val name: String,
    val field: String,
    val description: String,
    val discoverers: List<String>, // NPC IDs
    val institution: String?,
    val date: Long,
    val significance: BreakthroughSignificance,
    val applications: List<String>,
    val patents: List<String>,
    val nobelPrize: Boolean,
    val nobelYear: Int?,
    val impact: BreakthroughImpact
) : Parcelable

enum class BreakthroughSignificance {
    MINOR,
    MODERATE,
    MAJOR,
    REVOLUTIONARY,
    PARADIGM_SHIFTING,
    WORLD_CHANGING
}

@Parcelize
data class BreakthroughImpact(
    val economic: Double,
    val social: Double,
    val environmental: Double,
    val military: Double,
    val health: Double,
    val global: Double
) : Parcelable

// Brain Drain

@Parcelize
data class BrainDrainStatistics(
    val scientistsEmigrated: Int,
    val scientistsImmigrated: Int,
    val netBrainDrain: Int,
    val topDestinations: List<String>,
    val topOrigins: List<String>,
    val reasons: List<BrainDrainReason>,
    val fieldsLost: Map<String, Int>,
    val fieldsGained: Map<String, Int>,
    val economicImpact: Long,
    val returnPrograms: List<ReturnProgram>,
    val retentionRate: Double
) : Parcelable

@Parcelize
data class BrainDrainReason(
    val reason: String,
    val percentage: Double
) : Parcelable

@Parcelize
data class ReturnProgram(
    val id: String,
    val name: String,
    val incentives: List<String>,
    val participants: Int,
    val successRate: Double,
    val budget: Long
) : Parcelable

// Student Movements

@Parcelize
data class StudentMovement(
    val id: String,
    val name: String,
    val universityId: String?,
    val leadership: List<String>, // NPC IDs
    val members: Int,
    val ideology: Ideology,
    val demands: List<String>,
    val tactics: List<StudentTactic>,
    val publicSupport: Double,
    val governmentResponse: GovernmentResponse,
    val startDate: Long,
    val endDate: Long?,
    val outcomes: List<String>,
    val status: MovementStatus
) : Parcelable

enum class StudentTactic {
    PEACEFUL_PROTEST,
    STRIKE,
    OCCUPATION,
    MARCH,
    PETITION,
    BOYCOTT,
    CIVIL_DISOBEDIENCE,
    VIOLENT_PROTEST,
    SIT_IN,
    TEACH_IN,
    SOCIAL_MEDIA_CAMPAIGN
}

enum class GovernmentResponse {
    IGNORE,
    NEGOTIATE,
    CONCEDE,
    CRACKDOWN,
    ARREST,
    EXPULSION,
    PROPAGANDA,
    INFILTRATION,
    VIOLENCE
}

enum class MovementStatus {
    GROWING,
    ACTIVE,
    DECLINING,
    SUPPRESSED,
    SUCCESSFUL,
    FAILED,
    DORMANT,
    RADICALIZED
}

// Rankings and Statistics

@Parcelize
data class EducationRankings(
    val overall: Int,
    val primary: Int,
    val secondary: Int,
    val higherEducation: Int,
    val research: Int,
    val stem: Int,
    val humanities: Int,
    val rankingSystem: String,
    val lastUpdated: Long
) : Parcelable

@Parcelize
data class EducationStatistics(
    val totalStudents: Long,
    val totalTeachers: Long,
    val totalSchools: Int,
    val averageClassSize: Double,
    val literacyRate: Double,
    val numeracyRate: Double,
    val stemGraduates: Int,
    val humanitiesGraduates: Int,
    val researchPapers: Int,
    val patentsFiled: Int,
    val internationalStudents: Long,
    val studyAbroadStudents: Long
) : Parcelable

// Policies

@Parcelize
data class EducationPolicies(
    val compulsoryEducationYears: Int,
    val schoolStartingAge: Int,
    val freeEducation: Boolean,
    val schoolChoice: Boolean,
    val vouchers: Boolean,
    val standardizedTesting: TestingPolicy,
    val teacherTenure: Boolean,
    val teacherPayScale: String,
    val curriculumControl: CurriculumControl,
    val universityAutonomy: Double,
    val foreignLanguagePolicy: String,
    val specialNeedsPolicy: SpecialNeedsPolicy,
    val disciplinePolicy: DisciplinePolicy,
    val homeworkPolicy: HomeworkPolicy,
    val schoolUniforms: Boolean,
    val religiousSchoolsPolicy: ReligiousSchoolsPolicy,
    val privateEducationPolicy: PrivateEducationPolicy,
    val onlineEducationPolicy: OnlineEducationPolicy,
    val researchFundingPolicy: ResearchFundingPolicy
) : Parcelable

enum class TestingPolicy {
    NONE,
    MINIMAL,
    MODERATE,
    HIGH_STAKES,
    CONTINUOUS_ASSESSMENT
}

enum class CurriculumControl {
    NATIONAL,
    STATE_PROVINCIAL,
    LOCAL,
    SCHOOL_BASED,
    MIXED
}

enum class SpecialNeedsPolicy {
    INCLUSION,
    MAINSTREAMING,
    SPECIAL_SCHOOLS,
    MIXED,
    MINIMAL
}

enum class DisciplinePolicy {
    ZERO_TOLERANCE,
    RESTORATIVE,
    TRADITIONAL,
    PROGRESSIVE,
    PERMISSIVE
}

enum class HomeworkPolicy {
    NONE,
    MINIMAL,
    MODERATE,
    HEAVY,
    GRADE_BASED
}

enum class ReligiousSchoolsPolicy {
    ALLOWED_FREELY,
    ALLOWED_REGULATED,
    SUBSIDIZED,
    PROHIBITED,
    REQUIRED
}

enum class PrivateEducationPolicy {
    BANNED,
    ALLOWED_UNREGULATED,
    ALLOWED_REGULATED,
    SUBSIDIZED,
    ENCOURAGED
}

enum class OnlineEducationPolicy {
    BANNED,
    LIMITED,
    REGULATED,
    ENCOURAGED,
    PRIMARY_METHOD
}

enum class ResearchFundingPolicy {
    GOVERNMENT_DIRECTED,
    PEER_REVIEWED,
    MIXED,
    PRIVATE_LED,
    MILITARY_FOCUSED
}

// Education Issues

@Parcelize
data class EducationIssue(
    val id: String,
    val name: String,
    val severity: Double,
    val affected: Long,
    val description: String,
    val solutions: List<String>,
    val politicalSupport: Double,
    val budgetRequired: Long
) : Parcelable

// Academic Freedom

@Parcelize
data class AcademicFreedom(
    val index: Double,
    val censorshipLevel: Double,
    val selfCensorship: Double,
    val politicalInterference: Double,
    val restrictedTopics: List<String>,
    val persecutedAcademics: Int,
    val bannedBooks: Int,
    val internetRestrictions: List<String>,
    val visaRestrictions: Boolean,
    val internationalCollaborationRestrictions: List<String>
) : Parcelable
