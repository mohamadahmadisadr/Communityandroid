package com.community.data.auth.remote.api

import com.community.data.auth.remote.dto.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API service for authentication endpoints
 * 
 * This interface defines all the API endpoints related to user authentication
 * and profile management.
 */
interface AuthApiService {
    
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>
    
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): Response<LoginResponseDto>
    
    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponseDto<Unit>>
    
    @POST("auth/refresh")
    suspend fun refreshToken(): Response<AuthTokensDto>
    
    @POST("auth/password/reset")
    suspend fun requestPasswordReset(
        @Body request: PasswordResetRequestDto
    ): Response<ApiResponseDto<Unit>>
    
    @POST("auth/password/reset/confirm")
    suspend fun confirmPasswordReset(
        @Body request: PasswordResetConfirmationDto
    ): Response<ApiResponseDto<Unit>>
    
    @GET("auth/user")
    suspend fun getCurrentUser(): Response<UserDto>
    
    @PUT("auth/user")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequestDto
    ): Response<UserDto>
    
    @POST("auth/password/change")
    suspend fun changePassword(
        @Body request: ChangePasswordRequestDto
    ): Response<ApiResponseDto<Unit>>
    
    @Multipart
    @POST("auth/user/avatar")
    suspend fun uploadAvatar(
        @Part avatar: MultipartBody.Part
    ): Response<ApiResponseDto<String>>
    
    @POST("auth/email/verify")
    suspend fun verifyEmail(
        @Body request: EmailVerificationRequestDto
    ): Response<ApiResponseDto<Unit>>
    
    @POST("auth/email/verify/resend")
    suspend fun resendEmailVerification(): Response<ApiResponseDto<Unit>>
    
    @POST("auth/phone/verify")
    suspend fun verifyPhone(
        @Body request: PhoneVerificationRequestDto
    ): Response<ApiResponseDto<Unit>>
    
    @POST("auth/phone/verify/send")
    suspend fun sendPhoneVerificationCode(
        @Body request: SendVerificationCodeRequestDto
    ): Response<ApiResponseDto<Unit>>
    
    @DELETE("auth/user")
    suspend fun deleteAccount(): Response<ApiResponseDto<Unit>>
}
