package com.example.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.EqListItemBinding

private val TAG=EqAdapter::class.java.simpleName

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
    //Se inicializa en otro lado
    lateinit var onItemClickListener:(Earthquake)-> Unit

    inner class EqViewHolder(private val binding:EqListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(earthquake: Earthquake){
            binding.tvEqMagnitude.text=earthquake.magnitude.toString()
            binding.tvEqPlace.text=earthquake.place
            //binding.root hace referencia a view
            binding.root.setOnClickListener{
                //Validacion para ver si se inicializo el listener , en este caso en main
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                }else{
                    Log.e(TAG,"onItemClicListener not initialized")
                }

            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder {
        //El parent es el recycler view donde va a estar funcionando el adapter
        val binding= EqListItemBinding.inflate(
            LayoutInflater.from(parent.context))

        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {
        //Escribimos desde la lista a la vista del recycler view
        val earthquake=getItem(position)
        holder.bind(earthquake)
    }
}