package com.mediserve.pharma.mediservepharma

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding
import com.mediserve.pharma.mediservepharma.databinding.TransactionItemBinding

class ViewTransactionsAdapter (private val data: ArrayList<Transaction>,
) : RecyclerView.Adapter<ViewTransactionsViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTransactionsViewHolder {
        // Inflating the layout for individual product items
        val viewItemBinding: TransactionItemBinding = TransactionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewTransactionsViewHolder(viewItemBinding)
    }

    override fun onBindViewHolder(holder: ViewTransactionsViewHolder, position: Int) {
       holder.bindData(data[position])
    }


    override fun getItemCount(): Int {
        return data.size
    }
}