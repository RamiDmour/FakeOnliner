package com.example.fakeonliner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepo: CategoryRepo) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Success(categoryRepo.getCategories())
        }
    }
}

sealed class CategoryUiState {
    data class Success(val categories: List<Category>) : CategoryUiState()
    data class Error(val exception: Throwable) : CategoryUiState()
    object Loading : CategoryUiState()
}