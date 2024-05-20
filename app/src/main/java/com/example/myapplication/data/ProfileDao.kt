package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Upsert
    suspend fun upsertProfile(profile: Profile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProfile(profile: Profile): Int

    @Delete
    fun deleteProfile(profile: Profile): Int

    @Query("SELECT * FROM profiles WHERE password = :passWord AND name = :name")
    fun getByPasswordAndName(passWord: String, name: String): Flow<List<Profile>>

    @Query("SELECT * FROM profiles WHERE password = :passWord")
    fun isPasswordAvailable(passWord: String): Flow<List<Profile>>

    @Query("SELECT * FROM profiles")
    fun getAllProfiles(): Flow<List<Profile>>
}