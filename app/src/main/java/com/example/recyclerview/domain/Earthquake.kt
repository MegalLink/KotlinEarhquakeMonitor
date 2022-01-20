package com.example.recyclerview.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "earthquakes")
@Parcelize
data class Earthquake(
    @PrimaryKey
    val id:String,
    val place:String,
  //  @ColumnInfo(name = "mag") if we want to save this field with other name in the table
    val magnitude:Double,
    val time:Long,
    val longitude: Double,
    val latitude:Double):Parcelable {
}