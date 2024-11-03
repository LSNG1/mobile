package com.example.travelcompanion.util

import com.example.travelcompanion.api.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConstants {
    const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val GEONAMES_BASE_URL = "http://api.geonames.org/"
    const val EVENT_BASE_URL = "https://presdecheznous.gogocarto.fr/api/"
    const val CURRENCY_BASE_URL = "https://api.exchangeratesapi.io/"
    const val NEWS_BASE_URL = "https://newsapi.org/v2/"
}

object RetrofitInstance {

    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: OpenWeatherMapApi by lazy {
        createRetrofitInstance(ApiConstants.WEATHER_BASE_URL)
            .create(OpenWeatherMapApi::class.java)
    }

    val geoNamesApi: GeoNamesApi by lazy {
        createRetrofitInstance(ApiConstants.GEONAMES_BASE_URL)
            .create(GeoNamesApi::class.java)
    }

    val eventApi: EventApi by lazy {
        createRetrofitInstance(ApiConstants.EVENT_BASE_URL)
            .create(EventApi::class.java)
    }

    val currencyApi: CurrencyExchangeApi by lazy {
        createRetrofitInstance(ApiConstants.CURRENCY_BASE_URL)
            .create(CurrencyExchangeApi::class.java)
    }

    val newsApi: NewsApi by lazy {
        createRetrofitInstance(ApiConstants.NEWS_BASE_URL)
            .create(NewsApi::class.java)
    }
}
