package com.mediserve.pharma.mediservepharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediserve.pharma.mediservepharma.databinding.ActivityAddOrderBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityViewTransactionsBinding

class ViewTransactions : ComponentActivity() {

    private lateinit var viewBinding : ActivityViewTransactionsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var data: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = ActivityViewTransactionsBinding.inflate(layoutInflater)
        setContentView(this.viewBinding.root)

        data = DataGenerator.generateTransactionList()

        this.recyclerView = viewBinding.recyclerView
        this.recyclerView.adapter = ViewTransactionsAdapter(this.data)
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        

    }
}