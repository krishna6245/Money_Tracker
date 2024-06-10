package com.example.moneytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneytracker.R
import com.example.moneytracker.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {
    private lateinit var binding: FragmentBudgetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }
}