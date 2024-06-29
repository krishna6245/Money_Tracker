package com.example.moneytracker.dao
import androidx.room.*
import com.example.moneytracker.dataModels.RecordItemModel

@Dao
interface RecordItemDao {
    @Insert
    suspend fun insertRecord(record: RecordItemModel): Long

    @Query("SELECT * FROM records")
    suspend fun getAllRecords(): List<RecordItemModel>

    suspend fun getRecordsByDate(day: Int, month: Int, year: Int): List<RecordItemModel>{
        return getAllRecords().filter { it.day == day && it.month == month && it.year == year }
    }

    suspend fun getRecordById(id: Long): RecordItemModel{
        return getAllRecords().find { it.id == id }!!
    }

    @Update
    suspend fun updateRecord(record: RecordItemModel): Int

    @Delete
    suspend fun deleteRecord(record: RecordItemModel): Int

    @Query("DELETE FROM records WHERE id = :id")
    suspend fun deleteRecordById(id: Long): Int
}
