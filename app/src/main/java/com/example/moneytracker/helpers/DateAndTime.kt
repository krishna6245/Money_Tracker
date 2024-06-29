package com.example.moneytracker.helpers

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.example.moneytracker.R
import com.example.moneytracker.databinding.ActivityAddRecordBinding
import java.util.Calendar

class DateAndTime(private val binding: ActivityAddRecordBinding, private val context: Context){
    var currentDay = 0
        private set
    var currentMonth = 0
        private set
    var currentYear = 0
        private set
    var currentHour = 0
        private set
    var currentMinute = 0
        private set
    private var monthNamesShort = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")


    init {
        val calendar = Calendar.getInstance()

        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)
        setDate()

        currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        currentMinute = calendar.get(Calendar.MINUTE)
        setTime()

        binding.addRecordActivityDateSelect.setOnClickListener{ showDatePickerDialog()}
        binding.addRecordActivityTimeSelect.setOnClickListener { showTimePickerDialog()}
    }

    private fun setDate(){
        val monthName = monthNamesShort[currentMonth]

        val date = "$monthName $currentDay, $currentYear"
        binding.addRecordActivityDateSelect.text = date
    }
    private fun setTime(){
        val amPmString = if (currentHour < 12) "AM" else "PM"
        val currentAdjustedHour = if (currentHour > 12) currentHour - 12 else if (currentHour == 0) 12 else currentHour

        val time = "$currentAdjustedHour:$currentMinute $amPmString"
        binding.addRecordActivityTimeSelect.text = time
    }
    private fun showDatePickerDialog(){
        val datePickerDialog = DatePickerDialog(context,
            R.style.CustomDatePickerDialogTheme, { _, selectedYear, selectedMonth, selectedDay ->
            currentYear = selectedYear
            currentMonth = selectedMonth
            currentDay = selectedDay
            setDate()
        }, currentYear, currentMonth, currentDay)
        datePickerDialog.show()
    }
    private fun showTimePickerDialog(){
        val timePickerDialog = TimePickerDialog(context,
            R.style.CustomTimePickerDialogTheme ,{ _, selectedHour, selectedMinute ->
            currentHour = selectedHour
            currentMinute = selectedMinute
            setTime()
        }, currentHour, currentMinute, false)
        timePickerDialog.show()
    }

}