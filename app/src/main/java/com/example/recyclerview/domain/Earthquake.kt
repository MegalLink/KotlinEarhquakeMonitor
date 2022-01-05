package com.example.recyclerview.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "earthquakes")
data class Earthquake(
    @PrimaryKey
    val id:String,
    val place:String,
  //  @ColumnInfo(name = "mag") if we want to save this field with other name in the table
    val magnitude:Double,
    val time:Long,
    val longitude: Double,
    val latitude:Double) {
}