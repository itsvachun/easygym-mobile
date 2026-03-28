package com.easygym.di

import com.easygym.data.repository.*
import com.easygym.domain.repository.*
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

    @Binds
    @Singleton
    abstract fun provideGroupRepository(groupRepositoryImpl: GroupRepositoryImpl): GroupRepository

    @Binds
    @Singleton
    abstract fun provideEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository
}