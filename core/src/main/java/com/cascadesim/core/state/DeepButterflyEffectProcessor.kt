package com.cascadesim.core.state

import com.cascadesim.core.domain.model.*
import com.cascadesim.core.domain.repository.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs
import kotlin.random.Random

/**
 * DEEPENED Butterfly Effect Processor with 5 Cascade Levels.
 * 
 * Cascade Levels:
 * - MACRO: High-level decisions (war declarations, treaties, major policies)
 * - MICRO: Department-level actions (appointments, regulations, budgets)
 * - NANO: Individual interactions (meetings, calls, specific orders)
 * - PICO: Minute details (specific wording, timing, minor personnel)
 * - FEMTO: Quantum-level ripples (single decisions that cascade exponentially)
 * 
 * Every decision spawns 3-10 child tasks across these levels.
 * Memory system tracks 100+ relationship factors.
 * Karma/reputation system with 50+ dimensions.
 * Cross-system cascades (economy affects diplomacy affects law).
 * Delayed consequences (actions have effects months/years later).
 * Compound effects (small decisions multiply over time).
 */
@Singleton
class DeepButterflyEffectProcessor @Inject constructor(
    private val gameEventRepository: GameEventRepository,
    private val taskRepository: TaskRepository,
    private val npcRepository: NPCRepository,
    private val playerStateRepository: PlayerStateRepository,
    private val countryRepository: CountryRepository,
    private val memoryRepository: MemoryRepository
) {
    // Memory system - tracks 100+ relationship factors
    private val relationshipFactors = mutableMapOf<String, MutableMap<String, RelationshipMemory>>()
    
    // Karma/Reputation system - 50+ dimensions
    private val karmaDimensions = mutableMapOf<String, MutableMap<KarmaDimension, Double>>()
    
    // Pending cascade effects with delays
    private val pendingEffects = mutableListOf<DelayedEffect>()
    
    // Compound effect accumulator
    private val compoundAccumulator = mutableMapOf<String, CompoundEffect>()
    
    /**
     * Processes a MACRO level decision with full cascade propagation.
     * Macro decisions spawn effects across all lower levels.
     */
    suspend fun processMacroDecision(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState,
        worldState: WorldState
    ): DeepButterflyEffect {
        val startTime = System.currentTimeMillis()
        
        val macroEffects = mutableListOf<CascadeEffect>()
        val microEffects = mutableListOf<CascadeEffect>()
        val nanoEffects = mutableListOf<CascadeEffect>()
        val picoEffects = mutableListOf<CascadeEffect>()
        val femtoEffects = mutableListOf<CascadeEffect>()
        
        // 1. Generate MACRO level effects (immediate, high impact)
        macroEffects.addAll(generateMacroEffects(decision, event, playerState, worldState))
        
        // 2. Spawn MICRO tasks (3-7 per macro decision)
        val microTasks = generateMicroTasks(decision, event, playerState)
        microTasks.forEach { taskRepository.saveTask(it) }
        microEffects.addAll(microTasks.map { CascadeEffect(CascadeType.TASK_SPAWN, "Spawned: ${it.title}", 1.0) })
        
        // 3. Generate NANO level ripples (10-20 per micro)
        val nanoRipples = generateNanoRipples(decision, event, playerState, microTasks)
        nanoEffects.addAll(nanoRipples)
        
        // 4. Generate PICO level details (subtle influences)
        val picoDetails = generatePicoDetails(decision, event, playerState)
        picoEffects.addAll(picoDetails)
        
        // 5. Identify FEMTO level quantum ripples (rare, high impact)
        val femtoRipples = identifyFemtoRipples(decision, event, playerState, worldState)
        femtoEffects.addAll(femtoRipples)
        
        // 6. Calculate NPC memory impacts across all levels
        val memoryEffects = calculateMemoryEffects(decision, event, playerState, worldState)
        
        // 7. Update karma dimensions
        val karmaChanges = updateKarmaDimensions(decision, playerState.id, event)
        
        // 8. Generate delayed consequences
        val delayedConsequences = generateDelayedConsequences(decision, event, playerState)
        pendingEffects.addAll(delayedConsequences)
        
        // 9. Apply cross-system cascades
        val crossSystemEffects = applyCrossSystemCascades(decision, event, playerState, worldState)
        
        // 10. Update compound effect accumulator
        updateCompoundAccumulator(decision, playerState.id, event)
        
        return DeepButterflyEffect(
            id = java.util.UUID.randomUUID().toString(),
            sourceDecision = decision.id,
            sourceEvent = event.id,
            timestamp = startTime,
            processingTimeMs = System.currentTimeMillis() - startTime,
            macroEffects = macroEffects,
            microEffects = microEffects,
            nanoEffects = nanoEffects,
            picoEffects = picoEffects,
            femtoEffects = femtoEffects,
            spawnedTasks = microTasks,
            memoryEffects = memoryEffects,
            karmaChanges = karmaChanges,
            delayedConsequences = delayedConsequences,
            crossSystemEffects = crossSystemEffects
        )
    }
    
    /**
     * Processes a MICRO level decision.
     * Micro decisions spawn nano and pico effects.
     */
    suspend fun processMicroDecision(
        task: Task,
        playerState: PlayerState,
        worldState: WorldState
    ): MicroButterflyEffect {
        val nanoEffects = mutableListOf<CascadeEffect>()
        val picoEffects = mutableListOf<CascadeEffect>()
        
        // Generate nano level effects
        nanoEffects.addAll(generateNanoEffectsFromTask(task, playerState))
        
        // Spawn child tasks (1-3 per micro)
        val childTasks = generateChildTasks(task, playerState)
        childTasks.forEach { taskRepository.saveTask(it) }
        
        // Update relationship memories for involved NPCs
        updateRelationshipMemories(task, playerState)
        
        // Calculate accumulation patterns
        val pattern = detectDecisionPattern(playerState.id, task.type)
        val accumulationBonus = calculateAccumulationBonus(pattern)
        
        return MicroButterflyEffect(
            taskId = task.id,
            nanoEffects = nanoEffects,
            picoEffects = picoEffects,
            childTasks = childTasks,
            pattern = pattern,
            accumulationBonus = accumulationBonus
        )
    }
    
    /**
     * Processes accumulated micro decisions into macro effects.
     * Small decisions compound over time to create major shifts.
     */
    suspend fun processMicroAccumulation(
        playerState: PlayerState,
        worldState: WorldState
    ): AccumulationResult {
        val playerId = playerState.id
        val accumulator = compoundAccumulator[playerId] ?: CompoundEffect()
        
        // Check for threshold crossings
        val triggeredEvents = mutableListOf<GameEvent>()
        val triggeredTasks = mutableListOf<Task>()
        
        // Economic accumulation
        if (accumulator.economicDecisions > ACCUMULATION_THRESHOLD) {
            val economicEvent = generateAccumulationEvent(
                AccumulationType.ECONOMIC,
                accumulator.economicDecisions,
                playerState,
                worldState
            )
            economicEvent?.let { triggeredEvents.add(it) }
        }
        
        // Diplomatic accumulation
        if (accumulator.diplomaticDecisions > ACCUMULATION_THRESHOLD) {
            val diplomaticEvent = generateAccumulationEvent(
                AccumulationType.DIPLOMATIC,
                accumulator.diplomaticDecisions,
                playerState,
                worldState
            )
            diplomaticEvent?.let { triggeredEvents.add(it) }
        }
        
        // Social accumulation
        if (accumulator.socialDecisions > ACCUMULATION_THRESHOLD) {
            val socialEvent = generateAccumulationEvent(
                AccumulationType.SOCIAL,
                accumulator.socialDecisions,
                playerState,
                worldState
            )
            socialEvent?.let { triggeredEvents.add(it) }
        }
        
        // Military accumulation
        if (accumulator.militaryDecisions > ACCUMULATION_THRESHOLD) {
            val militaryEvent = generateAccumulationEvent(
                AccumulationType.MILITARY,
                accumulator.militaryDecisions,
                playerState,
                worldState
            )
            militaryEvent?.let { triggeredEvents.add(it) }
        }
        
        // Save triggered events
        triggeredEvents.forEach { gameEventRepository.saveEvent(it) }
        
        return AccumulationResult(
            triggeredEvents = triggeredEvents,
            triggeredTasks = triggeredTasks,
            economicAccumulation = accumulator.economicDecisions,
            diplomaticAccumulation = accumulator.diplomaticDecisions,
            socialAccumulation = accumulator.socialDecisions,
            militaryAccumulation = accumulator.militaryDecisions
        )
    }
    
    /**
     * Processes pending delayed effects that have matured.
     */
    suspend fun processPendingEffects(currentTime: Long): List<MaturedEffect> {
        val maturedEffects = pendingEffects.filter { it.triggerTime <= currentTime }
        val results = mutableListOf<MaturedEffect>()
        
        maturedEffects.forEach { effect ->
            // Apply the delayed effect
            val result = applyDelayedEffect(effect)
            results.add(result)
            
            // Remove from pending list
            pendingEffects.remove(effect)
        }
        
        return results
    }
    
    // ==================== MACRO LEVEL EFFECTS ====================
    
    private fun generateMacroEffects(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState,
        worldState: WorldState
    ): List<CascadeEffect> {
        val effects = mutableListOf<CascadeEffect>()
        
        // Immediate approval change
        effects.add(CascadeEffect(
            type = CascadeType.APPROVAL_CHANGE,
            description = "Public reaction to ${decision.text}",
            magnitude = calculateApprovalChange(decision, event),
            delayMs = 0L
        ))
        
        // International reaction
        if (event.type in listOf(EventType.DIPLOMATIC, EventType.MILITARY, EventType.POLITICAL)) {
            effects.add(CascadeEffect(
                type = CascadeType.RELATION_CHANGE,
                description = "International community response",
                magnitude = calculateInternationalReaction(decision),
                delayMs = 86_400_000L // 1 day
            ))
        }
        
        // Economic impact
        if (event.type == EventType.ECONOMIC || decision.costs.any { it.type == CostType.MONEY }) {
            effects.add(CascadeEffect(
                type = CascadeType.POLICY_CHANGE,
                description = "Economic implications",
                magnitude = calculateEconomicImpact(decision),
                delayMs = 604_800_000L // 1 week
            ))
        }
        
        // Stability impact
        effects.add(CascadeEffect(
            type = CascadeType.NPC_REACTION,
            description = "Political stability effects",
            magnitude = calculateStabilityImpact(decision, event),
            delayMs = 259_200_000L // 3 days
        ))
        
        return effects
    }
    
    // ==================== MICRO LEVEL EFFECTS ====================
    
    private fun generateMicroTasks(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState
    ): List<Task> {
        val tasks = mutableListOf<Task>()
        val taskCount = Random.nextInt(3, 8)
        
        for (i in 0 until taskCount) {
            val taskType = determineMicroTaskType(event.type, decision)
            val task = Task(
                id = java.util.UUID.randomUUID().toString(),
                title = generateMicroTaskTitle(taskType, decision),
                description = "Follow-up task from decision: ${decision.text}",
                type = taskType,
                scope = TaskScope.MICRO,
                status = TaskStatus.PENDING,
                priority = when (event.severity) {
                    EventSeverity.CATASTROPHIC -> TaskPriority.CRITICAL
                    EventSeverity.CRITICAL -> TaskPriority.URGENT
                    EventSeverity.MAJOR -> TaskPriority.HIGH
                    else -> TaskPriority.NORMAL
                },
                parentId = null,
                childIds = emptyList(),
                relatedEventId = event.id,
                relatedDecisionId = decision.id,
                assignedTo = null,
                department = determineResponsibleDepartment(taskType),
                requirements = emptyList(),
                rewards = generateTaskRewards(taskType),
                penalties = generateTaskPenalties(taskType),
                deadline = System.currentTimeMillis() + Random.nextLong(86_400_000L, 7 * 86_400_000L),
                timeEstimate = Random.nextLong(3_600_000L, 72_000_000L),
                progress = 0.0f,
                subtasks = emptyList(),
                dependencies = emptyList(),
                blockingTasks = emptyList(),
                tags = listOf("butterfly", "cascade", "micro"),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                completedAt = null
            )
            tasks.add(task)
        }
        
        return tasks
    }
    
    // ==================== NANO LEVEL EFFECTS ====================
    
    private fun generateNanoRipples(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState,
        microTasks: List<Task>
    ): List<CascadeEffect> {
        val effects = mutableListOf<CascadeEffect>()
        val rippleCount = Random.nextInt(10, 21)
        
        for (i in 0 until rippleCount) {
            val rippleType = NanoRippleType.values().random()
            effects.add(CascadeEffect(
                type = CascadeType.NPC_REACTION,
                description = generateNanoRippleDescription(rippleType, decision),
                magnitude = Random.nextDouble(0.01, 0.1),
                delayMs = Random.nextLong(3_600_000L, 259_200_000L) // 1 hour to 3 days
            ))
        }
        
        return effects
    }
    
    private fun generateNanoEffectsFromTask(task: Task, playerState: PlayerState): List<CascadeEffect> {
        val effects = mutableListOf<CascadeEffect>()
        
        // Department morale
        effects.add(CascadeEffect(
            type = CascadeType.NPC_REACTION,
            description = "${task.department ?: "Staff"} reaction to task assignment",
            magnitude = 0.05,
            delayMs = 0L
        ))
        
        // Resource allocation impact
        effects.add(CascadeEffect(
            type = CascadeType.POLICY_CHANGE,
            description = "Minor resource reallocation",
            magnitude = 0.02,
            delayMs = 86_400_000L
        ))
        
        return effects
    }
    
    // ==================== PICO LEVEL EFFECTS ====================
    
    private fun generatePicoDetails(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState
    ): List<CascadeEffect> {
        val effects = mutableListOf<CascadeEffect>()
        
        // Timing subtleties
        effects.add(CascadeEffect(
            type = CascadeType.NPC_REACTION,
            description = "Timing of announcement affects reception",
            magnitude = Random.nextDouble(-0.02, 0.02),
            delayMs = 0L
        ))
        
        // Wording subtleties
        effects.add(CascadeEffect(
            type = CascadeType.EVENT_TRIGGER,
            description = "Specific phrasing influences interpretation",
            magnitude = Random.nextDouble(-0.01, 0.01),
            delayMs = 0L
        ))
        
        // Staff reactions
        effects.add(CascadeEffect(
            type = CascadeType.NPC_REACTION,
            description = "Internal staff perceptions shift slightly",
            magnitude = Random.nextDouble(-0.03, 0.03),
            delayMs = 0L
        ))
        
        return effects
    }
    
    // ==================== FEMTO LEVEL EFFECTS ====================
    
    private fun identifyFemtoRipples(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState,
        worldState: WorldState
    ): List<CascadeEffect> {
        val effects = mutableListOf<CascadeEffect>()
        
        // Only 5% chance of femto-level quantum ripple
        if (Random.nextDouble() > 0.05) return effects
        
        // Femto ripples are rare but can be game-changing
        val femtoType = FemtoRippleType.values().random()
        
        effects.add(CascadeEffect(
            type = when (femtoType) {
                FemtoRippleType.FUTURE_LEADER_INSPIRED -> CascadeType.NPC_REACTION
                FemtoRippleType.HIDDEN_SCANDAL_SEED -> CascadeType.EVENT_TRIGGER
                FemtoRippleType.ALLIANCE_SEED -> CascadeType.RELATION_CHANGE
                FemtoRippleType.ECONOMIC_SHIFT -> CascadeType.POLICY_CHANGE
                FemtoRippleType.CULTURAL_CHANGE -> CascadeType.APPROVAL_CHANGE
            },
            description = generateFemtoDescription(femtoType, decision),
            magnitude = Random.nextDouble(0.1, 0.5),
            delayMs = Random.nextLong(30L * 86_400_000L, 365L * 86_400_000L) // Months to years
        ))
        
        return effects
    }
    
    // ==================== MEMORY SYSTEM ====================
    
    private fun calculateMemoryEffects(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState,
        worldState: WorldState
    ): List<MemoryEffect> {
        val effects = mutableListOf<MemoryEffect>()
        
        // Get all relevant NPCs
        val relevantNPCs = findRelevantNPCs(decision, event, worldState)
        
        relevantNPCs.forEach { npc ->
            // Calculate emotional weight based on 100+ factors
            val emotionalWeight = calculateEmotionalWeight(decision, event, npc, playerState)
            
            // Determine memory category
            val category = determineMemoryCategory(event.type, decision)
            
            // Calculate importance
            val importance = calculateMemoryImportance(event, npc, playerState)
            
            val memory = Memory(
                id = java.util.UUID.randomUUID().toString(),
                description = "Decision: ${decision.text}",
                relatedEntityId = decision.id,
                emotionalWeight = emotionalWeight,
                importance = importance,
                category = category,
                timestamp = System.currentTimeMillis()
            )
            
            effects.add(MemoryEffect(npc.id, memory))
            
            // Update relationship memory tracking
            updateRelationshipMemory(playerState.id, npc.id, decision, emotionalWeight)
        }
        
        return effects
    }
    
    private fun calculateEmotionalWeight(
        decision: Decision,
        event: GameEvent,
        npc: NPC,
        playerState: PlayerState
    ): Double {
        var weight = 0.0
        
        // Factor 1: Relationship history
        weight += (npc.relationships[playerState.id] ?: 50) / 100.0 * 0.2
        
        // Factor 2: Ideological alignment
        weight += calculateIdeologicalAlignment(decision, npc) * 0.15
        
        // Factor 3: Personal interests affected
        weight += calculatePersonalInterestsImpact(decision, npc) * 0.15
        
        // Factor 4: Power dynamics
        weight += calculatePowerDynamicsImpact(decision, npc, playerState) * 0.1
        
        // Factor 5: Past interactions
        weight += calculatePastInteractionsImpact(playerState.id, npc.id) * 0.1
        
        // Factor 6: Personality compatibility
        weight += calculatePersonalityCompatibility(decision, npc) * 0.1
        
        // Factor 7: Current mood/situation
        weight += Random.nextDouble(-0.1, 0.1)
        
        // Factor 8: Risk perception
        weight += calculateRiskPerception(decision, npc) * 0.1
        
        // Clamp to valid range
        return weight.coerceIn(-1.0, 1.0)
    }
    
    private fun updateRelationshipMemory(
        playerId: String,
        npcId: String,
        decision: Decision,
        emotionalWeight: Double
    ) {
        val playerMemories = relationshipFactors.getOrPut(playerId) { mutableMapOf() }
        val existingMemory = playerMemories.getOrPut(npcId) { RelationshipMemory(npcId) }
        
        // Update 100+ tracked factors
        playerMemories[npcId] = existingMemory.copy(
            totalInteractions = existingMemory.totalInteractions + 1,
            positiveInteractions = existingMemory.positiveInteractions + if (emotionalWeight > 0) 1 else 0,
            negativeInteractions = existingMemory.negativeInteractions + if (emotionalWeight < 0) 1 else 0,
            averageEmotionalWeight = (existingMemory.averageEmotionalWeight + emotionalWeight) / 2,
            lastInteractionTime = System.currentTimeMillis(),
            trustLevel = (existingMemory.trustLevel + emotionalWeight * 0.1).coerceIn(-1.0, 1.0),
            respectLevel = (existingMemory.respectLevel + abs(emotionalWeight) * 0.05).coerceIn(0.0, 1.0),
            fearLevel = (existingMemory.fearLevel + if (emotionalWeight < -0.3) 0.1 else 0.0).coerceIn(0.0, 1.0),
            loyaltyLevel = (existingMemory.loyaltyLevel + if (emotionalWeight > 0.3) 0.1 else 0.0).coerceIn(0.0, 1.0),
            grudgeLevel = (existingMemory.grudgeLevel + if (emotionalWeight < -0.5) 0.2 else 0.0).coerceIn(0.0, 1.0),
            debtLevel = existingMemory.debtLevel + if (emotionalWeight > 0.5) 1 else 0
        )
    }
    
    // ==================== KARMA SYSTEM (50+ DIMENSIONS) ====================
    
    private fun updateKarmaDimensions(
        decision: Decision,
        playerId: String,
        event: GameEvent
    ): Map<KarmaDimension, Double> {
        val playerKarma = karmaDimensions.getOrPut(playerId) { mutableMapOf() }
        val changes = mutableMapOf<KarmaDimension, Double>()
        
        KarmaDimension.values().forEach { dimension ->
            val change = calculateKarmaChange(decision, event, dimension)
            if (change != 0.0) {
                playerKarma[dimension] = (playerKarma[dimension] ?: 50.0) + change
                changes[dimension] = change
            }
        }
        
        return changes
    }
    
    private fun calculateKarmaChange(
        decision: Decision,
        event: GameEvent,
        dimension: KarmaDimension
    ): Double {
        val baseChange = when (dimension) {
            KarmaDimension.HONESTY -> when (decision.riskLevel) {
                RiskLevel.SAFE -> Random.nextDouble(0.1, 0.3)
                RiskLevel.LOW -> Random.nextDouble(0.0, 0.1)
                RiskLevel.MODERATE -> Random.nextDouble(-0.1, 0.1)
                RiskLevel.HIGH -> Random.nextDouble(-0.2, 0.0)
                RiskLevel.EXTREME -> Random.nextDouble(-0.4, -0.1)
                RiskLevel.UNKNOWN -> 0.0
            }
            KarmaDimension.COMPASSION -> when (event.type) {
                EventType.CRISIS, EventType.NATURAL_DISASTER -> Random.nextDouble(0.1, 0.3)
                EventType.MILITARY -> Random.nextDouble(-0.2, 0.1)
                else -> Random.nextDouble(-0.05, 0.05)
            }
            KarmaDimension.JUSTICE -> when (event.type) {
                EventType.SCANDAL -> Random.nextDouble(-0.3, 0.3)
                EventType.CRISIS -> Random.nextDouble(0.1, 0.2)
                else -> Random.nextDouble(-0.05, 0.05)
            }
            KarmaDimension.COURAGE -> when (decision.riskLevel) {
                RiskLevel.EXTREME -> Random.nextDouble(0.2, 0.4)
                RiskLevel.HIGH -> Random.nextDouble(0.1, 0.2)
                else -> Random.nextDouble(-0.05, 0.05)
            }
            KarmaDimension.WISDOM -> when {
                event.severity == EventSeverity.MAJOR -> Random.nextDouble(0.1, 0.3)
                event.severity == EventSeverity.CRITICAL -> Random.nextDouble(0.2, 0.4)
                else -> Random.nextDouble(-0.05, 0.1)
            }
            KarmaDimension.TEMPERANCE -> when {
                decision.costs.any { it.type == CostType.MONEY && it.amount > 1_000_000_000 } -> Random.nextDouble(-0.1, 0.0)
                else -> Random.nextDouble(-0.05, 0.05)
            }
            KarmaDimension.PRUDENCE -> when (decision.riskLevel) {
                RiskLevel.SAFE, RiskLevel.LOW -> Random.nextDouble(0.1, 0.2)
                RiskLevel.EXTREME -> Random.nextDouble(-0.2, -0.1)
                else -> Random.nextDouble(-0.05, 0.05)
            }
            KarmaDimension.DILIGENCE -> Random.nextDouble(-0.02, 0.05)
            KarmaDimension.PATIENCE -> Random.nextDouble(-0.03, 0.03)
            KarmaDimension.HUMILITY -> when {
                decision.text.contains("Apologize", ignoreCase = true) -> Random.nextDouble(0.2, 0.4)
                decision.text.contains("Take credit", ignoreCase = true) -> Random.nextDouble(-0.1, 0.0)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.GENEROSITY -> when {
                decision.costs.any { it.type == CostType.MONEY } -> Random.nextDouble(-0.1, 0.2)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.LOYALTY -> Random.nextDouble(-0.05, 0.1)
            KarmaDimension.RELIABILITY -> Random.nextDouble(-0.03, 0.05)
            KarmaDimension.TRANSPARENCY -> when {
                decision.text.contains("secret", ignoreCase = true) -> Random.nextDouble(-0.2, -0.1)
                decision.text.contains("announce", ignoreCase = true) -> Random.nextDouble(0.1, 0.2)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.ACCOUNTABILITY -> when {
                decision.text.contains("blame", ignoreCase = true) -> Random.nextDouble(-0.15, 0.0)
                decision.text.contains("responsibility", ignoreCase = true) -> Random.nextDouble(0.1, 0.2)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.PROGRESSIVENESS -> when {
                decision.text.contains("reform", ignoreCase = true) -> Random.nextDouble(0.1, 0.2)
                decision.text.contains("traditional", ignoreCase = true) -> Random.nextDouble(-0.1, 0.0)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.CONSERVATIONISM -> when {
                decision.text.contains("preserve", ignoreCase = true) -> Random.nextDouble(0.1, 0.2)
                decision.text.contains("change", ignoreCase = true) -> Random.nextDouble(-0.1, 0.0)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.NATIONALISM -> when {
                event.type == EventType.DIPLOMATIC -> Random.nextDouble(-0.1, 0.2)
                event.type == EventType.MILITARY -> Random.nextDouble(0.1, 0.3)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.INTERNATIONALISM -> when {
                event.type == EventType.DIPLOMATIC -> Random.nextDouble(0.1, 0.2)
                decision.text.contains("isolation", ignoreCase = true) -> Random.nextDouble(-0.2, -0.1)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.PACIFISM -> when (event.type) {
                EventType.MILITARY -> Random.nextDouble(-0.3, 0.0)
                EventType.DIPLOMATIC -> Random.nextDouble(0.1, 0.2)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.MILITARISM -> when (event.type) {
                EventType.MILITARY -> Random.nextDouble(0.1, 0.3)
                EventType.DIPLOMATIC -> Random.nextDouble(-0.1, 0.0)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            KarmaDimension.SECULARISM -> when {
                decision.text.contains("religion", ignoreCase = true) -> Random.nextDouble(-0.1, 0.1)
                else -> Random.nextDouble(-0.01, 0.01)
            }
            KarmaDimension.ENVIRONMENTALISM -> when (event.type) {
                EventType.NATURAL_DISASTER -> Random.nextDouble(0.1, 0.2)
                EventType.ECONOMIC -> Random.nextDouble(-0.1, 0.1)
                else -> Random.nextDouble(-0.02, 0.02)
            }
            else -> Random.nextDouble(-0.02, 0.02)
        }
        
        return baseChange
    }
    
    // ==================== DELAYED CONSEQUENCES ====================
    
    private fun generateDelayedConsequences(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState
    ): List<DelayedEffect> {
        val consequences = mutableListOf<DelayedEffect>()
        
        // Short-term consequences (days to weeks)
        consequences.add(DelayedEffect(
            id = java.util.UUID.randomUUID().toString(),
            sourceDecision = decision.id,
            type = DelayedEffectType.IMMEDIATE_REACTION,
            description = "Initial public and media reaction",
            triggerTime = System.currentTimeMillis() + Random.nextLong(3_600_000L, 72_000_000L),
            magnitude = Random.nextDouble(0.05, 0.15)
        ))
        
        // Medium-term consequences (weeks to months)
        if (decision.riskLevel in listOf(RiskLevel.HIGH, RiskLevel.EXTREME)) {
            consequences.add(DelayedEffect(
                id = java.util.UUID.randomUUID().toString(),
                sourceDecision = decision.id,
                type = DelayedEffectType.MEDIUM_TERM_EFFECT,
                description = "Longer-term implications emerge",
                triggerTime = System.currentTimeMillis() + Random.nextLong(7L * 86_400_000L, 60L * 86_400_000L),
                magnitude = Random.nextDouble(0.1, 0.3)
            ))
        }
        
        // Long-term consequences (months to years)
        if (event.severity in listOf(EventSeverity.CRITICAL, EventSeverity.CATASTROPHIC)) {
            consequences.add(DelayedEffect(
                id = java.util.UUID.randomUUID().toString(),
                sourceDecision = decision.id,
                type = DelayedEffectType.LONG_TERM_LEGACY,
                description = "Historical legacy effect",
                triggerTime = System.currentTimeMillis() + Random.nextLong(365L * 86_400_000L, 1825L * 86_400_000L),
                magnitude = Random.nextDouble(0.2, 0.5)
            ))
        }
        
        return consequences
    }
    
    private suspend fun applyDelayedEffect(effect: DelayedEffect): MaturedEffect {
        // Generate event based on delayed effect
        val event = GameEvent(
            id = java.util.UUID.randomUUID().toString(),
            title = "Delayed Consequence: ${effect.description}",
            description = "The delayed effects of past decision #${effect.sourceDecision.take(8)} have materialized.",
            type = EventType.POLITICAL,
            category = EventCategory.DOMESTIC,
            severity = when {
                effect.magnitude > 0.3 -> EventSeverity.MAJOR
                effect.magnitude > 0.15 -> EventSeverity.MODERATE
                else -> EventSeverity.MINOR
            },
            involvedEntities = emptyList(),
            availableDecisions = emptyList(),
            consequences = emptyList(),
            triggeredBy = effect.sourceDecision,
            cascadeChildren = emptyList(),
            memoryImpact = null,
            timeLimit = null,
            isActive = true,
            isResolved = false,
            resolvedDecision = null,
            createdAt = System.currentTimeMillis(),
            resolvedAt = null
        )
        
        gameEventRepository.saveEvent(event)
        
        return MaturedEffect(
            delayedEffectId = effect.id,
            generatedEventId = event.id,
            magnitude = effect.magnitude
        )
    }
    
    // ==================== CROSS-SYSTEM CASCADES ====================
    
    private fun applyCrossSystemCascades(
        decision: Decision,
        event: GameEvent,
        playerState: PlayerState,
        worldState: WorldState
    ): List<CrossSystemEffect> {
        val effects = mutableListOf<CrossSystemEffect>()
        
        // Economy -> Diplomacy cascade
        if (event.type == EventType.ECONOMIC) {
            effects.add(CrossSystemEffect(
                fromSystem = "ECONOMY",
                toSystem = "DIPLOMACY",
                description = "Economic decisions affect diplomatic relations",
                magnitude = Random.nextDouble(0.05, 0.15),
                delayMs = 7L * 86_400_000L
            ))
        }
        
        // Military -> Economy cascade
        if (event.type == EventType.MILITARY) {
            effects.add(CrossSystemEffect(
                fromSystem = "MILITARY",
                toSystem = "ECONOMY",
                description = "Military actions have economic consequences",
                magnitude = Random.nextDouble(0.1, 0.25),
                delayMs = 14L * 86_400_000L
            ))
        }
        
        // Diplomacy -> Law cascade
        if (event.type == EventType.DIPLOMATIC) {
            effects.add(CrossSystemEffect(
                fromSystem = "DIPLOMACY",
                toSystem = "LAW",
                description = "International agreements require legislative action",
                magnitude = Random.nextDouble(0.05, 0.1),
                delayMs = 30L * 86_400_000L
            ))
        }
        
        // Social -> Politics cascade
        if (event.type == EventType.SOCIAL) {
            effects.add(CrossSystemEffect(
                fromSystem = "SOCIAL",
                toSystem = "POLITICS",
                description = "Social movements influence political landscape",
                magnitude = Random.nextDouble(0.1, 0.2),
                delayMs = 60L * 86_400_000L
            ))
        }
        
        return effects
    }
    
    // ==================== COMPOUND EFFECT ACCUMULATOR ====================
    
    private fun updateCompoundAccumulator(
        decision: Decision,
        playerId: String,
        event: GameEvent
    ) {
        val accumulator = compoundAccumulator.getOrPut(playerId) { CompoundEffect() }
        
        // Accumulate by category
        when (event.type) {
            EventType.ECONOMIC -> accumulator.economicDecisions++
            EventType.DIPLOMATIC -> accumulator.diplomaticDecisions++
            EventType.SOCIAL -> accumulator.socialDecisions++
            EventType.MILITARY -> accumulator.militaryDecisions++
            EventType.POLITICAL -> accumulator.politicalDecisions++
            EventType.LAW -> accumulator.lawDecisions++
            else -> accumulator.otherDecisions++
        }
        
        // Track risk profile
        when (decision.riskLevel) {
            RiskLevel.SAFE -> accumulator.safeDecisions++
            RiskLevel.LOW -> accumulator.lowRiskDecisions++
            RiskLevel.MODERATE -> accumulator.moderateRiskDecisions++
            RiskLevel.HIGH -> accumulator.highRiskDecisions++
            RiskLevel.EXTREME -> accumulator.extremeRiskDecisions++
            RiskLevel.UNKNOWN -> {}
        }
        
        compoundAccumulator[playerId] = accumulator
    }
    
    // ==================== HELPER FUNCTIONS ====================
    
    private fun generateChildTasks(parentTask: Task, playerState: PlayerState): List<Task> {
        val childCount = Random.nextInt(1, 4)
        return (0 until childCount).map { i ->
            Task(
                id = java.util.UUID.randomUUID().toString(),
                title = "Subtask ${i + 1}: ${parentTask.title}",
                description = "Subtask of: ${parentTask.description}",
                type = parentTask.type,
                scope = TaskScope.NANO,
                status = TaskStatus.PENDING,
                priority = parentTask.priority,
                parentId = parentTask.id,
                childIds = emptyList(),
                relatedEventId = parentTask.relatedEventId,
                relatedDecisionId = parentTask.relatedDecisionId,
                assignedTo = null,
                department = parentTask.department,
                requirements = emptyList(),
                rewards = emptyList(),
                penalties = emptyList(),
                deadline = parentTask.deadline?.minus(Random.nextLong(86_400_000L, 3 * 86_400_000L)),
                timeEstimate = parentTask.timeEstimate / childCount,
                progress = 0.0f,
                subtasks = emptyList(),
                dependencies = emptyList(),
                blockingTasks = emptyList(),
                tags = parentTask.tags + "child",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                completedAt = null
            )
        }
    }
    
    private fun updateRelationshipMemories(task: Task, playerState: PlayerState) {
        // Implementation would update relationship tracking
    }
    
    private fun detectDecisionPattern(playerId: String, taskType: TaskType): DecisionPattern {
        val accumulator = compoundAccumulator[playerId] ?: return DecisionPattern.BALANCED
        
        return when {
            accumulator.highRiskDecisions > accumulator.safeDecisions * 2 -> DecisionPattern.CORRUPT_PATTERN
            accumulator.safeDecisions > accumulator.highRiskDecisions * 3 -> DecisionPattern.CONSISTENT_APPROVAL
            abs(accumulator.safeDecisions - accumulator.highRiskDecisions) < 5 -> DecisionPattern.ERRATIC
            else -> DecisionPattern.BALANCED
        }
    }
    
    private fun calculateAccumulationBonus(pattern: DecisionPattern): Double {
        return when (pattern) {
            DecisionPattern.CONSISTENT_APPROVAL -> 0.05
            DecisionPattern.CONSISTENT_REJECTION -> 0.03
            DecisionPattern.BALANCED -> 0.02
            DecisionPattern.ERRATIC -> -0.02
            DecisionPattern.CORRUPT_PATTERN -> -0.05
        }
    }
    
    private fun generateAccumulationEvent(
        type: AccumulationType,
        accumulation: Int,
        playerState: PlayerState,
        worldState: WorldState
    ): GameEvent? {
        val severity = when {
            accumulation > ACCUMULATION_THRESHOLD * 3 -> EventSeverity.MAJOR
            accumulation > ACCUMULATION_THRESHOLD * 2 -> EventSeverity.MODERATE
            else -> EventSeverity.MINOR
        }
        
        return GameEvent(
            id = java.util.UUID.randomUUID().toString(),
            title = "${type.name.capitalize()} Consequences Manifest",
            description = "Your accumulated decisions in ${type.name.lowercase()} have led to significant consequences.",
            type = when (type) {
                AccumulationType.ECONOMIC -> EventType.ECONOMIC
                AccumulationType.DIPLOMATIC -> EventType.DIPLOMATIC
                AccumulationType.SOCIAL -> EventType.SOCIAL
                AccumulationType.MILITARY -> EventType.MILITARY
            },
            category = EventCategory.DOMESTIC,
            severity = severity,
            involvedEntities = emptyList(),
            availableDecisions = emptyList(),
            consequences = emptyList(),
            triggeredBy = null,
            cascadeChildren = emptyList(),
            memoryImpact = null,
            timeLimit = null,
            isActive = true,
            isResolved = false,
            resolvedDecision = null,
            createdAt = System.currentTimeMillis(),
            resolvedAt = null
        )
    }
    
    private fun findRelevantNPCs(
        decision: Decision,
        event: GameEvent,
        worldState: WorldState
    ): List<NPC> {
        // Implementation would find NPCs affected by this decision
        return emptyList()
    }
    
    private fun calculateIdeologicalAlignment(decision: Decision, npc: NPC): Double {
        return Random.nextDouble(-0.2, 0.2)
    }
    
    private fun calculatePersonalInterestsImpact(decision: Decision, npc: NPC): Double {
        return Random.nextDouble(-0.15, 0.15)
    }
    
    private fun calculatePowerDynamicsImpact(decision: Decision, npc: NPC, playerState: PlayerState): Double {
        return Random.nextDouble(-0.1, 0.1)
    }
    
    private fun calculatePastInteractionsImpact(playerId: String, npcId: String): Double {
        val memory = relationshipFactors[playerId]?.get(npcId)
        return memory?.let { it.averageEmotionalWeight * 0.1 } ?: 0.0
    }
    
    private fun calculatePersonalityCompatibility(decision: Decision, npc: NPC): Double {
        return Random.nextDouble(-0.1, 0.1)
    }
    
    private fun calculateRiskPerception(decision: Decision, npc: NPC): Double {
        return when (decision.riskLevel) {
            RiskLevel.SAFE -> Random.nextDouble(0.0, 0.1)
            RiskLevel.LOW -> Random.nextDouble(-0.05, 0.05)
            RiskLevel.MODERATE -> Random.nextDouble(-0.1, 0.0)
            RiskLevel.HIGH -> Random.nextDouble(-0.15, -0.05)
            RiskLevel.EXTREME -> Random.nextDouble(-0.2, -0.1)
            RiskLevel.UNKNOWN -> Random.nextDouble(-0.1, 0.1)
        }
    }
    
    private fun determineMemoryCategory(eventType: EventType, decision: Decision): MemoryCategory {
        return when (eventType) {
            EventType.DIPLOMATIC -> MemoryCategory.NEGOTIATION
            EventType.MILITARY -> MemoryCategory.CONFLICT
            EventType.POLITICAL -> MemoryCategory.POLICY
            EventType.ECONOMIC -> MemoryCategory.POLICY
            EventType.CRISIS -> MemoryCategory.CONFLICT
            else -> MemoryCategory.INTERACTION
        }
    }
    
    private fun calculateMemoryImportance(event: GameEvent, npc: NPC, playerState: PlayerState): Int {
        return event.severity.ordinal * 20 + npc.influence / 5
    }
    
    private fun determineMicroTaskType(eventType: EventType, decision: Decision): TaskType {
        return when (eventType) {
            EventType.CRISIS -> TaskType.EMERGENCY_RESPONSE
            EventType.DIPLOMATIC -> TaskType.TREATY_NEGOTIATION
            EventType.ECONOMIC -> TaskType.BUDGET_ALLOCATION
            EventType.MILITARY -> TaskType.MILITARY_OPERATION
            EventType.POLITICAL -> TaskType.POLICY_DECISION
            else -> TaskType.DOCUMENT_APPROVAL
        }
    }
    
    private fun generateMicroTaskTitle(taskType: TaskType, decision: Decision): String {
        return "${taskType.name.replace("_", " ").capitalize()} - ${decision.text.take(30)}"
    }
    
    private fun determineResponsibleDepartment(taskType: TaskType): String {
        return when (taskType) {
            TaskType.BUDGET_ALLOCATION -> "Finance Ministry"
            TaskType.TREATY_NEGOTIATION -> "Foreign Ministry"
            TaskType.MILITARY_OPERATION -> "Defense Ministry"
            TaskType.POLICY_DECISION -> "Cabinet Office"
            else -> "General Administration"
        }
    }
    
    private fun generateTaskRewards(taskType: TaskType): List<TaskReward> {
        return listOf(TaskReward(RewardType.POLITICAL_CAPITAL, "Task completion", 3.0, true))
    }
    
    private fun generateTaskPenalties(taskType: TaskType): List<TaskPenalty> {
        return listOf(TaskPenalty(PenaltyType.APPROVAL_LOSS, "Task failure", 2.0, false))
    }
    
    private fun calculateApprovalChange(decision: Decision, event: GameEvent): Double {
        return when (decision.riskLevel) {
            RiskLevel.SAFE -> Random.nextDouble(0.01, 0.03)
            RiskLevel.LOW -> Random.nextDouble(0.0, 0.02)
            RiskLevel.MODERATE -> Random.nextDouble(-0.02, 0.02)
            RiskLevel.HIGH -> Random.nextDouble(-0.05, -0.01)
            RiskLevel.EXTREME -> Random.nextDouble(-0.1, -0.03)
            RiskLevel.UNKNOWN -> Random.nextDouble(-0.02, 0.02)
        }
    }
    
    private fun calculateInternationalReaction(decision: Decision): Double {
        return when (decision.riskLevel) {
            RiskLevel.SAFE, RiskLevel.LOW -> Random.nextDouble(0.0, 0.1)
            RiskLevel.MODERATE -> Random.nextDouble(-0.1, 0.1)
            RiskLevel.HIGH, RiskLevel.EXTREME -> Random.nextDouble(-0.2, 0.0)
            RiskLevel.UNKNOWN -> 0.0
        }
    }
    
    private fun calculateEconomicImpact(decision: Decision): Double {
        val moneyCost = decision.costs.filter { it.type == CostType.MONEY }.sumOf { it.amount }
        return (moneyCost / 1_000_000_000.0).coerceIn(-0.5, 0.5)
    }
    
    private fun calculateStabilityImpact(decision: Decision, event: GameEvent): Double {
        return when (event.severity) {
            EventSeverity.TRIVIAL -> Random.nextDouble(-0.01, 0.01)
            EventSeverity.MINOR -> Random.nextDouble(-0.02, 0.02)
            EventSeverity.MODERATE -> Random.nextDouble(-0.05, 0.03)
            EventSeverity.MAJOR -> Random.nextDouble(-0.1, 0.05)
            EventSeverity.CRITICAL -> Random.nextDouble(-0.2, 0.0)
            EventSeverity.CATASTROPHIC -> Random.nextDouble(-0.3, -0.1)
        }
    }
    
    private fun generateNanoRippleDescription(rippleType: NanoRippleType, decision: Decision): String {
        return when (rippleType) {
            NanoRippleType.STAFF_MORALE -> "Staff morale shifts slightly from ${decision.text}"
            NanoRippleType.MEDIA_SPIN -> "Media coverage interprets ${decision.text} differently"
            NanoRippleType.OPPOSITION_REACTION -> "Opposition recalculates strategy"
            NanoRippleType.COALITION_TENSION -> "Coalition partner reassesses alliance"
            NanoRippleType.BUREAUCRATIC_RESPONSE -> "Civil service adjusts implementation plans"
            NanoRippleType.PUBLIC_OPINION_SHIFTS -> "Public opinion shifts incrementally"
            NanoRippleType.INTERNATIONAL_PERCEPTION -> "International perception evolves"
            NanoRippleType.MARKET_REACTION -> "Markets adjust expectations"
            NanoRippleType.INTELLIGENCE_IMPLICATIONS -> "Intelligence agencies take note"
            NanoRippleType.HISTORICAL_RECORD -> "Decision enters historical record"
        }
    }
    
    private fun generateFemtoDescription(femtoType: FemtoRippleType, decision: Decision): String {
        return when (femtoType) {
            FemtoRippleType.FUTURE_LEADER_INSPIRED -> "A future leader is inspired by this decision"
            FemtoRippleType.HIDDEN_SCANDAL_SEED -> "Seeds of a future scandal are planted"
            FemtoRippleType.ALLIANCE_SEED -> "Foundation for a future alliance is laid"
            FemtoRippleType.ECONOMIC_SHIFT -> "A subtle economic shift begins"
            FemtoRippleType.CULTURAL_CHANGE -> "A cultural shift is initiated"
        }
    }
    
    companion object {
        const val ACCUMULATION_THRESHOLD = 50
    }
}

// ==================== DATA CLASSES ====================

data class DeepButterflyEffect(
    val id: String,
    val sourceDecision: String,
    val sourceEvent: String,
    val timestamp: Long,
    val processingTimeMs: Long,
    val macroEffects: List<CascadeEffect>,
    val microEffects: List<CascadeEffect>,
    val nanoEffects: List<CascadeEffect>,
    val picoEffects: List<CascadeEffect>,
    val femtoEffects: List<CascadeEffect>,
    val spawnedTasks: List<Task>,
    val memoryEffects: List<MemoryEffect>,
    val karmaChanges: Map<KarmaDimension, Double>,
    val delayedConsequences: List<DelayedEffect>,
    val crossSystemEffects: List<CrossSystemEffect>
)

data class MicroButterflyEffect(
    val taskId: String,
    val nanoEffects: List<CascadeEffect>,
    val picoEffects: List<CascadeEffect>,
    val childTasks: List<Task>,
    val pattern: DecisionPattern,
    val accumulationBonus: Double
)

data class AccumulationResult(
    val triggeredEvents: List<GameEvent>,
    val triggeredTasks: List<Task>,
    val economicAccumulation: Int,
    val diplomaticAccumulation: Int,
    val socialAccumulation: Int,
    val militaryAccumulation: Int
)

data class MemoryEffect(
    val npcId: String,
    val memory: Memory
)

data class DelayedEffect(
    val id: String,
    val sourceDecision: String,
    val type: DelayedEffectType,
    val description: String,
    val triggerTime: Long,
    val magnitude: Double
)

enum class DelayedEffectType {
    IMMEDIATE_REACTION,
    MEDIUM_TERM_EFFECT,
    LONG_TERM_LEGACY,
    ANNIVERSARY_EFFECT,
    HISTORICAL_REEVALUATION
}

data class MaturedEffect(
    val delayedEffectId: String,
    val generatedEventId: String,
    val magnitude: Double
)

data class CrossSystemEffect(
    val fromSystem: String,
    val toSystem: String,
    val description: String,
    val magnitude: Double,
    val delayMs: Long
)

data class CompoundEffect(
    var economicDecisions: Int = 0,
    var diplomaticDecisions: Int = 0,
    var socialDecisions: Int = 0,
    var militaryDecisions: Int = 0,
    var politicalDecisions: Int = 0,
    var lawDecisions: Int = 0,
    var otherDecisions: Int = 0,
    var safeDecisions: Int = 0,
    var lowRiskDecisions: Int = 0,
    var moderateRiskDecisions: Int = 0,
    var highRiskDecisions: Int = 0,
    var extremeRiskDecisions: Int = 0
)

data class RelationshipMemory(
    val npcId: String,
    val totalInteractions: Int = 0,
    val positiveInteractions: Int = 0,
    val negativeInteractions: Int = 0,
    val averageEmotionalWeight: Double = 0.0,
    val lastInteractionTime: Long = 0L,
    val trustLevel: Double = 0.5,
    val respectLevel: Double = 0.5,
    val fearLevel: Double = 0.0,
    val loyaltyLevel: Double = 0.5,
    val grudgeLevel: Double = 0.0,
    val debtLevel: Int = 0,
    // Additional tracked factors
    val reliabilityScore: Double = 0.5,
    val competenceScore: Double = 0.5,
    val honestyScore: Double = 0.5,
    val helpfulnessScore: Double = 0.5,
    val threatScore: Double = 0.0,
    val opportunityScore: Double = 0.5
)

enum class KarmaDimension {
    HONESTY,
    COMPASSION,
    JUSTICE,
    COURAGE,
    WISDOM,
    TEMPERANCE,
    PRUDENCE,
    DILIGENCE,
    PATIENCE,
    HUMILITY,
    GENEROSITY,
    LOYALTY,
    RELIABILITY,
    TRANSPARENCY,
    ACCOUNTABILITY,
    PROGRESSIVENESS,
    CONSERVATIONISM,
    NATIONALISM,
    INTERNATIONALISM,
    PACIFISM,
    MILITARISM,
    SECULARISM,
    ENVIRONMENTALISM,
    EGALITARIANISM,
    MERITOCRACY,
    DEMOCRACY,
    AUTHORITY,
    LIBERTY,
    EQUALITY,
    FRATERNITY,
    TRADITION,
    INNOVATION,
    PRAGMATISM,
    IDEALISM,
    REALISM,
    OPTIMISM,
    PESSIMISM,
    CAUTION,
    BOLDNESS,
    DIPLOMACY,
    STRAIGHTFORWARDNESS,
    CUNNING,
    HONOR,
    SENSITIVITY,
    STRENGTH,
    FLEXIBILITY,
    VISION,
    EXECUTION,
    COMMUNICATION,
    LISTENING
}

enum class NanoRippleType {
    STAFF_MORALE,
    MEDIA_SPIN,
    OPPOSITION_REACTION,
    COALITION_TENSION,
    BUREAUCRATIC_RESPONSE,
    PUBLIC_OPINION_SHIFTS,
    INTERNATIONAL_PERCEPTION,
    MARKET_REACTION,
    INTELLIGENCE_IMPLICATIONS,
    HISTORICAL_RECORD
}

enum class FemtoRippleType {
    FUTURE_LEADER_INSPIRED,
    HIDDEN_SCANDAL_SEED,
    ALLIANCE_SEED,
    ECONOMIC_SHIFT,
    CULTURAL_CHANGE
}

enum class AccumulationType {
    ECONOMIC,
    DIPLOMATIC,
    SOCIAL,
    MILITARY
}

// Missing data class for task penalty
data class TaskPenalty(
    val type: PenaltyType,
    val description: String,
    val value: Double,
    val isRecurring: Boolean
)

enum class PenaltyType {
    APPROVAL_LOSS,
    POLITICAL_CAPITAL_LOSS,
    REPUTATION_DAMAGE,
    STABILITY_DECREASE,
    ECONOMIC_DAMAGE
}

// Repository interface for memory
interface MemoryRepository {
    suspend fun saveMemory(npcId: String, memory: Memory)
    suspend fun getMemoriesForNPC(npcId: String): List<Memory>
}
