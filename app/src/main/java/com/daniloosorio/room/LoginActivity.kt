package com.daniloosorio.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bt_entrar.setOnClickListener {
            entrar()
        }
        bt_registro.setOnClickListener {
            val intent= Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
    fun entrar() {
        val correo = et_email.text.toString()
        val userDAO = SesionROOM.database2.userDAO()
        val user = userDAO.buscarUser(correo)
        if (et_email.text.toString().isNullOrEmpty() || et_password.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            if (user != null) {
                if (user.contrasena == et_password.text.toString()) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("correo",correo)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "La contraseña es invalida", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "El correo no esta registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}