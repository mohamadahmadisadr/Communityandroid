package com.community.domain.restaurants.repository

import androidx.paging.PagingData
import com.community.core.common.result.Result
import com.community.domain.restaurants.model.Restaurant
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for restaurant data operations
 * 
 * This interface defines the contract for restaurant data access,
 * abstracting the data sources (network, database) from the domain layer.
 */
interface RestaurantRepository {
    
    /**
     * Get paginated list of restaurants
     */
    fun getRestaurants(): Flow<PagingData<Restaurant>>
    
    /**
     * Get restaurants by city with pagination
     */
    fun getRestaurantsByCity(city: String): Flow<PagingData<Restaurant>>
    
    /**
     * Get restaurants by cuisine with pagination
     */
    fun getRestaurantsByCuisine(cuisine: String): Flow<PagingData<Restaurant>>
    
    /**
     * Search restaurants by query
     */
    fun searchRestaurants(query: String): Flow<PagingData<Restaurant>>
    
    /**
     * Get restaurant by ID
     */
    suspend fun getRestaurantById(id: String): Result<Restaurant>
    
    /**
     * Get nearby restaurants based on location
     */
    fun getNearbyRestaurants(
        latitude: Double,
        longitude: Double,
        radiusKm: Int = 10
    ): Flow<PagingData<Restaurant>>
    
    /**
     * Get featured restaurants
     */
    fun getFeaturedRestaurants(): Flow<Result<List<Restaurant>>>
    
    /**
     * Refresh restaurant data from network
     */
    suspend fun refreshRestaurants(): Result<Unit>
    
    /**
     * Add restaurant to favorites
     */
    suspend fun addToFavorites(restaurantId: String): Result<Unit>
    
    /**
     * Remove restaurant from favorites
     */
    suspend fun removeFromFavorites(restaurantId: String): Result<Unit>
    
    /**
     * Check if restaurant is in favorites
     */
    suspend fun isFavorite(restaurantId: String): Boolean
    
    /**
     * Get user's favorite restaurants
     */
    fun getFavoriteRestaurants(): Flow<PagingData<Restaurant>>
}
