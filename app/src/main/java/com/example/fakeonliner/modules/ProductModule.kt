package com.example.fakeonliner.modules

import com.example.fakeonliner.repos.ProductRepo
import com.example.fakeonliner.viewModels.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productModule = module {
    single { ProductRepo(get()) }
    viewModel { parameters -> ProductsViewModel(get(), parameters.get()) }
}