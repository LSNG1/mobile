package com.example.travelcompanion.repository

import com.example.travelcompanion.util.RetrofitInstance
import com.example.travelcompanion.model.CurrencyResponse
import retrofit2.Response

class CurrencyRepository {
    suspend fun getExchangeRates(base: String): Response<CurrencyResponse> {
        return RetrofitInstance.currencyApi.getExchangeRates(base)
    }
}
