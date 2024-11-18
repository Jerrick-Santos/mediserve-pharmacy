package com.mediserve.pharma.mediservepharma

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding

class ProcessOrderViewHolder (val viewBinding: OrderItemBinding) : ViewHolder(viewBinding.root){

    fun bindData(transaction: Transaction) {
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.brandName.text = transaction.inventoryStock.product.brandName
        viewBinding.id.text = transaction.inventoryStock.product.id
        viewBinding.currVal.text = transaction.qty.toString()
    }
}