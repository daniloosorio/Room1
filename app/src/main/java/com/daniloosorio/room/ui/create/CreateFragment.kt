
package com.daniloosorio.room.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.Deudor
import com.daniloosorio.room.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_create.*
import java.sql.Types.NULL

class CreateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_create, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_entrar.setOnClickListener {
            if(et_nombre.text.isNullOrEmpty() || et_telefono.text.isNullOrEmpty() || et_cantidad.text.isNullOrEmpty()){
                Toast.makeText(context, "Deber llenar todos los datos", Toast.LENGTH_SHORT).show()
            }else {
                val nombre = et_nombre.text.toString()
                val telefono = et_telefono.text.toString()
                val cantidad = et_cantidad.text.toString().toLong()
                val deudor = Deudor(NULL, nombre, telefono, cantidad)
                val deudorDAO: DeudorDAO = SesionROOM.database.deudorDAO()
                deudorDAO.crearDeudor(deudor)
                et_nombre.setText("")
                et_telefono.setText("")
                et_cantidad.setText("")
            }
        }
    }
}