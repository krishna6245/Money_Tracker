package com.example.moneytracker.helpers

import android.content.Context
import android.view.View
import com.example.moneytracker.R
import com.example.moneytracker.databinding.ActivityAddRecordBinding

class Calculator(private val binding: ActivityAddRecordBinding, private val context: Context) {
    private val display = Display(context)

    var currentAmount: Double = 0.0
        private set
    private var currentInput = "0"
    private var previousAmount: Double = 0.0
    private var previousOperator = "+"
    private var currentDecimal = 10
    private var isResultDisplayed = false
    private var decimalEnabled = true

    init {
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

            addRecordActivityDeleteButton.setOnClickListener { onDeleteClicked() }
        }
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
    private fun updateAmount(){
        binding.addRecordActivityAmount.text = currentInput
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
                    display.toast("Cannot divide by zero")
                    display.log("Cannot divide by zero")
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
            currentAmount = currentAmount *10 + number.toDouble()
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
    private fun onDeleteClicked(){
        if(currentAmount != 0.0 || currentInput.length>1){
            currentInput = currentInput.dropLast(1)
            if(currentInput.isEmpty()){
                currentInput = "0"
            }
            currentAmount = currentInput.toDoubleOrNull() ?: 0.0
            if(!decimalEnabled && currentDecimal >10){
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