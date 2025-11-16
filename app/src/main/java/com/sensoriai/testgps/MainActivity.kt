package com.sensoriai.testgps

import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sensoriai.testgps.ui.theme.TestGPSTheme

import com.google.android.gms.location.*

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude = mutableStateOf(0.0)
    private var longitude = mutableStateOf(0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("Hello World!")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        setupLocationUpdates()

        enableEdgeToEdge()
        setContent {
            TestGPSTheme {
                Text(
                    text = "LAT: ${latitude.value} LON: ${longitude.value}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    private fun setupLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L
        ).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    println("-----")
                    println("LAT: ${location.latitude} LON: ${location.longitude}")
                    println("-----")

                    latitude.value = location.latitude
                    longitude.value = location.longitude
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,Looper.getMainLooper())
    }
}