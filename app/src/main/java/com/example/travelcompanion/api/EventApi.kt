package com.example.travelcompanion.api

import com.example.travelcompanion.model.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {

    @GET("elements.json")
    suspend fun getEvents(
        @Query("limit") limit: Int,
        @Query("categories") categories: String?,
        @Query("bounds") bounds: String
    ): Response<EventResponse>
}
