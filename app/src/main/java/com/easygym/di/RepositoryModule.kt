package com.easygym.di

import com.easygym.data.repository.AthleteRepositoryImpl
import com.easygym.data.repository.AuthRepositoryImpl
import com.easygym.data.repository.CoachRepositoryImpl
import com.easygym.data.repository.UserRepositoryImpl
import com.easygym.domain.repository.AthleteRepository
import com.easygym.domain.repository.AuthRepository
import com.easygym.domain.repository.CoachRepository
import com.easygym.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideAuthRepositoryImpl(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun provideAthleteRepository(athleteRepositoryImpl: AthleteRepositoryImpl): AthleteRepository

    @Binds
    @Singleton
    abstract fun provideCoachRepository(coachRepositoryImpl: CoachRepositoryImpl): CoachRepository
}