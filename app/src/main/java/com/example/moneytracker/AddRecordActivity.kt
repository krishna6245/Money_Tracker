package com.example.moneytracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.example.moneytracker.dataModels.RecordItemModel
import com.example.moneytracker.database.RecordDatabase
import com.example.moneytracker.databaseClients.RecordDatabaseClient
import com.example.moneytracker.databinding.ActivityAddRecordBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class AddRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecordBinding

    private lateinit var db: RecordDatabase

    private var monthNamesShort = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0
    private var currentHour = 0
    private var currentMinute = 0

    private var currentRecordMode = "expense"
    private var toAccount = ""
    private var fromAccount = ""
    private var category = ""

    private var previousAmount: Double = 0.0
    private var previousOperator = "+"
    private var currentAmount: Double = 0.0
    private var currentInput = "0"
    private var currentDecimal = 10
    private var isResultDisplayed = false
    private var decimalEnabled = true

    private val accentColor = R.color.accent_color
    private val accentColorLight = R.color.accent_color_light

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        initializeUiElements()
        setListeners()
        setCalculator()
    }
    private fun initializeUiElements(){
        val calendar = Calendar.getInstance()

        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)
        setDate()

        currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        currentMinute = calendar.get(Calendar.MINUTE)
        setTime()

        binding.addRecordActivityAmount.text = "0"

        db = RecordDatabaseClient.getInstance(this)
    }
    private fun setDate(){
        val monthName = monthNamesShort[currentMonth]

        val date = "$monthName $currentDay, $currentYear"
        binding.addRecordActivityDateSelect.text = date
    }
    private fun setTime(){
        val amPmString = if (currentHour < 12) "AM" else "PM"
        val currentAdjustedHour = if (currentHour > 12) currentHour - 12 else currentHour

        val time = "$currentAdjustedHour:$currentMinute $amPmString"
        binding.addRecordActivityTimeSelect.text = time
    }
    private fun setListeners(){
        binding.addRecordActivityCancelButton.setOnClickListener {
            finish()
        }
        binding.addRecordActivitySaveButton.setOnClickListener {
            lifecycleScope.launch {
                val record = RecordItemModel(
                    recordType = currentRecordMode,
                    fromAccount = fromAccount,
                    toAccount = toAccount,
                    category = category,
                    amount = currentAmount,
                    day = currentDay,
                    month = currentMonth,
                    year = currentYear,
                    hour = currentHour,
                    minute = currentMinute
                )
                record.id = db.recordDao().insert(record)
                log("Record saved")
                log(record)
                toast("Record saved")
            }
        }

        binding.addRecordActivityIncomeButton.setOnClickListener {
            updateRecordMode("income")
        }
        binding.addRecordActivityExpenseButton.setOnClickListener {
            updateRecordMode("expense")
        }
        binding.addRecordActivityTransferButton.setOnClickListener {
            updateRecordMode("transfer")
        }

        binding.addRecordActivityToButton.setOnClickListener {
            //selectTo()
        }
        binding.addRecordActivityFromButton.setOnClickListener {
            //selectFrom()
        }

        binding.addRecordActivityDateSelect.setOnClickListener{
            showDatePickerDialog()
        }
        binding.addRecordActivityTimeSelect.setOnClickListener {
            showTimePickerDialog()
        }
    }
    private fun showDatePickerDialog(){
        val datePickerDialog = DatePickerDialog(this,R.style.CustomDatePickerDialogTheme, { _, selectedYear, selectedMonth, selectedDay ->
            currentYear = selectedYear
            currentMonth = selectedMonth
            currentDay = selectedDay
            setDate()
        }, currentYear, currentMonth, currentDay)

        datePickerDialog.show()
    }
    private fun showTimePickerDialog(){
        val timePickerDialog = TimePickerDialog(this,R.style.CustomTimePickerDialogTheme ,{ _, selectedHour, selectedMinute ->
            currentHour = selectedHour
            currentMinute = selectedMinute
            setTime()
        }, currentHour, currentMinute, false)
        timePickerDialog.show()
    }
    private fun updateRecordMode(newRecordMode : String){
        setRecordModeColor(currentRecordMode,accentColorLight)
        setRecordModeColor(newRecordMode,accentColor)

        currentRecordMode = newRecordMode

        if (newRecordMode == "transfer"){
            binding.addRecordActivityFromText.text = "From"
            binding.addRecordActivityToText.text = "To"
            binding.addRecordActivityToImage.setImageResource(R.drawable.accounts)
            binding.addRecordActivityToName.text = "Account"
        }
        else{
            binding.addRecordActivityFromText.text = "Account"
            binding.addRecordActivityToText.text = "Category"
            binding.addRecordActivityToImage.setImageResource(R.drawable.categories)
            binding.addRecordActivityToName.text = "Category"
        }
    }
    private fun setRecordModeColor(recordMode: String , textColor: Int){
        when (recordMode){
            "income" -> binding.addRecordActivityIncomeButton.setTextColor(getColor(textColor))
            "expense" -> binding.addRecordActivityExpenseButton.setTextColor(getColor(textColor))
            "transfer" -> binding.addRecordActivityTransferButton.setTextColor(getColor(textColor))
        }
    }

    //The Calculator Part
    private fun setCalculator(){
        binding.apply {
            addRecordActivityButton0.setOnClickListener { onNumberClicked("0") }
            addRecordActivityButton1.setOnClickListener { onNumberClicked("1") }
            addRecordActivityButton2.setOnClickListener { onNumberClicked("2") }
            addRecordActivityButton3.setOnClickListener { onNumberClicked("3") }
            addRecordActivityButton4.setOnClickListener { onNumberClicked("4") }
            addRecordActivityButton5.setOnClickListener { onNumberClicked("5") }
            addRecordActivityButton6.setOnClickListener { onNumberClicked("6") }
            addRecordActivityButton7.setOnClickListener { onNumberClicked("7") }
            addRecordActivityButton8.setOnClickListener { onNumberClicked("8") }
            addRecordActivityButton9.setOnClickListener { onNumberClicked("9") }
            addRecordActivityButtonDot.setOnClickListener { onNumberClicked(".") }

            addRecordActivityButtonPlus.setOnClickListener { onOperatorClicked("+") }
            addRecordActivityButtonMinus.setOnClickListener { onOperatorClicked("-") }
            addRecordActivityButtonMultiply.setOnClickListener { onOperatorClicked("*") }
            addRecordActivityButtonDivide.setOnClickListener { onOperatorClicked("/") }

            addRecordActivityButtonEqualTo.setOnClickListener { onEqualsClicked() }

            addRecordActivityDeleteButton.setOnClickListener {
                if(currentAmount != 0.0 || currentInput.length>1){
                    currentInput = currentInput.dropLast(1)
                    if(currentInput.isEmpty()){
                        currentInput = "0"
                    }
                    currentAmount = currentInput.toDoubleOrNull() ?: 0.0
                    if(!decimalEnabled && currentDecimal>10){
                        currentDecimal /= 10
                    }
                    else{
                        decimalEnabled = true
                        currentDecimal = 10
                    }
                    updateAmount()
                }
            }
        }
    }
    private fun onNumberClicked(number: String){
        if (previousOperator == "="){
            resetAmount()
            previousOperator = "+"
            currentAmount = 0.0
        }
        if (isResultDisplayed){
            isResultDisplayed = false
            decimalEnabled = true
            previousAmount = currentAmount
            currentAmount = 0.0
            currentDecimal = 10
            currentInput = ""
        }
        if (number == "."){
            if(decimalEnabled){
                decimalEnabled = false
                currentInput += "."
                updateAmount()
            }
            return
        }
        if (decimalEnabled){
            currentAmount = currentAmount*10 + number.toDouble()
            if(currentAmount >= 1000000000){
                currentAmount /= 10
                return
            }
            currentInput = currentAmount.toInt().toString()
        }
        else {
            if(currentDecimal > 100){
                return
            }
            currentAmount += number.toDouble() / currentDecimal
            currentDecimal *= 10

            currentInput += number
        }
        updateAmount()
    }
    private fun onOperatorClicked(operator: String){
        if(!isResultDisplayed){
            if (!performCalculation()){
                resetAmount()
                return
            }
        }
        binding.addRecordActivityPreviousOperator.visibility = View.VISIBLE
        when (operator){
            "+" -> binding.addRecordActivityPreviousOperator.setImageResource(R.drawable.add_sign_icon)
            "-" -> binding.addRecordActivityPreviousOperator.setImageResource(R.drawable.minus_sign_icon)
            "*" -> binding.addRecordActivityPreviousOperator.setImageResource(R.drawable.multiply_sign_icon)
            "/" -> binding.addRecordActivityPreviousOperator.setImageResource(R.drawable.divide_sign_icon)
        }
        previousOperator = operator
    }
    private fun onEqualsClicked(){
        if(!isResultDisplayed){
            if(!performCalculation()){
                resetAmount()
                return
            }
        }
        binding.addRecordActivityPreviousOperator.visibility = View.INVISIBLE
        previousAmount = 0.0
        previousOperator = "="
    }
    private fun resetAmount(){
        previousAmount = 0.0
        previousOperator = "+"
        currentAmount = 0.0
        currentInput = "0"
        currentDecimal = 10
        isResultDisplayed = false
        decimalEnabled = true
        binding.addRecordActivityPreviousOperator.visibility = View.INVISIBLE
    }
    private fun performCalculation(): Boolean{
        fun Double.round(decimals: Int): Double {
            return "%.${decimals}f".format(this).toDouble()
        }
        when (previousOperator){
            "+" -> previousAmount += currentAmount
            "-" -> previousAmount -= currentAmount
            "*" -> previousAmount *= currentAmount
            "/" -> {
                if (currentAmount == 0.0){
                    toast("Cannot divide by zero")
                    log("Cannot divide by zero")
                    updateAmount()
                    return false
                }
                previousAmount /= currentAmount
            }
        }
        currentAmount = previousAmount.round(2)
        currentInput = if(currentAmount % 1 == 0.0){
            currentAmount.toInt().toString()
        }
        else{
            currentAmount.toString()
        }
        decimalEnabled = true
        isResultDisplayed = true

        updateAmount()

        return true
    }
    private fun updateAmount(){
        binding.addRecordActivityAmount.text = currentInput
    }

    private fun toast(message: Any?){
        Toast.makeText(this,"$message", Toast.LENGTH_SHORT).show()
    }
    private fun log(message: Any?){
        Log.d("AddRecordActivity", "$message")
    }
}