package com.example.fakeonliner.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakeonliner.models.ProductSimplified
import com.example.fakeonliner.repos.ProductRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProductsViewModel(repo: ProductRepo, categoryId: String) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState
    private val _eventChannel = Channel<ProductsEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _uiState.value = try {
                ProductsUiState.Success(repo.getProducts(categoryId))
            } catch (e: Throwable) {
                ProductsUiState.Error(e)
            }
        }
    }

    fun selectProduct(product: ProductSimplified) =
        viewModelScope.launch {
            _eventChannel.send(ProductsEvent.ProductSelected(product))
        }
}

sealed class ProductsUiState {
    data class Success(val products: List<ProductSimplified>) : ProductsUiState()
    object Loading : ProductsUiState()
    data class Error(val exception: Throwable) : ProductsUiState()
}

sealed class ProductsEvent {
    data class ProductSelected(val product: ProductSimplified) : ProductsEvent()
}