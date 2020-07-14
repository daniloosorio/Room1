package com.daniloosorio.room

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if(user !=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_entrar.setOnClickListener {
            // entrar()
            if (et_email.text.toString().isNullOrEmpty() || et_password.text.toString()
                    .isNullOrEmpty()
            ) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val email = et_email.text.toString()
                val password = et_password.text.toString()
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            val intent= Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(
                                this, "Correo o contraseña incorrecto",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                        }
                    }
            }
        }
        bt_registro.setOnClickListener {
            val intent= Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
    /*fun entrar() {
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
    }*/
}