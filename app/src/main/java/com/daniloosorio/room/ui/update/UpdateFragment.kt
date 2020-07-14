package com.daniloosorio.room.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.local.Deudor
import com.daniloosorio.room.model.local.DeudorDAO
import com.daniloosorio.room.model.remote.DeudorRemote
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create.et_cantidad
import kotlinx.android.synthetic.main.fragment_create.et_telefono
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.bt_buscar

class UpdateFragment : Fragment() {
    var idDeudorFirebase :String? = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_update, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideDedudorDatos()
        //var idDeudor = 0
        //val deudorDAO: DeudorDAO = SesionROOM.database.deudorDAO()
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("deudores")
        bt_buscar.setOnClickListener {
            val nombre = et_nombre2.text.toString()
            if (nombre.isNullOrEmpty()) {
                Toast.makeText(context, "Debe escribir un nombre", Toast.LENGTH_SHORT).show()
            } else {
               // BuscarEnRoom(deudorDAO, nombre, idDeudor)
                BuscarEnFirebase(nombre,myRef)
            }
        }

        bt_actualizar.setOnClickListener {
            if (et_nombre2.text.toString().isNullOrEmpty() || et_telefono.text.toString()
                    .isNullOrEmpty() || et_cantidad.text.toString().isNullOrEmpty()
            ) {
                Toast.makeText(context, "Debe llenar todos los campos ", Toast.LENGTH_SHORT).show()
            } else {
                //ActualizarEnRoom(idDeudor, deudorDAO)
                ActualizarEnFireBase(myRef)
                habilitarWidgetsBuscar()
            }
        }
    }

    private fun ActualizarEnRoom(idDeudor: Int, deudorDAO: DeudorDAO) {
        val deudor = Deudor(
            idDeudor,
            et_nombre2.text.toString(),
            et_telefono.text.toString(),
            et_cantidad.text.toString().toLong()
        )
        deudorDAO.actualizarDeudor(deudor)
    }

    private fun habilitarWidgetsBuscar() {
        et_telefono.visibility = View.GONE
        cont_telefono.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        cont_cantidad.visibility = View.GONE
        bt_buscar.visibility = View.VISIBLE
        bt_actualizar.visibility = View.GONE
    }

    private fun BuscarEnFirebase(nombre: String, myRef: DatabaseReference){
       // val database = FirebaseDatabase.getInstance()
       // val myRef =database.getReference("deudores")
        var deudorExiste =false
        val postListener = object  : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot : DataSnapshot in snapshot.children){
                    val deudor =datasnapshot.getValue(DeudorRemote::class.java)
                    if(deudor?.nombre== nombre){
                        deudorExiste = true
                        habilitarWidgets()
                        et_telefono.setText(deudor.telefono)
                        et_cantidad.setText(deudor.cantidad.toString())
                        idDeudorFirebase =deudor.id
                    }
                }
                if(!deudorExiste){
                    Toast.makeText(context, "Deudor no Existe", Toast.LENGTH_SHORT).show()
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

    private fun BuscarEnRoom(deudorDAO: DeudorDAO, nombre: String, idDeudor: Int) {
        var idDeudor1 = idDeudor
        val deudor = deudorDAO.buscarDeudor(nombre)
        if (deudor != null) {
            idDeudor1 = deudor.id
            habilitarWidgets()
            et_telefono.setText(deudor.telefono)
            et_cantidad.setText(deudor.cantidad.toString())

        }
    }

    private fun habilitarWidgets() {
        et_telefono.visibility = View.VISIBLE
        cont_telefono.visibility = View.VISIBLE
        et_cantidad.visibility = View.VISIBLE
        cont_cantidad.visibility = View.VISIBLE
        bt_buscar.visibility = View.GONE
        bt_actualizar.visibility = View.VISIBLE
    }

    private fun hideDedudorDatos() {
        et_telefono.visibility = View.GONE
        cont_telefono.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        cont_cantidad.visibility = View.GONE
        bt_actualizar.visibility = View.GONE
    }
    private fun ActualizarEnFireBase(myRef: DatabaseReference) {

        val chilUpdate = HashMap <String,Any>()
        chilUpdate ["nombre"]= et_nombre2.text.toString()
        chilUpdate["telefono"]=et_telefono.text.toString()
        chilUpdate["cantidad"]=et_cantidad.text.toString().toLong()
        myRef.child(idDeudorFirebase!!).updateChildren(chilUpdate)
    }
}


