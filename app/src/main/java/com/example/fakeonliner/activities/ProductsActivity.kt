package com.example.fakeonliner.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeonliner.ProductsUiState
import com.example.fakeonliner.ProductsViewModel
import com.example.fakeonliner.adapters.ProductsAdapter
import com.example.fakeonliner.databinding.ActivityProductsBinding
import com.example.fakeonliner.models.Category
import com.example.fakeonliner.repos.ProductRepo
import com.example.fakeonliner.viewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private val productsViewModel: ProductsViewModel by viewModels {
        val categoryId = intent.getStringExtra(CATEGORY_ID_KEY) ?: ""

        viewModelFactory {
            ProductsViewModel(ProductRepo(), categoryId)
        }
    }
    private val productsAdapter = ProductsAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityProductsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.productsList.layoutManager = LinearLayoutManager(this)
        binding.productsList.adapter = productsAdapter

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

    companion object {
        private const val CATEGORY_ID_KEY = "CATEGORY_ID_KEY"

        fun createIntent(context: Context, category: Category): Intent {
            return Intent(context, ProductsActivity::class.java).apply {
                putExtra(CATEGORY_ID_KEY, category.categoryId)
            }
        }
    }

    private fun loadingVisibility(isLoading: Boolean) {
        binding.productsList.isVisible = !isLoading
        binding.progressBar.isVisible = isLoading
    }
}
