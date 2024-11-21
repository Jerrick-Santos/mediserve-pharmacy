package com.mediserve.pharma.mediservepharma

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding
import com.mediserve.pharma.mediservepharma.databinding.ActivityScanQrBinding
import kotlinx.coroutines.launch
import java.io.IOException

class ScanQrActivity : ComponentActivity() {


    private lateinit var viewBinding: ActivityScanQrBinding
    private var isScannerInstalled = false
    private lateinit var scanner : GmsBarcodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        this.viewBinding = ActivityScanQrBinding.inflate(layoutInflater)
        setContentView(this.viewBinding.root)

        installGoogleScanner()
        initVars()
        registerUiListener()
    }

    private fun installGoogleScanner(){
        val moduleInstall = ModuleInstall.getClient(this)
        val moduleInstallRequest = ModuleInstallRequest.newBuilder()
            .addApi(GmsBarcodeScanning.getClient(this))
            .build()

        moduleInstall.installModules(moduleInstallRequest).addOnSuccessListener {
            isScannerInstalled = true
        } .addOnFailureListener{
            isScannerInstalled = false
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initVars() {
        val options = initializeGoogleScanner()
        scanner = GmsBarcodeScanning.getClient(this,options)
    }


    private fun initializeGoogleScanner() : GmsBarcodeScannerOptions{

        return GmsBarcodeScannerOptions.Builder().
                setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom().build()
    }

    private fun registerUiListener(){
        viewBinding.scanQrBtn.setOnClickListener{
            if (isScannerInstalled) {
                startScanning()
            } else {
                Toast.makeText(this, "Please try again...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun startScanning() {
        scanner.startScan().addOnSuccessListener {
            val result = it.rawValue
            result?.let {
                this.viewBinding.prescValue.text = buildString {
                    append("Scanned Value : ")
                    append(it.removePrefix("presc_id = "))
                }

                val sharedPreferences = getSharedPreferences("MediServePrefs", Context.MODE_PRIVATE)
                val pharmacyID = sharedPreferences.getInt("pharmacyID", -1)

                if (pharmacyID == -1) {
                    Toast.makeText(this, "Pharmacy ID not found. Please log in again.", Toast.LENGTH_SHORT).show()
                }

                val prescID = it.removePrefix("presc_id = ").toInt()

                val additionalItems = ScanQRPOST(
                    prescID, pharmacyID
                )

                lifecycleScope.launch {
                    val response = try {
                        RetrofitInstance.api.scanQR(additionalItems)
                    } catch (e: IOException) {
                        Log.e(TAG, "IOException: No network connection", e)
                        return@launch
                    } catch (e: HttpException) {
                        Log.e(TAG, "HttpException: Unexpected response", e)
                        return@launch
                    }


                    if (response.isSuccessful) {
                        Toast.makeText(this@ScanQrActivity, "Added to CART!", Toast.LENGTH_SHORT).show()
                        intent = Intent(applicationContext, ProcessOrderActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "POST Failed: ${response.message()}")
                    }
                }


            }
        }.addOnCanceledListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()

        }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            }
    }
}
