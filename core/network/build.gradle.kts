plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.community.core.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://api.community.app/\"")
        buildConfigField("String", "API_VERSION", "\"v1\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://dev-api.community.app/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }

    testOptions {
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    lint {
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
}

dependencies {
    // Core modules
    implementation(project(":core:common"))
    
    // Android Core
    implementation(libs.androidx.core.ktx)

    // Networking
    implementation(libs.bundles.networking)

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