package com.example.fakeonliner

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeonliner.databinding.ActivityProductsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var productsViewModel: ProductsViewModel
    private val productsAdapter = ProductsAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var categoryId = ""

        if (intent.getStringExtra("categoryId") != null)
            categoryId = intent.getStringExtra("categoryId")!!


        binding = ActivityProductsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.productsList.layoutManager = LinearLayoutManager(this)
        binding.productsList.adapter = productsAdapter

        productsViewModel = ViewModelProvider(
            ViewModelStore(),
            viewModelFactory {
                ProductsViewModel(
                    ProductRepo(),
                    categoryId
                )
            }).get()

        lifecycleScope.launchWhenStarted {
            productsViewModel.uiState.collect {
                when (it) {
                    is ProductsUiState.Error -> Snackbar.make(
                        binding.root,
                        it.exception.localizedMessage,
                        Snackbar.LENGTH_LONG
                    ).show()
                    ProductsUiState.Loading -> loadingVisibility(true)
                    is ProductsUiState.Success -> {
                        productsAdapter.updateData(it.products)
                        loadingVisibility(false)

                    }
                }
            }
        }
    }
    private fun loadingVisibility(isLoading: Boolean) {
        binding.productsList.isVisible = !isLoading
        binding.progressBar.isVisible = isLoading
    }
}
