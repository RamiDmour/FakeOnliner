package com.example.fakeonliner.service

import com.example.fakeonliner.BuildConfig
import com.example.fakeonliner.service.api.OnlinerAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.ONLINER_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val onlinerApi: OnlinerAPI = retrofit.create(OnlinerAPI::class.java)
