package com.community.data.auth.di

import com.community.data.auth.remote.api.AuthApiService
import com.community.data.auth.repository.AuthRepositoryImpl
import com.community.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger Hilt module for authentication data layer dependencies
 * 
 * This module provides the API service and repository implementation
 * for authentication-related data operations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDataModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
            return retrofit.create(AuthApiService::class.java)
        }
    }
}
