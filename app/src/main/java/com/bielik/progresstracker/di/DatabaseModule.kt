package com.bielik.progresstracker.di

import android.content.Context
import androidx.room.Room
import com.bielik.progresstracker.database.AppDatabase
import com.bielik.progresstracker.database.dao.TicketsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "progress_tracker_database"

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideTicketsDao(appDatabase: AppDatabase): TicketsDao {
        return appDatabase.ticketsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME).build()
    }
}