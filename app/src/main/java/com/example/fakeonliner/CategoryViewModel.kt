package com.example.fakeonliner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepo: CategoryRepo): ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Empty)
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            _uiState.value = CategoryUiState.Success(categoryRepo.getCategories())
        }
    }
}

sealed class CategoryUiState {
    data class Success(val categories: ArrayList<Category>): CategoryUiState()
    data class Error(val exception: Throwable): CategoryUiState()
    object Loading: CategoryUiState()
    object Empty: CategoryUiState()
}