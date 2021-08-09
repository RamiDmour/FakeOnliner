package com.example.fakeonliner.repos

import com.example.fakeonliner.models.ProductPrice
import com.example.fakeonliner.models.ProductSimplified
import com.example.fakeonliner.service.api.OnlinerAPI

class ProductRepo(private val onlinerAPI: OnlinerAPI) {

    suspend fun getProducts(categoryId: String): List<ProductSimplified> {
        val response = onlinerAPI.getProducts(categoryId, 20, 1)

        return response.products.map {
            val productPrice: ProductPrice? = it.prices?.let { prices ->
                ProductPrice(
                    prices.price_min.amount,
                    prices.price_max.amount,
                    prices.price_max.currency
                )
            }

            ProductSimplified(
                "https:" + it.images.header,
                it.name,
                it.micro_description,
                it.html_url,
                productPrice
            )
        }
    }
}