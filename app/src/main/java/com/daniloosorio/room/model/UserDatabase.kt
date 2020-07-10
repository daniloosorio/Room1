package com.daniloosorio.room.model
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDatabase :RoomDatabase(){
    abstract fun userDAO():UserDAO
}