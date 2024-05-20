package com.example.myapplication.domain

data class InputValidation(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)
