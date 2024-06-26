package com.example.moneytracker.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordItemModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var recordType: String?,  // can be - transfer/expense/income
    var fromAccount: String?, // applicable in transfer/expense
    var toAccount: String?,   // applicable in transfer/income
    var category: String?,    // applicable in income/expense
    var amount: Double = 0.0,
    var day:   Int = 1,
    var month: Int = 1,
    var year:  Int = 2000,
    var hour: Int = 12,
    var minute: Int = 0,
)
