package com.example.fakeonliner

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeonliner.databinding.CategoryListItemBinding

class CategoryAdapter(private var dataSet: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        holder.binding.categoryTitle.text = dataSet[position].title
        holder.binding.categoryTitle.setOnClickListener {
            val context = holder.binding.root.context
            val intent = Intent(context, ProductsActivity::class.java).apply {
                putExtra("categoryId", dataSet[position].categoryId)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}