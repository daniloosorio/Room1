package com.daniloosorio.room.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.room.R
import com.daniloosorio.room.model.remote.DeudorRemote
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_deudor.view.*

class DeudoresRVAdapter(
   // var context: ArrayList<DeudorRemote>,
    var deudoresList: ArrayList<DeudorRemote>
): RecyclerView.Adapter<DeudoresRVAdapter.DeudoresViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeudoresViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_deudor,parent,false)
        return DeudoresViewHolder(itemView)
    }

    override fun getItemCount(): Int = deudoresList.size

    override fun onBindViewHolder(holder: DeudoresViewHolder, position: Int) {
        val deudor = deudoresList[position]
        holder.binDeudor(deudor)
    }


    class DeudoresViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        fun binDeudor(deudor:DeudorRemote) {
            itemView.tv_nombre.text = deudor.nombre
            itemView.tv_deuda.text = deudor.cantidad.toString()
            if (!deudor.urlPhoto.isNullOrEmpty()) {
                Picasso.get().load(deudor.urlPhoto).into(itemView.imageView4)
            itemView.setOnClickListener{
                Log.d("edudor", deudor.nombre)
            }
            }
        }
    }


}


/*
* package com.daniloosorio.room.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniloosorio.room.R
import com.daniloosorio.room.model.local.Deudor
import kotlinx.android.synthetic.main.item_deudor.view.*

class DeudoresRVAdapter (
    var context : Context,
    var deudoresList: ArrayList<Deudor>
): RecyclerView.Adapter<DeudoresRVAdapter.DeudoresViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeudoresViewHolder {
        var itemView =LayoutInflater.from(context).inflate(R.layout.item_deudor,parent,false)
        return DeudoresViewHolder(
            itemView,
            context
        )
    }

    override fun getItemCount(): Int =deudoresList.size

    override fun onBindViewHolder(
        holder: DeudoresViewHolder,
        position: Int
    ) {
       val deudor : Deudor = deudoresList[position]
        holder.bindDeudor(deudor)
    }

    class DeudoresViewHolder(
        itemView: View,
        context: Context
    ): RecyclerView.ViewHolder(itemView){
        fun bindDeudor(deudor: Deudor) {
            itemView.tv_nombre.text = deudor.nombre
            itemView.tv_deuda.text = deudor.cantidad.toString()
        }
    }
}*/