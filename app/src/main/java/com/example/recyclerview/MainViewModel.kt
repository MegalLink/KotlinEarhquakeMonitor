package com.example.recyclerview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>> = _eqList

    init {
        viewModelScope.launch {
            val eqList= service.getLastHourEarthquake()
            Log.i("REQUEST",eqList)
        }
    }

    private fun fetchEarthquakes(){

    }
    //Los metodos que se llamen dentro de una corutina debe llevar suspend
}