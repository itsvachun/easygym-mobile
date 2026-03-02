package com.easygym.di

import com.easygym.data.remote.auth.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EndpointModule {
    @Provides
    @Singleton
    fun provideAuth(retrofit: Retrofit): Auth =
        retrofit.create(Auth::class.java)
}