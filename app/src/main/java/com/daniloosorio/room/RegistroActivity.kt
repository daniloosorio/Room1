package com.daniloosorio.room

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        bt_guardar.setOnClickListener {
            val email =et_Email.text.toString()
            val password = et_contrasena.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        crearUsuarioEnBaseDeDatos()
                       onBackPressed()
                    } else {

                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        )
                    }

                    // ...
                }
            if(et_nombre.text.isNullOrEmpty() || et_Email.text.isNullOrEmpty()||et_contrasena.text.isNullOrEmpty()|| et_Rcontrasena.text.isNullOrEmpty()||sp_pais.selectedItem.toString()=="Seleccione un pais"){
                
            }
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun crearUsuarioEnBaseDeDatos() {
        TODO("Not yet implemented")
    }
}