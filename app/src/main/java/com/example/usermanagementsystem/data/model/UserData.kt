package com.example.usermanagementsystem.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.usermanagementsystem.constants.Constants.DATABASE_TABLE

@Entity(DATABASE_TABLE)
data class UserData(
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val email: String,
    val password: String,
    val gender: String,
    val dob: String
)
