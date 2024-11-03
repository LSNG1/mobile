package com.example.travelcompanion.model

data class Event(
    val id: String,
    val name: String,
    val geo: Geo,
    val sourceKey: String?,
    val address: Address?,
    val openHours: Map<String, String>?,
    val createdAt: String?,
    val updatedAt: String?,
    val status: Int?,
    val categories: List<String>?,
    val categoriesFull: List<CategoryFull>?,
    val commitment: String?,
    val description: String?,
    val openhours_more_infos: String?,
    val telephone: Telephone?,
    val subscriberEmails: List<String>?
)

data class Geo(
    val latitude: Double,
    val longitude: Double
)

data class Address(
    val streetAddress: String?,
    val addressLocality: String?,
    val postalCode: String?,
    val addressCountry: String?,
    val streetNumber: String?,
    val customFormatedAddress: String?
)

data class CategoryFull(
    val id: Int,
    val name: String,
    val description: String?,
    val index: Int
)

data class EventResponse(
    val licence: String,
    val ontology: String,
    val data: List<Event>
)
