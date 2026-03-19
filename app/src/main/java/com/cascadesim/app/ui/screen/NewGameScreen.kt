package com.cascadesim.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cascadesim.core.domain.model.GovernmentType
import com.cascadesim.app.ui.viewmodel.NewGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGameScreen(
    onGameStarted: () -> Unit,
    onBack: () -> Unit,
    viewModel: NewGameViewModel = hiltViewModel()
) {
    var playerName by remember { mutableStateOf("") }
    var countryName by remember { mutableStateOf("") }
    var selectedGovernment by remember { mutableStateOf(GovernmentType.PRESIDENTIAL_DEMOCRACY) }
    val isLoading by viewModel.isLoading.collectAsState()
    
    LaunchedEffect(viewModel.gameStarted) {
        if (viewModel.gameStarted) {
            onGameStarted()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Game") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Your Nation",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Player Name
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Your Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Country Name
            OutlinedTextField(
                value = countryName,
                onValueChange = { countryName = it },
                label = { Text("Country Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                leadingIcon = {
                    Icon(Icons.Default.Flag, contentDescription = null)
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Government Type
            Text(
                text = "Select Government Type",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            val governmentTypes = listOf(
                GovernmentType.PRESIDENTIAL_DEMOCRACY to "Presidential Democracy",
                GovernmentType.PARLIAMENTARY_DEMOCRACY to "Parliamentary Democracy",
                GovernmentType.CONSTITUTIONAL_MONARCHY to "Constitutional Monarchy",
                GovernmentType.ABSOLUTE_MONARCHY to "Absolute Monarchy",
                GovernmentType.DICTATORSHIP to "Dictatorship",
                GovernmentType.TECHNOCRACY to "Technocracy"
            )
            
            governmentTypes.forEach { (type, label) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGovernment == type,
                        onClick = { selectedGovernment = type }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Description of selected government
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = getGovernmentDescription(selectedGovernment),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Start button
            Button(
                onClick = {
                    if (playerName.isNotBlank() && countryName.isNotBlank()) {
                        viewModel.startNewGame(playerName, countryName, selectedGovernment)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = playerName.isNotBlank() && countryName.isNotBlank() && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start Game")
                }
            }
        }
    }
}

private fun getGovernmentDescription(type: GovernmentType): String {
    return when (type) {
        GovernmentType.PRESIDENTIAL_DEMOCRACY -> 
            "You are the elected President with executive powers. " +
            "You can veto legislation, appoint ministers, and set policy direction. " +
            "Balance power between branches and maintain public support."
        GovernmentType.PARLIAMENTARY_DEMOCRACY -> 
            "You are the Prime Minister, leading a coalition government. " +
            "You must maintain parliamentary confidence while implementing your agenda. " +
            "Coalition management is key to success."
        GovernmentType.CONSTITUTIONAL_MONARCHY -> 
            "You are the ruling Monarch with ceremonial and limited powers. " +
            "Work with elected officials while preserving traditions. " +
            "Your influence shapes policy behind the scenes."
        GovernmentType.ABSOLUTE_MONARCHY -> 
            "You hold absolute power as Monarch. " +
            "Your word is law, but beware of revolution. " +
            "Manage your realm wisely to maintain stability."
        GovernmentType.DICTATORSHIP -> 
            "You have seized total control of the state. " +
            "Manage the military, secret police, and propaganda. " +
            "Watch your back - coup attempts are always possible."
        GovernmentType.TECHNOCRACY -> 
            "You lead a government of experts and technocrats. " +
            "Make data-driven decisions to optimize society. " +
            "Balance efficiency with public acceptance."
        else -> "Lead your nation to prosperity through careful governance."
    }
}
