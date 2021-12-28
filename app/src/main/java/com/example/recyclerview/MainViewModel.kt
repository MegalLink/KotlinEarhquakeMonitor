package com.example.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    private val _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>> = _eqList

    init {

    }

    private fun fetchEarthquakes(){
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
        this._eqList.value=eqList
    }
}