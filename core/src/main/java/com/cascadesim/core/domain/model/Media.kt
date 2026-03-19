package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Media and Propaganda System
 * Comprehensive media management including state media, private media, propaganda, and censorship.
 */

@Parcelize
data class MediaSystem(
    val countryId: String,
    val pressFreedomIndex: Double, // 0-100
    val pluralismScore: Double,
    val independenceScore: Double,
    val selfCensorship: Double,
    val totalOutletCount: Int,
    val stateOwned: List<MediaOutlet>,
    val privateOwned: List<MediaOutlet>,
    val foreignMedia: List<MediaOutlet>,
    val onlineMedia: List<DigitalMedia>,
    val journalists: List<Journalist>,
    val regulations: MediaRegulations,
    val censorship: CensorshipSystem,
    val propagandaApparatus: PropagandaApparatus,
    val informationWarfare: InformationWarfare,
    val socialMediaControl: SocialMediaControl,
    val mediaScandals: List<MediaScandal>,
    val publicTrust: Double,
    val lastUpdated: Long
) : Parcelable {
    
    val mediaLandscapeHealth: Double
        get() = (pressFreedomIndex * 0.3 + pluralismScore * 0.25 + 
                independenceScore * 0.25 + (100 - selfCensorship) * 0.2)
    
    val stateControlPercentage: Double
        get() = stateOwned.size.toDouble() / totalOutletCount * 100
}

@Parcelize
data class MediaOutlet(
    val id: String,
    val name: String,
    val type: MediaType,
    val ownership: MediaOwnership,
    val owner: String?, // NPC or corporation ID
    val established: Int,
    val headquarters: String,
    val reach: Long, // audience size
    val marketShare: Double,
    val editorialStance: EditorialStance,
    val credibilityScore: Double,
    val biasScore: Double, // -1 (far left) to +1 (far right)
    val employees: Int,
    val journalists: Int,
    val revenue: Long,
    val governmentFunding: Long,
    val editorialIndependence: Double,
    val chiefEditor: String?, // NPC ID
    val publications: List<Publication>,
    val broadcasts: List<Broadcast>,
    val digitalPresence: DigitalPresence?,
    val scandals: List<String>, // scandal IDs
    val awards: List<MediaAward>,
    val restrictions: List<MediaRestriction>,
    val politicalAlignment: String?
) : Parcelable

enum class MediaType {
    NEWSPAPER,
    MAGAZINE,
    NEWS_AGENCY,
    TV_STATION,
    RADIO_STATION,
    NEWS_WEBSITE,
    BLOG,
    PODCAST,
    STREAMING_SERVICE,
    WIRE_SERVICE,
    SATELLITE_CHANNEL,
    CABLE_CHANNEL
}

enum class MediaOwnership {
    STATE_OWNED,
    GOVERNMENT_CONTROLLED,
    PRIVATE_INDEPENDENT,
    PRIVATE_PARTISAN,
    CORPORATE,
    CONGLOMERATE,
    FAMILY_OWNED,
    COOPERATIVE,
    FOREIGN_OWNED,
    PARTY_OWNED,
    RELIGIOUS,
    MILITARY,
    FOUNDATION
}

enum class EditorialStance {
    GOVERNMENT_MOUTHPIECE,
    PRO_GOVERNMENT,
    NEUTRAL,
    CRITICAL,
    OPPOSITION,
    INDEPENDENT,
    TABLOID,
    SENSATIONALIST,
    INVESTIGATIVE,
    OPINION_FOCUSED,
    FACT_BASED
}

@Parcelize
data class Publication(
    val id: String,
    val title: String,
    val date: Long,
    val type: PublicationType,
    val author: String?,
    val circulation: Long,
    val digitalViews: Long
) : Parcelable

enum class PublicationType {
    NEWSPAPER_EDITION,
    MAGAZINE_ISSUE,
    ONLINE_ARTICLE,
    OP_ED,
    EDITORIAL,
    INVESTIGATIVE_REPORT,
    INTERVIEW,
    OPINION_PIECE,
    LETTER_TO_EDITOR,
    SUNDAY_SUPPLEMENT
}

@Parcelize
data class Broadcast(
    val id: String,
    val programName: String,
    val type: BroadcastType,
    val timeSlot: String,
    val duration: Int, // minutes
    val viewers: Long,
    val host: String?, // NPC ID
    val guests: List<String>
) : Parcelable

enum class BroadcastType {
    NEWS_BULLETIN,
    CURRENT_AFFAIRS,
    TALK_SHOW,
    DOCUMENTARY,
    DEBATE,
    SUNDAY_POLITICS,
    LATE_SHOW,
    MORNING_SHOW,
    PRIME_TIME_NEWS,
    SPECIAL_REPORT,
    BREAKING_NEWS,
    INVESTIGATIVE
}

@Parcelize
data class DigitalPresence(
    val website: String,
    val monthlyVisitors: Long,
    val socialMediaFollowers: Map<String, Long>,
    val appDownloads: Long,
    val paywall: Boolean,
    val subscriptionPrice: Double,
    val digitalRevenue: Long
) : Parcelable

@Parcelize
data class MediaAward(
    val id: String,
    val name: String,
    val category: String,
    val year: Int,
    val recipient: String
) : Parcelable

@Parcelize
data class MediaRestriction(
    val id: String,
    val type: RestrictionType,
    val description: String,
    val sinceDate: Long,
    val enforcedBy: String
) : Parcelable

enum class RestrictionType {
    BAN,
    SUSPENSION,
    FINE,
    LICENSE_RESTRICTION,
    CONTENT_RESTRICTION,
    GEOGRAPHIC_BAN,
    TEMPORARY_BAN,
    ADVERTISING_BAN,
    DISTRIBUTION_RESTRICTION
}

@Parcelize
data class Journalist(
    val id: String, // NPC ID
    val name: String,
    val outlet: String, // MediaOutlet ID
    val position: String,
    val specialization: String,
    val awards: Int,
    val credentials: List<String>,
    val pressPassStatus: PressPassStatus,
    val imprisoned: Boolean,
    val inExile: Boolean,
    val killed: Boolean,
    val threats: List<JournalistThreat>,
    val stories: Int,
    val controversies: List<String>
) : Parcelable

enum class PressPassStatus {
    ACTIVE,
    SUSPENDED,
    REVOKED,
    RESTRICTED,
    PENDING
}

@Parcelize
data class JournalistThreat(
    val id: String,
    val type: ThreatType,
    val source: String?,
    val date: Long,
    val severity: Double
) : Parcelable

enum class ThreatType {
    DEATH_THREAT,
    LEGAL_HARASSMENT,
    PHYSICAL_ATTACK,
    ONLINE_HARASSMENT,
    GOVERNMENT_PRESSURE,
    CORPORATE_PRESSURE,
    DETENTION,
    TORTURE,
    DISAPPEARANCE
}

@Parcelize
data class DigitalMedia(
    val id: String,
    val name: String,
    val type: DigitalMediaType,
    val url: String,
    val monthlyTraffic: Long,
    val registeredUsers: Long,
    val contentModeration: ContentModerationPolicy,
    val politicalContent: Boolean,
    val foreignOwned: Boolean,
    val blocked: Boolean,
    val blockedSince: Long?,
    val governmentRequests: Int, // for data/takedowns
    val compliance: Double
) : Parcelable

enum class DigitalMediaType {
    NEWS_WEBSITE,
    SOCIAL_MEDIA,
    VIDEO_PLATFORM,
    PODCAST_PLATFORM,
    BLOG_PLATFORM,
    FORUM,
    MESSENGER_APP,
    AGGREGATOR,
    SATIRE_SITE,
    CITIZEN_JOURNALISM
}

@Parcelize
data class ContentModerationPolicy(
    val id: String,
    val name: String,
    val rules: List<String>,
    val automatedFiltering: Boolean,
    val humanModerators: Int,
    val appealProcess: Boolean,
    val transparency: Double
) : Parcelable

// Censorship System

@Parcelize
data class CensorshipSystem(
    val overallLevel: CensorshipLevel,
    val mechanisms: List<CensorshipMechanism>,
    val bannedTopics: List<String>,
    val sensitiveWords: List<String>,
    val prePublicationReview: Boolean,
    val postPublicationControl: Boolean,
    val internetFiltering: InternetFiltering,
    val bookCensorship: BookCensorship,
    val filmCensorship: FilmCensorship,
    val musicCensorship: MusicCensorship,
    val artCensorship: ArtCensorship,
    val academicCensorship: AcademicCensorship,
    val penalties: List<CensorshipPenalty>,
    val enforcementAgency: String?,
    val annualBudget: Long
) : Parcelable

enum class CensorshipLevel {
    NONE,
    MINIMAL,
    MODERATE,
    EXTENSIVE,
    TOTAL,
    SELECTIVE
}

@Parcelize
data class CensorshipMechanism(
    val id: String,
    val name: String,
    val type: MechanismType,
    val effectiveness: Double,
    val scope: String,
    val enforcement: Double
) : Parcelable

enum class MechanismType {
    PRE_PUBLICATION_REVIEW,
    POST_PUBLICATION_BAN,
    LICENSING,
    SELF_CENSORSHIP_PRESSURE,
    ECONOMIC_PRESSURE,
    LEGAL_HARASSMENT,
    PHYSICAL_INTIMIDATION,
    TECHNICAL_BLOCKING,
    DNS_FILTERING,
    KEYWORD_FILTERING,
    DEEP_PACKET_INSPECTION,
    SPEED_THROTTLING,
    COMPLETE_SHUTDOWN
}

@Parcelize
data class InternetFiltering(
    val enabled: Boolean,
    val method: FilteringMethod,
    val blockedSites: Int,
    val blockedCategories: List<String>,
    val vpnBlocking: Boolean,
    val torBlocking: Boolean,
    val socialMediaBlocks: List<String>,
    val circumventionDifficulty: Double
) : Parcelable

enum class FilteringMethod {
    NONE,
    DNS_MANIPULATION,
    IP_BLOCKING,
    URL_FILTERING,
    DEEP_PACKET_INSPECTION,
    KEYWORD_FILTERING,
    MIXED,
    NATIONWIDE_FIREWALL
}

@Parcelize
data class BookCensorship(
    val bannedBooks: List<BannedBook>,
    val libraryRestrictions: List<String>,
    val publishingControls: Boolean,
    val importRestrictions: Boolean,
    val bookstoreInspections: Boolean
) : Parcelable

@Parcelize
data class BannedBook(
    val id: String,
    val title: String,
    val author: String,
    val reason: String,
    val banDate: Long,
    val copiesDestroyed: Int,
    val blackMarketAvailability: Double
) : Parcelable

@Parcelize
data class FilmCensorship(
    val ratingSystem: Boolean,
    val bannedFilms: List<String>,
    val requiredCuts: List<String>,
    val importRestrictions: Boolean,
    val cinemaInspections: Boolean,
    val streamingControls: Boolean
) : Parcelable

@Parcelize
data class MusicCensorship(
    val bannedArtists: List<String>,
    val bannedSongs: List<String>,
    val concertRestrictions: Boolean,
    val radioPlaylists: Boolean, // government controlled
    val lyricsReview: Boolean
) : Parcelable

@Parcelize
data class ArtCensorship(
    val bannedArtworks: List<String>,
    val galleryRestrictions: Boolean,
    val museumOversight: Boolean,
    val artistPersecution: List<String>,
    val publicArtApproval: Boolean
) : Parcelable

@Parcelize
data class AcademicCensorship(
    val restrictedTopics: List<String>,
    val bannedTextbooks: List<String>,
    val professorOversight: Boolean,
    val researchApproval: Boolean,
    val conferenceRestrictions: Boolean,
    val visitingScholarVetting: Boolean
) : Parcelable

@Parcelize
data class CensorshipPenalty(
    val id: String,
    val violation: String,
    val penalty: String,
    val severity: Int
) : Parcelable

// Propaganda System

@Parcelize
data class PropagandaApparatus(
    val id: String,
    val budget: Long,
    val personnel: Int,
    val director: String?, // NPC ID
    val campaigns: List<PropagandaCampaign>,
    val methods: List<PropagandaMethod>,
    val effectiveness: Double,
    val reach: Long,
    val internationalOperations: Boolean,
    val digitalCapabilities: Double,
    val historicalCampaigns: List<String>
) : Parcelable

@Parcelize
data class PropagandaCampaign(
    val id: String,
    val name: String,
    val objective: String,
    val startDate: Long,
    val endDate: Long?,
    val targetAudience: String,
    val methods: List<String>,
    val outlets: List<String>,
    val budget: Long,
    val reach: Long,
    val effectiveness: Double,
    val international: Boolean,
    val status: CampaignStatus
) : Parcelable

enum class CampaignStatus {
    PLANNING,
    ACTIVE,
    PAUSED,
    COMPLETED,
    FAILED,
    EXPOSED
}

@Parcelize
data class PropagandaMethod(
    val id: String,
    val name: String,
    val description: String,
    val effectiveness: Double,
    val cost: Long,
    val frequency: Int
) : Parcelable

// Information Warfare

@Parcelize
data class InformationWarfare(
    val capability: Double,
    val operations: List<InfoOperation>,
    val targets: List<String>, // country IDs
    val capabilities: List<InfoWarCapability>,
    val defenses: List<InfoWarDefense>,
    val budget: Long,
    val personnel: Int
) : Parcelable

@Parcelize
data class InfoOperation(
    val id: String,
    val codename: String,
    val type: InfoOperationType,
    val targetCountry: String,
    val startDate: Long,
    val endDate: Long?,
    val objective: String,
    val methods: List<String>,
    val assets: Int,
    val budget: Long,
    val success: Double,
    val status: OperationStatus
) : Parcelable

enum class InfoOperationType {
    DISINFORMATION,
    PROPAGANDA,
    SOCIAL_MEDIA_MANIPULATION,
    ELECTION_INTERFERENCE,
    SOWING_DISCORD,
    ECONOMIC_MANIPULATION,
    REPUTATION_ATTACK,
    COUNTER_NARRATIVE,
    FAKE_NEWS,
    DEEPFAKE,
    HACK_AND_LEAK
}

@Parcelize
data class InfoWarCapability(
    val id: String,
    val name: String,
    val type: String,
    val level: Double,
    val personnel: Int,
    val lastUsed: Long?
) : Parcelable

@Parcelize
data class InfoWarDefense(
    val id: String,
    val name: String,
    val type: String,
    val effectiveness: Double,
    val coverage: Double
) : Parcelable

// Social Media Control

@Parcelize
data class SocialMediaControl(
    val platformsBlocked: List<String>,
    val platformsRestricted: List<String>,
    val nationalPlatforms: List<String>,
    val realNamePolicies: Boolean,
    val contentRemovalRequests: Int,
    val takedownCompliance: Double,
    val influenceOperations: List<InfluenceOperation>,
    val trollFarms: List<TrollFarm>,
    val botNetwork: BotNetwork?,
    val surveillance: Boolean
) : Parcelable

@Parcelize
data class InfluenceOperation(
    val id: String,
    val platform: String,
    val objective: String,
    val accounts: Int,
    val posts: Long,
    val engagement: Long,
    val active: Boolean
) : Parcelable

@Parcelize
data class TrollFarm(
    val id: String,
    val name: String,
    val location: String,
    val personnel: Int,
    val accounts: Int,
    val dailyPosts: Int,
    val targets: List<String>,
    val budget: Long,
    val active: Boolean
) : Parcelable

@Parcelize
data class BotNetwork(
    val id: String,
    val totalBots: Int,
    val platforms: List<String>,
    val capabilities: List<String>,
    val dailyActivity: Long,
    val effectiveness: Double
) : Parcelable

// Media Regulations

@Parcelize
data class MediaRegulations(
    val licensingRequired: Boolean,
    val licenseTypes: List<String>,
    val foreignOwnershipLimits: Double,
    val concentrationLimits: Double,
    val defamationLaws: DefamationLaws,
    val secrecyLaws: List<String>,
    val sourceProtection: Boolean,
    val shieldLaw: Boolean,
    val whistleblowerProtection: Boolean,
    val freedomOfInformation: FreedomOfInformationStatus,
    val broadcastRegulations: BroadcastRegulations,
    val onlineRegulations: OnlineRegulations,
    val regulatoryBody: RegulatoryBody?,
    val penalties: List<RegulatoryPenalty>
) : Parcelable

@Parcelize
data class DefamationLaws(
    val criminalDefamation: Boolean,
    val maximumFine: Long,
    val imprisonmentPossible: Boolean,
    val burdenOfProof: String,
    val truthDefense: Boolean,
    val publicFigureStandard: String
) : Parcelable

enum class FreedomOfInformationStatus {
    NONE,
    LIMITED,
    MODERATE,
    STRONG,
    EXCELLENT
}

@Parcelize
data class BroadcastRegulations(
    val politicalAdTimeLimits: Boolean,
    val equalTimeRule: Boolean,
    val fairnessDoctrine: Boolean,
    val indecencyStandards: Boolean,
    val childrensProgramming: Boolean,
    val publicServiceRequirements: Boolean,
    val localContentRequirements: Double
) : Parcelable

@Parcelize
data class OnlineRegulations(
    val intermediaryLiability: Boolean,
    val noticeAndTakedown: Boolean,
    val dataRetention: Boolean,
    val realNameVerification: Boolean,
    val contentRemovalDeadlines: Int, // hours
    val ageVerification: Boolean,
    val hateSpeechLaws: Boolean
) : Parcelable

@Parcelize
data class RegulatoryBody(
    val id: String,
    val name: String,
    val independence: Double,
    val budget: Long,
    val staff: Int,
    val head: String?, // NPC ID
    val politicalAppointments: Boolean,
    val decisionsAppealable: Boolean
) : Parcelable

@Parcelize
data class RegulatoryPenalty(
    val id: String,
    val violation: String,
    val penalty: String,
    val maximumFine: Long
) : Parcelable

// Media Scandals

@Parcelize
data class MediaScandal(
    val id: String,
    val title: String,
    val date: Long,
    val description: String,
    val type: MediaScandalType,
    val outletsInvolved: List<String>,
    val journalistsInvolved: List<String>,
    val governmentResponse: String,
    val publicReaction: Double,
    val resolutions: List<String>,
    val ongoing: Boolean
) : Parcelable

enum class MediaScandalType {
    PLAGIARISM,
    FABRICATION,
    PHONE_HACKING,
    BRIBERY,
    CENSORSHIP_REVEALED,
    POLITICAL_INTERFERENCE,
    ETHICS_VIOLATION,
    CONFLICT_OF_INTEREST,
    DEFAMATION,
    PRIVACY_VIOLATION,
    FABRICATED_EVIDENCE,
    SOURCE_MANIPULATION
}

// News Headlines System

@Parcelize
data class NewsHeadline(
    val id: String,
    val headline: String,
    val subheadline: String?,
    val source: String,
    val date: Long,
    val category: NewsCategory,
    val importance: Int, // 1-10
    val sentiment: Double, // -1 to 1
    val relatedEvent: String?, // event ID
    val relatedCountry: String?,
    val relatedNPC: String?,
    val isFake: Boolean,
    val retracted: Boolean,
    val correction: String?
) : Parcelable

enum class NewsCategory {
    POLITICS,
    ECONOMY,
    INTERNATIONAL,
    MILITARY,
    SPORTS,
    ENTERTAINMENT,
    TECHNOLOGY,
    SCIENCE,
    HEALTH,
    ENVIRONMENT,
    CRIME,
    DISASTER,
    CULTURE,
    OPINION,
    INVESTIGATIVE,
    BREAKING
}

// News Ticker

@Parcelize
data class NewsTicker(
    val headlines: List<NewsHeadline>,
    val scrollSpeed: Int,
    val urgentItems: List<NewsHeadline>,
    val lastUpdate: Long
) : Parcelable
