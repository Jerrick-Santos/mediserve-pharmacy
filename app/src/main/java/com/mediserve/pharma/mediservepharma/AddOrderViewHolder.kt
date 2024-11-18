package com.mediserve.pharma.mediservepharma

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mediserve.pharma.mediservepharma.databinding.AddOrderItemBinding
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding

class AddOrderViewHolder (val viewBinding: AddOrderItemBinding) : ViewHolder(viewBinding.root){

    fun bindData(inventory: InventoryStock) {
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.brandName.text = inventory.product.brandName
        viewBinding.id.text = inventory.product.id
        viewBinding.genericName.text = inventory.product.genericName
        viewBinding.manufacturer.text = inventory.product.manufacturer
        viewBinding.dosage.text = inventory.product.dosage
        viewBinding.qty.text =  "QTY: " + inventory.qty.toString()
    }
}