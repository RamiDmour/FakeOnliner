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
            if(product.priceMin != null && product.priceMax != null)
                if(product.priceMin != product.priceMax)
                    binding.price.text = "${product.priceMin}-${product.priceMax}"
                else
                    binding.price.text = product.priceMax
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