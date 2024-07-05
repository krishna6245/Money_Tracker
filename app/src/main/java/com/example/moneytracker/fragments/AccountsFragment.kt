package com.example.moneytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneytracker.adapters.AccountItemAdapter
import com.example.moneytracker.dataModels.AccountModel
import com.example.moneytracker.database.AccountDatabase
import com.example.moneytracker.databaseClients.AccountDatabaseClient
import com.example.moneytracker.databinding.FragmentAccountsBinding
import com.example.moneytracker.helpers.Display
import com.example.moneytracker.viewModel.AccountViewModel
import kotlinx.coroutines.launch

class AccountsFragment : Fragment() {
    private lateinit var binding: FragmentAccountsBinding

    private lateinit var accountAdapter: AccountItemAdapter

    private lateinit var db: AccountDatabase
    private lateinit var display: Display
    private lateinit var accountViewModel: AccountViewModel
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
        db = AccountDatabaseClient.getInstance(requireContext())
        display = Display(requireContext())

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        accountAdapter = AccountItemAdapter(requireContext())
    }
    private fun setListeners() {
        binding.accountsFragmentAddNewAccountButton.setOnClickListener {
            val dialog = AddAccountDialog()
            dialog.show(parentFragmentManager, "AddAccountDialog")
        }
    }
    private fun setAdapters(){
        binding.accountsFragmentAccountList.layoutManager = LinearLayoutManager(requireContext())
        binding.accountsFragmentAccountList.adapter = accountAdapter

        lifecycleScope.launch {
            val accountList = accountViewModel.getAccounts()
            accountAdapter.setAccountList(accountList)

            accountViewModel.allAccounts.observe(requireActivity()) { accounts ->
                accounts?.let { accountAdapter.setAccountList(it) }
            }
        }
    }

}