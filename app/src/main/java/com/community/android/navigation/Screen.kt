package com.community.android.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Sealed class representing all screens in the app
 * 
 * This class defines all the navigation destinations and their properties
 * including routes, titles, and icons for bottom navigation.
 */
sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    // Main navigation screens
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Restaurants : Screen("restaurants", "Restaurants", Icons.Default.Restaurant)
    object Cafes : Screen("cafes", "Cafes", Icons.Default.LocalCafe)
    object Rentals : Screen("rentals", "Rentals", Icons.Default.Home)
    object Jobs : Screen("jobs", "Jobs", Icons.Default.Work)
    object Services : Screen("services", "Services", Icons.Default.Build)
    object Events : Screen("events", "Events", Icons.Default.Event)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    
    // Detail screens
    object RestaurantDetail : Screen("restaurant_detail/{restaurantId}", "Restaurant Details")
    object CafeDetail : Screen("cafe_detail/{cafeId}", "Cafe Details")
    object RentalDetail : Screen("rental_detail/{rentalId}", "Rental Details")
    object JobDetail : Screen("job_detail/{jobId}", "Job Details")
    object ServiceDetail : Screen("service_detail/{serviceId}", "Service Details")
    object EventDetail : Screen("event_detail/{eventId}", "Event Details")
    
    // Authentication screens
    object Login : Screen("login", "Login")
    object Register : Screen("register", "Register")
    object ForgotPassword : Screen("forgot_password", "Forgot Password")
    
    // User screens
    object EditProfile : Screen("edit_profile", "Edit Profile")
    object Favorites : Screen("favorites", "Favorites")
    object Messages : Screen("messages", "Messages")
    object Settings : Screen("settings", "Settings")
    
    // Search and filter screens
    object Search : Screen("search", "Search")
    object Filter : Screen("filter", "Filter")
    
    // Add/Edit screens
    object AddRestaurant : Screen("add_restaurant", "Add Restaurant")
    object AddCafe : Screen("add_cafe", "Add Cafe")
    object AddRental : Screen("add_rental", "Add Rental")
    object AddJob : Screen("add_job", "Add Job")
    object AddService : Screen("add_service", "Add Service")
    object AddEvent : Screen("add_event", "Add Event")
    
    // Helper functions for parameterized routes
    fun createRoute(vararg args: String): String {
        var route = this.route
        args.forEach { arg ->
            route = route.replaceFirst("{${route.split("/").find { it.startsWith("{") && it.endsWith("}") }?.removePrefix("{")?.removeSuffix("}")}}", arg)
        }
        return route
    }
    
    companion object {
        // Bottom navigation items
        val bottomNavItems = listOf(
            Home,
            Restaurants,
            Cafes,
            Rentals,
            Jobs,
            Services,
            Events,
            Profile
        )
        
        // Main category screens
        val categoryScreens = listOf(
            Restaurants,
            Cafes,
            Rentals,
            Jobs,
            Services,
            Events
        )
    }
}
