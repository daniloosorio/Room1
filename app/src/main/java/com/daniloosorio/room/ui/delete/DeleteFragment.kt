package com.daniloosorio.room.ui.delete

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.remote.DeudorRemote
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_delete.*

class DeleteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_eliminar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            //borrarRoom(nombre)
            if(nombre.isNullOrEmpty()) {
                Toast.makeText(context, "Debe escribir un nombre", Toast.LENGTH_SHORT).show()
            }else{borrarFirebase(nombre)}
        }
    }

    private fun borrarRoom(nombre: String) {
        val deudorDAO = SesionROOM.database.deudorDAO()
        val deudor = deudorDAO.buscarDeudor(nombre)
        if (et_nombre.text.toString().isNullOrEmpty()) {
            Toast.makeText(context, "Digite un Nombre", Toast.LENGTH_SHORT).show()
        } else {
            if (deudor != null) {
                Toast.makeText(context, "Se ha borrado ${deudor.nombre}", Toast.LENGTH_SHORT).show()
                deudorDAO.borrarDeudor(deudor)
                et_nombre.setText("")
            } else {
                Toast.makeText(context, "Deudor no encontrado", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun borrarFirebase(nombre: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        var deudorExiste = false
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val deudor = datasnapshot.getValue(DeudorRemote::class.java)
                    if (deudor?.nombre == nombre) {
                        deudorExiste = true
                        val alertDialog: AlertDialog? = activity?.let {
                            val builder = AlertDialog.Builder(it)
                            builder.apply {
                                setMessage("Desea eliminar el deudor $nombre?")
                                setPositiveButton("Aceptar") { dialog, id ->
                                    Toast.makeText(context, "Se ha borrado $nombre", Toast.LENGTH_SHORT).show()
                                    myRef.child(deudor.id!!).removeValue()
                                }
                                setNegativeButton("cancelar") { dialog, id ->

                                }
                            }
                            builder.create()
                        }
                        alertDialog?.show()
                    }
                }
                if (!deudorExiste) {
                    Toast.makeText(context, "Deudor no Existe", Toast.LENGTH_SHORT).show()
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

}