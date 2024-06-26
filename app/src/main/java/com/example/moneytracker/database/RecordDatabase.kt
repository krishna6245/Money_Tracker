package com.example.moneytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytracker.dao.RecordItemDao
import com.example.moneytracker.dataModels.RecordItemModel

@Database(entities = [RecordItemModel::class], version = 1)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordItemDao
}
