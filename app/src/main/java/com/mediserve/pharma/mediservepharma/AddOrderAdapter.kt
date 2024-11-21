package com.mediserve.pharma.mediservepharma

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.InventoryAdapter.Companion.inventoryBrandKey
import com.mediserve.pharma.mediservepharma.InventoryAdapter.Companion.inventoryDosageKey
import com.mediserve.pharma.mediservepharma.InventoryAdapter.Companion.inventoryGenericKey
import com.mediserve.pharma.mediservepharma.InventoryAdapter.Companion.inventoryIdKey
import com.mediserve.pharma.mediservepharma.InventoryAdapter.Companion.inventoryManufacturerKey
import com.mediserve.pharma.mediservepharma.InventoryAdapter.Companion.inventoryQtyKey
import com.mediserve.pharma.mediservepharma.InventoryAdapter.Companion.inventoryStockIdKey
import com.mediserve.pharma.mediservepharma.databinding.AddOrderItemBinding
import com.mediserve.pharma.mediservepharma.databinding.OrderItemBinding
import kotlinx.coroutines.launch
import java.io.IOException

class AddOrderAdapter(
    private val data: ArrayList<InventoryStock>,
    private val pharmacyID: Int,
    private val mediServeApi: MediServeApi, // Pass MediServeApi instance to the adapter
    private val lifecycleOwner: LifecycleOwner // Pass lifecycleOwner (Activity or Fragment)
) : RecyclerView.Adapter<AddOrderViewHolder>() {

    companion object {
        const val RESULT_KEY = "RESULT_KEY"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddOrderViewHolder {
        val itemBinding: AddOrderItemBinding = AddOrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddOrderViewHolder(itemBinding)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onBindViewHolder(holder: AddOrderViewHolder, position: Int) {
        holder.bindData(data[position])

        holder.viewBinding.addCartBtn.setOnClickListener {
            val newStock = NewCartItemPOST(
                pharmacyID, // Use the passed pharmacyID
                data[position].stockID,
                1 // Example quantity, you might want to change this dynamically based on user input
            )

            // Perform the network request asynchronously
            lifecycleOwner.lifecycleScope.launch {
                try {
                    // Send POST request to add to the cart
                    val response = mediServeApi.addCart(newStock)
                    if (response.isSuccessful) {
                        Toast.makeText(holder.viewBinding.root.context, "Added to Cart", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(holder.viewBinding.root.context, "Failed to Add to Cart", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "Error adding to cart: ${e.message}")
                    Toast.makeText(holder.viewBinding.root.context, "Network error", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    Log.e(TAG, "IO Exception: ${e.message}")
                    Toast.makeText(holder.viewBinding.root.context, "IOException occurred", Toast.LENGTH_SHORT).show()
                }
            }

            val intent = Intent(holder.itemView.context, ProcessOrderActivity::class.java)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
