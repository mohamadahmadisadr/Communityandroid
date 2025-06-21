plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
}

android {
    namespace 'com.community.core.ui'
    compileSdk 34

    defaultConfig {
        minSdk 24
        targetSdk 34

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = '1.8'
    }

    buildFeatures {
        compose true
        dataBinding true
        viewBinding true
    }

    composeOptions {
        kotlinCompilerExtensionVersion compose_version
    }
}

dependencies {
    // Core modules
    implementation project(':core:common')
    
    // Android Core
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.7.0'
    implementation 'androidx.activity:activity-compose:1.8.2'

    // Compose
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    implementation 'androidx.compose.material3:material3:1.1.2'
    implementation 'androidx.compose.material:material-icons-extended:1.5.8'
    
    // Navigation
    implementation "androidx.navigation:navigation-compose:$navigation_version"
    
    // Image Loading
    implementation 'io.coil-kt:coil-compose:2.5.0'
    
    // Lottie Animations
    implementation 'com.airbnb.android:lottie-compose:6.3.0'
    
    // Accompanist (for system UI, permissions, etc.)
    implementation 'com.google.accompanist:accompanist-systemuicontroller:0.32.0'
    implementation 'com.google.accompanist:accompanist-permissions:0.32.0'
    implementation 'com.google.accompanist:accompanist-placeholder-material:0.32.0'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
    debugImplementation "androidx.compose.ui:ui-tooling:$compose_version"
    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_version"
}
