package com.mediserve.pharma.mediservepharma

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mediserve.pharma.mediservepharma.databinding.ActivityClickedProductCatalogueBinding
import kotlinx.coroutines.launch
import java.io.IOException

class ClickedProductCatalogue : ComponentActivity() {

    private lateinit var viewBinding: ActivityClickedProductCatalogueBinding

    // STEP 5: Define companion object for Intent keys (same as in adapter)
    companion object {
        const val productIdKey: String = "PRODUCT_ID"
        const val productBrandKey: String = "PRODUCT_BRAND"
        const val productGenericKey: String = "PRODUCT_GENERIC"
        const val productManufacturerKey: String = "PRODUCT_MANUFACTURER"
        const val productDosageKey: String = "PRODUCT_DOSAGE"
        const val positionKey: String = "POSITION"
        const val resultKey: String = "INPUT_STOCK"
        const val qtyKey : String = "QTY_"
    }

    private lateinit var productId: String
    private lateinit var productBrand: String
    private lateinit var productGeneric: String
    private lateinit var productManufacturer: String
    private lateinit var productDosage: String

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewBinding
        viewBinding = ActivityClickedProductCatalogueBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // STEP 6: Get the values from the intent and store them to class attributes
        productId = intent.getStringExtra(productIdKey).toString()
        productBrand = intent.getStringExtra(productBrandKey).toString()
        productGeneric = intent.getStringExtra(productGenericKey).toString()
        productManufacturer = intent.getStringExtra(productManufacturerKey).toString()
        productDosage = intent.getStringExtra(productDosageKey).toString()

        // STEP 7: Set the attribute values to activity widgets
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.brandName.text = productBrand
        viewBinding.id.text = productId
        viewBinding.genericName.text = productGeneric
        viewBinding.manufacturer.text = productManufacturer
        viewBinding.dosage.text = productDosage

        viewBinding.addToInventoryBtn.setOnClickListener {

            val sharedPreferences = getSharedPreferences("MediServePrefs", Context.MODE_PRIVATE)
            val pharmacyID = sharedPreferences.getInt("pharmacyID", -1)

            if (pharmacyID == -1) {
                Toast.makeText(this, "Pharmacy ID not found. Please log in again.", Toast.LENGTH_SHORT).show()
            }

            val productID = viewBinding.id.text.toString().removePrefix("PRDCT").toIntOrNull() ?: -1
            val currentAmt = viewBinding.addStock.text.toString().toIntOrNull() ?: -1

            if (currentAmt == null || currentAmt <= 0) {
                Toast.makeText(this, "Please enter a valid stock value greater than 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val newStock = NewStockPOST(
                pharmacyID,
                productID,
                currentAmt
            )

            Log.d("POST_TEST", newStock.toString())

            lifecycleScope.launch {
                val response = try {
                    RetrofitInstance.api.addStock(newStock)
                } catch (e: IOException) {
                    Log.e(TAG, "IOException: No network connection", e)
                    return@launch
                } catch (e: HttpException) {
                    Log.e(TAG, "HttpException: Unexpected response", e)
                    return@launch
                }


                if (response.isSuccessful) {
                    Toast.makeText(this@ClickedProductCatalogue, "Stock Added to Inventory!", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, InventoryActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "POST Failed: ${response.message()}")
                }
            }


        }


    }
}
