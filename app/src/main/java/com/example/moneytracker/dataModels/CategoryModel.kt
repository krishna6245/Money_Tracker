package com.example.moneytracker.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var image: Int,
    val type: String, //Income category or Expense category
    var enabled: Boolean = true,
)
