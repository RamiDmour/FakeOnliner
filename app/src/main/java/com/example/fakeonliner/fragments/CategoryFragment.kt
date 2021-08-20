package com.example.fakeonliner.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeonliner.R
import com.example.fakeonliner.adapters.CategoryAdapter
import com.example.fakeonliner.components.LoadingDialog
import com.example.fakeonliner.databinding.CategoryFragmentBinding
import com.example.fakeonliner.viewModels.CategoryEvent
import com.example.fakeonliner.viewModels.CategoryUiState
import com.example.fakeonliner.viewModels.CategoryViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment(R.layout.category_fragment) {
    private val binding by viewBinding(CategoryFragmentBinding::bind)
    private val categoryViewModel: CategoryViewModel by viewModel()
    private val categoryListAdapter = CategoryAdapter(emptyList()) { category ->
        categoryViewModel.selectCategory(category)
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }

    override fun onDestroy() {
        Log.d("CHECK_FLOW", "DESTROY: CATEGORY")
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swiperefresh.setOnRefreshListener {
            categoryViewModel.fetchCategories(false)
        }

        binding.categoryList.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryList.adapter = categoryListAdapter

        val loadingDialog = LoadingDialog(requireContext())

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("CHECK_FLOW", "Handle event: CATEGORY")
                launch {
                    categoryViewModel.eventFlow.collect {
                        when (it) {
                            is CategoryEvent.CategorySelected -> {
                                parentFragmentManager.commit {
                                    setReorderingAllowed(true)
                                    replace(
                                        R.id.root_container,
                                        ProductsFragment.newInstance(it.category)
                                    )
                                    addToBackStack(null)
                                }
                            }
                        }
                    }
                }
                launch {
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
