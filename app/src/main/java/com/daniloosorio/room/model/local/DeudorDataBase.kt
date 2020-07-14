package com.daniloosorio.room.model.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.daniloosorio.room.model.local.Deudor
import com.daniloosorio.room.model.local.DeudorDAO

@Database(entities = arrayOf(Deudor::class), version=1)
abstract class DeudorDataBase :RoomDatabase(){
    abstract fun deudorDAO(): DeudorDAO
}
