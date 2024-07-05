package com.example.moneytracker.repository

import androidx.lifecycle.LiveData
import com.example.moneytracker.dao.AccountDao
import com.example.moneytracker.dataModels.AccountModel

class AccountRepository (private var accountDao: AccountDao) {
    val allAccounts: LiveData<List<AccountModel>> = accountDao.getAllAccounts()

    suspend fun insert(account: AccountModel): Long {
        return accountDao.insertAccount(account)
    }
    suspend fun update(account: AccountModel) {
        accountDao.updateAccount(account)
    }
    suspend fun delete(account: AccountModel) {
        accountDao.deleteAccount(account)
    }
    suspend fun getAccounts(): List<AccountModel>{
        return accountDao.getAll()
    }
    suspend fun getAccountById(accountId: Long): AccountModel {
        return accountDao.getAccountById(accountId)
    }
}