package com.example.myapplication.presentation

import android.content.Context
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.domain.repository.ProfileRepository

object SetupDatabase {
    lateinit var db: AppDatabase
    private set

    val repository by lazy {
        ProfileRepository(
           profileDao = db.profileDao()
        )
    }

    fun provideDB(context: Context){
        db = AppDatabase.getInstance(context)
    }
}