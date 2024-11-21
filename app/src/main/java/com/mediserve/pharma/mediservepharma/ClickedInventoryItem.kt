package com.mediserve.pharma.mediservepharma

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.mediserve.pharma.mediservepharma.ClickedProductCatalogue.Companion
import com.mediserve.pharma.mediservepharma.databinding.ActivityClickedInventoryItemBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityClickedProductCatalogueBinding
import kotlinx.coroutines.launch
import java.io.IOException

class ClickedInventoryItem : ComponentActivity() {

    private lateinit var viewBinding: ActivityClickedInventoryItemBinding
    private lateinit var productId: String
//    private lateinit var stockId: String
    private lateinit var productBrand: String
    private lateinit var productGeneric: String
    private lateinit var productManufacturer: String
    private lateinit var productDosage: String
//    private lateinit var qty: String


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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


        viewBinding.addToInventoryBtn.setOnClickListener {
            val editedStock = EditStockPatch(
                intent.getIntExtra(InventoryAdapter.inventoryStockIdKey, -1),
                "add",
                viewBinding.addStock.text.toString().toIntOrNull() ?: -1
            )

            Log.d("EDIT STOCK TEST", editedStock.toString())

            lifecycleScope.launch {
                val response = try {
                    RetrofitInstance.api.editStock(editedStock)
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: No network connection", e)
                    return@launch
                } catch (e: HttpException) {
                    Log.e(TAG, "HttpException: Unexpected response", e)
                    return@launch
                }


                if (response.isSuccessful) {
                    Toast.makeText(this@ClickedInventoryItem, "Stock Edited!", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, InventoryActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "PATCH Failed: ${response.message()}")
                }
            }
        }

        viewBinding.deductToInventoryBtn5.setOnClickListener {
            val editedStock = EditStockPatch(
                intent.getIntExtra(InventoryAdapter.inventoryStockIdKey, -1),
                "deduct",
                viewBinding.addStock.text.toString().toIntOrNull() ?: -1
            )

            Log.d("EDIT STOCK TEST", editedStock.toString())

            lifecycleScope.launch {
                val response = try {
                    RetrofitInstance.api.editStock(editedStock)
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: No network connection", e)
                    return@launch
                } catch (e: HttpException) {
                    Log.e(TAG, "HttpException: Unexpected response", e)
                    return@launch
                }


                if (response.isSuccessful) {
                    Toast.makeText(this@ClickedInventoryItem, "Stock Edited!", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, InventoryActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "PATCH Failed: ${response.message()}")
                }
            }

        }

    }
}