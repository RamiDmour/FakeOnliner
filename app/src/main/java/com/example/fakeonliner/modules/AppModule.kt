package com.example.fakeonliner.modules

import com.example.fakeonliner.service.onlinerApi
import org.koin.dsl.module

val appModule = module {
    single { onlinerApi }
}