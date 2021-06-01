package com.example.fakeonliner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import java.lang.Exception

class CategoryRepo {

    suspend fun getCategories(): ArrayList<Category> {
        delay(5000)

        val categories: ArrayList<Category> = ArrayList()
        categories.add(Category("Электроника"))
        categories.add(Category("Компьютеры и сети"))
        categories.add(Category("Бытовая техника"))
        categories.add(Category("Стройка и ремонт"))
        categories.add(Category("Дом и сад"))
        categories.add(Category("Авто и мото"))

        return categories
    }
}
