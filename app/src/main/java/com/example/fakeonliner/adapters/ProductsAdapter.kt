package com.example.fakeonliner.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeonliner.databinding.ProductsListItemBinding
import com.example.fakeonliner.models.ProductSimplified
import com.squareup.picasso.Picasso

class ProductsAdapter(private var products: List<ProductSimplified>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ProductsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductSimplified) {
            binding.productTitle.text = product.title
            binding.productDescription.text = product.description
            Picasso.get().load(product.imageUrl).into(binding.productImage)
            binding.root.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.productUri))
                binding.root.context.startActivity(browserIntent)
            }

            product.productPrice?.let {
                if (it.priceMin != null && it.priceMax != null)
                    if(it.priceMin != it.priceMax)
                        binding.price.text = "${it.priceMin}-${it.priceMax}${it.currency}"
                    else
                        binding.price.text = "${it.priceMax}${it.currency}"
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