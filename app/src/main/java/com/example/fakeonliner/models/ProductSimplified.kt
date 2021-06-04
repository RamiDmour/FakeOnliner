package com.example.fakeonliner.models

data class ProductSimplified(
    val imageUrl: String,
    val title: String,
    val description: String,
    val productUri: String,
    val priceMin: String? = null,
    val priceMax: String? = null
)