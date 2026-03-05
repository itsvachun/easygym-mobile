package com.easygym.di

import com.easygym.data.local.AppDatabase
import com.easygym.data.local.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DAOModule {
    @Provides
    @Singleton
    fun provideUserDAO(db: AppDatabase): UserDAO = db.userDAO
}