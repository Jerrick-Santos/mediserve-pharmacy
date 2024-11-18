package com.mediserve.pharma.mediservepharma

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.mediserve.pharma.mediservepharma.databinding.ActivityMainBinding
import com.mediserve.pharma.mediservepharma.ui.theme.MediservePharmaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.activityMainBinding.root)

        this.activityMainBinding.btnLogin.setOnClickListener {
            val username = activityMainBinding.editTextTextEmailAddress.text.toString()
            val password = activityMainBinding.editTextTextPassword.text.toString()

            // Use lifecycleScope for coroutine management
            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.api.login(username, password)
                    if (response.isSuccessful && response.body() != null) {
                        val userPharmacist = response.body()!!

                        Log.d("SUCCESS", "Username: ${userPharmacist.username}")

                        // Save details to SharedPreferences
                        val sharedPreferences = getSharedPreferences("MediServePrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putInt("pharmacyID", userPharmacist.pharmacy_ID)
                        editor.putString("username", userPharmacist.username)
                        editor.putInt("userID", userPharmacist.userId)
                        editor.apply() // Save changes asynchronously


                        // Navigate to HomeActivity
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                        
                    } else {
                        // Handle login failure
                        Log.d("FAIL: ", response.toString())
                        Toast.makeText(
                            this@MainActivity,
                            "Login failed: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    // Handle network or unexpected errors
                    Log.d("ERROR: ", e.message.toString())
                    Toast.makeText(
                        this@MainActivity,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
