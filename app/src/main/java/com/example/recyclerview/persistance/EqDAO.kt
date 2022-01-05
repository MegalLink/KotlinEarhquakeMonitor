package com.example.recyclerview.persistance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recyclerview.domain.Earthquake

@Dao
interface EqDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //remplaza si se mete un elemento con el mismo id
    suspend fun insertAll(eqList:MutableList<Earthquake>)
    @Query(value = "Select * from earthquakes")
    fun getEarthquakes():LiveData<MutableList<Earthquake>> //with live data this emits the list
    //every time that detects a change in the table
    /*
    @Delete
    fun deleteEarthquake(vararg eq:Earthquake) //vararg para pasarle una lista de terremotos
    @Update
    fun updateEq(vararg eq:Earthquake)
    @Query(value = "Select * from earthquakes where magnitude > :mag")
    fun getEarthquakesWithMagnitude(mag:Double):MutableList<Earthquake>
*/
}