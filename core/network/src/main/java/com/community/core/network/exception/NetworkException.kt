package com.community.core.network.exception

/**
 * Custom exception for network-related errors
 * 
 * This exception provides structured error information for network failures,
 * including HTTP status codes and meaningful error messages.
 */
class NetworkException(
    val code: Int,
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {
    
    companion object {
        // Common HTTP status codes
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val REQUEST_TIMEOUT = 408
        const val TOO_MANY_REQUESTS = 429
        const val INTERNAL_SERVER_ERROR = 500
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
        const val GATEWAY_TIMEOUT = 504
        
        // Custom error codes
        const val NETWORK_UNAVAILABLE = -1
        const val UNKNOWN_ERROR = -2
    }
    
    /**
     * Check if the error is due to authentication issues
     */
    fun isAuthenticationError(): Boolean {
        return code == UNAUTHORIZED || code == FORBIDDEN
    }
    
    /**
     * Check if the error is a client-side error (4xx)
     */
    fun isClientError(): Boolean {
        return code in 400..499
    }
    
    /**
     * Check if the error is a server-side error (5xx)
     */
    fun isServerError(): Boolean {
        return code in 500..599
    }
    
    /**
     * Check if the error is retryable
     */
    fun isRetryable(): Boolean {
        return when (code) {
            REQUEST_TIMEOUT,
            TOO_MANY_REQUESTS,
            INTERNAL_SERVER_ERROR,
            BAD_GATEWAY,
            SERVICE_UNAVAILABLE,
            GATEWAY_TIMEOUT,
            NETWORK_UNAVAILABLE -> true
            else -> false
        }
    }
}
