package com.example.fakeonliner.repos

import com.example.fakeonliner.models.Category
import com.example.fakeonliner.service.api.OnlinerAPI
import retrofit2.await

class CategoryRepo(private val onlinerApi: OnlinerAPI) {

    suspend fun getCategories(): List<Category> {
        val response = onlinerApi.getCategories(100, 1).await()

        return response.schemas.map { Category(it.name, it.key) }
    }
}
