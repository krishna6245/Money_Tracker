package com.example.moneytracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moneytracker.dataModels.RecordItemModel
import com.example.moneytracker.database.RecordDatabase
import com.example.moneytracker.databaseClients.RecordDatabaseClient
import com.example.moneytracker.databinding.ActivityAddRecordBinding
import com.example.moneytracker.helpers.*
import kotlinx.coroutines.launch

@SuppressLint("SetTextI18n")
class AddRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecordBinding

    private lateinit var calculator: Calculator
    private lateinit var display: Display
    private lateinit var dateAndTime: DateAndTime
    private lateinit var db: RecordDatabase

    private var currentRecordMode = "expense"
    private var toAccount = ""
    private var fromAccount = ""
    private var category = ""

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
    }
    private fun initializeUiElements(){
        binding.addRecordActivityAmount.text = "0"

        db = RecordDatabaseClient.getInstance(this)
        calculator = Calculator(binding,this)
        display = Display(this)
        dateAndTime = DateAndTime(binding,this)
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
                    amount = calculator.currentAmount,
                    day = dateAndTime.currentDay,
                    month = dateAndTime.currentMonth,
                    year = dateAndTime.currentYear,
                    hour = dateAndTime.currentHour,
                    minute = dateAndTime.currentMinute
                )
                record.id = db.recordDao().insertRecord(record)
                display.log("Record saved:- $record")
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
}
