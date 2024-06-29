package com.example.moneytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytracker.dao.AccountDao
import com.example.moneytracker.dataModels.AccountModel

@Database(entities = [AccountModel::class] , version = 1)
abstract class AccountDatabase : RoomDatabase(){
    abstract fun accountDao(): AccountDao
}