package com.example.travelcompanion.repository

import com.example.travelcompanion.util.RetrofitInstance
import com.example.travelcompanion.model.LocationResponse
import retrofit2.Response

class GeoNamesRepository {
    suspend fun getLocation(latitude: Double, longitude: Double, username: String): Response<LocationResponse> {
        return RetrofitInstance.geoNamesApi.getLocation(latitude, longitude, username)
    }
}