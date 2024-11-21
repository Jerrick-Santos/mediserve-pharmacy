package com.mediserve.pharma.mediservepharma

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.ActivityAddOrderBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddOrderActivity : ComponentActivity() {

    private lateinit var viewBinding: ActivityAddOrderBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddOrderAdapter
    private val inventoryList = ArrayList<InventoryStock>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("MediServePrefs", Context.MODE_PRIVATE)
        val pharmacyID = sharedPreferences.getInt("pharmacyID", -1)

        if (pharmacyID == -1) {
            Toast.makeText(this, "Pharmacy ID not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        this.viewBinding = ActivityAddOrderBinding.inflate(layoutInflater)
        setContentView(this.viewBinding.root)

        this.recyclerView = viewBinding.recyclerView
        this.adapter = AddOrderAdapter(inventoryList, pharmacyID, RetrofitInstance.api, this)
        this.recyclerView.adapter = adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        fetchInventoryData()

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

    private fun fetchInventoryData() {
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
                val response = RetrofitInstance.api.getInventory(pharmacyID)
                if (response.isSuccessful && response.body() != null) {
                    val fetchedInventory = response.body()!! // List<InventoryStockGET>

                    // Convert the API response to InventoryStock objects
                    val inventoryStocks = ArrayList<InventoryStock>()
                    for (productData in fetchedInventory) {
                        // Transform the id
                        val transformedId = productData.product_id.removePrefix("PRDCT").toIntOrNull()
                        if (transformedId == null) {
                            Log.e("Data Error", "Invalid product ID format: ${productData.product_id}")
                            continue
                        }

                        // Create Product and InventoryStock objects
                        val product = Product(
                            transformedId.toString(), // Use the transformed ID as a string
                            productData.brandName,
                            productData.genericName,
                            productData.manufacturer,
                            productData.dosage
                        )
                        val inventoryStock = InventoryStock(productData.stock_id, product, productData.qty)
                        inventoryStocks.add(inventoryStock)
                    }

                    // Update UI with the fetched data
                    withContext(Dispatchers.Main) {
                        inventoryList.clear()
                        inventoryList.addAll(inventoryStocks)
                        Log.d("test", inventoryList.toString())
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("API Error", "Error fetching inventory: ${response.message()}")
                        Toast.makeText(
                            this@AddOrderActivity,
                            "Error fetching inventory: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Exception: ${e.message}")
                    Toast.makeText(
                        this@AddOrderActivity,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}