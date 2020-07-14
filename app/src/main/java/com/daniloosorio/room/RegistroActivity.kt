package com.daniloosorio.room

import android.R.attr.password
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.daniloosorio.room.model.remote.UserRemote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference =database.getReference("usuarios")
        val id :String? = myRef.push().key

        bt_guardar.setOnClickListener {
            if(et_nombre.text.isNullOrEmpty() || et_Email.text.isNullOrEmpty()||et_contrasena.text.isNullOrEmpty()|| et_Rcontrasena.text.isNullOrEmpty()||sp_pais.selectedItem.toString()=="Seleccione un Pais") {
                Toast.makeText(this, "Debe llenar todos los datos", Toast.LENGTH_SHORT).show()
            }else if(et_Rcontrasena.text.toString() == et_contrasena.text.toString()) {
                val nombre = et_nombre.text.toString()
                val pais = sp_pais.selectedItem.toString()
                val telefono = et_telefono.text.toString()
                val email = et_Email.text.toString()
                val password = et_Rcontrasena.text.toString()
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            //crearUsuarioEnBaseDeDatos()
                            val usuario = UserRemote(
                                id,
                                nombre,
                                pais,
                                telefono,
                                email
                            )
                            myRef.child(id!!).setValue(usuario)
                            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        } else {
                            if (task.getException()?.message.toString() == "The email address is badly formatted.") {
                                Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show()
                            } else if (task.getException()?.message.toString() == "The given password is invalid. [ Password should be at least 6 characters ]") {
                                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show()
                            } else if(task.getException()?.message.toString()=="The email address is already in use by another account."){
                                Toast.makeText(this, "El correo ya esta registrado", Toast.LENGTH_SHORT).show()
                            }else {
                                Toast.makeText(this, "Authentication failed.${task.getException()?.message.toString()}", Toast.LENGTH_SHORT).show()
                                Log.w("TAG", "signInWithEmail:failure", task.getException())
                            }
                        }
                    }
            }else{ Toast.makeText(this, "las contraseñas no son iguales", Toast.LENGTH_SHORT).show()}
        }
        /*
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
        }*/
    }

    private fun crearUsuarioEnBaseDeDatos() {
        TODO("Not yet implemented")
    }
}