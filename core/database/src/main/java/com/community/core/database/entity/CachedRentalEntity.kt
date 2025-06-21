package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_rentals")
data class CachedRentalEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val address: String?,
    val city: String?,
    val province: String?,
    val rent: Double?,
    val bedrooms: Int?,
    val bathrooms: Int?,
    val images: List<String>?,
    val cachedAt: Long = System.currentTimeMillis()
)
