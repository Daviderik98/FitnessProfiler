package com.example.myapplication.domain

class PasswordValidation {
    fun execute(password: String): InputValidation {
        if(password.length < 8){
            return InputValidation(
                isSuccessful = false,
                errorMessage = "Password must be at least 8 characters" //Put in stringResource later
            )
        }else{
            if(!isPasswordValid(passWord = password)){
                return InputValidation(
                    isSuccessful = false,
                    errorMessage = "Password must contain both letters and digits" //Put in stringResource later
                )
            }
            else{
                return InputValidation(
                    isSuccessful =true,
                    errorMessage = "Password Accepted"
                )
            }
        }
    }

    fun isPasswordValid(passWord: String): Boolean{
        return passWord.any{ it.isDigit() } &&
                passWord.any{ it.isLetter() }
    }
}