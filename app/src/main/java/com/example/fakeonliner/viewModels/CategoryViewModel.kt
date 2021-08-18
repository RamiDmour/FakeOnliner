package com.example.fakeonliner.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakeonliner.models.Category
import com.example.fakeonliner.repos.CategoryRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepo: CategoryRepo) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState
    private val _eventChannel = Channel<CategoryEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    init {
        fetchCategories()
    }

    fun fetchCategories(cache: Boolean = true) {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            _uiState.value = try {
                val categories = categoryRepo.getCategories(cache)
                CategoryUiState.Success(categories)
            } catch (e: Throwable) {
                Log.e("DEBUG", e.message ?: "ERROR. CategoryViewModel")
                CategoryUiState.Error(e)
            }
        }
    }

    fun selectCategory(category: Category) = viewModelScope.launch {
        _eventChannel.send(CategoryEvent.CategorySelected(category))
    }
}


sealed class CategoryUiState {
    data class Success(val categories: List<Category>) : CategoryUiState()
    data class Error(val exception: Throwable) : CategoryUiState()
    object Loading : CategoryUiState()
}

sealed class CategoryEvent {
    data class CategorySelected(val category: Category) : CategoryEvent()
}