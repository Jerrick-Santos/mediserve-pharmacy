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
import com.mediserve.pharma.mediservepharma.databinding.ActivityAddOrderBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding

class AddOrderActivity : ComponentActivity() {

    private lateinit var viewBinding: ActivityAddOrderBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var data: ArrayList<InventoryStock>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = ActivityAddOrderBinding.inflate(layoutInflater)
        setContentView(this.viewBinding.root)


        data = DataGenerator.generateInventoryStockList()


        this.recyclerView = viewBinding.recyclerView
        this.recyclerView.adapter = AddOrderAdapter(this.data)
        this.recyclerView.layoutManager = LinearLayoutManager(this)

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