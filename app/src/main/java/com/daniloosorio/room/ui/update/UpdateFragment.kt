package com.daniloosorio.room.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.Deudor
import com.daniloosorio.room.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.et_cantidad
import kotlinx.android.synthetic.main.fragment_create.et_telefono
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_update, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_telefono.visibility =View.GONE
        et_cantidad.visibility=View.GONE
        bt_actualizar.visibility =View.GONE
        var idDeudor =0
        val deudorDAO: DeudorDAO= SesionROOM.database.deudorDAO()
        bt_buscar.setOnClickListener {
            val nombre =et_nombre2.text.toString()


            val deudor =deudorDAO.buscarDeudor(nombre)
                if(deudor != null){
                    idDeudor = deudor.id
                    et_telefono.visibility =View.VISIBLE
                    et_cantidad.visibility =View.VISIBLE
                    et_telefono.setText(deudor.telefono)
                    et_cantidad.setText(deudor.cantidad.toString())
                    bt_buscar.visibility = View.GONE
                    bt_actualizar.visibility =View.VISIBLE
                }

        }
        bt_actualizar.setOnClickListener {
            val deudor = Deudor(
                idDeudor,
                et_nombre2.text.toString(),
                et_telefono.text.toString(),
                et_cantidad.text.toString().toLong()
            )
            deudorDAO.actualizarDeudor(deudor)
            et_telefono.visibility =View.GONE
            et_cantidad.visibility =View.GONE
            bt_buscar.visibility = View.VISIBLE
            bt_actualizar.visibility =View.GONE

        }
    }
}