package com.example.fakeonliner.repos

import com.example.fakeonliner.models.Category
import com.example.fakeonliner.room.dao.CategoryDao
import com.example.fakeonliner.room.dao.getAllConverted
import com.example.fakeonliner.service.api.OnlinerAPI
import com.example.fakeonliner.service.api.getConvertedCategories

class CategoryRepo(
    private val onlinerApi: OnlinerAPI,
    private val categoryDao: CategoryDao
) {
    suspend fun getCategories(cache: Boolean = true): List<Category> {
        return if(cache) {
            val cachedCategories = categoryDao.getAllConverted()
            if (cachedCategories.isNotEmpty()) {
                cachedCategories
            } else {
                refreshCategories()
            }
        } else {
            refreshCategories()
        }
    }

    suspend fun refreshCategories(): List<Category> {
        categoryDao.clearTable()
        categoryDao.insertAll(onlinerApi.getConvertedCategories(100, 1))
        return categoryDao.getAllConverted()
    }
}
