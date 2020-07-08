package com.daniloosorio.room

import android.app.Application
import androidx.room.Room
import com.daniloosorio.room.model.DeudorDataBase

class SesionROOM :Application(){
    companion object{
        lateinit var database: DeudorDataBase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DeudorDataBase::class.java,
            "misdeudores_db"
        ).allowMainThreadQueries()
            .build()
    }
}