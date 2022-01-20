package com.example.recyclerview.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recyclerview.EqApiResponse
import com.example.recyclerview.domain.Earthquake
import com.example.recyclerview.network.service
import com.example.recyclerview.persistance.EqDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EqRepository(private val db: EqDB) {

    suspend fun fetchEqList(sortByMagnitude: Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            val response = service.getLastHourEarthquake()
            val eqList = parseResponse(response)
            db.eqDao.insertAll(eqList)
            fetchEqListFromDB(sortByMagnitude)
        }
    }

    suspend fun fetchEqListFromDB(sortByMagnitude: Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            if (sortByMagnitude) {
                db.eqDao.getEarthquakesOrderByMagnitude()
            } else {
                db.eqDao.getEarthquakes()
            }
        }
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


