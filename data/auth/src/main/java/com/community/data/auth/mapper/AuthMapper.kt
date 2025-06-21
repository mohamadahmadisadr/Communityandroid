package com.community.data.auth.mapper

import com.community.core.database.entity.CachedUserEntity
import com.community.data.auth.remote.dto.*
import com.community.domain.auth.model.*

/**
 * Mapper functions for converting between different auth representations
 */

/**
 * Convert UserDto to User domain model
 */
fun UserDto.toDomainModel(): User {
    return User(
        id = id,
        email = email,
        name = name,
        phone = phone,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        isEmailVerified = isEmailVerified,
        isPhoneVerified = isPhoneVerified,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Convert User domain model to UserDto
 */
fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        name = name,
        phone = phone,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        isEmailVerified = isEmailVerified,
        isPhoneVerified = isPhoneVerified,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Convert UserDto to CachedUserEntity
 */
fun UserDto.toEntity(): CachedUserEntity {
    return CachedUserEntity(
        id = id,
        email = email,
        name = name,
        phone = phone,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        cachedAt = System.currentTimeMillis()
    )
}

/**
 * Convert CachedUserEntity to User domain model
 */
fun CachedUserEntity.toDomainModel(): User {
    return User(
        id = id,
        email = email,
        name = name,
        phone = phone,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        isEmailVerified = false, // Not stored in cache
        isPhoneVerified = false, // Not stored in cache
        createdAt = null, // Not stored in cache
        updatedAt = null // Not stored in cache
    )
}

/**
 * Convert AuthTokensDto to AuthTokens domain model
 */
fun AuthTokensDto.toDomainModel(): AuthTokens {
    return AuthTokens(
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn
    )
}

/**
 * Convert LoginCredentials to LoginRequestDto
 */
fun LoginCredentials.toDto(): LoginRequestDto {
    return LoginRequestDto(
        email = email,
        password = password
    )
}

/**
 * Convert RegisterData to RegisterRequestDto
 */
fun RegisterData.toDto(): RegisterRequestDto {
    return RegisterRequestDto(
        email = email,
        password = password,
        passwordConfirmation = confirmPassword,
        name = name,
        phone = phone
    )
}

/**
 * Convert PasswordResetRequest to PasswordResetRequestDto
 */
fun PasswordResetRequest.toDto(): PasswordResetRequestDto {
    return PasswordResetRequestDto(
        email = email
    )
}

/**
 * Convert PasswordResetConfirmation to PasswordResetConfirmationDto
 */
fun PasswordResetConfirmation.toDto(): PasswordResetConfirmationDto {
    return PasswordResetConfirmationDto(
        token = token,
        password = newPassword,
        passwordConfirmation = confirmPassword
    )
}

/**
 * Convert User to UpdateProfileRequestDto
 */
fun User.toUpdateProfileDto(): UpdateProfileRequestDto {
    return UpdateProfileRequestDto(
        name = name,
        phone = phone,
        bio = bio,
        location = location
    )
}
