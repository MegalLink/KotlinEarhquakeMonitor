package com.example.recyclerview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject

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

    private fun parseStringToJson(response: String): MutableList<Earthquake> {
        val responseJson = JSONObject(response)
        val features = responseJson.getJSONArray("features")

        val eqList = mutableListOf<Earthquake>()
        for (i in 0 until features.length()) {
            val feature = features[i] as JSONObject
            Log.i("feature",feature.toString())
            val id = feature.getString("id")
            val properties = feature.getJSONObject("properties")
            val magnitude = properties.getDouble("mag")
            val place = properties.getString("place")
            val time = properties.getLong("time")
            val geometry = feature.getJSONObject("geometry")
            val coordinates = geometry.getJSONArray("coordinates")
            val longitude = coordinates.getDouble(0)
            val latitude = coordinates.getDouble(1)
            val earthquake = Earthquake(id, place, magnitude, time, longitude, latitude)
            Log.i("earthquake",earthquake.toString())
            eqList.add(earthquake)
        }
        return eqList

    }

    private fun fetchEarthquakes() {

    }
    //Los metodos que se llamen dentro de una corutina debe llevar suspend
}