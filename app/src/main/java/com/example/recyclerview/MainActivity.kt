package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEq.layoutManager=LinearLayoutManager(this)
        val eqList= mutableListOf<Earthquake>()
        eqList.add(Earthquake("1","Buenos aires",4.3,1234543453,-103.423,28.233))
        eqList.add(Earthquake("2","Quito",4.3,1234543453,-103.423,28.233))
        eqList.add(Earthquake("3","Malos aires",4.3,1234543453,-103.423,28.233))
        eqList.add(Earthquake("4","Bogota",4.3,1234543453,-103.423,28.233))
        eqList.add(Earthquake("5","Santiago de chile",4.3,1234543453,-103.423,28.233))
        eqList.add(Earthquake("6","Lima",3.1,1234543453,-103.423,28.233))
        eqList.add(Earthquake("7","Guayaquil",4.3,1234543453,-103.423,28.233))
        eqList.add(Earthquake("8","Tulcan",4.3,1234543453,-103.423,28.233))
        eqList.add(Earthquake("9","Mexico DF",4.3,1234543453,-103.423,28.233))

        val adapter=EqAdapter()
        binding.rvEq.adapter=adapter
        adapter.submitList(eqList)

    }
}