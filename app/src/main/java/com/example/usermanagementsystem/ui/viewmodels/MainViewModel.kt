package com.example.usermanagementsystem.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.usermanagementsystem.data.model.UserDao
import com.example.usermanagementsystem.data.model.UserData
import com.example.usermanagementsystem.data.model.UserDatabase
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    // init db
    private lateinit var db: UserDao

    // setup db
    fun setUpDatabase(context: Context) {
        db = UserDatabase.getDatabase(context).getUserDao()
    }

    // get user data
    fun getUserData(email: String) = db.getUserData(email).asLiveData()

    // add user data
    fun addUserData(userData: UserData) = viewModelScope.launch {
        db.addUser(userData = userData)
    }

    // delete user data
    fun deleteUserData(userData: UserData) = viewModelScope.launch {
        db.deleteUser(userData = userData)
    }
}