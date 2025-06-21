package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.CachedRestaurantEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for restaurant operations
 */
@Dao
interface RestaurantDao {
    
    @Query("SELECT * FROM cached_restaurants ORDER BY name ASC")
    fun getAllRestaurants(): Flow<List<CachedRestaurantEntity>>
    
    @Query("SELECT * FROM cached_restaurants WHERE id = :id")
    suspend fun getRestaurantById(id: String): CachedRestaurantEntity?
    
    @Query("SELECT * FROM cached_restaurants WHERE city = :city ORDER BY rating DESC")
    fun getRestaurantsByCity(city: String): Flow<List<CachedRestaurantEntity>>
    
    @Query("SELECT * FROM cached_restaurants WHERE cuisine = :cuisine ORDER BY rating DESC")
    fun getRestaurantsByCuisine(cuisine: String): Flow<List<CachedRestaurantEntity>>
    
    @Query("SELECT * FROM cached_restaurants WHERE name LIKE '%' || :query || '%' OR cuisine LIKE '%' || :query || '%'")
    fun searchRestaurants(query: String): Flow<List<CachedRestaurantEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: CachedRestaurantEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<CachedRestaurantEntity>)
    
    @Update
    suspend fun updateRestaurant(restaurant: CachedRestaurantEntity)
    
    @Delete
    suspend fun deleteRestaurant(restaurant: CachedRestaurantEntity)
    
    @Query("DELETE FROM cached_restaurants WHERE id = :id")
    suspend fun deleteRestaurantById(id: String)
    
    @Query("DELETE FROM cached_restaurants")
    suspend fun deleteAllRestaurants()
    
    @Query("DELETE FROM cached_restaurants WHERE cachedAt < :timestamp")
    suspend fun deleteOldRestaurants(timestamp: Long)
}
