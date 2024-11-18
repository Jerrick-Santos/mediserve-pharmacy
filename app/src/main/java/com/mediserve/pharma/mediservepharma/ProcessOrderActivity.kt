package com.mediserve.pharma.mediservepharma

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityProcessOrderBinding
import com.mediserve.pharma.mediservepharma.databinding.ProductItemBinding

class ProcessOrderActivity : ComponentActivity() {

    private lateinit var activityProcessOrderBinding: ActivityProcessOrderBinding

    private lateinit var data : ArrayList<Transaction>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.activityProcessOrderBinding = ActivityProcessOrderBinding.inflate(layoutInflater)
        setContentView(this.activityProcessOrderBinding.root)

        this.data = DataGenerator.generateTransactionList()

        this.recyclerView = this.activityProcessOrderBinding.recyclerView

        this.recyclerView.adapter = ProcessOrderAdapter(this.data)
        this.recyclerView.layoutManager = LinearLayoutManager(this)


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
}