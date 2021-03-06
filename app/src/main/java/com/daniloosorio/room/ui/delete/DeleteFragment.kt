package com.daniloosorio.room.ui.delete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
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
    }

}