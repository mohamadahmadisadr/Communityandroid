package com.community.data.auth.repository

import android.content.SharedPreferences
import com.community.core.common.result.Result
import com.community.core.common.utils.Constants
import com.community.core.database.dao.UserDao
import com.community.core.network.exception.NetworkException
import com.community.data.auth.mapper.*
import com.community.data.auth.remote.api.AuthApiService
import com.community.data.auth.remote.dto.*
import com.community.domain.auth.model.*
import com.community.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of AuthRepository
 * 
 * This class handles authentication operations, coordinating between
 * network API calls, local database caching, and SharedPreferences storage.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val userDao: UserDao,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override fun getCurrentUser(): Flow<User?> = flow {
        try {
            // First check if user is logged in
            if (!isLoggedIn()) {
                emit(null)
                return@flow
            }

            // Try to get from cache first
            val userId = sharedPreferences.getString(Constants.KEY_USER_ID, null)
            if (userId != null) {
                val cachedUser = userDao.getUserById(userId)
                if (cachedUser != null) {
                    emit(cachedUser.toDomainModel())
                    return@flow
                }
            }

            // If not in cache, fetch from API
            val response = apiService.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                val userDto = response.body()!!
                
                // Cache the user
                userDao.insertUser(userDto.toEntity())
                
                // Update user ID in preferences
                sharedPreferences.edit()
                    .putString(Constants.KEY_USER_ID, userDto.id)
                    .apply()
                
                emit(userDto.toDomainModel())
            } else {
                Timber.e("Failed to fetch current user: ${response.code()}")
                emit(null)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching current user")
            emit(null)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        val token = sharedPreferences.getString(Constants.KEY_AUTH_TOKEN, null)
        return !token.isNullOrEmpty()
    }

    override suspend fun login(credentials: LoginCredentials): Result<User> {
        return try {
            val response = apiService.login(credentials.toDto())
            
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                
                // Store tokens and user info
                storeAuthData(loginResponse.tokens, loginResponse.user)
                
                Timber.d("Login successful for user: ${loginResponse.user.email}")
                Result.Success(loginResponse.user.toDomainModel())
            } else {
                Timber.e("Login failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Login failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error during login")
            Result.Error(e)
        }
    }

    override suspend fun register(registerData: RegisterData): Result<User> {
        return try {
            val response = apiService.register(registerData.toDto())
            
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                
                // Store tokens and user info
                storeAuthData(loginResponse.tokens, loginResponse.user)
                
                Timber.d("Registration successful for user: ${loginResponse.user.email}")
                Result.Success(loginResponse.user.toDomainModel())
            } else {
                Timber.e("Registration failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Registration failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error during registration")
            Result.Error(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            // Call logout API
            val response = apiService.logout()
            
            // Clear local data regardless of API response
            clearAuthData()
            
            if (response.isSuccessful) {
                Timber.d("Logout successful")
                Result.Success(Unit)
            } else {
                Timber.w("Logout API failed but local data cleared: ${response.code()}")
                Result.Success(Unit) // Still consider it successful since local data is cleared
            }
        } catch (e: Exception) {
            Timber.e(e, "Error during logout")
            // Clear local data even if API call fails
            clearAuthData()
            Result.Success(Unit)
        }
    }

    override suspend fun requestPasswordReset(request: PasswordResetRequest): Result<Unit> {
        return try {
            val response = apiService.requestPasswordReset(request.toDto())
            
            if (response.isSuccessful) {
                Timber.d("Password reset requested for: ${request.email}")
                Result.Success(Unit)
            } else {
                Timber.e("Password reset request failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Password reset request failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error requesting password reset")
            Result.Error(e)
        }
    }

    override suspend fun confirmPasswordReset(confirmation: PasswordResetConfirmation): Result<Unit> {
        return try {
            val response = apiService.confirmPasswordReset(confirmation.toDto())
            
            if (response.isSuccessful) {
                Timber.d("Password reset confirmed")
                Result.Success(Unit)
            } else {
                Timber.e("Password reset confirmation failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Password reset confirmation failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error confirming password reset")
            Result.Error(e)
        }
    }

    override suspend fun refreshToken(): Result<AuthTokens> {
        return try {
            val response = apiService.refreshToken()
            
            if (response.isSuccessful && response.body() != null) {
                val tokens = response.body()!!
                
                // Store new tokens
                sharedPreferences.edit()
                    .putString(Constants.KEY_AUTH_TOKEN, tokens.accessToken)
                    .putString(Constants.KEY_REFRESH_TOKEN, tokens.refreshToken)
                    .apply()
                
                Timber.d("Token refreshed successfully")
                Result.Success(tokens.toDomainModel())
            } else {
                Timber.e("Token refresh failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Token refresh failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing token")
            Result.Error(e)
        }
    }

    override suspend fun updateProfile(user: User): Result<User> {
        return try {
            val response = apiService.updateProfile(user.toUpdateProfileDto())
            
            if (response.isSuccessful && response.body() != null) {
                val updatedUser = response.body()!!
                
                // Update cache
                userDao.updateUser(updatedUser.toEntity())
                
                Timber.d("Profile updated successfully")
                Result.Success(updatedUser.toDomainModel())
            } else {
                Timber.e("Profile update failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Profile update failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating profile")
            Result.Error(e)
        }
    }

    override suspend fun changePassword(currentPassword: String, newPassword: String): Result<Unit> {
        return try {
            val request = ChangePasswordRequestDto(
                currentPassword = currentPassword,
                newPassword = newPassword,
                newPasswordConfirmation = newPassword
            )
            
            val response = apiService.changePassword(request)
            
            if (response.isSuccessful) {
                Timber.d("Password changed successfully")
                Result.Success(Unit)
            } else {
                Timber.e("Password change failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Password change failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error changing password")
            Result.Error(e)
        }
    }

    override suspend fun uploadAvatar(imageUri: String): Result<String> {
        // TODO: Implement avatar upload with multipart
        return Result.Error(Exception("Avatar upload not implemented yet"))
    }

    override suspend fun verifyEmail(token: String): Result<Unit> {
        return try {
            val request = EmailVerificationRequestDto(token)
            val response = apiService.verifyEmail(request)
            
            if (response.isSuccessful) {
                Timber.d("Email verified successfully")
                Result.Success(Unit)
            } else {
                Timber.e("Email verification failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Email verification failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error verifying email")
            Result.Error(e)
        }
    }

    override suspend fun resendEmailVerification(): Result<Unit> {
        return try {
            val response = apiService.resendEmailVerification()
            
            if (response.isSuccessful) {
                Timber.d("Email verification resent")
                Result.Success(Unit)
            } else {
                Timber.e("Resend email verification failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Resend email verification failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error resending email verification")
            Result.Error(e)
        }
    }

    override suspend fun verifyPhone(phoneNumber: String, code: String): Result<Unit> {
        return try {
            val request = PhoneVerificationRequestDto(phoneNumber, code)
            val response = apiService.verifyPhone(request)
            
            if (response.isSuccessful) {
                Timber.d("Phone verified successfully")
                Result.Success(Unit)
            } else {
                Timber.e("Phone verification failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Phone verification failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error verifying phone")
            Result.Error(e)
        }
    }

    override suspend fun sendPhoneVerificationCode(phoneNumber: String): Result<Unit> {
        return try {
            val request = SendVerificationCodeRequestDto(phoneNumber)
            val response = apiService.sendPhoneVerificationCode(request)
            
            if (response.isSuccessful) {
                Timber.d("Phone verification code sent")
                Result.Success(Unit)
            } else {
                Timber.e("Send phone verification code failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Send phone verification code failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error sending phone verification code")
            Result.Error(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return try {
            val response = apiService.deleteAccount()
            
            if (response.isSuccessful) {
                // Clear local data
                clearAuthData()
                
                Timber.d("Account deleted successfully")
                Result.Success(Unit)
            } else {
                Timber.e("Account deletion failed: ${response.code()}")
                Result.Error(NetworkException(response.code(), "Account deletion failed"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting account")
            Result.Error(e)
        }
    }

    private fun storeAuthData(tokens: AuthTokensDto, user: UserDto) {
        sharedPreferences.edit()
            .putString(Constants.KEY_AUTH_TOKEN, tokens.accessToken)
            .putString(Constants.KEY_REFRESH_TOKEN, tokens.refreshToken)
            .putString(Constants.KEY_USER_ID, user.id)
            .putBoolean(Constants.KEY_IS_LOGGED_IN, true)
            .apply()
        
        // Cache user data
        try {
            userDao.insertUser(user.toEntity())
        } catch (e: Exception) {
            Timber.e(e, "Error caching user data")
        }
    }

    private fun clearAuthData() {
        val userId = sharedPreferences.getString(Constants.KEY_USER_ID, null)
        
        sharedPreferences.edit()
            .remove(Constants.KEY_AUTH_TOKEN)
            .remove(Constants.KEY_REFRESH_TOKEN)
            .remove(Constants.KEY_USER_ID)
            .putBoolean(Constants.KEY_IS_LOGGED_IN, false)
            .apply()
        
        // Clear cached user data
        try {
            if (userId != null) {
                userDao.getUserById(userId)?.let { user ->
                    userDao.deleteUser(user)
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error clearing cached user data")
        }
    }
}
