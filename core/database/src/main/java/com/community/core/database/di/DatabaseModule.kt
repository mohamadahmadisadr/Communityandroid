package com.community.core.database.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.community.core.common.utils.Constants
import com.community.core.database.CommunityDatabase
import com.community.core.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for database dependencies
 * 
 * This module provides the Room database instance and all DAO interfaces
 * for dependency injection throughout the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideCommunityDatabase(@ApplicationContext context: Context): CommunityDatabase {
        return Room.databaseBuilder(
            context,
            CommunityDatabase::class.java,
            CommunityDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // For development only
            .build()
    }

    @Provides
    fun provideRestaurantDao(database: CommunityDatabase): RestaurantDao {
        return database.restaurantDao()
    }

    @Provides
    fun provideCafeDao(database: CommunityDatabase): CafeDao {
        return database.cafeDao()
    }

    @Provides
    fun provideRentalDao(database: CommunityDatabase): RentalDao {
        return database.rentalDao()
    }

    @Provides
    fun provideJobDao(database: CommunityDatabase): JobDao {
        return database.jobDao()
    }

    @Provides
    fun provideServiceDao(database: CommunityDatabase): ServiceDao {
        return database.serviceDao()
    }

    @Provides
    fun provideEventDao(database: CommunityDatabase): EventDao {
        return database.eventDao()
    }

    @Provides
    fun provideFavoriteDao(database: CommunityDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun provideSearchDao(database: CommunityDatabase): SearchDao {
        return database.searchDao()
    }

    @Provides
    fun provideUserDao(database: CommunityDatabase): UserDao {
        return database.userDao()
    }
}
