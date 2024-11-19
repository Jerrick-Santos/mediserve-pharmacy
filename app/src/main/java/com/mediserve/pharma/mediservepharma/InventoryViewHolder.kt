package com.mediserve.pharma.mediservepharma

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mediserve.pharma.mediservepharma.databinding.AddOrderItemBinding
import com.mediserve.pharma.mediservepharma.databinding.InventoryItemBinding

class InventoryViewHolder (private val viewBinding: InventoryItemBinding) : ViewHolder(viewBinding.root){

    fun bindData(inventory: InventoryStock) {
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.stockID.text = inventory.stockID.toString()
        viewBinding.brandName.text = inventory.product.brandName
        viewBinding.id.text = inventory.product.id
        viewBinding.genericName.text = inventory.product.genericName
        viewBinding.manufacturer.text = inventory.product.manufacturer
        viewBinding.dosage.text = inventory.product.dosage
        viewBinding.qty.text =  inventory.qty.toString()
    }
}