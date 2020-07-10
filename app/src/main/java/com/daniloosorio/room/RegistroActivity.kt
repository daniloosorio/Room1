package com.daniloosorio.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.daniloosorio.room.model.User
import com.daniloosorio.room.model.UserDAO
import kotlinx.android.synthetic.main.activity_registro.*
import java.sql.Types

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        bt_guardar.setOnClickListener {
            if(et_nombre.text.isNullOrEmpty() || et_Email.text.isNullOrEmpty()||et_contrasena.text.isNullOrEmpty()|| et_Rcontrasena.text.isNullOrEmpty()||sp_pais.selectedItem.toString()=="Seleccione un Pais") {
                Toast.makeText(this, "Debe llenar todos los datos", Toast.LENGTH_SHORT).show()
            }else if(et_Rcontrasena.text.toString() == et_contrasena.text.toString()){
                val nombre =et_nombre.text.toString()
                val email = et_Email.text.toString()
                val telefono = et_telefono.text.toString()
                val contrasena = et_contrasena.text.toString()
                val pais = sp_pais.selectedItem.toString()
                val user = User(Types.NULL, nombre,telefono, email,pais, contrasena)
                val userDAO : UserDAO = SesionROOM.database2.userDAO()
                userDAO.crearUser(user)
                onBackPressed()
            }else {
                Toast.makeText(this, "las contraseñas no son iguales", Toast.LENGTH_SHORT).show()}
        }
    }
}