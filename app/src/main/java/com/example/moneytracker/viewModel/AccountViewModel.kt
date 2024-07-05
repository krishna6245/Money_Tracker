package com.example.moneytracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.dataModels.AccountModel
import com.example.moneytracker.databaseClients.AccountDatabaseClient
import com.example.moneytracker.repository.AccountRepository
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AccountRepository
    val allAccounts: LiveData<List<AccountModel>>

    init {
        val accountDao = AccountDatabaseClient.getInstance(application).accountDao()
        repository = AccountRepository(accountDao)
        allAccounts = repository.allAccounts
    }
    suspend fun insert(account: AccountModel): Long {
        return repository.insert(account)
    }

    suspend fun getAccounts(): List<AccountModel>{
        return repository.getAccounts()
    }

    suspend fun update(account: AccountModel) = viewModelScope.launch {
        repository.update(account)
    }

    suspend fun delete(account: AccountModel) = viewModelScope.launch {
        repository.delete(account)
    }

    fun getAccountById(accountId: Long) = viewModelScope.launch {
        repository.getAccountById(accountId)
    }
}