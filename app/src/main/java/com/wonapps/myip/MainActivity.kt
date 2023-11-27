package com.wonapps.myip


import android.Manifest.permission.INTERNET
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.wonapps.myip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: View
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    private fun requestPermission(view: View) {
        when {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                INTERNET
            ) -> {
                layout.showSnackBar(
                    view,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.ok)
                ) {
                    requestPermissionLauncher.launch(
                        INTERNET
                    )
                }
            }

            else -> {
                requestPermissionLauncher.launch(
                    INTERNET
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getMyIPButton.setOnClickListener {
            mainActivityViewModel.getNetworkInfo()
        }

        layout = binding.root

        starObservers()
    }

    private fun starObservers() {
        mainActivityViewModel.errorResponse.observe(this) {
            if (it) {
                Toast.makeText(
                    this,
                    getString(R.string.request_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        mainActivityViewModel.requestInProgress.observe(this){
            binding.getMyIPButton.isEnabled = !it
        }

        mainActivityViewModel.myIP.observe(this)
        {
            val myIPText = "IP: ${it.ip}${System.lineSeparator()}" +
                    "Country: ${it.country}${System.lineSeparator()}" +
                    "CC: ${it.cc}${System.lineSeparator()}"

            binding.myIPTextView.text = myIPText
        }

        mainActivityViewModel.localIp.observe(this) {
            val localIPText = "Local IP: $it"

            binding.localIPTextView.text = localIPText
        }
    }

    override fun onResume() {
        super.onResume()
        requestPermission(layout)

        mainActivityViewModel.getNetworkInfo()
    }
}

fun View.showSnackBar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackBar = Snackbar.make(view, msg, length)

    if (actionMessage != null) {
        snackBar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackBar.show()
    }
}