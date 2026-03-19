package com.cascadesim.core.generator

import com.cascadesim.core.domain.model.*
import java.util.*
import kotlin.random.Random

/**
 * Procedural generation engine for creating game content.
 * Generates countries, NPCs, events, and other game elements.
 */
class ProceduralGenerator(private val seed: Long = System.currentTimeMillis()) {
    
    private val random = Random(seed)
    
    // Country name components
    private val prefixes = listOf(
        "Repub", "Demo", "Fede", "Unit", "King", "Emp", "Grand", "New", "Great", "Free",
        "Peop", "Soc", "Democ", "Natl", "Holy", "Div", "Eter", "Sove", "Cons", "Progr"
    )
    
    private val suffixes = listOf(
        "land", "stan", "ia", "onia", "rica", "tania", "burg", "gard", "heim", "dor",
        "vania", "thia", "ria", "sia", "nia", "desh", "pur", "bad", "nagar", "ristan"
    )
    
    private val capitalPrefixes = listOf(
        "Port", "Fort", "New", "Old", "Saint", "Mount", "River", "Lake", "Bay", "North",
        "South", "East", "West", "Central", "Upper", "Lower", "Greater", "Royal", "Free"
    )
    
    private val capitalSuffixes = listOf(
        " City", "ton", "ville", "burg", "grad", "polis", "haven", "worth", "field", "wood",
        "bridge", "ford", "dale", "wick", "chester", "ham", "stown", "bury", "port", "land"
    )
    
    // First names for NPCs
    private val maleNames = listOf(
        "John", "Michael", "David", "Robert", "William", "James", "Alexander", "Daniel",
        "Thomas", "Richard", "Benjamin", "Samuel", "Edward", "Henry", "Charles", "George",
        "Joseph", "Andrew", "Peter", "Mark", "Anthony", "Nicholas", "Gregory", "Stephen"
    )
    
    private val femaleNames = listOf(
        "Mary", "Elizabeth", "Sarah", "Catherine", "Victoria", "Margaret", "Anne", "Jane",
        "Emma", "Olivia", "Sophia", "Isabella", "Charlotte", "Amelia", "Harper", "Evelyn",
        "Abigail", "Emily", "Grace", "Alice", "Clara", "Eleanor", "Helen", "Ruth"
    )
    
    private val lastNames = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Wilson", "Anderson", "Taylor", "Thomas", "Moore",
        "Jackson", "Martin", "Lee", "Thompson", "White", "Harris", "Clark", "Lewis", "Robinson"
    )
    
    private val titles = listOf(
        "President", "Prime Minister", "Chancellor", "Premier", "Minister", "Director",
        "Secretary", "Chairman", "Governor", "Mayor", "General", "Admiral", "Ambassador",
        "Judge", "Professor", "Doctor", "Senator", "Deputy", "Commissioner"
    )
    
    /**
     * Generates a unique country with all properties.
     */
    fun generateCountry(
        id: String = UUID.randomUUID().toString(),
        continent: Continent = Continent.values().random(random)
    ): Country {
        val name = generateCountryName()
        val governmentType = GovernmentType.values().random(random)
        val population = random.nextLong(1_000_000L, 500_000_000L)
        val gdpPerCapita = when (random.nextInt(4)) {
            0 -> random.nextDouble(500.0, 5_000.0)   // Developing
            1 -> random.nextDouble(5_000.0, 15_000.0) // Emerging
            2 -> random.nextDouble(15_000.0, 40_000.0) // Developed
            else -> random.nextDouble(40_000.0, 100_000.0) // Wealthy
        }
        
        val flagColors = generateFlagColors()
        val resources = generateResources(continent)
        
        val now = System.currentTimeMillis()
        
        return Country(
            id = id,
            name = name,
            officialName = generateOfficialName(name, governmentType),
            flagEmoji = generateFlagEmoji(flagColors),
            flagColors = flagColors,
            continent = continent,
            governmentType = governmentType,
            population = population,
            areaKm2 = random.nextLong(10_000L, 10_000_000L),
            capitalCity = generateCapitalName(),
            majorCities = (1..random.nextInt(3, 8)).map { generateCapitalName() },
            languages = generateLanguages(continent),
            currency = generateCurrency(name),
            resources = resources,
            terrain = generateTerrain(continent),
            climate = generateClimate(continent),
            gdpPerCapita = gdpPerCapita,
            happinessIndex = random.nextDouble(3.0, 8.5),
            stabilityIndex = random.nextDouble(0.2, 0.9),
            militaryStrength = random.nextInt(10, 1000),
            techLevel = random.nextInt(20, 100),
            relations = emptyMap(),
            createdAt = now,
            updatedAt = now
        )
    }
    
    /**
     * Generates an NPC with personality and skills.
     */
    fun generateNPC(
        id: String = UUID.randomUUID().toString(),
        countryId: String,
        role: NPCRole = NPCRole.values().random(random),
        party: String? = null
    ): NPC {
        val gender = Gender.values().random(random)
        val firstName = if (gender == Gender.MALE) maleNames.random(random) else femaleNames.random(random)
        val lastName = lastNames.random(random)
        
        val personality = generatePersonality()
        val age = random.nextInt(28, 75)
        
        val now = System.currentTimeMillis()
        
        return NPC(
            id = id,
            fullName = "$firstName $lastName",
            title = titles.random(random),
            age = age,
            gender = gender,
            nationality = countryId,
            role = role,
            party = party,
            portraitSeed = random.nextLong(),
            personality = personality,
            skills = generateSkills(role),
            traits = generateTraits(personality),
            memories = emptyList(),
            relationships = emptyMap(),
            currentPositions = emptyList(),
            pastPositions = emptyList(),
            scandals = emptyList(),
            achievements = emptyList(),
            wealth = random.nextLong(10_000L, 50_000_000L),
            approvalRating = random.nextDouble(0.2, 0.8),
            influence = random.nextInt(10, 90),
            corruptionLevel = random.nextDouble(0.0, 0.5),
            isAlive = true,
            createdAt = now,
            updatedAt = now
        )
    }
    
    /**
     * Generates a political party.
     */
    fun generateParty(
        id: String = UUID.randomUUID().toString(),
        countryId: String,
        leaderId: String
    ): Party {
        val ideology = Ideology.values().random(random)
        val orientation = when (ideology) {
            Ideology.SOCIALIST, Ideology.COMMUNIST -> PoliticalOrientation.FAR_LEFT
            Ideology.SOCIAL_DEMOCRAT, Ideology.PROGRESSIVE -> PoliticalOrientation.PROGRESSIVE
            Ideology.GREEN -> PoliticalOrientation.LEFT
            Ideology.LIBERAL -> PoliticalOrientation.CENTRIST
            Ideology.CENTRIST -> PoliticalOrientation.MODERATE
            Ideology.CHRISTIAN_DEMOCRAT -> PoliticalOrientation.MODERATE
            Ideology.CONSERVATIVE, Ideology.NATIONAL_CONSERVATIVE -> PoliticalOrientation.CONSERVATIVE
            Ideology.NATIONALIST -> PoliticalOrientation.RIGHT
            Ideology.POPULIST -> PoliticalOrientation.POPULIST
            Ideology.LIBERTARIAN -> PoliticalOrientation.LIBERTARIAN
            else -> PoliticalOrientation.CENTRIST
        }
        
        return Party(
            id = id,
            name = generatePartyName(ideology),
            shortName = generatePartyAcronym(),
            color = generatePartyColor(),
            ideology = ideology,
            orientation = orientation,
            foundedYear = random.nextInt(1900, 2020),
            country = countryId,
            leader = leaderId,
            members = emptyList(),
            parliamentSeats = random.nextInt(0, 150),
            totalSeats = 200,
            pollingPercentage = random.nextDouble(2.0, 45.0),
            platform = generatePlatform(ideology),
            internalFactions = emptyList(),
            scandals = emptyList(),
            achievements = emptyList(),
            isRuling = false,
            coalitionRank = null,
            isActive = true
        )
    }
    
    /**
     * Generates a game event.
     */
    fun generateEvent(
        id: String = UUID.randomUUID().toString(),
        type: EventType = EventType.values().random(random),
        severity: EventSeverity = EventSeverity.values().random(random)
    ): GameEvent {
        val now = System.currentTimeMillis()
        
        return GameEvent(
            id = id,
            title = generateEventTitle(type),
            description = generateEventDescription(type),
            type = type,
            category = EventCategory.values().random(random),
            severity = severity,
            involvedEntities = emptyList(),
            availableDecisions = generateDecisions(type),
            consequences = emptyList(),
            triggeredBy = null,
            cascadeChildren = emptyList(),
            memoryImpact = null,
            timeLimit = if (severity == EventSeverity.CRITICAL) 60_000L else null,
            isActive = true,
            isResolved = false,
            resolvedDecision = null,
            createdAt = now,
            resolvedAt = null
        )
    }
    
    /**
     * Generates a task.
     */
    fun generateTask(
        id: String = UUID.randomUUID().toString(),
        type: TaskType = TaskType.values().random(random),
        parentId: String? = null
    ): Task {
        val now = System.currentTimeMillis()
        val deadline = now + random.nextLong(86_400_000L, 7 * 86_400_000L) // 1-7 days
        
        return Task(
            id = id,
            title = generateTaskTitle(type),
            description = generateTaskDescription(type),
            type = type,
            scope = when (type) {
                TaskType.POLICY_DECISION, TaskType.BUDGET_ALLOCATION, TaskType.CONSTITUTIONAL_REFORM -> TaskScope.MACRO
                TaskType.DOCUMENT_APPROVAL, TaskType.PURCHASE_ORDER, TaskType.PRESS_STATEMENT -> TaskScope.MICRO
                else -> TaskScope.MESO
            },
            status = TaskStatus.PENDING,
            priority = TaskPriority.values().random(random),
            parentId = parentId,
            childIds = emptyList(),
            relatedEventId = null,
            relatedDecisionId = null,
            assignedTo = null,
            department = null,
            requirements = emptyList(),
            rewards = emptyList(),
            penalties = emptyList(),
            deadline = deadline,
            timeEstimate = random.nextLong(3_600_000L, 72_000_000L),
            progress = 0.0f,
            subtasks = emptyList(),
            dependencies = emptyList(),
            blockingTasks = emptyList(),
            tags = emptyList(),
            createdAt = now,
            updatedAt = now,
            completedAt = null
        )
    }
    
    /**
     * Generates initial game world with countries and NPCs.
     */
    fun generateWorld(countryCount: Int = 10): WorldState {
        val countries = mutableListOf<Country>()
        val npcs = mutableListOf<NPC>()
        
        for (i in 0 until countryCount) {
            val continent = Continent.values()[i % Continent.values().size]
            val country = generateCountry(continent = continent)
            countries.add(country)
            
            // Generate NPCs for each country
            val npcCount = random.nextInt(5, 15)
            for (j in 0 until npcCount) {
                npcs.add(generateNPC(countryId = country.id))
            }
        }
        
        // Generate initial relations between countries
        val countriesWithRelations = countries.map { country ->
            val relations = countries
                .filter { it.id != country.id }
                .associate { other ->
                    other.id to random.nextInt(-50, 80)
                }
            country.copy(relations = relations)
        }
        
        return WorldState(
            countries = countriesWithRelations,
            activeNPCs = npcs,
            activeEvents = listOf(generateEvent()),
            pendingTasks = listOf(generateTask()),
            globalTensions = emptyMap(),
            economicConditions = EconomicConditions(
                globalGrowthRate = random.nextDouble(-2.0, 5.0),
                inflationRate = random.nextDouble(0.5, 15.0),
                unemploymentRate = random.nextDouble(3.0, 25.0),
                oilPrice = random.nextDouble(40.0, 120.0),
                tradeTension = random.nextInt(10, 60),
                stockMarketIndex = random.nextDouble(2000.0, 35000.0),
                cryptoValue = random.nextDouble(10000.0, 60000.0)
            ),
            activeTreaties = emptyList(),
            currentYear = 2024,
            currentMonth = 1,
            currentDay = 1,
            timeSpeed = TimeSpeed.NORMAL
        )
    }
    
    // Helper functions
    
    private fun generateCountryName(): String {
        val prefix = prefixes.random(random)
        val suffix = suffixes.random(random)
        return prefix + suffix
    }
    
    private fun generateOfficialName(name: String, govType: GovernmentType): String {
        return when (govType) {
            GovernmentType.PRESIDENTIAL_DEMOCRACY -> "Republic of $name"
            GovernmentType.PARLIAMENTARY_DEMOCRACY -> "$name Democratic Republic"
            GovernmentType.CONSTITUTIONAL_MONARCHY -> "Kingdom of $name"
            GovernmentType.ABSOLUTE_MONARCHY -> "The $name Empire"
            GovernmentType.DICTATORSHIP -> "People's Republic of $name"
            GovernmentType.TECHNOCRACY -> "$name Technate"
            GovernmentType.THEOCRACY -> "Holy State of $name"
            GovernmentType.MILITARY_JUNTA -> "$name Military Authority"
            GovernmentType.SINGLE_PARTY_STATE -> "$name People's Republic"
            GovernmentType.OLIGARCHY -> "The $name Federation"
        }
    }
    
    private fun generateCapitalName(): String {
        return capitalPrefixes.random(random) + capitalSuffixes.random(random)
    }
    
    private fun generateFlagColors(): List<String> {
        val allColors = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FFFFFF", 
            "#000000", "#FFA500", "#800080", "#008080", "#FFC0CB", "#A52A2A", "#808080")
        return (1..random.nextInt(2, 4)).map { allColors.random(random) }.distinct()
    }
    
    private fun generateFlagEmoji(colors: List<String>): String {
        val colorEmojis = mapOf(
            "#FF0000" to "🔴", "#00FF00" to "🟢", "#0000FF" to "🔵",
            "#FFFF00" to "🟡", "#FFFFFF" to "⚪", "#000000" to "⚫",
            "#FFA500" to "🟠", "#800080" to "🟣"
        )
        return colors.mapNotNull { colorEmojis[it] }.take(3).joinToString("")
    }
    
    private fun generateResources(continent: Continent): List<NaturalResource> {
        val continentResources = when (continent) {
            Continent.MIDDLE_EAST -> listOf(ResourceType.OIL, ResourceType.NATURAL_GAS)
            Continent.AFRICA -> listOf(ResourceType.GOLD, ResourceType.DIAMONDS, ResourceType.URANIUM)
            Continent.ASIA -> listOf(ResourceType.RARE_EARTH, ResourceType.COAL, ResourceType.IRON_ORE)
            Continent.SOUTH_AMERICA -> listOf(ResourceType.COPPER, ResourceType.IRON_ORE, ResourceType.TIMBER)
            Continent.NORTH_AMERICA -> listOf(ResourceType.OIL, ResourceType.NATURAL_GAS, ResourceType.COAL)
            Continent.EUROPE -> listOf(ResourceType.COAL, ResourceType.IRON_ORE, ResourceType.NATURAL_GAS)
            Continent.OCEANIA -> listOf(ResourceType.FISH, ResourceType.FRESH_WATER, ResourceType.URANIUM)
        }
        
        return continentResources.map { type ->
            NaturalResource(
                name = type.name.lowercase().replace("_", " "),
                type = type,
                abundance = random.nextDouble(0.1, 1.0),
                extractionDifficulty = random.nextDouble(0.1, 0.9),
                annualProduction = random.nextDouble(1000.0, 1_000_000.0)
            )
        }
    }
    
    private fun generateTerrain(continent: Continent): List<TerrainType> {
        return when (continent) {
            Continent.AFRICA -> listOf(TerrainType.PLAINS, TerrainType.DESERT, TerrainType.RAINFOREST)
            Continent.ASIA -> listOf(TerrainType.MOUNTAINS, TerrainType.PLAINS, TerrainType.COASTLINE)
            Continent.EUROPE -> listOf(TerrainType.MOUNTAINS, TerrainType.PLAINS, TerrainType.HILLS)
            Continent.NORTH_AMERICA -> listOf(TerrainType.MOUNTAINS, TerrainType.PLAINS, TerrainType.COASTLINE)
            Continent.SOUTH_AMERICA -> listOf(TerrainType.RAINFOREST, TerrainType.MOUNTAINS, TerrainType.PLAINS)
            Continent.OCEANIA -> listOf(TerrainType.ISLANDS, TerrainType.COASTLINE, TerrainType.RAINFOREST)
            Continent.MIDDLE_EAST -> listOf(TerrainType.DESERT, TerrainType.HILLS, TerrainType.COASTLINE)
        }
    }
    
    private fun generateClimate(continent: Continent): ClimateType {
        return when (continent) {
            Continent.AFRICA -> ClimateType.TROPICAL
            Continent.ASIA -> ClimateType.CONTINENTAL
            Continent.EUROPE -> ClimateType.TEMPERATE
            Continent.NORTH_AMERICA -> ClimateType.CONTINENTAL
            Continent.SOUTH_AMERICA -> ClimateType.TROPICAL
            Continent.OCEANIA -> ClimateType.SUBTROPICAL
            Continent.MIDDLE_EAST -> ClimateType.ARID
        }
    }
    
    private fun generateLanguages(continent: Continent): List<String> {
        val baseLanguage = when (continent) {
            Continent.AFRICA -> "Swahili"
            Continent.ASIA -> "Mandarin"
            Continent.EUROPE -> "Germanic"
            Continent.NORTH_AMERICA -> "English"
            Continent.SOUTH_AMERICA -> "Spanish"
            Continent.OCEANIA -> "English"
            Continent.MIDDLE_EAST -> "Arabic"
        }
        return listOf(baseLanguage, "English")
    }
    
    private fun generateCurrency(countryName: String): Currency {
        return Currency(
            name = "${countryName.take(3)} Dollar",
            code = countryName.take(3).uppercase() + "D",
            symbol = countryName.first().toString(),
            exchangeRateToUsd = random.nextDouble(0.1, 100.0)
        )
    }
    
    private fun generatePersonality(): Personality {
        return Personality(
            openness = random.nextInt(10, 90),
            conscientiousness = random.nextInt(10, 90),
            extraversion = random.nextInt(10, 90),
            agreeableness = random.nextInt(10, 90),
            neuroticism = random.nextInt(10, 90)
        )
    }
    
    private fun generateSkills(role: NPCRole): Map<Skill, Int> {
        val roleSkills = when (role) {
            NPCRole.POLITICIAN -> listOf(Skill.ORATORY, Skill.CHARISMA, Skill.POLITICAL_SAVVY)
            NPCRole.DIPLOMAT -> listOf(Skill.DIPLOMACY, Skill.NEGOTIATION, Skill.INTELLIGENCE)
            NPCRole.BUSINESS_LEADER -> listOf(Skill.ECONOMICS, Skill.NEGOTIATION, Skill.CORRUPTION)
            NPCRole.MILITARY_OFFICER -> listOf(Skill.MILITARY_STRATEGY, Skill.LOYALTY, Skill.CRISIS_MANAGEMENT)
            NPCRole.JUDGE -> listOf(Skill.LAW, Skill.INTELLIGENCE, Skill.ADMINISTRATION)
            NPCRole.CIVIL_SERVANT -> listOf(Skill.ADMINISTRATION, Skill.LOYALTY, Skill.ECONOMICS)
            else -> listOf(Skill.CHARISMA, Skill.MEDIA_RELATIONS, Skill.INTELLIGENCE)
        }
        
        return Skill.values().associate { skill ->
            val baseValue = if (skill in roleSkills) random.nextInt(50, 95) else random.nextInt(10, 60)
            skill to baseValue
        }
    }
    
    private fun generateTraits(personality: Personality): List<Trait> {
        val traits = mutableListOf<Trait>()
        
        if (personality.openness > 70) {
            traits.add(Trait("Visionary", TraitType.POSITIVE, 0.8))
        }
        if (personality.conscientiousness > 70) {
            traits.add(Trait("Disciplined", TraitType.POSITIVE, 0.8))
        }
        if (personality.neuroticism > 70) {
            traits.add(Trait("Anxious", TraitType.NEGATIVE, 0.6))
        }
        if (personality.agreeableness < 30) {
            traits.add(Trait("Ruthless", TraitType.NEUTRAL, 0.7))
        }
        
        return traits
    }
    
    private fun generatePartyName(ideology: Ideology): String {
        return when (ideology) {
            Ideology.SOCIALIST -> "Socialist Party"
            Ideology.SOCIAL_DEMOCRAT -> "Social Democratic Party"
            Ideology.PROGRESSIVE -> "Progressive Movement"
            Ideology.LIBERAL -> "Liberal Party"
            Ideology.CENTRIST -> "Center Alliance"
            Ideology.CONSERVATIVE -> "Conservative Party"
            Ideology.NATIONAL_CONSERVATIVE -> "National Conservative Union"
            Ideology.NATIONALIST -> "National Front"
            Ideology.POPULIST -> "People's Party"
            Ideology.LIBERTARIAN -> "Libertarian Alliance"
            Ideology.GREEN -> "Green Party"
            Ideology.COMMUNIST -> "Communist Party"
            Ideology.ISLAMIST -> "Islamic Justice Party"
            Ideology.CHRISTIAN_DEMOCRAT -> "Christian Democratic Union"
        }
    }
    
    private fun generatePartyAcronym(): String {
        return (1..3).map { ('A'..'Z').random(random) }.joinToString("")
    }
    
    private fun generatePartyColor(): String {
        val partyColors = listOf("#FF0000", "#0000FF", "#00FF00", "#FFFF00", "#FFA500", "#800080", "#008080")
        return partyColors.random(random)
    }
    
    private fun generatePlatform(ideology: Ideology): Platform {
        return Platform(
            economicPolicy = when (ideology) {
                Ideology.SOCIALIST, Ideology.COMMUNIST -> EconomicPolicy.STATE_CONTROLLED
                Ideology.SOCIAL_DEMOCRAT -> EconomicPolicy.MIXED_ECONOMY_LEFT
                Ideology.LIBERAL -> EconomicPolicy.MIXED_ECONOMY_CENTER
                Ideology.LIBERTARIAN -> EconomicPolicy.FREE_MARKET
                Ideology.CONSERVATIVE, Ideology.NATIONAL_CONSERVATIVE -> EconomicPolicy.MIXED_ECONOMY_RIGHT
                else -> EconomicPolicy.MIXED_ECONOMY_CENTER
            },
            socialPolicy = when (ideology) {
                Ideology.SOCIALIST, Ideology.PROGRESSIVE, Ideology.GREEN -> SocialPolicy.PROGRESSIVE
                Ideology.COMMUNIST -> SocialPolicy.MODERATE_PROGRESSIVE
                Ideology.CONSERVATIVE, Ideology.NATIONAL_CONSERVATIVE -> SocialPolicy.CONSERVATIVE
                Ideology.NATIONALIST -> SocialPolicy.TRADITIONALIST
                else -> SocialPolicy.CENTRIST
            },
            foreignPolicy = when (ideology) {
                Ideology.NATIONALIST -> ForeignPolicy.ISOLATIONIST
                Ideology.COMMUNIST -> ForeignPolicy.NEUTRALIST
                Ideology.LIBERAL -> ForeignPolicy.ENGAGEMENT
                Ideology.CONSERVATIVE -> ForeignPolicy.INTERVENTIONIST
                else -> ForeignPolicy.ENGAGEMENT
            },
            keyIssues = emptyList(),
            promises = emptyList()
        )
    }
    
    private fun generateDecisions(type: EventType): List<Decision> {
        return listOf(
            Decision(
                id = UUID.randomUUID().toString(),
                text = "Take action",
                description = "Address the situation directly",
                requirements = emptyList(),
                costs = listOf(Cost(CostType.POLITICAL_CAPITAL, 10.0, false, null)),
                outcomes = emptyList(),
                aiReasoning = "Direct action shows leadership",
                riskLevel = RiskLevel.MODERATE,
                timeToImplement = 3600000L,
                approvalRequired = false,
                approvers = emptyList()
            ),
            Decision(
                id = UUID.randomUUID().toString(),
                text = "Delegate to ministry",
                description = "Let the relevant ministry handle it",
                requirements = emptyList(),
                costs = listOf(Cost(CostType.POLITICAL_CAPITAL, 5.0, false, null)),
                outcomes = emptyList(),
                aiReasoning = "Delegation shows trust in institutions",
                riskLevel = RiskLevel.LOW,
                timeToImplement = 7200000L,
                approvalRequired = false,
                approvers = emptyList()
            )
        )
    }
    
    private fun generateEventTitle(type: EventType): String {
        return when (type) {
            EventType.CRISIS -> "National Crisis Emerges"
            EventType.OPPORTUNITY -> "New Opportunity Arises"
            EventType.SCANDAL -> "Scandal Breaks Out"
            EventType.DIPLOMATIC -> "Diplomatic Incident"
            EventType.ECONOMIC -> "Economic Development"
            EventType.NATURAL_DISASTER -> "Natural Disaster Strikes"
            EventType.POLITICAL -> "Political Turmoil"
            EventType.MILITARY -> "Military Situation"
            EventType.SOCIAL -> "Social Unrest"
            EventType.SPORTS -> "Sports Victory"
            EventType.CULTURAL -> "Cultural Milestone"
        }
    }
    
    private fun generateEventDescription(type: EventType): String {
        return when (type) {
            EventType.CRISIS -> "A major crisis has emerged requiring immediate attention and decisive action."
            EventType.OPPORTUNITY -> "A unique opportunity presents itself that could benefit the nation."
            EventType.SCANDAL -> "Allegations have surfaced that could damage your administration."
            EventType.DIPLOMATIC -> "A diplomatic situation requires careful handling."
            EventType.ECONOMIC -> "Economic news has significant implications for policy."
            EventType.NATURAL_DISASTER -> "A natural disaster has struck, requiring emergency response."
            EventType.POLITICAL -> "Political developments are shifting the landscape."
            EventType.MILITARY -> "Military matters require executive decision."
            EventType.SOCIAL -> "Social tensions are rising and need addressing."
            EventType.SPORTS -> "A major sports event has captured national attention."
            EventType.CULTURAL -> "A cultural moment has united or divided the nation."
        }
    }
    
    private fun generateTaskTitle(type: TaskType): String {
        return when (type) {
            TaskType.POLICY_DECISION -> "Review Policy Proposal"
            TaskType.BUDGET_ALLOCATION -> "Allocate Budget Resources"
            TaskType.TREATY_NEGOTIATION -> "Negotiate Treaty Terms"
            TaskType.MILITARY_OPERATION -> "Authorize Military Action"
            TaskType.ELECTION_CAMPAIGN -> "Plan Campaign Event"
            TaskType.CONSTITUTIONAL_REFORM -> "Review Constitutional Amendment"
            TaskType.MAJOR_INFRASTRUCTURE -> "Approve Infrastructure Project"
            TaskType.TRADE_DEAL -> "Finalize Trade Agreement"
            TaskType.DOCUMENT_APPROVAL -> "Sign Official Document"
            TaskType.APPOINTMENT_DECISION -> "Make Appointment Decision"
            TaskType.PURCHASE_ORDER -> "Approve Purchase Request"
            TaskType.PRESS_STATEMENT -> "Draft Press Statement"
            TaskType.MEETING_ATTENDANCE -> "Attend Official Meeting"
            TaskType.SPEECH_WRITING -> "Write Keynote Speech"
            TaskType.RALLY_ORGANIZATION -> "Organize Public Rally"
            TaskType.INDIVIDUAL_NEGOTIATION -> "Conduct Negotiation"
            TaskType.REPORT_REVIEW -> "Review Official Report"
            TaskType.EMERGENCY_RESPONSE -> "Respond to Emergency"
            TaskType.INVESTMENT_OPPORTUNITY -> "Evaluate Investment"
            else -> "Complete Task"
        }
    }
    
    private fun generateTaskDescription(type: TaskType): String {
        return "Complete the ${generateTaskTitle(type).lowercase()} task within the deadline."
    }
}
