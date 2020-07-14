package com.daniloosorio.room

import android.app.Application
import androidx.room.Room
import com.daniloosorio.room.model.local.DeudorDataBase
import com.daniloosorio.room.model.local.UserDatabase

class SesionROOM :Application(){
    companion object{
        lateinit var database: DeudorDataBase
        lateinit var database2: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DeudorDataBase::class.java,
            "misdeudores_db"
        ).allowMainThreadQueries().build()

        database2 =Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            "misusuarios_db"
        ).allowMainThreadQueries().build()
    }
}