package com.example.travelcompanion.util

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class LocationHelper(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Pair<Double, Double>? {
        val location = fusedLocationClient.lastLocation.await()
        return if (location != null) {
            Pair(location.latitude, location.longitude)
        } else {
            null
        }
    }
}
