package com.example.recyclerview.network


import com.example.recyclerview.EqApiResponse
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface EqApiService {
    @GET("all_hour.geojson")
    suspend fun getLastHourEarthquake(): EqApiResponse
}

private val retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

val service: EqApiService = retrofit.create(EqApiService::class.java)