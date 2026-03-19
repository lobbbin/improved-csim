package com.cascadesim.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cascadesim.core.domain.model.*

/**
 * Room entity for Country.
 */
@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val officialName: String,
    val flagEmoji: String,
    val flagColorsJson: String,
    val continent: String,
    val governmentType: String,
    val population: Long,
    val areaKm2: Long,
    val capitalCity: String,
    val majorCitiesJson: String,
    val languagesJson: String,
    val currencyName: String,
    val currencyCode: String,
    val currencySymbol: String,
    val currencyExchangeRate: Double,
    val resourcesJson: String,
    val terrainJson: String,
    val climate: String,
    val gdpPerCapita: Double,
    val happinessIndex: Double,
    val stabilityIndex: Double,
    val militaryStrength: Int,
    val techLevel: Int,
    val relationsJson: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toDomain(): Country = Country(
        id = id,
        name = name,
        officialName = officialName,
        flagEmoji = flagEmoji,
        flagColors = parseList(flagColorsJson),
        continent = Continent.valueOf(continent),
        governmentType = GovernmentType.valueOf(governmentType),
        population = population,
        areaKm2 = areaKm2,
        capitalCity = capitalCity,
        majorCities = parseList(majorCitiesJson),
        languages = parseList(languagesJson),
        currency = Currency(currencyName, currencyCode, currencySymbol, currencyExchangeRate),
        resources = parseResources(resourcesJson),
        terrain = terrainJson.split(",").map { TerrainType.valueOf(it.trim()) },
        climate = ClimateType.valueOf(climate),
        gdpPerCapita = gdpPerCapita,
        happinessIndex = happinessIndex,
        stabilityIndex = stabilityIndex,
        militaryStrength = militaryStrength,
        techLevel = techLevel,
        relations = parseMap(relationsJson),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    
    companion object {
        fun fromDomain(country: Country): CountryEntity = CountryEntity(
            id = country.id,
            name = country.name,
            officialName = country.officialName,
            flagEmoji = country.flagEmoji,
            flagColorsJson = country.flagColors.joinToString(","),
            continent = country.continent.name,
            governmentType = country.governmentType.name,
            population = country.population,
            areaKm2 = country.areaKm2,
            capitalCity = country.capitalCity,
            majorCitiesJson = country.majorCities.joinToString(","),
            languagesJson = country.languages.joinToString(","),
            currencyName = country.currency.name,
            currencyCode = country.currency.code,
            currencySymbol = country.currency.symbol,
            currencyExchangeRate = country.currency.exchangeRateToUsd,
            resourcesJson = country.resources.joinToString("|") { "${it.name}:${it.type.name}:${it.abundance}:${it.extractionDifficulty}:${it.annualProduction}" },
            terrainJson = country.terrain.joinToString(",") { it.name },
            climate = country.climate.name,
            gdpPerCapita = country.gdpPerCapita,
            happinessIndex = country.happinessIndex,
            stabilityIndex = country.stabilityIndex,
            militaryStrength = country.militaryStrength,
            techLevel = country.techLevel,
            relationsJson = country.relations.entries.joinToString(",") { "${it.key}:${it.value}" },
            createdAt = country.createdAt,
            updatedAt = country.updatedAt
        )
        
        private fun parseList(json: String): List<String> =
            if (json.isBlank()) emptyList() else json.split(",")
        
        private fun parseMap(json: String): Map<String, Int> =
            if (json.isBlank()) emptyMap()
            else json.split(",").associate {
                val (key, value) = it.split(":")
                key to value.toInt()
            }
        
        private fun parseResources(json: String): List<NaturalResource> =
            if (json.isBlank()) emptyList()
            else json.split("|").map {
                val parts = it.split(":")
                NaturalResource(
                    name = parts[0],
                    type = ResourceType.valueOf(parts[1]),
                    abundance = parts[2].toDouble(),
                    extractionDifficulty = parts[3].toDouble(),
                    annualProduction = parts[4].toDouble()
                )
            }
    }
}

/**
 * Room entity for NPC.
 */
@Entity(tableName = "npcs")
data class NPCEntity(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val title: String,
    val age: Int,
    val gender: String,
    val nationality: String,
    val role: String,
    val party: String?,
    val portraitSeed: Long,
    val openness: Int,
    val conscientiousness: Int,
    val extraversion: Int,
    val agreeableness: Int,
    val neuroticism: Int,
    val skillsJson: String,
    val traitsJson: String,
    val memoriesJson: String,
    val relationshipsJson: String,
    val currentPositionsJson: String,
    val pastPositionsJson: String,
    val scandalsJson: String,
    val achievementsJson: String,
    val wealth: Long,
    val approvalRating: Double,
    val influence: Int,
    val corruptionLevel: Double,
    val isAlive: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toDomain(): NPC = NPC(
        id = id,
        fullName = fullName,
        title = title,
        age = age,
        gender = Gender.valueOf(gender),
        nationality = nationality,
        role = NPCRole.valueOf(role),
        party = party,
        portraitSeed = portraitSeed,
        personality = Personality(openness, conscientiousness, extraversion, agreeableness, neuroticism),
        skills = parseSkills(skillsJson),
        traits = parseTraits(traitsJson),
        memories = emptyList(), // Would need separate table for large lists
        relationships = parseMap(relationshipsJson),
        currentPositions = emptyList(),
        pastPositions = emptyList(),
        scandals = emptyList(),
        achievements = emptyList(),
        wealth = wealth,
        approvalRating = approvalRating,
        influence = influence,
        corruptionLevel = corruptionLevel,
        isAlive = isAlive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    
    companion object {
        fun fromDomain(npc: NPC): NPCEntity = NPCEntity(
            id = npc.id,
            fullName = npc.fullName,
            title = npc.title,
            age = npc.age,
            gender = npc.gender.name,
            nationality = npc.nationality,
            role = npc.role.name,
            party = npc.party,
            portraitSeed = npc.portraitSeed,
            openness = npc.personality.openness,
            conscientiousness = npc.personality.conscientiousness,
            extraversion = npc.personality.extraversion,
            agreeableness = npc.personality.agreeableness,
            neuroticism = npc.personality.neuroticism,
            skillsJson = npc.skills.entries.joinToString(",") { "${it.key.name}:${it.value}" },
            traitsJson = npc.traits.joinToString(",") { "${it.name}:${it.type.name}:${it.intensity}" },
            memoriesJson = "",
            relationshipsJson = npc.relationships.entries.joinToString(",") { "${it.key}:${it.value}" },
            currentPositionsJson = "",
            pastPositionsJson = "",
            scandalsJson = "",
            achievementsJson = "",
            wealth = npc.wealth,
            approvalRating = npc.approvalRating,
            influence = npc.influence,
            corruptionLevel = npc.corruptionLevel,
            isAlive = npc.isAlive,
            createdAt = npc.createdAt,
            updatedAt = npc.updatedAt
        )
        
        private fun parseSkills(json: String): Map<Skill, Int> =
            if (json.isBlank()) emptyMap()
            else json.split(",").associate {
                val (key, value) = it.split(":")
                Skill.valueOf(key) to value.toInt()
            }
        
        private fun parseTraits(json: String): List<Trait> =
            if (json.isBlank()) emptyList()
            else json.split(",").map {
                val parts = it.split(":")
                Trait(parts[0], TraitType.valueOf(parts[1]), parts[2].toDouble())
            }
        
        private fun parseMap(json: String): Map<String, Int> =
            if (json.isBlank()) emptyMap()
            else json.split(",").associate {
                val parts = it.split(":")
                parts[0] to parts[1].toInt()
            }
    }
}

/**
 * Room entity for GameEvent.
 */
@Entity(tableName = "game_events")
data class GameEventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val category: String,
    val severity: String,
    val involvedEntitiesJson: String,
    val decisionsJson: String,
    val consequencesJson: String,
    val triggeredBy: String?,
    val cascadeChildrenJson: String,
    val memoryImpactJson: String?,
    val timeLimit: Long?,
    val isActive: Boolean,
    val isResolved: Boolean,
    val resolvedDecision: String?,
    val createdAt: Long,
    val resolvedAt: Long?
) {
    fun toDomain(): GameEvent = GameEvent(
        id = id,
        title = title,
        description = description,
        type = EventType.valueOf(type),
        category = EventCategory.valueOf(category),
        severity = EventSeverity.valueOf(severity),
        involvedEntities = emptyList(), // Parsed from JSON in real implementation
        availableDecisions = emptyList(),
        consequences = emptyList(),
        triggeredBy = triggeredBy,
        cascadeChildren = if (cascadeChildrenJson.isBlank()) emptyList() else cascadeChildrenJson.split(","),
        memoryImpact = null,
        timeLimit = timeLimit,
        isActive = isActive,
        isResolved = isResolved,
        resolvedDecision = resolvedDecision,
        createdAt = createdAt,
        resolvedAt = resolvedAt
    )
    
    companion object {
        fun fromDomain(event: GameEvent): GameEventEntity = GameEventEntity(
            id = event.id,
            title = event.title,
            description = event.description,
            type = event.type.name,
            category = event.category.name,
            severity = event.severity.name,
            involvedEntitiesJson = "",
            decisionsJson = "",
            consequencesJson = "",
            triggeredBy = event.triggeredBy,
            cascadeChildrenJson = event.cascadeChildren.joinToString(","),
            memoryImpactJson = null,
            timeLimit = event.timeLimit,
            isActive = event.isActive,
            isResolved = event.isResolved,
            resolvedDecision = event.resolvedDecision,
            createdAt = event.createdAt,
            resolvedAt = event.resolvedAt
        )
    }
}

/**
 * Room entity for Task.
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val scope: String,
    val status: String,
    val priority: String,
    val parentId: String?,
    val childIdsJson: String,
    val relatedEventId: String?,
    val relatedDecisionId: String?,
    val assignedTo: String?,
    val department: String?,
    val requirementsJson: String,
    val rewardsJson: String,
    val penaltiesJson: String,
    val deadline: Long?,
    val timeEstimate: Long,
    val progress: Float,
    val subtasksJson: String,
    val dependenciesJson: String,
    val blockingTasksJson: String,
    val tagsJson: String,
    val createdAt: Long,
    val updatedAt: Long,
    val completedAt: Long?
) {
    fun toDomain(): Task = Task(
        id = id,
        title = title,
        description = description,
        type = TaskType.valueOf(type),
        scope = TaskScope.valueOf(scope),
        status = TaskStatus.valueOf(status),
        priority = TaskPriority.valueOf(priority),
        parentId = parentId,
        childIds = if (childIdsJson.isBlank()) emptyList() else childIdsJson.split(","),
        relatedEventId = relatedEventId,
        relatedDecisionId = relatedDecisionId,
        assignedTo = assignedTo,
        department = department,
        requirements = emptyList(),
        rewards = emptyList(),
        penalties = emptyList(),
        deadline = deadline,
        timeEstimate = timeEstimate,
        progress = progress,
        subtasks = emptyList(),
        dependencies = if (dependenciesJson.isBlank()) emptyList() else dependenciesJson.split(","),
        blockingTasks = if (blockingTasksJson.isBlank()) emptyList() else blockingTasksJson.split(","),
        tags = if (tagsJson.isBlank()) emptyList() else tagsJson.split(","),
        createdAt = createdAt,
        updatedAt = updatedAt,
        completedAt = completedAt
    )
    
    companion object {
        fun fromDomain(task: Task): TaskEntity = TaskEntity(
            id = task.id,
            title = task.title,
            description = task.description,
            type = task.type.name,
            scope = task.scope.name,
            status = task.status.name,
            priority = task.priority.name,
            parentId = task.parentId,
            childIdsJson = task.childIds.joinToString(","),
            relatedEventId = task.relatedEventId,
            relatedDecisionId = task.relatedDecisionId,
            assignedTo = task.assignedTo,
            department = task.department,
            requirementsJson = "",
            rewardsJson = "",
            penaltiesJson = "",
            deadline = task.deadline,
            timeEstimate = task.timeEstimate,
            progress = task.progress,
            subtasksJson = "",
            dependenciesJson = task.dependencies.joinToString(","),
            blockingTasksJson = task.blockingTasks.joinToString(","),
            tagsJson = task.tags.joinToString(","),
            createdAt = task.createdAt,
            updatedAt = task.updatedAt,
            completedAt = task.completedAt
        )
    }
}

/**
 * Room entity for PlayerState.
 */
@Entity(tableName = "player_states")
data class PlayerStateEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val country: String,
    val currentMode: String,
    val governmentType: String,
    val positionTitle: String,
    val positionPower: Int,
    val positionResponsibilitiesJson: String,
    val positionLimitationsJson: String,
    val positionCanAppointJson: String,
    val positionCanVeto: Boolean,
    val positionCanPardon: Boolean,
    val positionCanDeclareWar: Boolean,
    val positionCanDissolveParliament: Boolean,
    val politicalCapital: Int,
    val approvalRating: Double,
    val personalWealth: Long,
    val party: String?,
    val coalitionPartnersJson: String,
    val oppositionJson: String,
    val achievementsJson: String,
    val reputationJson: String,
    val traitsJson: String,
    val decisionsHistoryJson: String,
    val currentTermStart: Long,
    val termLength: Long,
    val electionCycle: Long,
    val vetoes: Int,
    val executiveOrders: Int,
    val lawsPassed: Int,
    val treatiesSigned: Int,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toDomain(): PlayerState = PlayerState(
        id = id,
        name = name,
        country = country,
        currentMode = GameMode.valueOf(currentMode),
        governmentType = GovernmentType.valueOf(governmentType),
        position = PlayerPosition(
            title = positionTitle,
            power = positionPower,
            responsibilities = if (positionResponsibilitiesJson.isBlank()) emptyList() else positionResponsibilitiesJson.split(","),
            limitations = if (positionLimitationsJson.isBlank()) emptyList() else positionLimitationsJson.split(","),
            canAppoint = if (positionCanAppointJson.isBlank()) emptyList() else positionCanAppointJson.split(","),
            canVeto = positionCanVeto,
            canPardon = positionCanPardon,
            canDeclareWar = positionCanDeclareWar,
            canDissolveParliament = positionCanDissolveParliament
        ),
        politicalCapital = politicalCapital,
        approvalRating = approvalRating,
        personalWealth = personalWealth,
        party = party,
        coalitionPartners = if (coalitionPartnersJson.isBlank()) emptyList() else coalitionPartnersJson.split(","),
        opposition = if (oppositionJson.isBlank()) emptyList() else oppositionJson.split(","),
        achievements = emptyList(),
        reputation = emptyMap(),
        traits = emptyList(),
        decisionsHistory = emptyList(),
        currentTermStart = currentTermStart,
        termLength = termLength,
        electionCycle = electionCycle,
        vetoes = vetoes,
        executiveOrders = executiveOrders,
        lawsPassed = lawsPassed,
        treatiesSigned = treatiesSigned,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    
    companion object {
        fun fromDomain(state: PlayerState): PlayerStateEntity = PlayerStateEntity(
            id = state.id,
            name = state.name,
            country = state.country,
            currentMode = state.currentMode.name,
            governmentType = state.governmentType.name,
            positionTitle = state.position.title,
            positionPower = state.position.power,
            positionResponsibilitiesJson = state.position.responsibilities.joinToString(","),
            positionLimitationsJson = state.position.limitations.joinToString(","),
            positionCanAppointJson = state.position.canAppoint.joinToString(","),
            positionCanVeto = state.position.canVeto,
            positionCanPardon = state.position.canPardon,
            positionCanDeclareWar = state.position.canDeclareWar,
            positionCanDissolveParliament = state.position.canDissolveParliament,
            politicalCapital = state.politicalCapital,
            approvalRating = state.approvalRating,
            personalWealth = state.personalWealth,
            party = state.party,
            coalitionPartnersJson = state.coalitionPartners.joinToString(","),
            oppositionJson = state.opposition.joinToString(","),
            achievementsJson = "",
            reputationJson = state.reputation.entries.joinToString(",") { "${it.key.name}:${it.value}" },
            traitsJson = "",
            decisionsHistoryJson = "",
            currentTermStart = state.currentTermStart,
            termLength = state.termLength,
            electionCycle = state.electionCycle,
            vetoes = state.vetoes,
            executiveOrders = state.executiveOrders,
            lawsPassed = state.lawsPassed,
            treatiesSigned = state.treatiesSigned,
            createdAt = state.createdAt,
            updatedAt = state.updatedAt
        )
    }
}

/**
 * Room entity for GameSession.
 */
@Entity(tableName = "game_sessions")
data class GameSessionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val playerId: String,
    val gameDate: Long,
    val realTimeStart: Long,
    val totalPlayTime: Long,
    val version: Int,
    val isAutoSave: Boolean,
    val thumbnailPath: String?,
    val currentYear: Int,
    val currentMonth: Int,
    val currentDay: Int,
    val timeSpeed: String,
    val createdAt: Long
) {
    companion object {
        fun fromDomain(session: GameSession): GameSessionEntity = GameSessionEntity(
            id = session.id,
            name = session.name,
            playerId = session.playerState.id,
            gameDate = session.gameDate,
            realTimeStart = session.realTimeStart,
            totalPlayTime = session.totalPlayTime,
            version = session.version,
            isAutoSave = session.isAutoSave,
            thumbnailPath = session.thumbnailPath,
            currentYear = session.worldState.currentYear,
            currentMonth = session.worldState.currentMonth,
            currentDay = session.worldState.currentDay,
            timeSpeed = session.worldState.timeSpeed.name,
            createdAt = session.realTimeStart
        )
    }
}

/**
 * Room entity for Memory.
 */
@Entity(tableName = "memories")
data class MemoryEntity(
    @PrimaryKey
    val id: String,
    val npcId: String,
    val description: String,
    val relatedEntityId: String,
    val emotionalWeight: Double,
    val importance: Int,
    val category: String,
    val timestamp: Long
) {
    fun toDomain(): Memory = Memory(
        id = id,
        description = description,
        relatedEntityId = relatedEntityId,
        emotionalWeight = emotionalWeight,
        importance = importance,
        category = MemoryCategory.valueOf(category),
        timestamp = timestamp
    )
    
    companion object {
        fun fromDomain(memory: Memory, npcId: String): MemoryEntity = MemoryEntity(
            id = memory.id,
            npcId = npcId,
            description = memory.description,
            relatedEntityId = memory.relatedEntityId,
            emotionalWeight = memory.emotionalWeight,
            importance = memory.importance,
            category = memory.category.name,
            timestamp = memory.timestamp
        )
    }
}
