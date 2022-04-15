package com.example.taggame.config

import android.content.Context
import androidx.room.Room
import com.example.taggame.dao.RecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonProviderModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database")
        .build()
    }

    @Provides
    @Singleton
    fun provideRecordDao(appDatabase: AppDatabase): RecordDao {
        return appDatabase.recordDao()
    }
}