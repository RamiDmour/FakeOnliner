package com.example.fakeonliner.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fakeonliner.models.Category

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val categoryId: String,
    @ColumnInfo
    val title: String,
) {
    fun toDomain(): Category = Category(title, categoryId)
}