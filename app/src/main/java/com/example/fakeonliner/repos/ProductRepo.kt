package com.example.fakeonliner.repos

import com.example.fakeonliner.models.ProductSimplified
import com.example.fakeonliner.service.api.OnlinerAPI
import retrofit2.await

class ProductRepo(private val onlinerAPI: OnlinerAPI) {

    suspend fun getProducts(categoryId: String): List<ProductSimplified> {
        val response = onlinerAPI.getProducts(categoryId, 20, 1).await()

        return response.products.map {
            ProductSimplified(
                "http:" + it.images.header,
                it.name,
                it.micro_description
            )
        }
    }
}