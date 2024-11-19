package com.mediserve.pharma.mediservepharma

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mediserve.pharma.mediservepharma.ClickedProductCatalogue.Companion
import com.mediserve.pharma.mediservepharma.databinding.ActivityClickedInventoryItemBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityClickedProductCatalogueBinding

class ClickedInventoryItem : ComponentActivity() {

    private lateinit var viewBinding: ActivityClickedInventoryItemBinding
    private lateinit var productId: String
//    private lateinit var stockId: String
    private lateinit var productBrand: String
    private lateinit var productGeneric: String
    private lateinit var productManufacturer: String
    private lateinit var productDosage: String
//    private lateinit var qty: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewBinding
        viewBinding = ActivityClickedInventoryItemBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // STEP 6: Get the values from the intent and store them to class attributes
        productId = intent.getStringExtra(InventoryAdapter.inventoryIdKey).toString()
        productBrand = intent.getStringExtra(InventoryAdapter.inventoryBrandKey).toString()
        productGeneric = intent.getStringExtra(InventoryAdapter.inventoryGenericKey).toString()
        productManufacturer = intent.getStringExtra(InventoryAdapter.inventoryManufacturerKey).toString()
        productDosage = intent.getStringExtra(InventoryAdapter.inventoryDosageKey).toString()


        // STEP 7: Set the attribute values to activity widgets
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.brandName.text = productBrand
        viewBinding.id.text = productId
        viewBinding.genericName.text = productGeneric
        viewBinding.manufacturer.text = productManufacturer
        viewBinding.dosage.text = productDosage
        viewBinding.qty.text = intent.getIntExtra(InventoryAdapter.inventoryQtyKey, -1).toString()
        viewBinding.stockID.text = intent.getIntExtra(InventoryAdapter.inventoryStockIdKey, -1).toString()

    }
}