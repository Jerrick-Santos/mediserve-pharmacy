package com.mediserve.pharma.mediservepharma

import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding
import com.mediserve.pharma.mediservepharma.databinding.TransactionItemBinding

class ViewTransactionsViewHolder (val viewBinding: TransactionItemBinding) : ViewHolder(viewBinding.root){

    fun bindData(transaction: Transaction) {
        viewBinding.transactionOrderID.text = transaction.transac_id
        viewBinding.transactionProductName.text = transaction.product_name
        viewBinding.transactionType.text = transaction.change_type
        viewBinding.transactionQty.text = transaction.qty.toString()
    }
}