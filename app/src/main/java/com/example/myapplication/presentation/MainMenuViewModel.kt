package com.example.myapplication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Profile
import com.example.myapplication.domain.FullNameValidation
import com.example.myapplication.domain.PasswordValidation
import com.example.myapplication.domain.repository.ProfileRepository
import kotlinx.coroutines.launch

class MainMenuViewModel : ViewModel() {

    //Primarily for Textfield correction
    private val validateFullname = FullNameValidation()
    private val validatePassword = PasswordValidation()

    var signUpState by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.FullNameChanged -> {
                signUpState = signUpState.copy(fullName = event.fullname)
                validateFullName()

            }

            is MainEvent.PasswordChanged -> {
                signUpState = signUpState.copy(passWord = event.password)
                validatePassWord()
            }

            is MainEvent.Submit -> {
                if (validateFullName() && validatePassWord()) {
                    signUpState = signUpState.copy(isValid = true)
                } else {
                    signUpState = signUpState.copy(isValid = false)
                }
            }
        }
    }

    private fun validateFullName(): Boolean {
        val fullnameResult = validateFullname.execute(signUpState.fullName)
        signUpState = signUpState.copy(fullNameError = fullnameResult.errorMessage)
        return fullnameResult.isSuccessful
    }

    private fun validatePassWord(): Boolean {
        val passwordResult = validatePassword.execute(signUpState.passWord)
        signUpState = signUpState.copy(passWordError = passwordResult.errorMessage)
        return passwordResult.isSuccessful
    }
}


//If this is not valid, copy-paste to separate file
data class MainState(
    val fullName: String = "",
    val fullNameError: String? = "",
    val passWord: String = "",
    val passWordError: String? = "",
    val isValid: Boolean = false
)

//If this is not valid copy-paste to separate file
sealed class MainEvent {
    data class FullNameChanged(val fullname: String) : MainEvent()
    data class PasswordChanged(val password: String) : MainEvent()
    object Submit : MainEvent()
}