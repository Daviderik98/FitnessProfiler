package com.example.myapplication.domain.repository

import com.example.myapplication.data.Profile
import com.example.myapplication.data.ProfileDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first

class ProfileRepository( private val profileDao: ProfileDao ) {

    //ON Registration
    suspend fun addProfile(profileOne: Profile){
        profileDao.upsertProfile(profileOne)
        //Just for testing
        println("ACCOUNT ADDED : ${profileOne.name } , ${profileOne.password}")
    }
    //On swipe to delete(if I have time to implement it)
    fun deleteProfile(profileOne: Profile){
        profileDao.deleteProfile(profileOne)
    }
    //Send in new data
    fun updateProfile(profileOne: Profile){
        profileDao.updateProfile(profileOne)
    }

    //For db checking
    suspend fun countAllProfiles(): Int {
        val profilesList = profileDao.getAllProfiles()
        return profilesList.count()
    }
    //For logging in
    suspend fun scanForPassword(passCode: String): Boolean{
        val list = profileDao.isPasswordAvailable(passCode)
        return list.count() <= 1
    }






    suspend fun getByPasswordAndName(passcode: String, fullname: String): Profile{
        val firstProfile = profileDao.getByPasswordAndName(passcode, fullname).first()
        return firstProfile[0]
    }




}


