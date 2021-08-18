package com.example.fakeonliner.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fakeonliner.R
import com.example.fakeonliner.components.LoadingDialog
import com.example.fakeonliner.databinding.ProductFragmentBinding
import com.example.fakeonliner.models.Product
import com.example.fakeonliner.models.ProductSimplified
import com.example.fakeonliner.viewModels.ProductUiState
import com.example.fakeonliner.viewModels.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductFragment : Fragment(R.layout.product_fragment) {
    private val productViewModel: ProductViewModel by viewModel {
        parametersOf(requireArguments().getString(PRODUCT_KEY))
    }

    private val binding by viewBinding(ProductFragmentBinding::bind)

    companion object {
        const val PRODUCT_KEY = "PRODUCT_KEY"
        fun newInstance(product: ProductSimplified): ProductFragment = ProductFragment().apply {
            arguments = bundleOf(PRODUCT_KEY to product.key)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingDialog = LoadingDialog(requireContext())

        lifecycleScope.launchWhenStarted {
            productViewModel.uiState.collect { productUiState ->
                when (productUiState) {
                    is ProductUiState.Error -> {
                        Snackbar.make(
                            binding.root,
                            productUiState.exception.localizedMessage,
                            Snackbar.LENGTH_LONG
                        ).show()
                        loadingVisibility(false, loadingDialog)
                    }
                    ProductUiState.Loading -> loadingVisibility(true, loadingDialog)
                    is ProductUiState.Success -> {
                        loadingVisibility(false, loadingDialog)
                        setView(productUiState.product)
                    }
                }
            }
        }
    }

    fun setView(product: Product) {
        binding.productKey.text = product.key;
    }


    private fun loadingVisibility(isLoading: Boolean, loadingDialog: LoadingDialog) {
        if (isLoading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }
}