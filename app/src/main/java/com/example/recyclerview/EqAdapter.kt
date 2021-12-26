package com.example.recyclerview

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class EqAdapter:ListAdapter<Earthquake,EqAdapter.EqViewHolder>(DiffCallback) {
    //Ayuda al adapter a ver que elemento cambio
    companion object DiffCallback:DiffUtil.ItemCallback<Earthquake>(){
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
           return oldItem == newItem
        }
    }

    inner class EqViewHolder(val view:View):RecyclerView.ViewHolder(view){
        val magnitudeText=view.findViewById<TextView>(R.id.tv_eq_magnitude)
        val placeText=view.findViewById<TextView>(R.id.tv_eq_place)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder {
        //El parent es el recycler view donde va a estar funcionando el adapter
        val view =LayoutInflater.from(parent.context).inflate(R.layout.eq_list_item,parent,false)
        return EqViewHolder(view)
    }

    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {
        //Escribimos desde la lista a la vista del recycler view
        val earthquake=getItem(position)
        holder.magnitudeText.text=earthquake.magnitude.toString()
        holder.placeText.text=earthquake.place
    }
}