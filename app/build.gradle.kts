plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'kotlin-parcelize'
    id 'com.google.dagger.hilt.android'
}

android {
    namespace 'com.community.android'
    compileSdk 34

    defaultConfig {
        applicationId "com.community.android"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary true
        }
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug {
            debuggable true
            applicationIdSuffix ".debug"
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

    packagingOptions {
        resources {
            excludes += '/META-INF/{AL2.0,LGPL2.1}'
        }
    }
}

dependencies {
    // Core modules
    implementation project(':core:common')
    implementation project(':core:ui')
    implementation project(':core:network')
    implementation project(':core:database')

    // Data modules
    implementation project(':data:restaurants')
    implementation project(':data:auth')

    // Domain modules
    implementation project(':domain:restaurants')
    implementation project(':domain:auth')

    // Feature modules
    implementation project(':feature:restaurants')
    implementation project(':feature:auth')
    implementation project(':feature:search')
    implementation project(':feature:profile')
    // TODO: Add other feature modules as they are implemented
    // implementation project(':feature:home')
    // implementation project(':feature:cafes')
    // implementation project(':feature:rentals')
    // implementation project(':feature:jobs')
    // implementation project(':feature:services')
    // implementation project(':feature:events')
    // implementation project(':feature:profile')
    // implementation project(':feature:search')
    // implementation project(':feature:favorites')
    // implementation project(':feature:messaging')

    // Android Core
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.7.0'
    implementation 'androidx.activity:activity-compose:1.8.2'

    // Compose
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    implementation 'androidx.compose.material3:material3:1.1.2'

    // Navigation
    implementation "androidx.navigation:navigation-compose:$navigation_version"
    implementation "androidx.navigation:navigation-fragment-ktx:$navigation_version"
    implementation "androidx.navigation:navigation-ui-ktx:$navigation_version"

    // Hilt Dependency Injection
    implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-compiler:$hilt_version"
    implementation 'androidx.hilt:hilt-navigation-compose:1.1.0'

    // Splash Screen
    implementation 'androidx.core:core-splashscreen:1.0.1'

    // Logging
    implementation 'com.jakewharton.timber:timber:5.0.1'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
    debugImplementation "androidx.compose.ui:ui-tooling:$compose_version"
    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_version"
}

kapt {
    correctErrorTypes true
}
