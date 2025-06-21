plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'kotlin-parcelize'
}

android {
    namespace 'com.community.domain.restaurants'
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
    
    // Android Core
    implementation 'androidx.core:core-ktx:1.12.0'
    
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    
    // Paging 3
    implementation 'androidx.paging:paging-runtime-ktx:3.2.1'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
