package com.community.domain.auth.repository

import com.community.core.common.result.Result
import com.community.domain.auth.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for authentication operations
 * 
 * This interface defines the contract for authentication data access,
 * abstracting the data sources from the domain layer.
 */
interface AuthRepository {
    
    /**
     * Get current authenticated user
     */
    fun getCurrentUser(): Flow<User?>
    
    /**
     * Check if user is logged in
     */
    suspend fun isLoggedIn(): Boolean
    
    /**
     * Login with email and password
     */
    suspend fun login(credentials: LoginCredentials): Result<User>
    
    /**
     * Register new user account
     */
    suspend fun register(registerData: RegisterData): Result<User>
    
    /**
     * Logout current user
     */
    suspend fun logout(): Result<Unit>
    
    /**
     * Request password reset
     */
    suspend fun requestPasswordReset(request: PasswordResetRequest): Result<Unit>
    
    /**
     * Confirm password reset with token
     */
    suspend fun confirmPasswordReset(confirmation: PasswordResetConfirmation): Result<Unit>
    
    /**
     * Refresh authentication token
     */
    suspend fun refreshToken(): Result<AuthTokens>
    
    /**
     * Update user profile
     */
    suspend fun updateProfile(user: User): Result<User>
    
    /**
     * Change user password
     */
    suspend fun changePassword(currentPassword: String, newPassword: String): Result<Unit>
    
    /**
     * Upload user avatar
     */
    suspend fun uploadAvatar(imageUri: String): Result<String>
    
    /**
     * Verify email address
     */
    suspend fun verifyEmail(token: String): Result<Unit>
    
    /**
     * Resend email verification
     */
    suspend fun resendEmailVerification(): Result<Unit>
    
    /**
     * Verify phone number
     */
    suspend fun verifyPhone(phoneNumber: String, code: String): Result<Unit>
    
    /**
     * Send phone verification code
     */
    suspend fun sendPhoneVerificationCode(phoneNumber: String): Result<Unit>
    
    /**
     * Delete user account
     */
    suspend fun deleteAccount(): Result<Unit>
}
