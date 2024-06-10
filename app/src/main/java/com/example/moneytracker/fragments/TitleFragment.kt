package com.example.moneytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneytracker.R
import com.example.moneytracker.databinding.FragmentTitleBinding
import java.util.Calendar

class TitleFragment : Fragment() {
    private lateinit var binding: FragmentTitleBinding

    private var monthNamesShort = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private var monthNames = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    private var monthLengths = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
        val calendar = Calendar.getInstance()

        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)

        setDateRange()
    }
    private fun setListeners(){
        binding.titleFragmentLeftButton.setOnClickListener {
            currentMonth--
            if(currentMonth<0){
                currentMonth=11
                currentYear--
            }
            setDateRange()
        }
        binding.titleFragmentRightButton.setOnClickListener {
            currentMonth++
            if(currentMonth>11){
                currentMonth=0
                currentYear++
                }
            setDateRange()
        }
    }
    private fun setDateRange(){
        if(currentYear%4==0){
            monthLengths[1]=29
        }
        else{
            monthLengths[1]=28
        }
        val monthName = monthNames[currentMonth]
        val text = "$monthName, $currentYear"

        binding.titleFragmentDateRange.text = text
    }
}