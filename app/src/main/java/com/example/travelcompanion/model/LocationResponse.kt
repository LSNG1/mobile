package com.example.travelcompanion.model

data class LocationResponse(
    val geonames: List<GeoName>
)

data class GeoName(
    val name: String,
    val lat: Double,
    val lng: Double,
    val countryName: String
)
