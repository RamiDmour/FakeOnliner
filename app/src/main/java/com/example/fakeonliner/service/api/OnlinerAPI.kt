package com.example.fakeonliner.service.api

import com.example.fakeonliner.api.SchemasResponse
import com.example.fakeonliner.room.entities.CategoryEntity
import com.example.fakeonliner.service.response.GetProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OnlinerAPI {
    @GET("/schemas")
    suspend fun getCategories(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): SchemasResponse

    @GET("/search/{key}")
    suspend fun getProducts(
        @Path("key") path: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): GetProductsResponse
}

suspend fun OnlinerAPI.getConvertedCategories(limit: Int, page: Int): List<CategoryEntity> {
    val response = this.getCategories(limit, page)

    return response.schemas.map { CategoryEntity(it.key, it.name) }
}