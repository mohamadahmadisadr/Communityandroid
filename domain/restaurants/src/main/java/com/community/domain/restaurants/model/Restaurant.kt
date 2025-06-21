package com.community.domain.restaurants.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain model for Restaurant
 * 
 * This represents the business logic model for restaurants,
 * independent of data sources and UI frameworks.
 */
@Parcelize
data class Restaurant(
    val id: String,
    val name: String,
    val description: String?,
    val cuisine: String?,
    val address: String?,
    val city: String?,
    val province: String?,
    val postalCode: String?,
    val phone: String?,
    val email: String?,
    val website: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rating: Double?,
    val reviewCount: Int?,
    val priceRange: PriceRange?,
    val hours: Map<String, String>?,
    val features: List<String>?,
    val images: List<String>?,
    val isActive: Boolean = true,
    val createdAt: String?,
    val updatedAt: String?
) : Parcelable {
    
    /**
     * Get the primary image URL
     */
    fun getPrimaryImageUrl(): String? = images?.firstOrNull()
    
    /**
     * Check if restaurant is currently open
     */
    fun isCurrentlyOpen(): Boolean {
        // TODO: Implement logic to check if restaurant is open based on current time and hours
        return true
    }
    
    /**
     * Get formatted address
     */
    fun getFormattedAddress(): String {
        return buildString {
            address?.let { append(it) }
            if (city != null) {
                if (isNotEmpty()) append(", ")
                append(city)
            }
            if (province != null) {
                if (isNotEmpty()) append(", ")
                append(province)
            }
            if (postalCode != null) {
                if (isNotEmpty()) append(" ")
                append(postalCode)
            }
        }
    }
    
    /**
     * Get distance from a given location
     */
    fun getDistanceFrom(lat: Double, lng: Double): Double? {
        return if (latitude != null && longitude != null) {
            // Calculate distance using Haversine formula
            calculateDistance(lat, lng, latitude, longitude)
        } else {
            null
        }
    }
    
    private fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val earthRadius = 6371.0 // Earth's radius in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }
}

/**
 * Enum for restaurant price ranges
 */
@Parcelize
enum class PriceRange(val symbol: String, val description: String) : Parcelable {
    BUDGET("$", "Budget-friendly"),
    MODERATE("$$", "Moderate"),
    EXPENSIVE("$$$", "Expensive"),
    LUXURY("$$$$", "Luxury")
}
