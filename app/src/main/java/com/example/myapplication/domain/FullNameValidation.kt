package com.example.myapplication.domain

class FullNameValidation {
    fun execute(fullName: String): InputValidation {
        if(fullName.isEmpty()){
            return InputValidation(
                isSuccessful = false,
                errorMessage = "You cannot leave this field empty"
            )
        }
        else{
            if (!checkSpaceInFullName(fullname = fullName)) {
                return InputValidation(
                    isSuccessful = false,
                    errorMessage = "You must enter at least two names"
                )
            } else {
                return InputValidation(
                    isSuccessful = true,
                    errorMessage = "You Full Name is Valid"
                )
            }
        }
    }

    fun checkSpaceInFullName(fullname: String): Boolean{
        return fullname.any{ it.isWhitespace()}
    }
}