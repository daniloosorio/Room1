package com.daniloosorio.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_user")
class User (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name ="Nombre") val nombre :String,
    @ColumnInfo(name= "Telelfono") val telefono :String,
    @ColumnInfo(name = "Correo") val correo: String,
    @ColumnInfo(name = "Pais") val pais: String,
    @ColumnInfo(name = "Contrasena") val contrasena : String
)