package com.community.data.restaurants.mapper

import com.community.core.database.entity.CachedRestaurantEntity
import com.community.data.restaurants.remote.dto.RestaurantDto
import com.community.domain.restaurants.model.PriceRange
import com.community.domain.restaurants.model.Restaurant

/**
 * Mapper functions for converting between different restaurant representations
 * 
 * These functions handle the conversion between DTOs, entities, and domain models
 * while maintaining data integrity and handling null values appropriately.
 */

/**
 * Convert RestaurantDto to Restaurant domain model
 */
fun RestaurantDto.toDomainModel(): Restaurant {
    return Restaurant(
        id = id,
        name = name,
        description = description,
        cuisine = cuisine,
        address = address,
        city = city,
        province = province,
        postalCode = postalCode,
        phone = phone,
        email = email,
        website = website,
        latitude = latitude,
        longitude = longitude,
        rating = rating,
        reviewCount = reviewCount,
        priceRange = priceRange?.let { mapPriceRange(it) },
        hours = hours,
        features = features,
        images = images,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Convert Restaurant domain model to RestaurantDto
 */
fun Restaurant.toDto(): RestaurantDto {
    return RestaurantDto(
        id = id,
        name = name,
        description = description,
        cuisine = cuisine,
        address = address,
        city = city,
        province = province,
        postalCode = postalCode,
        phone = phone,
        email = email,
        website = website,
        latitude = latitude,
        longitude = longitude,
        rating = rating,
        reviewCount = reviewCount,
        priceRange = priceRange?.symbol,
        hours = hours,
        features = features,
        images = images,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Convert RestaurantDto to CachedRestaurantEntity
 */
fun RestaurantDto.toEntity(): CachedRestaurantEntity {
    return CachedRestaurantEntity(
        id = id,
        name = name,
        description = description,
        cuisine = cuisine,
        address = address,
        city = city,
        province = province,
        postalCode = postalCode,
        phone = phone,
        email = email,
        website = website,
        latitude = latitude,
        longitude = longitude,
        rating = rating,
        reviewCount = reviewCount,
        priceRange = priceRange,
        hours = hours,
        features = features,
        images = images,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt,
        cachedAt = System.currentTimeMillis()
    )
}

/**
 * Convert CachedRestaurantEntity to Restaurant domain model
 */
fun CachedRestaurantEntity.toDomainModel(): Restaurant {
    return Restaurant(
        id = id,
        name = name,
        description = description,
        cuisine = cuisine,
        address = address,
        city = city,
        province = province,
        postalCode = postalCode,
        phone = phone,
        email = email,
        website = website,
        latitude = latitude,
        longitude = longitude,
        rating = rating,
        reviewCount = reviewCount,
        priceRange = priceRange?.let { mapPriceRange(it) },
        hours = hours,
        features = features,
        images = images,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Map string price range to PriceRange enum
 */
private fun mapPriceRange(priceRange: String): PriceRange? {
    return when (priceRange) {
        "$" -> PriceRange.BUDGET
        "$$" -> PriceRange.MODERATE
        "$$$" -> PriceRange.EXPENSIVE
        "$$$$" -> PriceRange.LUXURY
        else -> null
    }
}
