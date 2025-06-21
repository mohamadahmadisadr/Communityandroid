package com.community.domain.auth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain model for User
 * 
 * This represents the authenticated user in the business logic layer,
 * independent of data sources and UI frameworks.
 */
@Parcelize
data class User(
    val id: String,
    val email: String,
    val name: String?,
    val phone: String?,
    val avatarUrl: String?,
    val bio: String?,
    val location: String?,
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val createdAt: String?,
    val updatedAt: String?
) : Parcelable {
    
    /**
     * Get user's display name
     */
    fun getDisplayName(): String {
        return when {
            !name.isNullOrBlank() -> name
            !email.isBlank() -> email.substringBefore("@")
            else -> "User"
        }
    }
    
    /**
     * Get user's initials for avatar placeholder
     */
    fun getInitials(): String {
        return if (!name.isNullOrBlank()) {
            name.split(" ")
                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                .take(2)
                .joinToString("")
        } else {
            email.firstOrNull()?.uppercaseChar()?.toString() ?: "U"
        }
    }
    
    /**
     * Check if user profile is complete
     */
    fun isProfileComplete(): Boolean {
        return !name.isNullOrBlank() && 
               !phone.isNullOrBlank() && 
               !location.isNullOrBlank()
    }
}

/**
 * Authentication tokens
 */
@Parcelize
data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
) : Parcelable

/**
 * Login credentials
 */
data class LoginCredentials(
    val email: String,
    val password: String
)

/**
 * Registration data
 */
data class RegisterData(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val name: String?,
    val phone: String?
) {
    /**
     * Validate registration data
     */
    fun validate(): List<String> {
        val errors = mutableListOf<String>()
        
        if (email.isBlank()) {
            errors.add("Email is required")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.add("Invalid email format")
        }
        
        if (password.isBlank()) {
            errors.add("Password is required")
        } else if (password.length < 6) {
            errors.add("Password must be at least 6 characters")
        }
        
        if (password != confirmPassword) {
            errors.add("Passwords do not match")
        }
        
        return errors
    }
}

/**
 * Password reset request
 */
data class PasswordResetRequest(
    val email: String
)

/**
 * Password reset confirmation
 */
data class PasswordResetConfirmation(
    val token: String,
    val newPassword: String,
    val confirmPassword: String
) {
    /**
     * Validate password reset data
     */
    fun validate(): List<String> {
        val errors = mutableListOf<String>()
        
        if (token.isBlank()) {
            errors.add("Reset token is required")
        }
        
        if (newPassword.isBlank()) {
            errors.add("New password is required")
        } else if (newPassword.length < 6) {
            errors.add("Password must be at least 6 characters")
        }
        
        if (newPassword != confirmPassword) {
            errors.add("Passwords do not match")
        }
        
        return errors
    }
}
