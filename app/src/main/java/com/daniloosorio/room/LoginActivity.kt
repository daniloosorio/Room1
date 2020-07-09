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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        bt_entrar.setOnClickListener {
            val email =et_email.text.toString()
            val password =et_password.text.toString()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                       startActivity(Intent(this,MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.w("TAG", "signInWithEmail:failure", task.getException());

                    }

                    // ...
                }
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        bt_registro.setOnClickListener {
            val intent= Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}