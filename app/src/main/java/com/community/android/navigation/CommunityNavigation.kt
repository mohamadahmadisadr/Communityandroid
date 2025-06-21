package com.community.android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.community.android.ui.components.BottomNavigationBar
import com.community.android.ui.screens.HomeScreen

/**
 * Main navigation component for the Community app
 * 
 * This composable sets up the navigation graph and handles the main app structure
 * including bottom navigation and screen routing.
 */
@Composable
fun CommunityNavigation(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Home Screen
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            
            // Restaurants Screen
            composable(Screen.Restaurants.route) {
                // RestaurantsScreen(navController = navController)
                // Placeholder for now
                HomeScreen(navController = navController)
            }
            
            // Cafes Screen
            composable(Screen.Cafes.route) {
                // CafesScreen(navController = navController)
                // Placeholder for now
                HomeScreen(navController = navController)
            }
            
            // Rentals Screen
            composable(Screen.Rentals.route) {
                // RentalsScreen(navController = navController)
                // Placeholder for now
                HomeScreen(navController = navController)
            }
            
            // Jobs Screen
            composable(Screen.Jobs.route) {
                // JobsScreen(navController = navController)
                // Placeholder for now
                HomeScreen(navController = navController)
            }
            
            // Services Screen
            composable(Screen.Services.route) {
                // ServicesScreen(navController = navController)
                // Placeholder for now
                HomeScreen(navController = navController)
            }
            
            // Events Screen
            composable(Screen.Events.route) {
                // EventsScreen(navController = navController)
                // Placeholder for now
                HomeScreen(navController = navController)
            }
            
            // Profile Screen
            composable(Screen.Profile.route) {
                // ProfileScreen(navController = navController)
                // Placeholder for now
                HomeScreen(navController = navController)
            }
        }
    }
}
