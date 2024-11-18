package com.mediserve.pharma.mediservepharma

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.mediserve.pharma.mediservepharma.databinding.ActivityClickedProductCatalogueBinding

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

//            val resultIntent = Intent()
//            val inputStock = viewBinding.addStock.text.toString().toIntOrNull()
//            var result = -1
//
//            val position = intent.getIntExtra(positionKey, 0)
//            resultIntent.putExtra(positionKey, position)
//
//            if (inputStock != null && inputStock > 0) {
//
//                result = 1
//
//                resultIntent.putExtra(resultKey, result)
//                resultIntent.putExtra(qtyKey, inputStock)
//            }
//            else {
//                resultIntent.putExtra(qtyKey, 0)
//                resultIntent.putExtra(resultKey, result)
//            }
//
//            setResult(Activity.RESULT_OK, resultIntent)
//            finish()

            intent = Intent(applicationContext, ProductCatalogueActivity::class.java)
            this.startActivity(intent);

        }


    }
}
