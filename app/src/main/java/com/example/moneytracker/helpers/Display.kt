package com.example.moneytracker.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast


class Display(private val context: Context){
    fun toast(message: Any?){
        Toast.makeText(context,"$message", Toast.LENGTH_SHORT).show()
    }
    fun log(message: Any?){
        Log.d("AddRecordActivity", "$message")
    }
}