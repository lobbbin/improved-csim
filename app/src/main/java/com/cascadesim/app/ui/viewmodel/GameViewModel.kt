package com.cascadesim.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cascadesim.core.domain.model.GameEvent
import com.cascadesim.core.domain.model.PlayerState
import com.cascadesim.core.domain.model.Task
import com.cascadesim.core.state.GameStateManager
import com.cascadesim.core.state.GameTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameStateManager: GameStateManager
) : ViewModel() {
    
    val playerState: StateFlow<PlayerState?> = gameStateManager.currentSession
        .map { it?.playerState }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    
    val gameTime: StateFlow<GameTime> = gameStateManager.gameTime
        .stateIn(viewModelScope, SharingStarted.Lazily, GameTime())
    
    val activeEvents: StateFlow<List<GameEvent>> = gameStateManager.getActiveEvents()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val pendingTasks: StateFlow<List<Task>> = gameStateManager.getPendingTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    fun advanceTime() {
        viewModelScope.launch {
            gameStateManager.advanceTime()
        }
    }
    
    fun processDecision(eventId: String, decisionId: String) {
        viewModelScope.launch {
            gameStateManager.processDecision(eventId, decisionId)
        }
    }
    
    fun completeTask(taskId: String) {
        viewModelScope.launch {
            gameStateManager.completeTask(taskId)
        }
    }
    
    fun saveGame() {
        viewModelScope.launch {
            gameStateManager.saveGame()
        }
    }
}
