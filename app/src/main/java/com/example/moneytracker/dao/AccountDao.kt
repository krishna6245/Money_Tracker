package com.example.moneytracker.dao
import androidx.room.*
import com.example.moneytracker.dataModels.AccountModel

@Dao
interface AccountDao {
    @Insert
    suspend fun insertAccount(account: AccountModel): Long

    @Query("SELECT * FROM account")
    suspend fun getAllAccounts(): List<AccountModel>

    @Query("SELECT * FROM account WHERE id = :accountId")
    suspend fun getAccountById(accountId: Long): AccountModel

    @Update
    suspend fun updateAccount(account: AccountModel)

    @Delete
    suspend fun deleteAccount(account: AccountModel)
}