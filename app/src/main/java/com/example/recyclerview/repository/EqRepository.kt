package com.example.recyclerview.repository

import androidx.lifecycle.LiveData
import com.example.recyclerview.EqApiResponse
import com.example.recyclerview.domain.Earthquake
import com.example.recyclerview.network.service
import com.example.recyclerview.persistance.EqDB

class EqRepository(private val db: EqDB) {
    val eqList: LiveData<MutableList<Earthquake>> = db.eqDao.getEarthquakes()

    suspend fun fetchEqList() {
        val response = service.getLastHourEarthquake()
        val eqList = parseResponse(response)
        db.eqDao.insertAll(eqList)
    }

    private fun parseResponse(response: EqApiResponse): MutableList<Earthquake> {

        val eqList = mutableListOf<Earthquake>()
        response.features.forEach { feature ->
            eqList.add(
                Earthquake(
                    feature.id,
                    feature.properties.place,
                    feature.properties.mag,
                    feature.properties.time,
                    feature.geometry.longitude,
                    feature.geometry.latitude
                )
            )
        }
        return eqList
    }
}


