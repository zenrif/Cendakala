package com.zen.cendakala.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CreateSurvey::class], version = 5, exportSchema = false)
abstract class CreateSurveyRoomDatabase : RoomDatabase() {
    abstract fun createSurveyDao(): CreateSurveyDao

    companion object {
        @Volatile
        private var INSTANCE: CreateSurveyRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CreateSurveyRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CreateSurveyRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CreateSurveyRoomDatabase::class.java, "survey"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as CreateSurveyRoomDatabase
        }
    }
}