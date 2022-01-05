package com.example.recyclerview.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recyclerview.domain.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EqDB: RoomDatabase() {
    abstract val eqDao:EqDAO

}

private lateinit var INSTANCE:EqDB
fun getDatabase(context: Context):EqDB{
    synchronized(EqDB::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE=Room.databaseBuilder(
                context.applicationContext,
                EqDB::class.java,
                "eartquakes.db").
            build()
        }
        return INSTANCE
    }
}