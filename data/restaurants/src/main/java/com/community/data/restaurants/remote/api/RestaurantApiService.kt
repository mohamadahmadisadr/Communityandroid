package com.community.data.restaurants.remote.api

import com.community.data.restaurants.remote.dto.RestaurantDto
import com.community.data.restaurants.remote.dto.RestaurantListResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API service for restaurant endpoints
 * 
 * This interface defines all the API endpoints related to restaurants
 * including CRUD operations, search, and filtering.
 */
interface RestaurantApiService {
    
    @GET("restaurants")
    suspend fun getRestaurants(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("city") city: String? = null,
        @Query("cuisine") cuisine: String? = null,
        @Query("price_range") priceRange: String? = null,
        @Query("rating_min") ratingMin: Double? = null,
        @Query("sort_by") sortBy: String? = null,
        @Query("sort_order") sortOrder: String? = null
    ): Response<RestaurantListResponse>
    
    @GET("restaurants/{id}")
    suspend fun getRestaurantById(
        @Path("id") id: String
    ): Response<RestaurantDto>
    
    @GET("restaurants/search")
    suspend fun searchRestaurants(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("city") city: String? = null,
        @Query("cuisine") cuisine: String? = null
    ): Response<RestaurantListResponse>
    
    @GET("restaurants/nearby")
    suspend fun getNearbyRestaurants(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radiusKm: Int = 10,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<RestaurantListResponse>
    
    @GET("restaurants/featured")
    suspend fun getFeaturedRestaurants(
        @Query("limit") limit: Int = 10
    ): Response<List<RestaurantDto>>
    
    @POST("restaurants")
    suspend fun createRestaurant(
        @Body restaurant: RestaurantDto
    ): Response<RestaurantDto>
    
    @PUT("restaurants/{id}")
    suspend fun updateRestaurant(
        @Path("id") id: String,
        @Body restaurant: RestaurantDto
    ): Response<RestaurantDto>
    
    @DELETE("restaurants/{id}")
    suspend fun deleteRestaurant(
        @Path("id") id: String
    ): Response<Unit>
    
    @POST("restaurants/{id}/favorite")
    suspend fun addToFavorites(
        @Path("id") id: String
    ): Response<Unit>
    
    @DELETE("restaurants/{id}/favorite")
    suspend fun removeFromFavorites(
        @Path("id") id: String
    ): Response<Unit>
    
    @GET("user/favorites/restaurants")
    suspend fun getFavoriteRestaurants(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<RestaurantListResponse>
}
