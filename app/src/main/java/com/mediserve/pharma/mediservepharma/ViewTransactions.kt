package com.mediserve.pharma.mediservepharma

import android.content.Context
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
import com.mediserve.pharma.mediservepharma.databinding.ActivityViewTransactionsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewTransactions : ComponentActivity() {

    private lateinit var viewBinding: ActivityViewTransactionsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ViewTransactionsAdapter
    private val transactionList = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = ActivityViewTransactionsBinding.inflate(layoutInflater)
        setContentView(this.viewBinding.root)

        // Set up RecyclerView
        this.recyclerView = this.viewBinding.myRecyclerView
        this.adapter = ViewTransactionsAdapter(transactionList)
        this.recyclerView.adapter = adapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from the API
        fetchTransactions()
    }

    private fun fetchTransactions() {
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
                val response = RetrofitInstance.api.getTransactions(pharmacyID)
                if (response.isSuccessful && response.body() != null) {
                    val fetchedTransactions = response.body()!!


                    // Process the API response
                    val transformedTransactions = ArrayList<Transaction>()
                    for (transactionData in fetchedTransactions) {

                        // Create a new Transaction object
                        val transaction = Transaction(
                            transac_id = transactionData.transac_id, // Optionally format the ID
                            product_name = transactionData.product_name,
                            change_type = transactionData.change_type,
                            qty = transactionData.qty
                        )
                        transformedTransactions.add(transaction)
                    }



                    // Update UI with transformed data
                    withContext(Dispatchers.Main) {
                        transactionList.clear()
                        transactionList.addAll(transformedTransactions)
                        Log.d("FETCHED_TRANSACS", transactionList.toString())
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.e("API Error", "Error fetching transactions: ${response.message()}")
                        Toast.makeText(
                            this@ViewTransactions,
                            "Error fetching transactions: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API Error", "Exception: ${e.message}")
                    Toast.makeText(
                        this@ViewTransactions,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
