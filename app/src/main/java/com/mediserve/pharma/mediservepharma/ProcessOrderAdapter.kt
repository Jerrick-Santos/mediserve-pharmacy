package com.mediserve.pharma.mediservepharma

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding

import com.mediserve.pharma.mediservepharma.databinding.ProductItemBinding

class ProcessOrderAdapter (private val data: ArrayList<Transaction>,
) : RecyclerView.Adapter<ProcessOrderViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessOrderViewHolder {
        // Inflating the layout for individual product items
        val orderItemBinding: OrderItemBinding = OrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProcessOrderViewHolder(orderItemBinding)
    }

    override fun onBindViewHolder(holder: ProcessOrderViewHolder, position: Int) {
        val transaction = data[position]

        // Bind the data to the view holder
        holder.bindData(transaction)

        // Set the initial quantity value
        holder.viewBinding.currVal.text = transaction.qty.toString()


        var tempTransaction = transaction.qty
        // Handle '+' button click
        holder.viewBinding.addStockBtn.setOnClickListener {

            tempTransaction += 1 // Increment the quantity in the transaction object
            holder.viewBinding.currVal.text = tempTransaction.toString() // Update the displayed value
        }

        // Handle '-' button click
        holder.viewBinding.minusStockBtn.setOnClickListener {
            if (transaction.qty > 0) { // Prevent negative quantity
                tempTransaction-- // Decrement the quantity in the transaction object
                holder.viewBinding.currVal.text = tempTransaction.toString() // Update the displayed value
            }
        }
    }



    override fun getItemCount(): Int {
        return data.size
    }
}