package com.example.fakeonliner.models

data class ProductSimplified(
    val key: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val productUri: String,
    val productPrice: ProductPrice?
)

data class ProductPrice(
    val priceMin: Float,
    val priceMax: Float,
    val currency: String
)
