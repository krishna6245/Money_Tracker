package com.example.moneytracker.databaseClients

import android.content.Context
import androidx.room.Room
import com.example.moneytracker.database.AccountDatabase

object AccountDatabaseClient {
    private var instance: AccountDatabase? = null

    fun getInstance(context: Context): AccountDatabase {
        if (instance == null) {
            synchronized(AccountDatabase::class) {
                instance = Room.databaseBuilder(
                    context,
                    AccountDatabase::class.java,
                    "account.db"
                ).build()
            }
        }
        return instance!!
    }
}