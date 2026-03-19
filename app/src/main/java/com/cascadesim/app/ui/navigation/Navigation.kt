package com.cascadesim.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cascadesim.app.ui.screen.*

/**
 * Navigation routes for the app.
 */
object Routes {
    const val SPLASH = "splash"
    const val MAIN_MENU = "main_menu"
    const val NEW_GAME = "new_game"
    const val LOAD_GAME = "load_game"
    const val DASHBOARD = "dashboard"
    const val TASKS = "tasks"
    const val EVENTS = "events"
    const val WORLD_MAP = "world_map"
    const val STATISTICS = "statistics"
    const val ELECTIONS = "elections"
    const val ECONOMY = "economy"
    const val LAW = "law"
    const val MINISTRIES = "ministries"
    const val INFRASTRUCTURE = "infrastructure"
    const val DIPLOMACY = "diplomacy"
    const val DEMOGRAPHICS = "demographics"
    const val SPORTS = "sports"
    const val SETTINGS = "settings"
}

/**
 * Main navigation host for the app.
 */
@Composable
fun CascadeNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_MENU
    ) {
        composable(Routes.MAIN_MENU) {
            MainMenuScreen(
                onNewGame = { navController.navigate(Routes.NEW_GAME) },
                onLoadGame = { navController.navigate(Routes.LOAD_GAME) },
                onSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }
        
        composable(Routes.NEW_GAME) {
            NewGameScreen(
                onGameStarted = { navController.navigate(Routes.DASHBOARD) },
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.LOAD_GAME) {
            LoadGameScreen(
                onGameLoaded = { navController.navigate(Routes.DASHBOARD) },
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onNavigateToTasks = { navController.navigate(Routes.TASKS) },
                onNavigateToEvents = { navController.navigate(Routes.EVENTS) },
                onNavigateToWorldMap = { navController.navigate(Routes.WORLD_MAP) },
                onNavigateToStatistics = { navController.navigate(Routes.STATISTICS) },
                onNavigateToElections = { navController.navigate(Routes.ELECTIONS) },
                onNavigateToEconomy = { navController.navigate(Routes.ECONOMY) },
                onNavigateToLaw = { navController.navigate(Routes.LAW) },
                onNavigateToMinistries = { navController.navigate(Routes.MINISTRIES) },
                onNavigateToInfrastructure = { navController.navigate(Routes.INFRASTRUCTURE) },
                onNavigateToDiplomacy = { navController.navigate(Routes.DIPLOMACY) },
                onNavigateToDemographics = { navController.navigate(Routes.DEMOGRAPHICS) },
                onNavigateToSports = { navController.navigate(Routes.SPORTS) },
                onMainMenu = { 
                    navController.popBackStack(Routes.MAIN_MENU, inclusive = false)
                }
            )
        }
        
        composable(Routes.TASKS) {
            TasksScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.EVENTS) {
            EventsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.WORLD_MAP) {
            WorldMapScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.STATISTICS) {
            StatisticsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.ELECTIONS) {
            ElectionsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.ECONOMY) {
            EconomyScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.LAW) {
            LawScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.MINISTRIES) {
            MinistriesScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.INFRASTRUCTURE) {
            InfrastructureScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.DIPLOMACY) {
            DiplomacyScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.DEMOGRAPHICS) {
            DemographicsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.SPORTS) {
            SportsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
