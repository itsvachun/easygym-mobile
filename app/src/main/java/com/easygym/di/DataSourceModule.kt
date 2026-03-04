package com.easygym.di

import com.easygym.data.remote.datasource.AuthDataSource
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
    fun provideAuth(retrofit: Retrofit): AuthDataSource =
        retrofit.create(AuthDataSource::class.java)
}