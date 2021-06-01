package com.example.fakeonliner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(repo: ProductRepo, categoryId: String) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Success(repo.getProducts(categoryId))
        }
    }
}

sealed class ProductsUiState {
    data class Success(val products: List<ProductSimplified>): ProductsUiState()
    object Loading: ProductsUiState()
    data class Error(val exception: Throwable): ProductsUiState()
}
