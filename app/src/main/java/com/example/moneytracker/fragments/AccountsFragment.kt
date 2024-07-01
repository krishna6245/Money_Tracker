package com.example.moneytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.moneytracker.dataModels.AccountModel
import com.example.moneytracker.database.AccountDatabase
import com.example.moneytracker.databinding.FragmentAccountsBinding
import com.example.moneytracker.helpers.Display
import kotlinx.coroutines.launch

class AccountsFragment : Fragment() {
    private lateinit var binding: FragmentAccountsBinding

    private lateinit var dp: AccountDatabase
    private lateinit var display: Display
    private var accountList: MutableList<AccountModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }
    private fun init() {
        initializeUiElements()
        setListeners()
        setAdapters()
    }
    private fun initializeUiElements() {
        lifecycleScope.launch {
            accountList = dp.accountDao().getAllAccounts().toMutableList()
        }
    }
    private fun setListeners() {
        binding.accountsFragmentAddNewAccountButton.setOnClickListener {
            val dialog = AddAccountDialog()
            dialog.show(parentFragmentManager, "AddAccountDialog")
        }
    }
    private fun setAdapters(){

    }

}