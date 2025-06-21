package com.community.core.network.interceptor

import com.community.core.network.exception.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor for handling network errors and converting them to custom exceptions
 * 
 * This interceptor catches HTTP errors and converts them to meaningful
 * NetworkException instances with appropriate error messages.
 */
@Singleton
class ErrorInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        
        if (!response.isSuccessful) {
            val errorMessage = when (response.code) {
                400 -> "Bad request"
                401 -> "Unauthorized - Please login again"
                403 -> "Forbidden - Access denied"
                404 -> "Resource not found"
                408 -> "Request timeout"
                429 -> "Too many requests - Please try again later"
                500 -> "Internal server error"
                502 -> "Bad gateway"
                503 -> "Service unavailable"
                504 -> "Gateway timeout"
                else -> "Unknown error occurred"
            }
            
            Timber.e("HTTP Error ${response.code}: $errorMessage for ${request.url}")
            
            // You can parse the response body for more detailed error messages
            val errorBody = response.body?.string()
            Timber.e("Error body: $errorBody")
            
            throw NetworkException(
                code = response.code,
                message = errorMessage,
                cause = null
            )
        }
        
        return response
    }
}
