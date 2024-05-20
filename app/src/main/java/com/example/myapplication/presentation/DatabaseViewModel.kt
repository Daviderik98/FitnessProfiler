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

    fun loggingIn(thisProfile: Profile) {
        profileState = profileState.copy(
            name = thisProfile.name,
            password = thisProfile.password,
            bmi = thisProfile.profileBMI,
            maxP = thisProfile.profileMaxPulse,
            hrr = thisProfile.profileHRR,
            loggedIn = true
        )
    }

    fun logInByPassword(password: String, fullName: String) { //This function is called when the user hits Login. It checks if the password is available in one and only one account, and then returns the right profile, then accesses that profiles values

        var onlyBMI: Int = 0
        var onlyMax: Int = 0
        var onlyHRR: Int = 0
        viewModelScope.launch { if (checkValidPass(passcode = password)) {
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
        }
        profileState = profileState.copy(
            name = fullName,
            password = password,
            loggedIn = true
        )
        }

    fun checkValidPass(passcode: String): Boolean {
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
            password = null,
            loggedIn = false,
            loginColor = Color.DarkGray
        )
    }





    var profileState by mutableStateOf(ProfileState())

    data class ProfileState(
        var name: String? = null,
        var password: String? = null,
        var bmi: Int = 0,
        var maxP: Int = 0,
        var hrr: Int = 0,
        var loggedIn: Boolean = false,
        var loginColor: Color = Color.DarkGray,
        val profilesCount: String = "xx"
    )

    }
















