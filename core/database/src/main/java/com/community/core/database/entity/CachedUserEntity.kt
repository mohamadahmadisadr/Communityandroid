package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_users")
data class CachedUserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val name: String?,
    val phone: String?,
    val avatarUrl: String?,
    val bio: String?,
    val location: String?,
    val cachedAt: Long = System.currentTimeMillis()
)
