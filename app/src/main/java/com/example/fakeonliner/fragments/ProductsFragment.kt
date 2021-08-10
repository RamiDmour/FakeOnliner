package com.example.fakeonliner.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeonliner.R
import com.example.fakeonliner.adapters.ProductsAdapter
import com.example.fakeonliner.components.LoadingDialog
import com.example.fakeonliner.databinding.ProductsFragmentBinding
import com.example.fakeonliner.models.Category
import com.example.fakeonliner.viewModels.ProductsUiState
import com.example.fakeonliner.viewModels.ProductsViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductsFragment : Fragment(R.layout.products_fragment) {
    private val productsViewModel: ProductsViewModel by viewModel {
        parametersOf(requireArguments().getString(CATEGORY_ID_KEY))
    }
    private val productsAdapter = ProductsAdapter(emptyList()) {product ->
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.productUri))
        requireContext().startActivity(browserIntent)
    }
    private val binding by viewBinding(ProductsFragmentBinding::bind)

    companion object {
        private const val CATEGORY_ID_KEY = "CATEGORY_ID_KEY"
        fun newInstance(category: Category): ProductsFragment = ProductsFragment().apply {
            arguments = bundleOf(CATEGORY_ID_KEY to category.categoryId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productsList.layoutManager = LinearLayoutManager(requireContext())
        binding.productsList.adapter = productsAdapter

        val loadingDialog = LoadingDialog(requireContext())

        lifecycleScope.launchWhenStarted {
            productsViewModel.uiState.collect {
                when (it) {
                    is ProductsUiState.Error -> {
                        Snackbar.make(
                            binding.root,
                            it.exception.localizedMessage,
                            Snackbar.LENGTH_LONG
                        ).show()
                        loadingVisibility(false, loadingDialog)
                    }
                    ProductsUiState.Loading -> loadingVisibility(true, loadingDialog)
                    is ProductsUiState.Success -> {
                        productsAdapter.updateData(it.products)
                        loadingVisibility(false, loadingDialog)

                    }
                }
            }
        }
    }

    private fun loadingVisibility(isLoading: Boolean, loadingDialog: LoadingDialog) {
        if (isLoading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }
}