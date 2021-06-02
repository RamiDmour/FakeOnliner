package com.example.fakeonliner

import kotlinx.coroutines.delay
class CategoryRepo {

    suspend fun getCategories(): List<Category> {
        delay(2000)

        return listOf(
            Category("Электроника", "Электроника"),
            Category("Компьютеры и сети", "Компьютеры и сети"),
        )
    }
}
