package com.daniloosorio.room.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.local.DeudorDAO
import com.daniloosorio.room.model.remote.DeudorRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_read.*

class ReadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_resultado.visibility= View.GONE
        //buscar
        bt_buscar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            if(nombre.isNullOrEmpty()){
                Toast.makeText(context, "Debe escribir un nombre", Toast.LENGTH_SHORT).show()
            }else {
                buscarEnFirebase(nombre)
            }
        }
    }

    private fun buscarEnFirebase(nombre: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("deudores")
        var deudorExiste =false
        val postListener = object  : ValueEventListener{
             override fun onCancelled(error: DatabaseError) {

             }

             override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot : DataSnapshot in snapshot.children){
                    val deudor =datasnapshot.getValue(DeudorRemote::class.java)
                        if(deudor?.nombre== nombre){
                            deudorExiste = true
                            tv_resultado.visibility= View.VISIBLE
                            tv_resultado.text ="nombre :${deudor.nombre}\n" +
                                    "telefono :${deudor.telefono}\n" +
                                    "cantidad: ${deudor.cantidad}\n"
                        }
                }
                 if(!deudorExiste){
                     Toast.makeText(context, "Deudor no Existe", Toast.LENGTH_SHORT).show()
                 }
             }

         }
        myRef.addListenerForSingleValueEvent(postListener)
    }
}

/*
* bt_buscar.setOnClickListener {
            val nombre =et_nombre.text.toString()
            val deudorDAO : DeudorDAO = SesionROOM.database.deudorDAO()
            val deudor = deudorDAO.buscarDeudor(nombre)
            if(deudor != null){
                tv_resultado.visibility= View.VISIBLE
                tv_resultado.text ="nombre :${deudor.nombre}\n" +
                        "telefono :${deudor.telefono}\n" +
                        "cantidad: ${deudor.cantidad}\n"
            }else{
                Toast.makeText(context, "deudor no existe", Toast.LENGTH_SHORT).show()
            }
        }*/