package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_services")
data class CachedServiceEntity(
    @PrimaryKey val id: String,
    val title: String,
    val provider: String?,
    val description: String?,
    val category: String?,
    val location: String?,
    val priceRange: String?,
    val cachedAt: Long = System.currentTimeMillis()
)
