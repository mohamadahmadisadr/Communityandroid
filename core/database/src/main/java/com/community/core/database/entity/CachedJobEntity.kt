package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_jobs")
data class CachedJobEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String?,
    val description: String?,
    val location: String?,
    val jobType: String?,
    val salaryMin: Double?,
    val salaryMax: Double?,
    val cachedAt: Long = System.currentTimeMillis()
)
