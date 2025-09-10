package com.lobito.appbusesvip.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lacalera.testapp.data.local.converter.TimeConverter
import com.lacalera.testapp.data.local.dao.CharacterDao
import com.lacalera.testapp.data.local.model.CharacterEntity


@TypeConverters(TimeConverter::class)
@Database(
    entities = [
        CharacterEntity::class,
    ],
    version = 2, // Incremented version for database migration - Added TipoPersonaEntity
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        const val DATABASE_NAME = "AppBusesVIP.db"
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
//                    .addCallback(seedDatabaseCallback(context))
                    .fallbackToDestructiveMigration()
//                        .openHelperFactory(factory)
                    .build()
            }
            return INSTANCE
        }


    }

}
