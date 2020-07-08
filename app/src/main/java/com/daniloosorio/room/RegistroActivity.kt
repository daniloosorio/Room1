package com.daniloosorio.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        bt_guardar.setOnClickListener {
            if(et_nombre.text.isNullOrEmpty() || et_Email.text.isNullOrEmpty()||et_contrasena.text.isNullOrEmpty()|| et_Rcontrasena.text.isNullOrEmpty()||sp_pais.selectedItem.toString()=="Seleccione un pais"){
                
            }
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}