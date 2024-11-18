package com.mediserve.pharma.mediservepharma

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding
import com.mediserve.pharma.mediservepharma.databinding.TransactionItemBinding

class ViewTransactionsViewHolder (val viewBinding: TransactionItemBinding) : ViewHolder(viewBinding.root){

    fun bindData(transaction: Transaction) {
        viewBinding.qty.text = transaction.qty.toString()
        viewBinding.orderID.text = transaction.inventoryStock.product.id
        viewBinding.productName.text = transaction.inventoryStock.product.brandName
    }
}