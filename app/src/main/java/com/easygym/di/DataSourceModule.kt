package com.easygym.di

import com.easygym.data.remote.datasource.AuthDataSource
import com.easygym.data.remote.datasource.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideAuthDataSource(retrofit: Retrofit): AuthDataSource =
        retrofit.create(AuthDataSource::class.java)

    @Provides
    @Singleton
    fun provideUserDataSource(retrofit: Retrofit): UserDataSource =
        retrofit.create(UserDataSource::class.java)
}