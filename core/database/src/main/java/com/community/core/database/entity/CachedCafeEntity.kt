package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_cafes")
data class CachedCafeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val address: String?,
    val city: String?,
    val province: String?,
    val phone: String?,
    val rating: Double?,
    val features: List<String>?,
    val images: List<String>?,
    val cachedAt: Long = System.currentTimeMillis()
)
