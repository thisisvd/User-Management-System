package com.example.usermanagementsystem.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.usermanagementsystem.constants.Constants.DATABASE_NAME

@Database(
    entities = [UserData::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        fun getDatabase(context: Context): UserDatabase {

            return Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}