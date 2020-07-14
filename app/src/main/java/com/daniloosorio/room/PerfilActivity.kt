package com.daniloosorio.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.daniloosorio.room.model.remote.DeudorRemote
import com.daniloosorio.room.model.remote.UserRemote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_read.*

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        //PerfilRoom()
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val usuario: FirebaseUser? = mAuth.currentUser
        val correo = usuario?.email
        buscarEnFirebase(correo!!)
    }

    private fun PerfilRoom() {
        val intent = intent.extras
        val correo = intent?.getString("correo").toString()
        val userDAO = SesionROOM.database2.userDAO()
        val user = userDAO.buscarUser(correo)
        tv_nombre.text = user.nombre2
        tv_pais.text = user.pais
        tv_correo.text = user.correo
        tv_telefono.text = user.telefono2
    }
    private fun buscarEnFirebase(correo: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("usuarios")
        val postListener = object  : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot : DataSnapshot in snapshot.children){
                    val usuario =datasnapshot.getValue(UserRemote::class.java)
                    if(usuario?.email== correo){
                        tv_nombre.text= usuario.nombre
                        tv_pais.text=usuario.pais
                        tv_correo.text=usuario.email
                        tv_telefono.text=usuario.telefono
                    }
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }
}