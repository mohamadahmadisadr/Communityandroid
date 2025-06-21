package com.community.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.community.android.navigation.CommunityNavigation
import com.community.android.ui.theme.CommunityAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Main Activity for Community Platform Android App
 * 
 * This activity serves as the main entry point and hosts the navigation graph.
 * It uses Jetpack Compose for the UI and implements the single-activity architecture.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // Keep splash screen visible until app is ready
        splashScreen.setKeepOnScreenCondition {
            viewModel.isLoading.value
        }
        
        setContent {
            CommunityAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CommunityApp()
                }
            }
        }
        
        Timber.d("MainActivity created successfully")
    }
}

@Composable
fun CommunityApp() {
    val navController = rememberNavController()
    
    CommunityNavigation(
        navController = navController
    )
}
