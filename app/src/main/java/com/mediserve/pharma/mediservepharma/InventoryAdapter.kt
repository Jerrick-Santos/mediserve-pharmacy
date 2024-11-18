package com.mediserve.pharma.mediservepharma

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.InventoryItemBinding
import com.mediserve.pharma.mediservepharma.databinding.ProductItemBinding

class InventoryAdapter (
    private val data: ArrayList<InventoryStock>,
) : RecyclerView.Adapter<InventoryViewHolder>() {

    // STEP 1: Initialize KEY VALUE PAIRS for passing data through Intent
    companion object {
        const val inventoryImg: String = "PRODUCT_IMG"
        const val inventoryIdKey: String = "PRODUCT_ID"
        const val inventoryBrandKey: String = "PRODUCT_BRAND"
        const val inventoryGenericKey: String = "PRODUCT_GENERIC"
        const val inventoryManufacturerKey: String = "PRODUCT_MANUFACTURER"
        const val inventoryDosageKey: String = "PRODUCT_DOSAGE"
        const val inventoryPositionKey: String = "POSITION"
        const val inventoryQtyKey : String = "QTY"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        // Inflating the layout for individual product items
        val itemViewBinding: InventoryItemBinding = InventoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InventoryViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        // Binding the product data
        holder.bindData(data[position])

        // Set an OnClickListener to handle user interaction
        holder.itemView.setOnClickListener {

            // STEP 2: Instantiate Intent (src_activity, dest_activity)
            val intent = Intent(holder.itemView.context, ClickedProductCatalogue::class.java)

            // STEP 3: Pass the product details using putExtra
            intent.putExtra(inventoryIdKey, data[position].product.id)
            intent.putExtra(inventoryBrandKey, data[position].product.brandName)
            intent.putExtra(inventoryGenericKey, data[position].product.genericName)
            intent.putExtra(inventoryManufacturerKey, data[position].product.manufacturer)
            intent.putExtra(inventoryDosageKey, data[position].product.dosage)
            intent.putExtra(inventoryQtyKey, data[position].qty)


            // STEP 4: Launch Activity
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
