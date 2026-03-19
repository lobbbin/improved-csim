package com.cascadesim.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cascadesim.core.domain.model.GovernmentType
import com.cascadesim.core.state.GameStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val gameStateManager: GameStateManager
) : ViewModel() {
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    var gameStarted: Boolean = false
        private set
    
    fun startNewGame(
        playerName: String,
        countryName: String,
        governmentType: GovernmentType
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            
            gameStateManager.startNewGame(
                playerName = playerName,
                countryName = countryName,
                governmentType = governmentType
            ).onSuccess {
                gameStarted = true
            }.onFailure {
                // Handle error
            }
            
            _isLoading.value = false
        }
    }
}
