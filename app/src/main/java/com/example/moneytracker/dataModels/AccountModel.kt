package com.example.moneytracker.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "account" )
data class AccountModel(
    @PrimaryKey( autoGenerate = true )
    var id: Long,
    var name: String,
    var image: Int,
    var balance: Double,
    var startingAmount: Double,
    var enabled: Boolean = true,
)
