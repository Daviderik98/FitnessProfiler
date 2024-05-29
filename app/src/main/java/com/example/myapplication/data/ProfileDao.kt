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

    //Standard Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Upsert
    suspend fun upsertProfile(profile: Profile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProfile(profile: Profile): Int

    @Delete
    fun deleteProfile(profile: Profile): Int


    //Queries for login-Logic
    @Query("SELECT * FROM profiles WHERE password = :passWord AND name = :name")
    fun getByPasswordAndName(passWord: String, name: String): Flow<List<Profile>>

    @Query("SELECT * from profiles WHERE password = :passWord")
    fun doesPasswordExist(passWord: String): Flow<List<Profile>>

    @Query("SELECT * from profiles")
    fun getAllProfiles(): Flow<List<Profile>>


    //Queries for updating Profile´s values
    @Query("UPDATE profiles SET profileBMI =:enterBmi WHERE password=:pass")
    suspend fun updateBMI(pass: String, enterBmi: Int)

    @Query("UPDATE profiles SET profileMaxPulse=:enterMaxPulse WHERE password=:pass")
    suspend fun updateMaxPulse(pass: String, enterMaxPulse: Int)

    @Query("UPDATE profiles SET profileHRR=:enterHRR WHERE password=:pass")
    suspend fun updateHRR(pass: String, enterHRR:Int)
}