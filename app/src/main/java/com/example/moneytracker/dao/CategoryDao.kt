package com.example.moneytracker.dao

import androidx.room.*
import com.example.moneytracker.dataModels.CategoryModel

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: CategoryModel): Long

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<CategoryModel>

    @Query("SELECT * FROM category WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): CategoryModel

    @Query("SELECT * FROM category WHERE type = :type")
    suspend fun getCategoriesByTypes(type: String): List<CategoryModel>

    @Update
    suspend fun updateCategory(category: CategoryModel)

    @Delete
    suspend fun deleteCategory(category: CategoryModel)
}