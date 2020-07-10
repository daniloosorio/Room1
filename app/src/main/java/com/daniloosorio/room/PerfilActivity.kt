package com.daniloosorio.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_perfil.*

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        val intent = intent.extras
        val correo = intent?.getString("correo").toString()
       val userDAO = SesionROOM.database2.userDAO()
        val user = userDAO.buscarUser(correo)
        tv_nombre.text =user.nombre2
        tv_pais.text = user.pais
        tv_correo.text=user.correo
        tv_telefono.text=user.telefono2
    }
}