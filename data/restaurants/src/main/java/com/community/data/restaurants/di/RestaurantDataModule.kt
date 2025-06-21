package com.community.data.restaurants.di

import com.community.data.restaurants.remote.api.RestaurantApiService
import com.community.data.restaurants.repository.RestaurantRepositoryImpl
import com.community.domain.restaurants.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger Hilt module for restaurant data layer dependencies
 * 
 * This module provides the API service and repository implementation
 * for restaurant-related data operations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RestaurantDataModule {

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        restaurantRepositoryImpl: RestaurantRepositoryImpl
    ): RestaurantRepository

    companion object {
        @Provides
        @Singleton
        fun provideRestaurantApiService(retrofit: Retrofit): RestaurantApiService {
            return retrofit.create(RestaurantApiService::class.java)
        }
    }
}
