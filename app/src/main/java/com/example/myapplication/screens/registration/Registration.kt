package com.example.myapplication.screens.registration

data class Registration(
    val fullName: String = "",
    val fullNameError: String? = null,
    val passWord: String ="",
    val repeatedPassword: String = "",
    val passWordError: String? = null,
    val repeatedPasswordError: String? = null
)
