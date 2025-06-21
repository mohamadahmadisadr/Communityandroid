package com.community.data.auth.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Objects for authentication API responses
 */

/**
 * User DTO for API responses
 */
data class UserDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("name")
    val name: String?,
    
    @SerializedName("phone")
    val phone: String?,
    
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    
    @SerializedName("bio")
    val bio: String?,
    
    @SerializedName("location")
    val location: String?,
    
    @SerializedName("is_email_verified")
    val isEmailVerified: Boolean = false,
    
    @SerializedName("is_phone_verified")
    val isPhoneVerified: Boolean = false,
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?
)

/**
 * Authentication tokens DTO
 */
data class AuthTokensDto(
    @SerializedName("access_token")
    val accessToken: String,
    
    @SerializedName("refresh_token")
    val refreshToken: String,
    
    @SerializedName("expires_in")
    val expiresIn: Long
)

/**
 * Login response DTO
 */
data class LoginResponseDto(
    @SerializedName("user")
    val user: UserDto,
    
    @SerializedName("tokens")
    val tokens: AuthTokensDto
)

/**
 * Login request DTO
 */
data class LoginRequestDto(
    @SerializedName("email")
    val email: String,
    
    @SerializedName("password")
    val password: String
)

/**
 * Register request DTO
 */
data class RegisterRequestDto(
    @SerializedName("email")
    val email: String,
    
    @SerializedName("password")
    val password: String,
    
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    
    @SerializedName("name")
    val name: String?,
    
    @SerializedName("phone")
    val phone: String?
)

/**
 * Password reset request DTO
 */
data class PasswordResetRequestDto(
    @SerializedName("email")
    val email: String
)

/**
 * Password reset confirmation DTO
 */
data class PasswordResetConfirmationDto(
    @SerializedName("token")
    val token: String,
    
    @SerializedName("password")
    val password: String,
    
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)

/**
 * Change password request DTO
 */
data class ChangePasswordRequestDto(
    @SerializedName("current_password")
    val currentPassword: String,
    
    @SerializedName("new_password")
    val newPassword: String,
    
    @SerializedName("new_password_confirmation")
    val newPasswordConfirmation: String
)

/**
 * Update profile request DTO
 */
data class UpdateProfileRequestDto(
    @SerializedName("name")
    val name: String?,
    
    @SerializedName("phone")
    val phone: String?,
    
    @SerializedName("bio")
    val bio: String?,
    
    @SerializedName("location")
    val location: String?
)

/**
 * Phone verification request DTO
 */
data class PhoneVerificationRequestDto(
    @SerializedName("phone")
    val phone: String,
    
    @SerializedName("code")
    val code: String
)

/**
 * Send verification code request DTO
 */
data class SendVerificationCodeRequestDto(
    @SerializedName("phone")
    val phone: String
)

/**
 * Email verification request DTO
 */
data class EmailVerificationRequestDto(
    @SerializedName("token")
    val token: String
)

/**
 * Generic API response wrapper
 */
data class ApiResponseDto<T>(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String?,
    
    @SerializedName("data")
    val data: T?
)
