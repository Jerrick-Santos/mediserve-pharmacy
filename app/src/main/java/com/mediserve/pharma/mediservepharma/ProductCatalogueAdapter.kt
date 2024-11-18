package com.mediserve.pharma.mediservepharma

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.ProductItemBinding

// STEP 0: Add launcher with the datatype of ActivityResultLauncher<Intent> as parameters
class ProductCatalogueAdapter(
    private val productList: ArrayList<Product>,
) : RecyclerView.Adapter<ProductCatalogueViewHolder>() {

    // STEP 1: Initialize KEY VALUE PAIRS for passing data through Intent
    companion object {
        const val productImg: String = "PRODUCT_IMG"
        const val productIdKey: String = "PRODUCT_ID"
        const val productBrandKey: String = "PRODUCT_BRAND"
        const val productGenericKey: String = "PRODUCT_GENERIC"
        const val productManufacturerKey: String = "PRODUCT_MANUFACTURER"
        const val productDosageKey: String = "PRODUCT_DOSAGE"
        const val positionKey: String = "POSITION"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCatalogueViewHolder {
        // Inflating the layout for individual product items
        val productItemBinding: ProductItemBinding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductCatalogueViewHolder(productItemBinding)
    }

    override fun onBindViewHolder(holder: ProductCatalogueViewHolder, position: Int) {
        // Binding the product data
        holder.bindData(productList[position])

        // Set an OnClickListener to handle user interaction
        holder.itemView.setOnClickListener {

            // STEP 2: Instantiate Intent (src_activity, dest_activity)
            val intent = Intent(holder.itemView.context, ClickedProductCatalogue::class.java)

            // STEP 3: Pass the product details using putExtra
            intent.putExtra(productIdKey, productList[position].id)
            intent.putExtra(productBrandKey, productList[position].brandName)
            intent.putExtra(productGenericKey, productList[position].genericName)
            intent.putExtra(productManufacturerKey, productList[position].manufacturer)
            intent.putExtra(productDosageKey, productList[position].dosage)
            intent.putExtra(positionKey, position)

            // STEP 4: Launch Activity
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
