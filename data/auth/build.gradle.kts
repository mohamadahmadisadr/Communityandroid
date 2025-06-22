// filepath: data/auth/build.gradle.kts
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.community.data.auth"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Core modules
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    
    // Domain modules
    implementation(project(":domain:auth"))
    
    // Android Core
    implementation(libs.androidx.core.ktx)

    // Networking
    implementation(libs.bundles.networking)

    // Database
    implementation(libs.bundles.room)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // JSON Serialization
    implementation(libs.gson)

    // Logging
    implementation(libs.timber)
    
    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

kapt {
    correctErrorTypes = true
}