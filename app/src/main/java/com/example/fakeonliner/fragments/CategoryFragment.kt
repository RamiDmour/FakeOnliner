package com.example.fakeonliner.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeonliner.R
import com.example.fakeonliner.adapters.CategoryAdapter
import com.example.fakeonliner.components.LoadingDialog
import com.example.fakeonliner.databinding.CategoryFragmentBinding
import com.example.fakeonliner.viewModels.CategoryUiState
import com.example.fakeonliner.viewModels.CategoryViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment(R.layout.category_fragment) {
    private val binding by viewBinding(CategoryFragmentBinding::bind)
    private val categoryViewModel: CategoryViewModel by viewModel()
    private val categoryListAdapter = CategoryAdapter(emptyList()) { category ->
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.root_container, ProductsFragment.newInstance(category))
            addToBackStack(null)
        }
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swiperefresh.setOnRefreshListener {
            categoryViewModel.fetchCategories(false)
        }

        binding.categoryList.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryList.adapter = categoryListAdapter

        val loadingDialog = LoadingDialog(requireContext())

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
