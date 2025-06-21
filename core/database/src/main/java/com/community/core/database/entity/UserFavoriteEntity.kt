package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for user favorites
 * 
 * This entity stores user's favorite items across all categories
 * for quick access and offline availability.
 */
@Entity(tableName = "user_favorites")
data class UserFavoriteEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val itemId: String,
    val itemType: String, // restaurant, cafe, rental, job, service, event
    val itemTitle: String,
    val itemDescription: String?,
    val itemImageUrl: String?,
    val addedAt: Long = System.currentTimeMillis()
)
