package com.example.recyclerview.repository

import com.example.recyclerview.EqApiResponse
import com.example.recyclerview.domain.Earthquake
import com.example.recyclerview.network.service

class EqRepository {

    suspend fun fetchEqList():MutableList<Earthquake>{
        val response = service.getLastHourEarthquake()
        val eqList = parseStringToJson(response)
        return eqList
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
}