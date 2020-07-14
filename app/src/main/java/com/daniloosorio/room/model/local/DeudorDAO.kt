package com.daniloosorio.room.model.local
import androidx.room.*
import com.daniloosorio.room.model.local.Deudor

@Dao
interface DeudorDAO {
    @Insert
    fun  crearDeudor(deudor: Deudor)
    @Query("SELECT * FROM tabla_deudor WHERE nombre LIKE :nombre")
    fun buscarDeudor(nombre : String): Deudor
    @Update
    fun actualizarDeudor(deudor: Deudor)
    @Delete
    fun borrarDeudor(deudor: Deudor)
    @Query( "SELECT *FROM tabla_deudor")
    fun getDeudores(): List<Deudor>
}