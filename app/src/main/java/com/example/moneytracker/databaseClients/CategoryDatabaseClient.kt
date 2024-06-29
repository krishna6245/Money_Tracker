package com.example.moneytracker.databaseClients

import android.content.Context
import androidx.room.Room
import com.example.moneytracker.database.CategoryDatabase

object CategoryDatabaseClient {
    private var instance: CategoryDatabase? = null

    fun getInstance(context: Context): CategoryDatabase {
        if (instance == null) {
            synchronized(CategoryDatabase::class) {
                instance = Room.databaseBuilder(
                    context,
                    CategoryDatabase::class.java,
                    "category.db"
                ).build()
            }
        }
        return instance!!
    }
}