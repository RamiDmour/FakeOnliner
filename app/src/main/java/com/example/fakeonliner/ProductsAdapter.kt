package com.example.fakeonliner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeonliner.databinding.ProductsListItemBinding
import com.squareup.picasso.Picasso

class ProductsAdapter(private var products: List<ProductSimplified>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ProductsListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProductsListItemBinding =
            ProductsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productTitle.text = products[position].title
        holder.binding.productDescription.text = products[position].description
        Picasso.get().load(products[position].imageUrl).into(holder.binding.productImage)
    }

    fun updateData(data: List<ProductSimplified>) {
        products = data
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = products.size
}