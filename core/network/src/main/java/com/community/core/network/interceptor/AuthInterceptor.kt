package com.community.core.network.interceptor

import android.content.SharedPreferences
import com.community.core.common.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor for adding authentication headers to API requests
 * 
 * This interceptor automatically adds the JWT token to all API requests
 * that require authentication.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Get the auth token from shared preferences
        val authToken = sharedPreferences.getString(Constants.KEY_AUTH_TOKEN, null)
        
        // If no token is available, proceed with the original request
        if (authToken.isNullOrEmpty()) {
            Timber.d("No auth token available, proceeding without authentication")
            return chain.proceed(originalRequest)
        }
        
        // Add the authorization header
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .build()
        
        Timber.d("Added auth token to request: ${originalRequest.url}")
        return chain.proceed(authenticatedRequest)
    }
}
