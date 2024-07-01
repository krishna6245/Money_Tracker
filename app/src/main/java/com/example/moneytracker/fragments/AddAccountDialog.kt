package com.example.moneytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.moneytracker.R
import com.example.moneytracker.dataModels.AccountModel
import com.example.moneytracker.database.AccountDatabase
import com.example.moneytracker.databaseClients.AccountDatabaseClient
import com.example.moneytracker.databinding.AddAccountDialogBinding
import com.example.moneytracker.helpers.Display
import kotlinx.coroutines.launch

class AddAccountDialog : DialogFragment() {
    private lateinit var binding: AddAccountDialogBinding
    private lateinit var db: AccountDatabase

    private lateinit var display: Display
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = AddAccountDialogBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        db = AccountDatabaseClient.getInstance(requireContext())
        display = Display(requireContext())
    }
    private fun setListeners(){
        binding.addAccountDialogCancel.setOnClickListener{
            dismiss()
        }
        binding.addAccountDialogSave.setOnClickListener{
            lifecycleScope.launch {
                val accountItem = AccountModel(
                    name = binding.addAccountDialogName.text.toString(),
                    image = R.drawable.cash_icon,
                    balance = binding.addAccountDialogInitialAmount.text.toString().toDouble(),
                    startingAmount = binding.addAccountDialogInitialAmount.text.toString().toDouble()
                )
                accountItem.id = db.accountDao().insertAccount(accountItem)

                display.log("Account created:- $accountItem")
                dismiss()
            }
        }
    }

}