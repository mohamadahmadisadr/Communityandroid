plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'kotlin-parcelize'
    id 'com.google.dagger.hilt.android'
}

android {
    namespace 'com.community.data.auth'
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
}

dependencies {
    // Core modules
    implementation project(':core:common')
    implementation project(':core:network')
    implementation project(':core:database')
    
    // Domain modules
    implementation project(':domain:auth')
    
    // Android Core
    implementation 'androidx.core:core-ktx:1.12.0'
    
    // Networking
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    
    // Database
    implementation "androidx.room:room-runtime:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
    
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
