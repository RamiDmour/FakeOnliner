package com.example.fakeonliner.api

data class SchemasResponse(
    val schemas: List<CategoryResponse>
)

data class CategoryResponse(
    val facets_url: String,
    val group: Group,
    val group_products: Boolean,
    val id: Int,
    val is_delivery_available: Boolean,
    val is_second_offer_available: Boolean,
    val key: String,
    val name: String,
    val products_url: String,
    val second_offers_url: String,
    val segments: List<Segment>,
    val url: String,
    val without_icons: Boolean
)

data class Group(
    val id: Int
)

data class Segment(
    val key: String,
    val name: String
)

data class BYRX(
    val amount: String,
    val currency: String
)