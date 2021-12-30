package com.example.recyclerview

import com.squareup.moshi.Json

data class EqApiResponse(val features:List<Feature>)
data class Feature(val id:String,val properties:Properties,val geometry:Geometry)
//We cand add Json decorator if we want another name in our data class but in the api is with other name like this
data class Properties(@Json(name = "mag") val magnitude:Double, val place:String, val time:Long )
data class Geometry(private val coordinates:Array<Double>){
    val longitude:Double get() = coordinates[0]
    val latitude:Double get() = coordinates[1]
}