package com.lacalera.testapp.di.module

import android.content.Context
import androidx.room.Room
import com.lacalera.testapp.data.local.dao.CharacterDao
import com.lobito.appbusesvip.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration() // This will clear the database on version mismatch
         .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(@ApplicationContext context: Context, appDatabase : AppDatabase) : CharacterDao = appDatabase.characterDao()
}