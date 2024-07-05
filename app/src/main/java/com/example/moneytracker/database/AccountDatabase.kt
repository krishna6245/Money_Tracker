package com.example.moneytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytracker.dao.AccountDao
import com.example.moneytracker.dataModels.AccountModel


// The Database works like this (I am using Room Database)
//
// This is the main database class, which calls The DAO(data access object)
// Benefit is that using Annotations like @Dao
// I don't need to write hard code, it is handled by Room
//
// Dao interacts with database directly
// and to avoid creating multiple entities of database,
// i used a singleton class called as ___Client class
// This ensures that only one instance of database is created
//
// But to achieve synchronisation of database,
// I created a repository class, which add another level of abstraction to the database
// This helps me to observe changes to database using live data
// and also provides basic crud operation,
//
// And a single View Model class, handles all of these operations


// PS:- It is subject to change


@Database(entities = [AccountModel::class] , version = 1)
abstract class AccountDatabase : RoomDatabase(){
    abstract fun accountDao(): AccountDao
}






