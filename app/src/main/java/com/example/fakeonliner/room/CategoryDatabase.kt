package com.example.fakeonliner.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fakeonliner.room.dao.CategoryDao
import com.example.fakeonliner.room.entities.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var instance: CategoryDatabase? = null

        fun getInstance(application: Application): CategoryDatabase {
            if (instance == null) {
                    instance = Room.databaseBuilder(
                        application,
                        CategoryDatabase::class.java,
                        "category_database"
                    ).build()
            }
            return instance!!
        }
    }
}