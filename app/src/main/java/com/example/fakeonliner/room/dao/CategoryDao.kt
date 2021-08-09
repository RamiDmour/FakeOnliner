package com.example.fakeonliner.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fakeonliner.models.Category
import com.example.fakeonliner.room.entities.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    suspend fun getAll(): List<CategoryEntity>

    @Insert()
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Query("DELETE FROM category")
    suspend fun clearTable()
}

suspend fun CategoryDao.getAllConverted(): List<Category> =
    this.getAll().map { Category(it.title, it.categoryId) }
