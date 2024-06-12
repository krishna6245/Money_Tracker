package com.example.moneytracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BlankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)

        val intent = Intent(this, AddRecordActivity::class.java)  //Redirect to opening page
        startActivity(intent)
        finish()
    }
}