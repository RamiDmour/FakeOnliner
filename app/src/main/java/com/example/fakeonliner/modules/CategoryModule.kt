package com.example.fakeonliner.modules

import android.app.Application
import androidx.room.Room
import com.example.fakeonliner.repos.CategoryRepo
import com.example.fakeonliner.room.CategoryDatabase
import com.example.fakeonliner.viewModels.CategoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    fun buildDatabaseInstance(application: Application) =
        Room.databaseBuilder(
            application,
            CategoryDatabase::class.java,
            "category_database"
        ).build()

    single { buildDatabaseInstance(androidApplication()) }
    single { get<CategoryDatabase>().categoryDao() }
    single { CategoryRepo(get(), get()) }
    viewModel { CategoryViewModel(get()) }
}
