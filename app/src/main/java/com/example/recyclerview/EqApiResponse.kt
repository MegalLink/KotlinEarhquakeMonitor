package com.example.recyclerview

data class EqApiResponse(val features:List<Feature>)
data class Feature(val id:String,val properties:Properties,val geometry:Geometry)
data class Properties(val mag:Double,val place:String,val time:Long )
data class Geometry(private val coordinates:Array<Double>){
    val longitude:Double get() = coordinates[0]
    val latitude:Double get() = coordinates[1]
}