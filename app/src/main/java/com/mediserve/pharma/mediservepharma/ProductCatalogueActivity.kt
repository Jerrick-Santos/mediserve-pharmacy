package com.mediserve.pharma.mediservepharma

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityProductCatalogueBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductCatalogueActivity : ComponentActivity() {

    private lateinit var activityProductCatalogueBinding: ActivityProductCatalogueBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductCatalogueAdapter
    private val productList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.activityProductCatalogueBinding = ActivityProductCatalogueBinding.inflate(layoutInflater)
        setContentView(this.activityProductCatalogueBinding.root)

        // Setup RecyclerView
        this.recyclerView = this.activityProductCatalogueBinding.recyclerView
        this.adapter = ProductCatalogueAdapter(productList)
        this.recyclerView.adapter = adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from the API
        fetchProductCatalogue()

        // NAV BAR
        setupNavigation()
    }

    private fun fetchProductCatalogue() {
        // Retrieve pharmacyID from SharedPreferences
        val sharedPreferences = getSharedPreferences("MediServePrefs", Context.MODE_PRIVATE)
        val pharmacyID = sharedPreferences.getInt("pharmacyID", -1)

        if (pharmacyID == -1) {
            Toast.makeText(this, "Pharmacy ID not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        // Make API call using Retrofit
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getProductCatalogue(pharmacyID)
                if (response.isSuccessful && response.body() != null) {
                    val fetchedProducts = response.body()!!

                    // Update UI with the fetched data
                    withContext(Dispatchers.Main) {
                        productList.clear()
                        productList.addAll(fetchedProducts)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("API Error", "Error fetching products: ${response.message()}")
                        Toast.makeText(
                            this@ProductCatalogueActivity,
                            "Error fetching product catalogue: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Exception: ${e.message}")
                    Toast.makeText(
                        this@ProductCatalogueActivity,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupNavigation() {
        this.activityProductCatalogueBinding.navProdCat.setOnClickListener {
            intent = Intent(applicationContext, ProductCatalogueActivity::class.java)
            this.startActivity(intent)
        }

        this.activityProductCatalogueBinding.navProcessOrder.setOnClickListener {
            intent = Intent(applicationContext, ProcessOrderActivity::class.java)
            this.startActivity(intent)
        }

        this.activityProductCatalogueBinding.navInventory.setOnClickListener {
            intent = Intent(applicationContext, InventoryActivity::class.java)
            this.startActivity(intent)
        }

        this.activityProductCatalogueBinding.navTransactions.setOnClickListener {
            intent = Intent(applicationContext, ViewTransactions::class.java)
            this.startActivity(intent)
        }
    }
}