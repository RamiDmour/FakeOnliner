package com.example.fakeonliner

import kotlinx.coroutines.delay

class CategoryRepo {

    suspend fun getCategories(): List<Category> {
        delay(5000)

        return listOf(
            Category("Электроника"),
            Category("Компьютеры и сети"),
            Category("Бытовая техника"),
            Category("Стройка и ремонт"),
            Category("Дом и сад"),
            Category("Авто и мото")
        )
    }
}
