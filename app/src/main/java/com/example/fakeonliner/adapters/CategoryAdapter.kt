package com.example.fakeonliner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeonliner.databinding.CategoryListItemBinding
import com.example.fakeonliner.models.Category

class CategoryAdapter(private var dataSet: List<Category>, private val onCategorySelect: (category: Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(category: Category) {
                binding.categoryTitle.text = category.title
                binding.categoryTitle.setOnClickListener {
                    onCategorySelect(category)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    fun updateData(data: List<Category>) {
        dataSet = data
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}