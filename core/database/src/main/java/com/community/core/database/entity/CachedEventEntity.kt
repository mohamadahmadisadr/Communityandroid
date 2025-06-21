package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_events")
data class CachedEventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val organizer: String?,
    val venue: String?,
    val startDate: String?,
    val endDate: String?,
    val category: String?,
    val cachedAt: Long = System.currentTimeMillis()
)
