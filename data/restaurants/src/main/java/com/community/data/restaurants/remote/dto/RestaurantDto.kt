package com.community.data.restaurants.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for Restaurant API responses
 * 
 * This class represents the JSON structure received from the API
 * and is used for network serialization/deserialization.
 */
data class RestaurantDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("cuisine")
    val cuisine: String?,
    
    @SerializedName("address")
    val address: String?,
    
    @SerializedName("city")
    val city: String?,
    
    @SerializedName("province")
    val province: String?,
    
    @SerializedName("postal_code")
    val postalCode: String?,
    
    @SerializedName("phone")
    val phone: String?,
    
    @SerializedName("email")
    val email: String?,
    
    @SerializedName("website")
    val website: String?,
    
    @SerializedName("latitude")
    val latitude: Double?,
    
    @SerializedName("longitude")
    val longitude: Double?,
    
    @SerializedName("rating")
    val rating: Double?,
    
    @SerializedName("review_count")
    val reviewCount: Int?,
    
    @SerializedName("price_range")
    val priceRange: String?,
    
    @SerializedName("hours")
    val hours: Map<String, String>?,
    
    @SerializedName("features")
    val features: List<String>?,
    
    @SerializedName("images")
    val images: List<String>?,
    
    @SerializedName("is_active")
    val isActive: Boolean = true,
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?
)

/**
 * API response wrapper for paginated restaurant data
 */
data class RestaurantListResponse(
    @SerializedName("data")
    val data: List<RestaurantDto>,
    
    @SerializedName("pagination")
    val pagination: PaginationDto
)

/**
 * Pagination metadata from API responses
 */
data class PaginationDto(
    @SerializedName("current_page")
    val currentPage: Int,
    
    @SerializedName("per_page")
    val perPage: Int,
    
    @SerializedName("total")
    val total: Int,
    
    @SerializedName("total_pages")
    val totalPages: Int,
    
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    
    @SerializedName("has_prev_page")
    val hasPrevPage: Boolean
)
