package com.mediserve.pharma.mediservepharma

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding

class ProcessOrderViewHolder (val viewBinding: OrderItemBinding) : ViewHolder(viewBinding.root){

    fun bindData(newOrder: Order) {
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.brandName.text = newOrder.brandName
        viewBinding.id.text = newOrder.cartID.toString()
        viewBinding.currVal.text = newOrder.qty.toString()
    }
}