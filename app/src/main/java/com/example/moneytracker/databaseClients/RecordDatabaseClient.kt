package com.example.moneytracker.databaseClients

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.moneytracker.database.RecordDatabase

object RecordDatabaseClient  {
    private var instance: RecordDatabase? = null

    fun getInstance(context: Context): RecordDatabase {
        if (instance == null) {
            synchronized(RecordDatabase::class) {
                instance = Room.databaseBuilder(
                    context,
                    RecordDatabase::class.java,
                    "records.db"
                ).build()
            }
        }
        return instance!!
    }
}