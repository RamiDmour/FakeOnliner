package com.example.fakeonliner.repos

import com.example.fakeonliner.models.Category
import com.example.fakeonliner.room.dao.CategoryDao
import com.example.fakeonliner.service.api.OnlinerAPI

class CategoryRepo(
    private val onlinerApi: OnlinerAPI,
    private val categoryDao: CategoryDao
) {
    suspend fun getCategories(cache: Boolean = true): List<Category> {
        return if(cache) {
            val cachedCategories = categoryDao.getAll().map { it.toDomain() }
            if (cachedCategories.isNotEmpty()) {
                cachedCategories
            } else {
                refreshCategories()
            }
        } else {
            refreshCategories()
        }
    }

    private suspend fun refreshCategories(): List<Category> {
        categoryDao.clearTable()
        categoryDao.insertAll(onlinerApi.getCategories(100, 1).toEntity())
        return categoryDao.getAll().map { it.toDomain() }
    }
}
