package com.easygym.di

//import android.content.Context
//import androidx.room.Room
//import com.easygym.data.local.AppDatabase
//import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "easygym.db"
//        ).build()
//    }
}