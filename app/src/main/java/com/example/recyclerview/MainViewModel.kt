package com.example.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>> = _eqList

    init {
        viewModelScope.launch {
            val response = service.getLastHourEarthquake()
            val eqList = parseStringToJson(response)
            _eqList.postValue(eqList)
        }
    }

    private fun parseStringToJson(response: EqApiResponse): MutableList<Earthquake> {

        val eqList = mutableListOf<Earthquake>()
        response.features.forEach { feature ->
            eqList.add(
                Earthquake(
                    feature.id,
                    feature.properties.place,
                    feature.properties.magnitude,
                    feature.properties.time,
                    feature.geometry.longitude,
                    feature.geometry.latitude
                )
            )
        }
        return eqList

    }

    private fun fetchEarthquakes() {

    }
    //Los metodos que se llamen dentro de una corutina debe llevar suspend
}