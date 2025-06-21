package com.community.core.common.utils

/**
 * Application-wide constants
 */
object Constants {
    
    // API Configuration
    const val BASE_URL = "https://api.community.app/"
    const val API_VERSION = "v1"
    const val TIMEOUT_SECONDS = 30L
    
    // Database Configuration
    const val DATABASE_NAME = "community_database"
    const val DATABASE_VERSION = 1
    
    // SharedPreferences Keys
    const val PREFS_NAME = "community_prefs"
    const val KEY_AUTH_TOKEN = "auth_token"
    const val KEY_REFRESH_TOKEN = "refresh_token"
    const val KEY_USER_ID = "user_id"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_FIRST_LAUNCH = "first_launch"
    const val KEY_SELECTED_LOCATION = "selected_location"
    
    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    const val INITIAL_PAGE = 1
    
    // Image Configuration
    const val MAX_IMAGE_SIZE_MB = 5
    const val IMAGE_QUALITY = 80
    const val MAX_IMAGES_PER_LISTING = 10
    
    // Location
    const val DEFAULT_LOCATION_RADIUS_KM = 50
    const val LOCATION_UPDATE_INTERVAL_MS = 10000L
    const val LOCATION_FASTEST_INTERVAL_MS = 5000L
    
    // Cache Configuration
    const val CACHE_SIZE_MB = 50L
    const val CACHE_MAX_AGE_HOURS = 24
    
    // Deep Links
    const val DEEP_LINK_SCHEME = "community"
    const val DEEP_LINK_HOST = "app"
    
    // Request Codes
    const val REQUEST_CODE_LOCATION_PERMISSION = 1001
    const val REQUEST_CODE_CAMERA_PERMISSION = 1002
    const val REQUEST_CODE_STORAGE_PERMISSION = 1003
    
    // Error Messages
    const val ERROR_NETWORK = "Network error occurred"
    const val ERROR_UNKNOWN = "An unknown error occurred"
    const val ERROR_UNAUTHORIZED = "Unauthorized access"
    const val ERROR_NOT_FOUND = "Resource not found"
    const val ERROR_SERVER = "Server error occurred"
    
    // Date Formats
    const val DATE_FORMAT_API = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_FORMAT_DISPLAY = "MMM dd, yyyy"
    const val TIME_FORMAT_DISPLAY = "hh:mm a"
    const val DATETIME_FORMAT_DISPLAY = "MMM dd, yyyy 'at' hh:mm a"
}
