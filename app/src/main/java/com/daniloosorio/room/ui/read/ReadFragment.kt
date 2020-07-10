package com.daniloosorio.room.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.DeudorDAO
import kotlinx.android.synthetic.main.fragment_read.*

class ReadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_read, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_resultado.visibility= View.GONE
        bt_buscar.setOnClickListener {
            val nombre =et_nombre.text.toString()
            val deudorDAO :DeudorDAO = SesionROOM.database.deudorDAO()
            val deudor = deudorDAO.buscarDeudor(nombre)
            if(deudor != null){
                tv_resultado.visibility= View.VISIBLE
                tv_resultado.text ="nombre :${deudor.nombre}\n" +
                        "telefono :${deudor.telefono}\n" +
                        "cantidad: ${deudor.cantidad}\n"
            }else{
                Toast.makeText(context, "deudor no existe", Toast.LENGTH_SHORT).show()
            }
        }
    }
}