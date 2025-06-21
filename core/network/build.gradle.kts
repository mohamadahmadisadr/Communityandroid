plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'kotlin-parcelize'
    id 'com.google.dagger.hilt.android'
}

android {
    namespace 'com.community.core.network'
    compileSdk 34

    defaultConfig {
        minSdk 24
        targetSdk 34

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"
        
        buildConfigField "String", "BASE_URL", "\"https://api.community.app/\""
        buildConfigField "String", "API_VERSION", "\"v1\""
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug {
            buildConfigField "String", "BASE_URL", "\"https://dev-api.community.app/\""
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
        buildConfig = true
    }
}

dependencies {
    // Core modules
    implementation project(':core:common')
    
    // Android Core
    implementation 'androidx.core:core-ktx:1.12.0'
    
    // Networking
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'
    
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    
    // Dependency Injection
    implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-compiler:$hilt_version"
    
    // JSON Serialization
    implementation 'com.google.code.gson:gson:2.10.1'
    
    // Logging
    implementation 'com.jakewharton.timber:timber:5.0.1'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3'
    testImplementation 'com.squareup.okhttp3:mockwebserver:4.12.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}

kapt {
    correctErrorTypes true
}
