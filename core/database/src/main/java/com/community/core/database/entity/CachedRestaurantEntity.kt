package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for cached restaurant data
 * 
 * This entity stores restaurant information locally for offline access
 * and improved performance.
 */
@Entity(tableName = "cached_restaurants")
data class CachedRestaurantEntity(
    @PrimaryKey
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
    val priceRange: String?,
    val hours: Map<String, String>?, // JSON stored as string
    val features: List<String>?, // JSON stored as string
    val images: List<String>?, // JSON stored as string
    val isActive: Boolean = true,
    val createdAt: String?,
    val updatedAt: String?,
    val cachedAt: Long = System.currentTimeMillis()
)
