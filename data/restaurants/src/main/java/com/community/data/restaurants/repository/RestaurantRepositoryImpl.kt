package com.community.data.restaurants.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.community.core.common.result.Result
import com.community.core.database.dao.FavoriteDao
import com.community.core.database.dao.RestaurantDao
import com.community.core.database.entity.UserFavoriteEntity
import com.community.core.network.exception.NetworkException
import com.community.data.restaurants.mapper.toDomainModel
import com.community.data.restaurants.mapper.toEntity
import com.community.data.restaurants.remote.api.RestaurantApiService
import com.community.domain.restaurants.model.Restaurant
import com.community.domain.restaurants.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of RestaurantRepository
 * 
 * This class handles data operations for restaurants, coordinating between
 * network API calls and local database caching.
 */
@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService,
    private val restaurantDao: RestaurantDao,
    private val favoriteDao: FavoriteDao
) : RestaurantRepository {

    override fun getRestaurants(): Flow<PagingData<Restaurant>> {
        // TODO: Implement with Paging 3 RemoteMediator
        // For now, return cached data
        return restaurantDao.getAllRestaurants().map { entities ->
            PagingData.from(entities.map { it.toDomainModel() })
        }
    }

    override fun getRestaurantsByCity(city: String): Flow<PagingData<Restaurant>> {
        return restaurantDao.getRestaurantsByCity(city).map { entities ->
            PagingData.from(entities.map { it.toDomainModel() })
        }
    }

    override fun getRestaurantsByCuisine(cuisine: String): Flow<PagingData<Restaurant>> {
        return restaurantDao.getRestaurantsByCuisine(cuisine).map { entities ->
            PagingData.from(entities.map { it.toDomainModel() })
        }
    }

    override fun searchRestaurants(query: String): Flow<PagingData<Restaurant>> {
        return restaurantDao.searchRestaurants(query).map { entities ->
            PagingData.from(entities.map { it.toDomainModel() })
        }
    }

    override suspend fun getRestaurantById(id: String): Result<Restaurant> {
        return try {
            // Try to get from cache first
            val cachedRestaurant = restaurantDao.getRestaurantById(id)
            if (cachedRestaurant != null) {
                Timber.d("Restaurant found in cache: $id")
                return Result.Success(cachedRestaurant.toDomainModel())
            }

            // If not in cache, fetch from API
            val response = apiService.getRestaurantById(id)
            if (response.isSuccessful && response.body() != null) {
                val restaurantDto = response.body()!!
                
                // Cache the result
                restaurantDao.insertRestaurant(restaurantDto.toEntity())
                
                Timber.d("Restaurant fetched from API and cached: $id")
                Result.Success(restaurantDto.toDomainModel())
            } else {
                Timber.e("Failed to fetch restaurant: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Failed to fetch restaurant"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching restaurant: $id")
            Result.Error(e)
        }
    }

    override fun getNearbyRestaurants(
        latitude: Double,
        longitude: Double,
        radiusKm: Int
    ): Flow<PagingData<Restaurant>> {
        // TODO: Implement with location-based filtering
        return getRestaurants()
    }

    override fun getFeaturedRestaurants(): Flow<Result<List<Restaurant>>> {
        // TODO: Implement featured restaurants logic
        return restaurantDao.getAllRestaurants().map { entities ->
            Result.Success(entities.take(5).map { it.toDomainModel() })
        }
    }

    override suspend fun refreshRestaurants(): Result<Unit> {
        return try {
            val response = apiService.getRestaurants()
            if (response.isSuccessful && response.body() != null) {
                val restaurants = response.body()!!.data
                
                // Clear old cache and insert new data
                restaurantDao.deleteAllRestaurants()
                restaurantDao.insertRestaurants(restaurants.map { it.toEntity() })
                
                Timber.d("Restaurants refreshed successfully")
                Result.Success(Unit)
            } else {
                Timber.e("Failed to refresh restaurants: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Failed to refresh restaurants"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing restaurants")
            Result.Error(e)
        }
    }

    override suspend fun addToFavorites(restaurantId: String): Result<Unit> {
        return try {
            val response = apiService.addToFavorites(restaurantId)
            if (response.isSuccessful) {
                // Add to local favorites
                val restaurant = restaurantDao.getRestaurantById(restaurantId)
                if (restaurant != null) {
                    val favorite = UserFavoriteEntity(
                        id = UUID.randomUUID().toString(),
                        userId = "current_user", // TODO: Get from auth
                        itemId = restaurantId,
                        itemType = "restaurant",
                        itemTitle = restaurant.name,
                        itemDescription = restaurant.description,
                        itemImageUrl = restaurant.images?.firstOrNull()
                    )
                    favoriteDao.insertFavorite(favorite)
                }
                
                Timber.d("Restaurant added to favorites: $restaurantId")
                Result.Success(Unit)
            } else {
                Timber.e("Failed to add to favorites: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Failed to add to favorites"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error adding to favorites: $restaurantId")
            Result.Error(e)
        }
    }

    override suspend fun removeFromFavorites(restaurantId: String): Result<Unit> {
        return try {
            val response = apiService.removeFromFavorites(restaurantId)
            if (response.isSuccessful) {
                // Remove from local favorites
                favoriteDao.deleteFavoriteByIds("current_user", restaurantId, "restaurant")
                
                Timber.d("Restaurant removed from favorites: $restaurantId")
                Result.Success(Unit)
            } else {
                Timber.e("Failed to remove from favorites: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Failed to remove from favorites"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error removing from favorites: $restaurantId")
            Result.Error(e)
        }
    }

    override suspend fun isFavorite(restaurantId: String): Boolean {
        return favoriteDao.isFavorite("current_user", restaurantId, "restaurant")
    }

    override fun getFavoriteRestaurants(): Flow<PagingData<Restaurant>> {
        return favoriteDao.getUserFavoritesByType("current_user", "restaurant").map { favorites ->
            // TODO: Join with restaurant data or fetch from API
            PagingData.from(emptyList<Restaurant>())
        }
    }
}
