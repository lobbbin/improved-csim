package com.cascadesim.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar

/**
 * COMPREHENSIVE Time System
 * Handles: Days, Weeks, Months progression, Seasons, Holidays,
 * Election Cycles, Budget Cycles, Diplomatic Summits, Sports Events
 */

@Parcelize
data class TimeSystem(
    val currentGameTime: GameTime,
    val timeSpeed: TimeSpeed,
    val isPaused: Boolean,
    val calendar: GameCalendar,
    val seasons: SeasonSystem,
    val holidays: HolidaySystem,
    val recurringEvents: List<RecurringEvent>,
    val electionCycles: List<ElectionCycle>,
    val budgetCycles: List<BudgetCycle>,
    val diplomaticCalendar: DiplomaticCalendar,
    val sportsCalendar: SportsCalendar,
    val academicCalendar: AcademicCalendar,
    val religiousCalendar: ReligiousCalendar,
    val marketCalendar: MarketCalendar
) : Parcelable

// Core Game Time

@Parcelize
data class GameTime(
    val year: Int,
    val month: Int, // 1-12
    val day: Int, // 1-31
    val hour: Int, // 0-23
    val minute: Int, // 0-59
    val dayOfWeek: DayOfWeek,
    val weekOfYear: Int,
    val quarter: Int, // 1-4
    val era: String,
    val tick: Long // total ticks since game start
) : Parcelable {
    
    val formattedDate: String
        get() = "${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/$year"
    
    val formattedTime: String
        get() = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
    
    val formattedDateTime: String
        get() = "$formattedDate $formattedTime"
    
    val dayOfYear: Int
        get() = when (month) {
            1 -> day
            2 -> 31 + day
            3 -> if (isLeapYear) 60 + day else 59 + day
            4 -> if (isLeapYear) 91 + day else 90 + day
            5 -> if (isLeapYear) 121 + day else 120 + day
            6 -> if (isLeapYear) 152 + day else 151 + day
            7 -> if (isLeapYear) 182 + day else 181 + day
            8 -> if (isLeapYear) 213 + day else 212 + day
            9 -> if (isLeapYear) 244 + day else 243 + day
            10 -> if (isLeapYear) 274 + day else 273 + day
            11 -> if (isLeapYear) 305 + day else 304 + day
            12 -> if (isLeapYear) 335 + day else 334 + day
            else -> day
        }
    
    val isLeapYear: Boolean
        get() = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    
    val season: Season
        get() = when (month) {
            12, 1, 2 -> Season.WINTER
            3, 4, 5 -> Season.SPRING
            6, 7, 8 -> Season.SUMMER
            9, 10, 11 -> Season.AUTUMN
            else -> Season.SPRING
        }
    
    val isWeekend: Boolean
        get() = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
    
    val isWorkday: Boolean
        get() = !isWeekend
    
    val daysInMonth: Int
        get() = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear) 29 else 28
            else -> 30
        }
    
    fun advance(minutes: Int = 1): GameTime {
        var newMinute = minute + minutes
        var newHour = hour
        var newDay = day
        var newMonth = month
        var newYear = year
        var newTick = tick + minutes
        
        // Handle minute overflow
        while (newMinute >= 60) {
            newMinute -= 60
            newHour++
        }
        
        // Handle hour overflow
        while (newHour >= 24) {
            newHour -= 24
            newDay++
        }
        
        // Handle day overflow
        while (newDay > daysInMonth) {
            newDay -= daysInMonth
            newMonth++
        }
        
        // Handle month overflow
        while (newMonth > 12) {
            newMonth -= 12
            newYear++
        }
        
        return copy(
            year = newYear,
            month = newMonth,
            day = newDay,
            hour = newHour,
            minute = newMinute,
            dayOfWeek = DayOfWeek.values()[(dayOfWeek.ordinal + (newDay - day)) % 7],
            weekOfYear = calculateWeekOfYear(newYear, newMonth, newDay),
            quarter = ((newMonth - 1) / 3) + 1,
            tick = newTick
        )
    }
    
    private fun calculateWeekOfYear(y: Int, m: Int, d: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(y, m - 1, d)
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }
}

enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

enum class Season {
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER
}

enum class TimeSpeed {
    PAUSED,
    SLOW,      // 1 minute real = 1 hour game
    NORMAL,    // 1 minute real = 6 hours game
    FAST,      // 1 minute real = 1 day game
    VERY_FAST, // 1 minute real = 1 week game
    EXTREME    // 1 minute real = 1 month game
}

// Game Calendar

@Parcelize
data class GameCalendar(
    val calendarType: CalendarType,
    val yearZero: Int,
    val monthNames: List<String>,
    val dayNames: List<String>,
    val fiscalYearStart: Int, // month
    val workWeekStart: DayOfWeek,
    val workWeekLength: Int,
    val standardWorkHours: Int
) : Parcelable

enum class CalendarType {
    GREGORIAN,
    JULIAN,
    ISLAMIC,
    HEBREW,
    CHINESE,
    CUSTOM
}

// Season System

@Parcelize
data class SeasonSystem(
    val currentSeason: Season,
    val northernHemisphere: Boolean,
    val temperatureRange: Map<Season, Pair<Double, Double>>,
    val weatherPatterns: Map<Season, List<WeatherPattern>>,
    val daylightHours: Map<Season, Double>,
    val agriculturalSeasons: List<AgriculturalSeason>,
    val tourismSeasons: List<TourismSeason>,
    val energyDemand: Map<Season, Double>,
    val seasonLength: Int // days per season
) : Parcelable

@Parcelize
data class WeatherPattern(
    val id: String,
    val name: String,
    val probability: Double,
    val effects: List<String>,
    val duration: Int // hours
) : Parcelable

@Parcelize
data class AgriculturalSeason(
    val id: String,
    val name: String,
    val startMonth: Int,
    val endMonth: Int,
    val activities: List<String>,
    val affectedCrops: List<String>
) : Parcelable

@Parcelize
data class TourismSeason(
    val id: String,
    val type: String, // "peak", "shoulder", "off"
    val startMonth: Int,
    val endMonth: Int,
    val touristMultiplier: Double,
    val priceMultiplier: Double
) : Parcelable

// Holiday System

@Parcelize
data class HolidaySystem(
    val nationalHolidays: List<NationalHoliday>,
    val regionalHolidays: List<RegionalHoliday>,
    val religiousHolidays: List<ReligiousHoliday>,
    val internationalDays: List<InternationalDay>,
    val observedHolidays: List<String>, // IDs of holidays being observed
    val upcomingHolidays: List<UpcomingHoliday>
) : Parcelable

@Parcelize
data class NationalHoliday(
    val id: String,
    val name: String,
    val date: HolidayDate,
    val type: HolidayType,
    val isPublicHoliday: Boolean,
    val traditions: List<String>,
    val economicImpact: Double,
    val travelImpact: Double,
    val governmentClosure: Boolean
) : Parcelable

@Parcelize
data class HolidayDate(
    val type: HolidayDateType,
    val fixedMonth: Int?,
    val fixedDay: Int?,
    val nthWeek: Int?, // for "third Monday of January" style
    val weekday: DayOfWeek?,
    val relativeToEaster: Int? // days before/after Easter
) : Parcelable

enum class HolidayDateType {
    FIXED,
    FLOATING,
    RELATIVE_EASTER,
    LUNAR
}

enum class HolidayType {
    NATIONAL,
    RELIGIOUS,
    COMMEMORATIVE,
    CULTURAL,
    MILITARY,
    POLITICAL,
    SEASONAL
}

@Parcelize
data class RegionalHoliday(
    val id: String,
    val name: String,
    val regionId: String,
    val date: HolidayDate,
    val isPublicHoliday: Boolean,
    val traditions: List<String>
) : Parcelable

@Parcelize
data class ReligiousHoliday(
    val id: String,
    val name: String,
    val religion: String,
    val date: HolidayDate,
    val isPublicHoliday: Boolean,
    val traditions: List<String>,
    val observance: String
) : Parcelable

@Parcelize
data class InternationalDay(
    val id: String,
    val name: String,
    val date: HolidayDate,
    val recognized: Boolean,
    val theme: String?
) : Parcelable

@Parcelize
data class UpcomingHoliday(
    val holidayId: String,
    val holidayName: String,
    val date: GameTime,
    val daysUntil: Int
) : Parcelable

// Recurring Events

@Parcelize
data class RecurringEvent(
    val id: String,
    val name: String,
    val description: String,
    val type: RecurringEventType,
    val frequency: EventFrequency,
    val startDate: GameTime?,
    val endDate: GameTime?,
    val participants: List<String>,
    val effects: List<String>,
    val nextOccurrence: GameTime?
) : Parcelable

enum class RecurringEventType {
    CABINET_MEETING,
    PARLIAMENT_SESSION,
    BUDGET_REVIEW,
    ECONOMIC_REPORT,
    DIPLOMATIC_SUMMIT,
    MILITARY_EXERCISE,
    SPORTS_EVENT,
    CULTURAL_EVENT,
    TRADE_MISSION,
    STATE_VISIT
}

enum class EventFrequency {
    DAILY,
    WEEKLY,
    BIWEEKLY,
    MONTHLY,
    QUARTERLY,
    SEMIANNUALLY,
    ANNUALLY,
    CUSTOM
}

// Election Cycles

@Parcelize
data class ElectionCycle(
    val id: String,
    val type: ElectionType,
    val termLength: Int, // years
    val termLimit: Int?, // max terms
    val electionMonth: Int,
    val electionDay: String, // "first Tuesday after first Monday of November"
    val primaryMonth: Int?,
    val filingDeadlineMonths: Int, // months before election
    val campaignSeasonMonths: Int,
    val inaugurationDay: String?,
    val nextElection: GameTime?,
    val pastElections: List<GameTime>
) : Parcelable

// Budget Cycles

@Parcelize
data class BudgetCycle(
    val id: String,
    val fiscalYearStart: Int, // month
    val budgetProposalDue: Int, // months before fiscal year
    val budgetApprovalDeadline: Int, // months before fiscal year
    val quarterlyReports: List<Int>, // months
    val annualAuditMonth: Int,
    val currentFiscalYear: Int,
    val budgetPhase: BudgetPhase,
    val upcomingDeadlines: List<BudgetDeadline>
) : Parcelable

enum class BudgetPhase {
    PLANNING,
    PROPOSAL,
    NEGOTIATION,
    APPROVAL,
    EXECUTION,
    REVIEW,
    AUDIT
}

@Parcelize
data class BudgetDeadline(
    val id: String,
    val name: String,
    val date: GameTime,
    val type: String,
    val consequences: String
) : Parcelable

// Diplomatic Calendar

@Parcelize
data class DiplomaticCalendar(
    val upcomingSummits: List<DiplomaticSummit>,
    val internationalOrganizations: List<OrganizationMeeting>,
    val treatyDeadlines: List<TreatyDeadline>,
    val sanctionsReviewDates: List<SanctionsReview>,
    val unGeneralAssembly: GameTime?,
    val g7Summit: GameTime?,
    val g20Summit: GameTime?,
    val climateConference: GameTime?,
    val tradeNegotiations: List<TradeNegotiationDate>
) : Parcelable

@Parcelize
data class DiplomaticSummit(
    val id: String,
    val name: String,
    val hostCountry: String,
    val location: String,
    val startDate: GameTime,
    val endDate: GameTime,
    val participants: List<String>,
    val topics: List<String>,
    val expectedOutcomes: List<String>
) : Parcelable

@Parcelize
data class OrganizationMeeting(
    val id: String,
    val organization: String,
    val type: String, // "General Assembly", "Security Council", etc.
    val date: GameTime,
    val duration: Int, // days
    val agenda: List<String>
) : Parcelable

@Parcelize
data class TreatyDeadline(
    val id: String,
    val treatyId: String,
    val treatyName: String,
    val deadline: GameTime,
    val type: String, // "ratification", "implementation", "review"
    val consequences: String
) : Parcelable

@Parcelize
data class SanctionsReview(
    val id: String,
    val targetCountry: String,
    val reviewDate: GameTime,
    val currentSanctions: List<String>,
    val reviewCriteria: List<String>
) : Parcelable

@Parcelize
data class TradeNegotiationDate(
    val id: String,
    val partner: String,
    val round: Int,
    val date: GameTime,
    val location: String,
    val issues: List<String>
) : Parcelable

// Sports Calendar

@Parcelize
data class SportsCalendar(
    val domesticLeagues: List<SportsLeague>,
    val internationalEvents: List<InternationalSportsEvent>,
    val nationalTeamFixtures: List<NationalTeamFixture>,
    val olympicCycle: OlympicCycle?,
    val worldCupCycles: List<WorldCupCycle>
) : Parcelable

@Parcelize
data class SportsLeague(
    val id: String,
    val name: String,
    val sport: String,
    val seasonStart: Int, // month
    val seasonEnd: Int, // month
    val teams: Int,
    val currentSeason: Int,
    val upcomingFixtures: List<SportsFixture>
) : Parcelable

@Parcelize
data class SportsFixture(
    val id: String,
    val homeTeam: String,
    val awayTeam: String,
    val date: GameTime,
    val competition: String,
    val venue: String
) : Parcelable

@Parcelize
data class InternationalSportsEvent(
    val id: String,
    val name: String,
    val sport: String,
    val hostCountry: String,
    val hostCity: String,
    val startDate: GameTime,
    val endDate: GameTime,
    val participants: Int,
    val prestige: Int // 1-10
) : Parcelable

@Parcelize
data class NationalTeamFixture(
    val id: String,
    val sport: String,
    val competition: String,
    val opponent: String,
    val home: Boolean,
    val date: GameTime,
    val venue: String?,
    val importance: Int // 1-10
) : Parcelable

@Parcelize
data class OlympicCycle(
    val summerOlympics: GameTime?,
    val summerHost: String?,
    val winterOlympics: GameTime?,
    val winterHost: String?,
    val paralympics: GameTime?
) : Parcelable

@Parcelize
data class WorldCupCycle(
    val sport: String,
    val nextWorldCup: GameTime?,
    val host: String?,
    val qualificationStart: GameTime?,
    val nationalTeamStatus: String // "qualified", "in_qualification", "eliminated"
) : Parcelable

// Academic Calendar

@Parcelize
data class AcademicCalendar(
    val schoolYearStart: Int, // month
    val schoolYearEnd: Int, // month
    val semesters: List<Semester>,
    val holidays: List<AcademicHoliday>,
    val examPeriods: List<ExamPeriod>,
    val graduationSeason: Int, // month
    val universityAdmissionsDeadline: GameTime?,
    val researchGrantCycles: List<GrantCycle>
) : Parcelable

@Parcelize
data class Semester(
    val id: String,
    val name: String,
    val startDate: GameTime,
    val endDate: GameTime,
    val weeks: Int
) : Parcelable

@Parcelize
data class AcademicHoliday(
    val id: String,
    val name: String,
    val startDate: GameTime,
    val endDate: GameTime,
    val type: String // "winter_break", "spring_break", "summer"
) : Parcelable

@Parcelize
data class ExamPeriod(
    val id: String,
    val name: String,
    val startDate: GameTime,
    val endDate: GameTime,
    val level: String // "primary", "secondary", "university"
) : Parcelable

@Parcelize
data class GrantCycle(
    val id: String,
    val name: String,
    val fundingBody: String,
    val submissionDeadline: GameTime,
    val decisionDate: GameTime,
    val fundingAmount: Long
) : Parcelable

// Religious Calendar

@Parcelize
data class ReligiousCalendar(
    val dominantReligion: String,
    val majorHolidays: List<ReligiousHolidayDate>,
    val fastingPeriods: List<FastingPeriod>,
    val pilgrimageSeasons: List<PilgrimageSeason>,
    val religiousObservances: List<ReligiousObservance>
) : Parcelable

@Parcelize
data class ReligiousHolidayDate(
    val id: String,
    val name: String,
    val religion: String,
    val date: GameTime,
    val duration: Int, // days
    val observanceLevel: String // "major", "minor"
) : Parcelable

@Parcelize
data class FastingPeriod(
    val id: String,
    val name: String,
    val religion: String,
    val startDate: GameTime,
    val endDate: GameTime,
    val restrictions: List<String>
) : Parcelable

@Parcelize
data class PilgrimageSeason(
    val id: String,
    val name: String,
    val destination: String,
    val startDate: GameTime,
    val endDate: GameTime,
    val expectedPilgrims: Long
) : Parcelable

@Parcelize
data class ReligiousObservance(
    val id: String,
    val name: String,
    val type: String,
    val frequency: String,
    val nextOccurrence: GameTime
) : Parcelable

// Market Calendar

@Parcelize
data class MarketCalendar(
    val tradingDays: List<DayOfWeek>,
    val marketHolidays: List<MarketHoliday>,
    val tradingHours: TradingHours,
    val earningsSeasons: List<EarningsSeason>,
    val economicDataReleases: List<EconomicDataRelease>,
    val centralBankMeetings: List<CentralBankMeeting>,
    val optionsExpirationDays: List<GameTime>,
    val futuresExpirationDays: List<GameTime>
) : Parcelable

@Parcelize
data class MarketHoliday(
    val id: String,
    val name: String,
    val date: GameTime,
    val marketsAffected: List<String>
) : Parcelable

@Parcelize
data class TradingHours(
    val preMarketStart: Int, // hour
    val marketOpen: Int,
    val marketClose: Int,
    val afterHoursEnd: Int,
    val timezone: String
) : Parcelable

@Parcelize
data class EarningsSeason(
    val id: String,
    val quarter: Int,
    val startDate: GameTime,
    val peakDate: GameTime,
    val endDate: GameTime,
    val expectedReports: Int
) : Parcelable

@Parcelize
data class EconomicDataRelease(
    val id: String,
    val name: String,
    val releaseDate: GameTime,
    val releaseTime: Int, // hour
    val frequency: String,
    val importance: Int // 1-5
) : Parcelable

@Parcelize
data class CentralBankMeeting(
    val id: String,
    val bank: String,
    val date: GameTime,
    val expectedDecision: String,
    val pressConference: Boolean
) : Parcelable
