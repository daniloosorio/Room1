package com.daniloosorio.room.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.Deudor
import com.daniloosorio.room.model.DeudorDAO

class ListFragment : Fragment() {
    var allDeudores : List<Deudor> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list,container,false)
        val rv_deudores = root.findViewById<RecyclerView>(R.id.rv_deudores)
        rv_deudores.layoutManager= LinearLayoutManager(
            requireActivity().applicationContext,/***************************/
            RecyclerView.VERTICAL,
            false
        )
        rv_deudores.setHasFixedSize(true)
        var deudorDAO: DeudorDAO =SesionROOM.database.deudorDAO()
        allDeudores = deudorDAO.getDeudores()
        var deudoresRVAdapter = DeudoresRVAdapter(
            requireActivity().applicationContext,
            /******************************/
            /******************************/
            allDeudores as ArrayList<Deudor>
        )
        rv_deudores.adapter = deudoresRVAdapter
        deudoresRVAdapter.notifyDataSetChanged()
        return root
    }
}