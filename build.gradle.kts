// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    ext {
        compose_version = '1.5.8'
        kotlin_version = '1.9.22'
        hilt_version = '2.48'
        room_version = '2.6.1'
        retrofit_version = '2.9.0'
        navigation_version = '2.7.6'
        lifecycle_version = '2.7.0'
    }
}

plugins {
    id 'com.android.application' version '8.2.2' apply false
    id 'com.android.library' version '8.2.2' apply false
    id 'org.jetbrains.kotlin.android' version '1.9.22' apply false
    id 'com.google.dagger.hilt.android' version '2.48' apply false
    id 'kotlin-kapt' apply false
    id 'kotlin-parcelize' apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir
}