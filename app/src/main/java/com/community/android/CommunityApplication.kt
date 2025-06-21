package com.community.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Main Application class for Community Platform Android App
 * 
 * This class serves as the entry point for the application and is responsible for:
 * - Initializing Hilt dependency injection
 * - Setting up global configurations
 * - Initializing logging and crash reporting
 * - Managing application-wide state
 */
@HiltAndroidApp
class CommunityApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize logging for debug builds
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // Initialize other global configurations here
        initializeAppConfigurations()
    }
    
    private fun initializeAppConfigurations() {
        // Initialize any global app configurations
        // This could include analytics, crash reporting, etc.
        Timber.d("Community Application initialized successfully")
    }
}
