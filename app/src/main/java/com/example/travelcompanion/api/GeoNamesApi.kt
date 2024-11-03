package com.example.travelcompanion.api

import com.example.travelcompanion.model.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoNamesApi {
    @GET("findNearbyPlaceNameJSON")
    suspend fun getLocation(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("username") username: String
    ): Response<LocationResponse>
}
