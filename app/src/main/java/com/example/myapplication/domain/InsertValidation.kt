package com.example.myapplication.domain

class InsertValidation {
    fun execute(input: String): InputValidation{
        if(input.isEmpty()){
            return InputValidation(
                isSuccessful = false,
                errorMessage = "You must enter a value"
            )
        }
        else{
            return InputValidation(
                isSuccessful = true,
                errorMessage = null
            )
        }
    }
}