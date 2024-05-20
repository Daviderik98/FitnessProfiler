package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile(
    //Essential
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //For logging in
    var isLoggedIn: Boolean,
    //All profile-specific variables
    var name: String,
    var password: String,
    var profileBMI: Int,
    var profileMaxPulse: Int,
    var profileHRR: Int,
) {

}
