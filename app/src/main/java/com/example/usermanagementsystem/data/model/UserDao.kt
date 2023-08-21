package com.example.usermanagementsystem.data.model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // insert data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userData: UserData)

    // delete data
    @Delete
    suspend fun deleteUser(userData: UserData)

    // read data
    @Query("SELECT * FROM user_table WHERE email =:email")
    fun getUserData(email: String) : Flow<UserData>
}