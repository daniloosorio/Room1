package com.daniloosorio.room.model
import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserDAO {
    @Insert
    fun crearUser(user:User)
}