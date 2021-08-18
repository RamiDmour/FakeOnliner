package com.example.fakeonliner.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakeonliner.models.Product
import com.example.fakeonliner.repos.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(productRepo: ProductRepo, productKey: String) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState;

    init {
        viewModelScope.launch {
            try {
                _uiState.value = ProductUiState.Success(productRepo.getProduct(productKey))
            } catch (e: Throwable) {
                _uiState.value = ProductUiState.Error(e)
            }
        }
    }
}

sealed class ProductUiState {
    data class Success(val product: Product) : ProductUiState()
    object Loading : ProductUiState()
    data class Error(val exception: Throwable) : ProductUiState()
}