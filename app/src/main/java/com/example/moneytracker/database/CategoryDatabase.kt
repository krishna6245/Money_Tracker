package com.example.moneytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytracker.dao.CategoryDao
import com.example.moneytracker.dataModels.CategoryModel

@Database(entities = [CategoryModel::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}