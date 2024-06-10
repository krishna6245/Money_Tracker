package com.example.moneytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneytracker.R
import com.example.moneytracker.databinding.FragmentRecordsBinding

class RecordsFragment : Fragment() {
    private lateinit var binding: FragmentRecordsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }
}