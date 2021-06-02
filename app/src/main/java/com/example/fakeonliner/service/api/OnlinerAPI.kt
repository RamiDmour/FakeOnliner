package com.example.fakeonliner.service.api

import com.example.fakeonliner.api.SchemasResponse
import com.example.fakeonliner.service.response.GetProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OnlinerAPI {
    @GET("/schemas")
    fun getCategories(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Call<SchemasResponse>

    @GET("/search/{key}")
    fun getProducts(
        @Path("key") path: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Call<GetProductsResponse>
}