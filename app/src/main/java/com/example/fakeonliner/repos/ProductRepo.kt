package com.example.fakeonliner.repos

import android.util.Log
import com.example.fakeonliner.models.ProductSimplified
import com.example.fakeonliner.service.api.OnlinerAPI
import retrofit2.await

class ProductRepo(private val onlinerAPI: OnlinerAPI) {

    suspend fun getProducts(categoryId: String): List<ProductSimplified> {
        val response = onlinerAPI.getProducts(categoryId, 20, 1).await()

        return response.products.map {
            var priceMin: String? = null
            var priceMax: String? = null

            if(it.prices != null) {
                priceMax = it.prices.price_max.amount + it.prices.price_max.currency
                priceMin = it.prices.price_min.amount + it.prices.price_min.currency
            }


            ProductSimplified(
                "http:" + it.images.header,
                it.name,
                it.micro_description,
                it.html_url,
                priceMin,
                priceMax
            )
        }
    }
}