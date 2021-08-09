package com.example.fakeonliner.modules

import androidx.room.Room
import com.example.fakeonliner.repos.CategoryRepo
import com.example.fakeonliner.room.CategoryDatabase
import com.example.fakeonliner.viewModels.CategoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CategoryDatabase::class.java,
            "category_database"
        ).build()
    }
    single { get<CategoryDatabase>().categoryDao() }
    single { CategoryRepo(onlinerApi = get(), categoryDao = get()) }
    viewModel { CategoryViewModel(get()) }
}
