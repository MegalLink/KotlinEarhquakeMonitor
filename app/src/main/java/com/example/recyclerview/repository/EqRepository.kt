package com.example.recyclerview.repository

import android.util.Log
import androidx.room.Database
import com.example.recyclerview.EqApiResponse
import com.example.recyclerview.domain.Earthquake
import com.example.recyclerview.network.service
import com.example.recyclerview.persistance.EqDB

class EqRepository(private val db: EqDB) {

    suspend fun fetchEqList(): MutableList<Earthquake> {
        val response = service.getLastHourEarthquake()
        val eqList = parseResponse(response)
        db.eqDao.insertAll(eqList)
       return db.eqDao.getEarthquakes()
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