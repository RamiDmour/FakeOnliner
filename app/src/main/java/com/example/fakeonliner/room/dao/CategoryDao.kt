package com.example.fakeonliner.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fakeonliner.room.entities.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAll(): List<CategoryEntity>

    @Insert()
    fun insertAll(categories: List<CategoryEntity>)

    @Delete
    fun delete(category: CategoryEntity)

    @Query("DELETE FROM category")
    fun clearTable()
}