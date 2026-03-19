package com.cascadesim.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cascadesim.app.ui.viewmodel.GameViewModel
import com.cascadesim.core.util.NumberUtils.formatPercentage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToTasks: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onNavigateToWorldMap: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToElections: () -> Unit,
    onNavigateToEconomy: () -> Unit,
    onNavigateToLaw: () -> Unit,
    onNavigateToMinistries: () -> Unit,
    onNavigateToInfrastructure: () -> Unit,
    onNavigateToDiplomacy: () -> Unit,
    onNavigateToDemographics: () -> Unit,
    onNavigateToSports: () -> Unit,
    onMainMenu: () -> Unit,
    viewModel: GameViewModel = hiltViewModel()
) {
    val playerState by viewModel.playerState.collectAsState()
    val gameTime by viewModel.gameTime.collectAsState()
    val activeEvents by viewModel.activeEvents.collectAsState()
    val pendingTasks by viewModel.pendingTasks.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(playerState?.name ?: "Dashboard") },
                subtitle = { Text(playerState?.position?.title ?: "Leader") },
                actions = {
                    IconButton(onClick = { viewModel.saveGame() }) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                    IconButton(onClick = onMainMenu) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Main Menu")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                    label = { Text("Dashboard") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToTasks,
                    icon = {
                        BadgedBox(badge = {
                            if (pendingTasks.isNotEmpty()) {
                                Badge { Text(pendingTasks.size.toString()) }
                            }
                        }) {
                            Icon(Icons.Default.Task, contentDescription = null)
                        }
                    },
                    label = { Text("Tasks") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToEvents,
                    icon = {
                        BadgedBox(badge = {
                            if (activeEvents.isNotEmpty()) {
                                Badge { Text(activeEvents.size.toString()) }
                            }
                        }) {
                            Icon(Icons.Default.Notifications, contentDescription = null)
                        }
                    },
                    label = { Text("Events") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToWorldMap,
                    icon = { Icon(Icons.Default.Public, contentDescription = null) },
                    label = { Text("World") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Game Time and Date
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Date", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = "${gameTime.day}/${gameTime.month}/${gameTime.year}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Term Progress", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = formatPercentage(playerState?.termProgress?.toDouble() ?: 0.0, 0),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            
            // Key Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Approval",
                    value = formatPercentage(playerState?.approvalRating ?: 0.5),
                    icon = Icons.Default.ThumbUp
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Capital",
                    value = "${playerState?.politicalCapital ?: 0}",
                    icon = Icons.Default.AccountBalance
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Government Sections
            Text(
                text = "Government",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Action buttons in grid
            val actions = listOf(
                "Elections" to (Icons.Default.HowToVote to onNavigateToElections),
                "Economy" to (Icons.Default.AttachMoney to onNavigateToEconomy),
                "Law" to (Icons.Default.Gavel to onNavigateToLaw),
                "Ministries" to (Icons.Default.Business to onNavigateToMinistries),
                "Diplomacy" to (Icons.Default.Language to onNavigateToDiplomacy),
                "Infrastructure" to (Icons.Default.Apartment to onNavigateToInfrastructure),
                "Demographics" to (Icons.Default.People to onNavigateToDemographics),
                "Sports" to (Icons.Default.Sports to onNavigateToSports)
            )
            
            actions.chunked(2).forEach { rowActions ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowActions.forEach { (title, pair) ->
                        val (icon, onClick) = pair
                        ActionCard(
                            title = title,
                            icon = icon,
                            onClick = onClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowActions.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            
            // Active Events Preview
            if (activeEvents.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Active Events",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                activeEvents.take(3).forEach { event ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        onClick = onNavigateToEvents
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (event.severity) {
                                    com.cascadesim.core.domain.model.EventSeverity.CRITICAL -> Icons.Default.Warning
                                    com.cascadesim.core.domain.model.EventSeverity.MAJOR -> Icons.Default.Error
                                    else -> Icons.Default.Info
                                },
                                contentDescription = null,
                                tint = when (event.severity) {
                                    com.cascadesim.core.domain.model.EventSeverity.CRITICAL ->
                                        MaterialTheme.colorScheme.error
                                    com.cascadesim.core.domain.model.EventSeverity.MAJOR ->
                                        MaterialTheme.colorScheme.tertiary
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = event.title, style = MaterialTheme.typography.titleSmall)
                                Text(
                                    text = event.description.take(60) + "...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.labelMedium)
                Text(text = value, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleSmall)
        }
    }
}