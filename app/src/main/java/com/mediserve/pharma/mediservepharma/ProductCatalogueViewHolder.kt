package com.mediserve.pharma.mediservepharma

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mediserve.pharma.mediservepharma.databinding.ProductItemBinding

class ProductCatalogueViewHolder (private val viewBinding: ProductItemBinding) : ViewHolder(viewBinding.root){

    fun bindData(product: Product) {
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.brandName.text = product.brandName
        viewBinding.id.text = product.id
        viewBinding.genericName.text = product.genericName
        viewBinding.manufacturer.text = product.manufacturer
        viewBinding.dosage.text = product.dosage
    }
}