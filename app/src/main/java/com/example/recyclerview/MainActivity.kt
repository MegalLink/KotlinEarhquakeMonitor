package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    private val adapter = EqAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initListeners()
        initObservers()
        service.getLastHourEarthquake()
    }

    private fun initObservers(){
        mainViewModel.eqList.observe(this, Observer {
            adapter.submitList(it)
            handleEmptyView(it)
        })
    }

    private fun initListeners(){
        //Inicializamos o damos la funcionalidad a onItemClicListener
        adapter.onItemClickListener = {
            Toast.makeText(this,it.place,Toast.LENGTH_SHORT).show()
        }

    }

    private fun initRecyclerView(){
        binding.rvEq.layoutManager = LinearLayoutManager(this)
        binding.rvEq.adapter = adapter
    }

    private fun handleEmptyView(list:MutableList<Earthquake>){
        if(list.isEmpty()){
            binding.tvEmptyEq.visibility = View.VISIBLE
        }else{
            binding.tvEmptyEq.visibility = View.GONE
        }
    }
}