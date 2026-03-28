package com.easygym.di

import com.easygym.BuildConfig
import com.easygym.data.remote.datasource.*
import com.easygym.utils.authentication.AuthInterceptor
import com.easygym.utils.authentication.TokenAuthenticator
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(retrofit: Retrofit): AuthDataSource =
        retrofit.create(AuthDataSource::class.java)

    @Provides
    @Singleton
    fun provideUserDataSource(retrofit: Retrofit): UserDataSource =
        retrofit.create(UserDataSource::class.java)

    @Provides
    @Singleton
    fun provideAthleteDataSource(retrofit: Retrofit): AthleteDataSource =
        retrofit.create(AthleteDataSource::class.java)

    @Provides
    @Singleton
    fun provideCoachDataSource(retrofit: Retrofit): CoachDataSource =
        retrofit.create(CoachDataSource::class.java)

    @Provides
    @Singleton
    fun provideGroupDataSource(retrofit: Retrofit): GroupDataSource =
        retrofit.create(GroupDataSource::class.java)

    @Provides
    @Singleton
    fun provideEventDataSource(retrofit: Retrofit): EventDataSource =
        retrofit.create(EventDataSource::class.java)
}