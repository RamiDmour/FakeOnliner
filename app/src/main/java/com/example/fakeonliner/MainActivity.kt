package com.example.fakeonliner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeonliner.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val categoryViewModel: CategoryViewModel = CategoryViewModel(CategoryRepo())
    private lateinit var binding: ActivityMainBinding
    private val categoryListAdapter = CategoryAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = categoryListAdapter

        lifecycleScope.launchWhenStarted {
            categoryViewModel.uiState.collect() {
                when(it) {
                    is CategoryUiState.Success -> {
                        categoryListAdapter.updateData(it.categories)
                        binding.categoryList.isVisible = true
                        binding.progressBar.isVisible = false
                    }
                    CategoryUiState.Empty -> TODO()
                    is CategoryUiState.Error -> Snackbar.make(view, "Error: " + it.exception.localizedMessage, Snackbar.LENGTH_LONG).show()
                    CategoryUiState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.categoryList.isVisible = false
                    }
                }
            }
        }
    }
}