package com.example.travelcompanion.api

import com.example.travelcompanion.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyExchangeApi {
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>
}
