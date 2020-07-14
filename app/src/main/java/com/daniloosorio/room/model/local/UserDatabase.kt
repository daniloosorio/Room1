package com.daniloosorio.room.model.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.daniloosorio.room.model.local.User
import com.daniloosorio.room.model.local.UserDAO

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDatabase :RoomDatabase(){
    abstract fun userDAO(): UserDAO
}