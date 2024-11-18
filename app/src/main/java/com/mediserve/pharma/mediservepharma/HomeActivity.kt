package com.mediserve.pharma.mediservepharma

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mediserve.pharma.mediservepharma.databinding.ActivityHomeBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding

class HomeActivity : ComponentActivity() {
    private lateinit var activityMainBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.activityMainBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(this.activityMainBinding.root)


        //NAV BAR

        this.activityMainBinding.navProdCat.setOnClickListener{
            intent = Intent(applicationContext, ProductCatalogueActivity::class.java)
            this.startActivity(intent);
        }

        this.activityMainBinding.navProcessOrder.setOnClickListener{
            intent = Intent(applicationContext, ProcessOrderActivity::class.java)
            this.startActivity(intent);
        }

        this.activityMainBinding.navInventory.setOnClickListener{
            intent = Intent(applicationContext, InventoryActivity::class.java)
            this.startActivity(intent);
        }

        this.activityMainBinding.navTransactions.setOnClickListener{
            intent = Intent(applicationContext, ViewTransactions::class.java)
            this.startActivity(intent);
        }

        // HOME

        // Navigate to ProductCatalogueActivity
        this.activityMainBinding.homeProdCat.setOnClickListener{
            intent = Intent(applicationContext, ProductCatalogueActivity::class.java)
            this.startActivity(intent);
        }

        this.activityMainBinding.homeProcessOrder.setOnClickListener{
            intent = Intent(applicationContext, ProcessOrderActivity::class.java)
            this.startActivity(intent);
        }


        this.activityMainBinding.homeInventory.setOnClickListener{
            intent = Intent(applicationContext, InventoryActivity::class.java)
            this.startActivity(intent);
        }

        this.activityMainBinding.homeTransactions.setOnClickListener{
            intent = Intent(applicationContext, ViewTransactions::class.java)
            this.startActivity(intent);
        }


    }

    override fun onStart() {
        super.onStart()

        // Retrieve and log SharedPreferences
        val sharedPreferences = getSharedPreferences("MediServePrefs", Context.MODE_PRIVATE)
        val pharmacyID = sharedPreferences.getInt("pharmacyID", -1) // Default to -1 if not found
        val username = sharedPreferences.getString("username", "N/A") // Default to "N/A" if not found
        val userID = sharedPreferences.getInt("userID", -1) // Default to -1 if not found

        Log.d("SharedPreferences", "pharmacyID: $pharmacyID, username: $username, userID: $userID")
    }
}