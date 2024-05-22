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

     fun deleteProfile(thisProfile: Profile){
         profileDao.deleteProfile(thisProfile)
     }

    //For logging in
    suspend fun scanForPassword(passCode: String): Boolean{
        val list = profileDao.isPasswordAvailable(passCode)
        if(list.count() >= 1){
            return true
        }
        else{
            return false
        }
    }


    suspend fun getByPasswordAndName(passcode: String, fullname: String): Profile{
        val firstProfile = profileDao.getByPasswordAndName(passcode, fullname).first()
        return firstProfile[0]
    }



    //For updating Profile values
    suspend fun updateBMI(passcode: String, bmi: Int){
        profileDao.updateBMI(passcode, bmi)
    }

    suspend fun updateMaxPulse(passcode: String, maxPulse: Int){
        profileDao.updateMaxPulse(passcode, maxPulse)
    }

    suspend fun updateHRR(passcode: String, hrr: Int){
        profileDao.updateHRR(passcode, hrr)
    }



}


