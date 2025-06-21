package com.community.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for search history
 * 
 * This entity stores user's search queries for quick suggestions
 * and improved user experience.
 */
@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey
    val id: String,
    val query: String,
    val category: String?, // restaurant, cafe, rental, job, service, event, or null for global
    val resultCount: Int?,
    val searchedAt: Long = System.currentTimeMillis()
)
