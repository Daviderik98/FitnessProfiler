package com.example.myapplication.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Profile
import com.example.myapplication.domain.repository.ProfileRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseViewModel : ViewModel() {
    //DB LOGIC
    private val profileRepository: ProfileRepository = SetupDatabase.repository
    
    private suspend fun addProfile(thisProfile: Profile) {
        profileRepository.addProfile(thisProfile)
    }


    fun registerProfile(thisName: String, thisPassword: String) {
        viewModelScope.launch {
                addProfile(
                    Profile(
                        name = thisName,
                        password = thisPassword,
                        isLoggedIn = false,
                        profileBMI = 0,
                        profileMaxPulse = 0,
                        profileHRR = 0,
                    )
                )
        }
        profileState = profileState.copy(
            name = thisName,
            password = thisPassword,
            loggedIn = true,
            bmi = 0,
            maxP = 0,
            hrr = 0,
        )
    }

    fun checkBeforeRegister(password: String){
       viewModelScope.launch { val countOfProfile = profileRepository.scanForPassword(password) == 0
           if(countOfProfile){
               println("No Pre-existing Profile")
           }
           else{
               println("TAKEN PASSWORD OR NAME. MINIMUM ONE MUST CHANGE")
           } }//Uses scanForPassword-function to check if the password is taken


    }

    fun loggingIn(thisProfile: Profile?){
        if(thisProfile!= null){
            profileState = profileState.copy(
                name = thisProfile.name,
                password = thisProfile.password,
                bmi = thisProfile.profileBMI,
                maxP = thisProfile.profileMaxPulse,
                hrr = thisProfile.profileHRR,
                loggedIn = true
            )
        }
    }

    //Unneccesary function
    suspend fun checkValidPass(fullname: String, passCode: String): Profile?{
        val deferredProfile = viewModelScope.async{
            profileRepository.getByPasswordAndName(passcode = passCode, nameOne = fullname)
        }
        return deferredProfile.await()
    }


    fun logInByPassword(password: String, fullName: String)  { //This function is called when the user hits Login. It checks if the password is available in one and only one account, and then returns the right profile, then accesses that profiles values
     //   TODO - This function now works for finding a profile, I think. All that´s left is to gain access to it´s data


        viewModelScope.launch {

                //If there´s only one profile with this password then log in that profile
                val onlyProfile = profileRepository.getByPasswordAndName(password, fullName)

                if (onlyProfile != null) {
                    profileState = profileState.copy(
                        bmi = onlyProfile.profileBMI,
                        maxP = onlyProfile.profileMaxPulse,
                        hrr = onlyProfile.profileHRR,
                    )
                }
        }


            profileState = profileState.copy(
                name = fullName,
                password = password,
                loggedIn = true,
            )
    }






    fun loggingOut() {
        profileState = profileState.copy(
            name = null,
            password = "",
            loggedIn = false,
            loginColor = Color.DarkGray
        )
    }

    fun testScanOne(passcode:String){
        viewModelScope.launch{
            testingScanForPassword(passcode)
        }
    }

//THIS IS ONLY FOR TESTING  SCANFORPASSWORD()
    private suspend fun testingScanForPassword(password: String){
        var numb: Int? = 0

             numb = profileRepository.scanForPassword(passCode = "password")

        profileState = profileState.copy(profilesIndex = numb.toString())
    }


    fun deleteOneProfile(name: String, pass: String){ //Didn´t work
        viewModelScope.launch {
            val oneProfile: Profile? = profileRepository.getByPasswordAndName(name, pass)
            if(oneProfile != null){
                profileRepository.deleteProfile(oneProfile)
            }
        }
    }



//Functions for updating Profile´s values
    fun updateBmi(enterBmi: Int){
        viewModelScope.launch{
            profileRepository.updateBMI(
                bmi = enterBmi,
                passcode = profileState.password
            )
        }
    }

    fun updateMaxPulse(enterMaxPulse: Int){
        viewModelScope.launch {
            profileRepository.updateMaxPulse(
                maxPulse = enterMaxPulse,
                passcode = profileState.password
            )
        }
    }

    fun updateHRR(enterHrr: Int){
        viewModelScope.launch{
            profileRepository.updateHRR(
                hrr = enterHrr,
                passcode = profileState.password
            )
        }
    }


    var profileState by mutableStateOf(ProfileState())

    data class ProfileState(
        var name: String? = null,
        var password: String = "",
        var bmi: Int = 0,
        var maxP: Int = 0,
        var hrr: Int = 0,
        var loggedIn: Boolean = false,
        var loginColor: Color = Color.DarkGray,
        //extra for correction
        var errorMessage: String = "",
        var profilesIndex: String = "Number of profiles with that password"
    )

}
















