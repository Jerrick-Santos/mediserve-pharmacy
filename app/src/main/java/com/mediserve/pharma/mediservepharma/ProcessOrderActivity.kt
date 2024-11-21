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
import com.mediserve.pharma.mediservepharma.databinding.ActivityProcessOrderBinding
import com.mediserve.pharma.mediservepharma.databinding.ProductItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProcessOrderActivity : ComponentActivity() {

    private lateinit var activityProcessOrderBinding: ActivityProcessOrderBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProcessOrderAdapter
    private var data: ArrayList<Order> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.activityProcessOrderBinding = ActivityProcessOrderBinding.inflate(layoutInflater)
        setContentView(this.activityProcessOrderBinding.root)

        this.recyclerView = activityProcessOrderBinding.recyclerView
        this.adapter = ProcessOrderAdapter(data)
        this.recyclerView.adapter = adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        fetchInventoryData()

        activityProcessOrderBinding.addItemsBtn.setOnClickListener{
            val intent = Intent(applicationContext, AddOrderActivity::class.java)

            this.startActivity(intent);
        }

        activityProcessOrderBinding.scanQRBttn.setOnClickListener{
            val intent = Intent(applicationContext, ScanQrActivity::class.java)

            this.startActivity(intent);
        }


        //NAV BAR

        this.activityProcessOrderBinding.navProdCat.setOnClickListener{
            intent = Intent(applicationContext, ProductCatalogueActivity::class.java)
            this.startActivity(intent);
        }

        this.activityProcessOrderBinding.navProcessOrder.setOnClickListener{
            intent = Intent(applicationContext, ProcessOrderActivity::class.java)
            this.startActivity(intent);
        }

        this.activityProcessOrderBinding.navInventory.setOnClickListener{
            intent = Intent(applicationContext, InventoryActivity::class.java)
            this.startActivity(intent);
        }

        this.activityProcessOrderBinding.navTransactions.setOnClickListener{
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
        Log.d("PARAM CHECK", pharmacyID.toString())
        // Make API call using Retrofit
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getCart(pharmacyID)
                if (response.isSuccessful && response.body() != null) {
                    val fetchedCartItems = response.body()!! // List<InventoryStockGET>
                    Log.d("TANGINAMOGAGOHAHA", fetchedCartItems.toString())
                    // Update UI with the fetched data
                    withContext(Dispatchers.Main) {
                        data.clear()
                        data.addAll(fetchedCartItems)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("API Error", "Error fetching inventory: ${response.message()}")
                        Toast.makeText(
                            this@ProcessOrderActivity,
                            "Error fetching inventory: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Exception: ${e.message}")
                    Toast.makeText(
                        this@ProcessOrderActivity,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}