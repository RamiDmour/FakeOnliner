package com.example.fakeonliner.modules

import com.example.fakeonliner.repos.CategoryRepo
import com.example.fakeonliner.viewModels.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    single { CategoryRepo(get()) }
    viewModel { CategoryViewModel(get()) }
}
