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
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
            loginColor = Color.Cyan
        )
    }

    fun checkBeforeRegister(password: String): Boolean{
        var validPassword: Boolean = false
        var onlyOnePassword: Boolean = false
        viewModelScope.launch{
            if(profileRepository.scanForPassword(password)){
                onlyOnePassword = true

            }
            else{

            }
        }
       if(!onlyOnePassword){
           profileState = profileState.copy(
               errorMessage = "You have to find another password"
           )
       }
        return onlyOnePassword
    }


    fun logInByPassword(password: String, fullName: String) : Boolean  { //This function is called when the user hits Login. It checks if the password is available in one and only one account, and then returns the right profile, then accesses that profiles values
        var loginValid: Boolean = false
     //   var loginMessage: String = ""
        var onlyBMI: Int = 0
        var onlyMax: Int = 0
        var onlyHRR: Int = 0
        viewModelScope.launch {
            if (checkValidPass(passcode = password)) {
                //If there´s only one profile with this password then log in that profile
                val onlyProfile = profileRepository.getByPasswordAndName(password, fullName)

                onlyBMI = onlyProfile.profileBMI
                onlyMax = onlyProfile.profileMaxPulse
                onlyHRR = onlyProfile.profileHRR

                profileState = profileState.copy(
                    bmi = onlyBMI,
                    maxP = onlyMax,
                    hrr = onlyHRR

                )

            }
          /*  else {
                loginMessage = "You have to find another password"
            }*/
        }
        profileState = profileState.copy(
            name = fullName,
            password = password,
            loggedIn = true,
           // errorMessage = loginMessage
        )
        return loginValid
    }

    private fun checkValidPass(passcode: String): Boolean {
        var result: Boolean = false
        viewModelScope.launch {
            if (
                profileRepository.scanForPassword(passCode = passcode)
            ) {
                result = true
            }
        }
        return result
    }


    fun loggingOut() {
        profileState = profileState.copy(
            name = null,
            password = "",
            loggedIn = false,
            loginColor = Color.DarkGray
        )
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
        var errorMessage: String = ""
    )

}
















