package com.cascadesim.feature.military

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cascadesim.core.domain.model.*
import com.cascadesim.core.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Military Feature Module
 * Comprehensive military and defense management system
 */

@HiltViewModel
class MilitaryViewModel @Inject constructor(
    private val militaryRepository: MilitaryRepository,
    private val countryRepository: CountryRepository,
    private val npcRepository: NPCRepository,
    private val gameEventRepository: GameEventRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<MilitaryUiState>(MilitaryUiState.Loading)
    val uiState: StateFlow<MilitaryUiState> = _uiState.asStateFlow()
    
    private val _selectedTab = MutableStateFlow(MilitaryTab.OVERVIEW)
    val selectedTab: StateFlow<MilitaryTab> = _selectedTab.asStateFlow()
    
    init {
        loadMilitaryData()
    }
    
    private fun loadMilitaryData() {
        viewModelScope.launch {
            combine(
                militaryRepository.getArmedForces(),
                militaryRepository.getActiveWars(),
                militaryRepository.getNuclearArsenal(),
                militaryRepository.getDefenseBudget()
            ) { forces, wars, nuclear, budget ->
                MilitaryData(
                    armedForces = forces,
                    activeWars = wars,
                    nuclearArsenal = nuclear,
                    defenseBudget = budget
                )
            }.collect { data ->
                _uiState.value = MilitaryUiState.Success(data)
            }
        }
    }
    
    fun selectTab(tab: MilitaryTab) {
        _selectedTab.value = tab
    }
    
    fun recruitTroops(divisionType: DivisionType, count: Int) {
        viewModelScope.launch {
            val result = militaryRepository.recruitTroops(divisionType, count)
            if (result.isSuccess) {
                // Trigger cascade effects
            }
        }
    }
    
    fun deployDivision(divisionId: String, location: String) {
        viewModelScope.launch {
            militaryRepository.deployDivision(divisionId, location)
        }
    }
    
    fun launchAttack(warId: String, attackPlan: AttackPlan) {
        viewModelScope.launch {
            val result = militaryRepository.launchAttack(warId, attackPlan)
            if (result.isSuccess) {
                // Generate battle events
            }
        }
    }
    
    fun negotiatePeace(warId: String, terms: PeaceTerms) {
        viewModelScope.launch {
            militaryRepository.negotiatePeace(warId, terms)
        }
    }
    
    fun orderNuclearStrike(target: String, weaponId: String) {
        viewModelScope.launch {
            // This requires multiple confirmations and creates major events
            militaryRepository.initiateNuclearProtocol(target, weaponId)
        }
    }
}

// UI State

sealed class MilitaryUiState {
    object Loading : MilitaryUiState()
    data class Success(val data: MilitaryData) : MilitaryUiState()
    data class Error(val message: String) : MilitaryUiState()
}

data class MilitaryData(
    val armedForces: ArmedForces?,
    val activeWars: List<War>,
    val nuclearArsenal: NuclearArsenal?,
    val defenseBudget: Long
)

enum class MilitaryTab {
    OVERVIEW,
    ARMY,
    NAVY,
    AIR_FORCE,
    SPECIAL_FORCES,
    NUCLEAR,
    WARS,
    INTELLIGENCE,
    PROCUREMENT
}

// Repository Interface

interface MilitaryRepository {
    fun getArmedForces(): Flow<ArmedForces?>
    fun getActiveWars(): Flow<List<War>>
    fun getNuclearArsenal(): Flow<NuclearArsenal?>
    fun getDefenseBudget(): Flow<Long>
    
    suspend fun recruitTroops(divisionType: DivisionType, count: Int): Result<Unit>
    suspend fun deployDivision(divisionId: String, location: String): Result<Unit>
    suspend fun launchAttack(warId: String, attackPlan: AttackPlan): Result<BattleResult>
    suspend fun negotiatePeace(warId: String, terms: PeaceTerms): Result<Unit>
    suspend fun initiateNuclearProtocol(target: String, weaponId: String): Result<Unit>
    
    suspend fun purchaseEquipment(equipmentType: EquipmentType, quantity: Int): Result<Unit>
    suspend fun upgradeUnit(unitId: String, upgradeType: UpgradeType): Result<Unit>
    suspend fun trainDivision(divisionId: String, trainingProgram: TrainingProgram): Result<Unit>
}

// Use Cases

class RecruitTroopsUseCase @Inject constructor(
    private val militaryRepository: MilitaryRepository,
    private val countryRepository: CountryRepository,
    private val butterflyProcessor: DeepButterflyEffectProcessor
) {
    suspend operator fun invoke(
        divisionType: DivisionType,
        count: Int,
        countryId: String
    ): Result<RecruitmentResult> {
        // Calculate costs
        val costPerUnit = when (divisionType) {
            DivisionType.INFANTRY -> 50_000L
            DivisionType.ARMORED -> 500_000L
            DivisionType.ARTILLERY -> 300_000L
            DivisionType.SPECIAL_FORCES -> 1_000_000L
            else -> 100_000L
        }
        val totalCost = costPerUnit * count
        
        // Check budget
        val country = countryRepository.getCountryById(countryId)
        // Budget check logic here
        
        // Recruit
        return militaryRepository.recruitTroops(divisionType, count).map {
            RecruitmentResult(
                divisionId = "new_division_${System.currentTimeMillis()}",
                recruitedCount = count,
                cost = totalCost,
                trainingTime = calculateTrainingTime(divisionType)
            )
        }
    }
    
    private fun calculateTrainingTime(divisionType: DivisionType): Long {
        return when (divisionType) {
            DivisionType.INFANTRY -> 30L * 86_400_000L // 30 days
            DivisionType.ARMORED -> 90L * 86_400_000L
            DivisionType.ARTILLERY -> 60L * 86_400_000L
            DivisionType.SPECIAL_FORCES -> 180L * 86_400_000L
            else -> 60L * 86_400_000L
        }
    }
}

class LaunchAttackUseCase @Inject constructor(
    private val militaryRepository: MilitaryRepository,
    private val battleSimulator: BattleSimulator,
    private val eventRepository: GameEventRepository
) {
    suspend operator fun invoke(
        warId: String,
        attackPlan: AttackPlan
    ): Result<BattleOutcome> {
        // Simulate battle
        val battleResult = battleSimulator.simulate(attackPlan)
        
        // Generate event
        val event = GameEvent(
            id = java.util.UUID.randomUUID().toString(),
            title = "Battle: ${attackPlan.operationName}",
            description = battleResult.summary,
            type = EventType.MILITARY,
            category = EventCategory.DOMESTIC,
            severity = if (battleResult.victory) EventSeverity.MODERATE else EventSeverity.MAJOR,
            involvedEntities = emptyList(),
            availableDecisions = generatePostBattleDecisions(battleResult),
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
        
        eventRepository.saveEvent(event)
        
        return Result.success(battleResult)
    }
    
    private fun generatePostBattleDecisions(result: BattleResult): List<Decision> {
        return listOf(
            Decision(
                id = java.util.UUID.randomUUID().toString(),
                text = if (result.victory) "Pursue retreating forces" else "Order tactical retreat",
                description = "Make a strategic decision following the battle",
                requirements = emptyList(),
                costs = listOf(Cost(CostType.MILITARY_STRENGTH, 5.0, false, null)),
                outcomes = emptyList(),
                aiReasoning = null,
                riskLevel = RiskLevel.MODERATE,
                timeToImplement = 3600000L,
                approvalRequired = false,
                approvers = emptyList()
            )
        )
    }
}

// Battle Simulator

class BattleSimulator {
    fun simulate(attackPlan: AttackPlan): BattleResult {
        val attackerStrength = calculateForceStrength(attackPlan.attackingForces)
        val defenderStrength = calculateForceStrength(attackPlan.defendingForces)
        
        // Apply terrain modifiers
        val terrainModifier = when (attackPlan.terrain) {
            TerrainType.MOUNTAINS -> 0.7 to 1.3
            TerrainType.PLAINS -> 1.0 to 1.0
            TerrainType.URBAN -> 0.8 to 1.2
            TerrainType.FOREST -> 0.9 to 1.1
            else -> 1.0 to 1.0
        }
        
        val modifiedAttackerStrength = attackerStrength * terrainModifier.first
        val modifiedDefenderStrength = defenderStrength * terrainModifier.second
        
        // Apply strategy modifiers
        val strategyModifier = when (attackPlan.strategy) {
            AttackStrategy.FRONTAL_ASSAULT -> 1.2 to 1.0
            AttackStrategy.FLANKING -> 1.3 to 0.8
            AttackStrategy.SIEGE -> 0.9 to 1.1
            AttackStrategy.PINCER -> 1.4 to 0.9
            AttackStrategy.DECEPTION -> 1.1 to 0.7
            AttackStrategy.AIRBORNE -> 1.1 to 0.9
            AttackStrategy.AMPHIBIOUS -> 1.0 to 1.1
        }
        
        val finalAttackerStrength = modifiedAttackerStrength * strategyModifier.first
        val finalDefenderStrength = modifiedDefenderStrength * strategyModifier.second
        
        val victory = finalAttackerStrength > finalDefenderStrength
        val margin = kotlin.math.abs(finalAttackerStrength - finalDefenderStrength) / 
                    kotlin.math.max(finalAttackerStrength, finalDefenderStrength)
        
        return BattleResult(
            victory = victory,
            margin = margin,
            attackerCasualties = (attackerStrength * (if (victory) 0.05 else 0.15) * (1 + kotlin.random.Random.nextDouble())).toInt(),
            defenderCasualties = (defenderStrength * (if (victory) 0.2 else 0.05) * (1 + kotlin.random.Random.nextDouble())).toInt(),
            territoryGained = if (victory) attackPlan.objective else null,
            summary = generateBattleSummary(victory, margin)
        )
    }
    
    private fun calculateForceStrength(forces: List<Division>): Double {
        return forces.sumOf { division ->
            division.personnel * division.trainingLevel * division.equipmentQuality * division.morale
        }
    }
    
    private fun generateBattleSummary(victory: Boolean, margin: Double): String {
        return when {
            victory && margin > 0.5 -> "Decisive victory! Enemy forces routed."
            victory && margin > 0.2 -> "Victory achieved after hard fighting."
            victory -> "Narrow victory. Enemy still capable of resistance."
            !victory && margin > 0.5 -> "Devastating defeat. Forces in disarray."
            !victory && margin > 0.2 -> "Defeat. Ordered retreat necessary."
            else -> "Stalemate. Both sides bloodied."
        }
    }
}

// Attack Plan

data class AttackPlan(
    val operationName: String,
    val attackingForces: List<Division>,
    val defendingForces: List<Division>,
    val terrain: TerrainType,
    val strategy: AttackStrategy,
    val objective: String?,
    val airSupport: Boolean,
    val navalSupport: Boolean,
    val intelligenceSupport: Boolean
)

enum class AttackStrategy {
    FRONTAL_ASSAULT,
    FLANKING,
    SIEGE,
    PINCER,
    DECEPTION,
    AIRBORNE,
    AMPHIBIOUS,
    GUERRILLA,
    BLITZKRIEG,
    ATTRITION
}

// Battle Result

data class BattleResult(
    val victory: Boolean,
    val margin: Double,
    val attackerCasualties: Int,
    val defenderCasualties: Int,
    val territoryGained: String?,
    val summary: String
)

// Recruitment Result

data class RecruitmentResult(
    val divisionId: String,
    val recruitedCount: Int,
    val cost: Long,
    val trainingTime: Long
)

// Peace Terms

data class PeaceTerms(
    val ceasefireImmediate: Boolean,
    val territorialChanges: List<TerritorialChange>,
    val reparations: Long?,
    val prisonerExchange: Boolean,
    val demilitarizedZone: Boolean,
    val guarantees: List<String>
)

data class TerritorialChange(
    val territory: String,
    val fromCountry: String,
    val toCountry: String
)

// Equipment Types

enum class EquipmentType {
    RIFLE,
    MACHINE_GUN,
    ARTILLERY,
    TANK,
    APC,
    HELICOPTER,
    FIGHTER_JET,
    BOMBER,
    TRANSPORT_PLANE,
    WARSHIP,
    SUBMARINE,
    AIRCRAFT_CARRIER,
    MISSILE,
    DRONE,
    RADAR,
    NIGHT_VISION,
    BODY_ARMOR,
    COMMUNICATION
}

// Upgrade Types

enum class UpgradeType {
    WEAPONS,
    ARMOR,
    MOBILITY,
    COMMUNICATIONS,
    TRAINING,
    LOGISTICS,
    MEDICAL,
    ENGINEERING
}

// Training Programs

enum class TrainingProgram {
    BASIC,
    ADVANCED,
    SPECIALIZED,
    JOINT_EXERCISES,
    COMBAT_SIMULATION,
    LEADERSHIP,
    SURVIVAL,
    COUNTER_INSURGENCY,
    URBAN_WARFARE,
    MOUNTAIN_WARFARE,
    DESERT_WARFARE,
    JUNGLE_WARFARE,
    ARCTIC_WARFARE,
    NAVAL_OPERATIONS,
    AIRBORNE,
    NIGHT_OPERATIONS
}
