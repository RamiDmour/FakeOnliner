package com.example.fakeonliner.service

import com.example.fakeonliner.service.api.OnlinerAPI

object API {
    private val BASE_URL = "https://catalog.api.onliner.by"
    val onlinerApi: OnlinerAPI
        get() = RetrofitClient.getClient(BASE_URL).create(OnlinerAPI::class.java)
}