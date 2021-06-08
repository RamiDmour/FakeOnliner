package com.example.fakeonliner.repos

import android.util.Log
import com.example.fakeonliner.models.ProductPrice
import com.example.fakeonliner.models.ProductSimplified
import com.example.fakeonliner.service.api.OnlinerAPI
import retrofit2.await

class ProductRepo(private val onlinerAPI: OnlinerAPI) {

    suspend fun getProducts(categoryId: String): List<ProductSimplified> {
        val response = onlinerAPI.getProducts(categoryId, 20, 1)

        return response.products.map {
            var priceMax: Float? = null
            var priceMin: Float? = null
            var currency: String? = null

            it.prices?.let { prices ->
                priceMax = prices.price_max.amount.toFloat()
                priceMin = prices.price_min.amount.toFloat()
                currency = prices.price_max.currency
            }

            ProductSimplified(
                "https:" + it.images.header,
                it.name,
                it.micro_description,
                it.html_url,
                ProductPrice(
                    priceMin,
                    priceMax,
                    currency
                )
            )
        }
    }
}