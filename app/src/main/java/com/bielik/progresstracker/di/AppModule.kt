package com.bielik.progresstracker.di

import android.app.Application
import android.content.Context
import com.bielik.progresstracker.repository.PreferencesRepository
import com.bielik.progresstracker.repository.PreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val PREFERENCES_NAME = "progress_tracker_app"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(app: Application): PreferencesRepository {
        return PreferencesRepositoryImpl(app.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE))
    }
}