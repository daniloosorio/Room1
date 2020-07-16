package com.daniloosorio.room.ui.list

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.room.R
import com.daniloosorio.room.model.remote.DeudorRemote
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private val deudoresList :MutableList<DeudorRemote> = mutableListOf()
    private lateinit var deudoresAdapter :DeudoresRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarDeudores()

        rv_deudores.layoutManager= LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        rv_deudores.setHasFixedSize(true)
       deudoresAdapter = DeudoresRVAdapter(deudoresList as ArrayList<DeudorRemote>)
        rv_deudores.adapter =deudoresAdapter
    }

    private fun cargarDeudores() {
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("deudores")
        val postListener = object  : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot : DataSnapshot in snapshot.children){
                    val deudor =datasnapshot.getValue(DeudorRemote::class.java)
                    deudoresList.add(deudor!!)
                }
                deudoresAdapter.notifyDataSetChanged()
            }

        }
        myRef.addValueEventListener(postListener)
    }
 }

/*
* package com.daniloosorio.room.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.room.R
import com.daniloosorio.room.SesionROOM
import com.daniloosorio.room.model.local.Deudor
import com.daniloosorio.room.model.local.DeudorDAO

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
}*/