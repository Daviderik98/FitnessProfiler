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
    suspend fun scanForPassword(passCode: String): Int?{
            val list = profileDao.doesPasswordExist(passCode)
            return list.count()
    }


      suspend fun getByPasswordAndName(passcode: String, nameOne: String): Profile?{
        val profilesList = profileDao.getByPasswordAndName(passcode, nameOne)
        if(profilesList.count() > 1){
            val firstProfile = profilesList.first()
            return firstProfile[0]
        }
        else{
            return null
        }
    }


    fun getAllProfiles(): Flow<List<Profile>> {
        return profileDao.getAllProfiles()
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


