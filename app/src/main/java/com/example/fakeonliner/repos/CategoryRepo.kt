package com.example.fakeonliner.repos

import com.example.fakeonliner.models.Category
import com.example.fakeonliner.room.dao.CategoryDao
import com.example.fakeonliner.room.entities.CategoryEntity
import com.example.fakeonliner.service.api.OnlinerAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CategoryRepo(
    private val onlinerApi: OnlinerAPI,
    private val categoryDao: CategoryDao
) {
    suspend fun getCategories(cache: Boolean = true): List<Category> {
        return if (!cache) {
            val response = onlinerApi.getCategories(100, 1)
            val categories: List<Category> = response.schemas.map { Category(it.name, it.key) }
            withContext(Dispatchers.IO) {
                categoryDao.clearTable()
                categoryDao
                    .insertAll(categories.map { CategoryEntity(it.categoryId, it.title) })}
            categories
        } else {
            var categories: List<Category>;
            withContext(Dispatchers.IO) {
                val cachedCategories: List<CategoryEntity> = categoryDao.getAll()
                if (cachedCategories.isNotEmpty()) {
                    categories = cachedCategories.map { Category(it.title, it.categoryId) }
                } else {
                    val response = onlinerApi.getCategories(100, 1)
                    categories = response.schemas.map { Category(it.name, it.key) }
                    categoryDao
                        .insertAll(categories.map { CategoryEntity(it.categoryId, it.title) })
                }
            }
            categories
        }
    }
}
