package com.example.fakeonliner.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fakeonliner.room.dao.CategoryDao
import com.example.fakeonliner.room.entities.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
