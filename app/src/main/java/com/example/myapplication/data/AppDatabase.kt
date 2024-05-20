package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Profile::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase(){

    abstract fun profileDao(): ProfileDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(
            context: Context
        ) :AppDatabase = instance?: synchronized(this){
            val instance_ = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            ).build()
            instance = instance_
            return instance_
        }
    }
}