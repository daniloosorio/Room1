package com.daniloosorio.room.model.local
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.daniloosorio.room.model.local.User

@Dao
interface UserDAO {
    @Insert
    fun crearUser(user: User)
    @Query("SELECT * FROM tabla_user WHERE Correo LIKE :correo")
    fun buscarUser(correo : String): User
}