package com.mediserve.pharma.mediservepharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityScanQrBinding

class ScanQrActivity : ComponentActivity() {


    private lateinit var viewBinding: ActivityScanQrBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        this.viewBinding = ActivityScanQrBinding.inflate(layoutInflater)
        setContentView(this.viewBinding.root)

    }
}