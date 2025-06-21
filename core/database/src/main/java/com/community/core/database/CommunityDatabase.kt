package com.community.core.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.community.core.database.dao.*
import com.community.core.database.entity.*
import com.community.core.database.converter.Converters

/**
 * Main Room database for the Community app
 * 
 * This database handles local storage for caching API responses,
 * user favorites, search history, and offline functionality.
 */
@Database(
    entities = [
        CachedRestaurantEntity::class,
        CachedCafeEntity::class,
        CachedRentalEntity::class,
        CachedJobEntity::class,
        CachedServiceEntity::class,
        CachedEventEntity::class,
        UserFavoriteEntity::class,
        SearchHistoryEntity::class,
        CachedUserEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CommunityDatabase : RoomDatabase() {
    
    abstract fun restaurantDao(): RestaurantDao
    abstract fun cafeDao(): CafeDao
    abstract fun rentalDao(): RentalDao
    abstract fun jobDao(): JobDao
    abstract fun serviceDao(): ServiceDao
    abstract fun eventDao(): EventDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun searchDao(): SearchDao
    abstract fun userDao(): UserDao
    
    companion object {
        const val DATABASE_NAME = "community_database"
    }
}
