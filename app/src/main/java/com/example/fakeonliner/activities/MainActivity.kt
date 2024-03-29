package com.example.fakeonliner.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeonliner.adapters.CategoryAdapter
import com.example.fakeonliner.components.LoadingDialog
import com.example.fakeonliner.databinding.ActivityMainBinding
import com.example.fakeonliner.viewModels.CategoryUiState
import com.example.fakeonliner.viewModels.CategoryViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val categoryViewModel: CategoryViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val categoryListAdapter = CategoryAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swiperefresh.setOnRefreshListener {
            categoryViewModel.fetchCategories(false)
        }

        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = categoryListAdapter

        val loadingDialog = LoadingDialog(this)

        lifecycleScope.launchWhenStarted {
            categoryViewModel.uiState.collect {
                when (it) {
                    is CategoryUiState.Success -> {
                        categoryListAdapter.updateData(it.categories)
                        loadingVisibility(false, loadingDialog)
                    }
                    is CategoryUiState.Error -> {
                        Snackbar.make(
                            binding.root,
                            it.exception.localizedMessage,
                            Snackbar.LENGTH_LONG
                        ).show()
                        loadingVisibility(false, loadingDialog)
                    }
                    CategoryUiState.Loading -> {
                        loadingVisibility(true, loadingDialog)
                    }
                }
            }
        }
    }

    private fun loadingVisibility(isLoading: Boolean, loadingDialog: LoadingDialog) {
        binding.categoryList.isVisible = !isLoading
        if (isLoading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
        binding.swiperefresh.isRefreshing = false
    }
}