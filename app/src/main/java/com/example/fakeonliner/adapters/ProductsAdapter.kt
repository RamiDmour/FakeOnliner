package com.example.fakeonliner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeonliner.databinding.ProductsListItemBinding
import com.example.fakeonliner.models.ProductSimplified
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private var products: List<ProductSimplified>,
    private val onProductSelect: (product: ProductSimplified) -> Unit
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ProductsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductSimplified) {
            binding.productTitle.text = product.title
            binding.productDescription.text = product.description
            Picasso.get().load(product.imageUrl).into(binding.productImage)
            binding.root.setOnClickListener {
                onProductSelect(product)
            }

            product.productPrice?.let {
                binding.price.text = if (it.priceMin != it.priceMax) {
                    "${it.priceMin}-${it.priceMax}${it.currency}"
                } else {
                    "${it.priceMax}${it.currency}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProductsListItemBinding =
            ProductsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun updateData(data: List<ProductSimplified>) {
        products = data
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = products.size
}
