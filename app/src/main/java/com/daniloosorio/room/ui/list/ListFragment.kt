package com.daniloosorio.room.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.room.R
import com.daniloosorio.room.model.local.Deudor
import com.daniloosorio.room.model.remote.DeudorRemote
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    var allDeudores : MutableList<DeudorRemote> = mutableListOf()
    lateinit var deudoresRVAdapter: DeudoresRVAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_list,container,false)
        val rv_deudores = root.findViewById<RecyclerView>(R.id.rv_deudores)
        rv_deudores.layoutManager= LinearLayoutManager(
            requireActivity().applicationContext,/***************************/
            RecyclerView.VERTICAL,
            false
        )
        deudoresRVAdapter = DeudoresRVAdapter(
            requireActivity().applicationContext,
            allDeudores as ArrayList<DeudorRemote>
        )
        return root
    }

    override fun onResume() {
        super.onResume()
        cargarDeudores()
    }

    private fun cargarDeudores(){
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("deudores")
        allDeudores.clear()
        val postListener = object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children){
                    //System.out.println(dataSnapshot.toString())
                    val deudor= snapshot.getValue(DeudorRemote::class.java)
                    allDeudores.add(deudor!!)
                }
                deudoresRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ListFragment:","loasPost:onCancelled",databaseError.toException())
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