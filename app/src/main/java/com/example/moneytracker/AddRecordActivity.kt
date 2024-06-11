package com.example.moneytracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moneytracker.databinding.ActivityAddRecordBinding

class AddRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}