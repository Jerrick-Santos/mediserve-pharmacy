package com.mediserve.pharma.mediservepharma

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.AddOrderItemBinding
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding

class AddOrderAdapter (private val data: ArrayList<InventoryStock>,
) : RecyclerView.Adapter<AddOrderViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddOrderViewHolder {
        // Inflating the layout for individual product items
        val itemBinding: AddOrderItemBinding = AddOrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddOrderViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AddOrderViewHolder, position: Int) {

        // Bind the data to the view holder
        holder.bindData(data[position])

        holder.viewBinding.addCartBtn.setOnClickListener{
            val intent = Intent(holder.itemView.context, ProcessOrderActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }

    }



    override fun getItemCount(): Int {
        return data.size
    }
}