package com.example.travelcompanion.api

import com.example.travelcompanion.model.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    @GET("weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("mode") mode: String? = null,
        @Query("units") units: String? = null,
        @Query("lang") language: String? = null
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("mode") mode: String? = null,
        @Query("units") units: String? = null,
        @Query("lang") language: String? = null
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherByZip(
        @Query("zip") zip: String,
        @Query("appid") apiKey: String,
        @Query("mode") mode: String? = null,
        @Query("units") units: String? = null,
        @Query("lang") language: String? = null
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String,
        @Query("mode") mode: String? = null,
        @Query("units") units: String? = null,
        @Query("lang") language: String? = null
    ): Response<WeatherResponse>
}