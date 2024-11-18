package com.mediserve.pharma.mediservepharma

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mediserve.pharma.mediservepharma.databinding.ActivityClickedProductCatalogueBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityUpdateInventoryBinding

class UpdateInventoryActivity: ComponentActivity() {

    private lateinit var viewBinding: ActivityUpdateInventoryBinding


    private lateinit var inventoryId: String
    private lateinit var inventoryBrand: String
    private lateinit var inventoryGeneric: String
    private lateinit var inventoryManufacturer: String
    private lateinit var inventoryDosage: String
    private lateinit var inventoryQty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewBinding
        viewBinding = ActivityUpdateInventoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // STEP 6: Get the values from the intent and store them to class attributes
        inventoryId = intent.getStringExtra(InventoryAdapter.inventoryIdKey).toString()
        inventoryBrand = intent.getStringExtra(InventoryAdapter.inventoryBrandKey).toString()
        inventoryGeneric = intent.getStringExtra(InventoryAdapter.inventoryGenericKey).toString()
        inventoryManufacturer = intent.getStringExtra(InventoryAdapter.inventoryManufacturerKey).toString()
        inventoryDosage = intent.getStringExtra(InventoryAdapter.inventoryDosageKey).toString()


        // STEP 7: Set the attribute values to activity widgets
        viewBinding.productImage.setImageResource(R.drawable.medicine)
        viewBinding.brandName.text = inventoryBrand
        viewBinding.id.text = inventoryId
        viewBinding.genericName.text = inventoryGeneric
        viewBinding.manufacturer.text = inventoryManufacturer
        viewBinding.dosage.text = inventoryDosage

        viewBinding.currQty.text = intent.getStringExtra(InventoryAdapter.inventoryQtyKey).toString()

        viewBinding.updateBtn.setOnClickListener {

            intent = Intent(applicationContext, InventoryActivity::class.java)
            this.startActivity(intent);

        }

        //NAV BAR

        this.viewBinding.navProdCat.setOnClickListener{
            intent = Intent(applicationContext, ProductCatalogueActivity::class.java)
            this.startActivity(intent);
        }

        this.viewBinding.navProcessOrder.setOnClickListener{
            intent = Intent(applicationContext, ProcessOrderActivity::class.java)
            this.startActivity(intent);
        }

        this.viewBinding.navInventory.setOnClickListener{
            intent = Intent(applicationContext, InventoryActivity::class.java)
            this.startActivity(intent);
        }

        this.viewBinding.navTransactions.setOnClickListener{
            intent = Intent(applicationContext, ViewTransactions::class.java)
            this.startActivity(intent);
        }


    }
}
