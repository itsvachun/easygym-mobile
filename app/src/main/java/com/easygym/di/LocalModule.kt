package com.easygym.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.easygym.data.local.AppDatabase
import com.easygym.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("auth_prefs")
        }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "easygym.db").fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDAO(db: AppDatabase): UserDAO = db.userDAO

    @Provides
    @Singleton
    fun provideAthleteDAO(db: AppDatabase): AthleteDAO = db.athleteDAO

    @Provides
    @Singleton
    fun provideCoachDAO(db: AppDatabase): CoachDAO = db.coachDAO

    @Provides
    @Singleton
    fun provideGroupDAO(db: AppDatabase): GroupDAO = db.groupDAO

    @Provides
    @Singleton
    fun provideEventDAO(db: AppDatabase): EventDAO = db.eventDAO
}