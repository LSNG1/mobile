package com.example.travelcompanion.repository

import com.example.travelcompanion.api.OpenWeatherMapApi
import com.example.travelcompanion.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepository constructor(
    private val weatherApi: OpenWeatherMapApi
) {
    fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double,
        apiKey: String,
        mode: String? = null,
        units: String? = null,
        language: String? = null
    ): Flow<Result<WeatherResponse>> = flow {
        try {
            val response = weatherApi.getWeatherByCoordinates(latitude, longitude, apiKey, mode, units, language)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Exception("Error fetching weather data: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getWeatherByCity(
        city: String,
        apiKey: String,
        mode: String? = null,
        units: String? = null,
        language: String? = null
    ): Flow<Result<WeatherResponse>> = flow {
        try {
            val response = weatherApi.getWeatherByCity(city, apiKey, mode, units, language)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Exception("Error fetching weather data: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getWeatherByZip(
        zip: String,
        apiKey: String,
        mode: String? = null,
        units: String? = null,
        language: String? = null
    ): Flow<Result<WeatherResponse>> = flow {
        try {
            val response = weatherApi.getWeatherByZip(zip, apiKey, mode, units, language)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Exception("Error fetching weather data: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getWeatherByCityId(
        cityId: Int,
        apiKey: String,
        mode: String? = null,
        units: String? = null,
        language: String? = null
    ): Flow<Result<WeatherResponse>> = flow {
        try {
            val response = weatherApi.getWeatherByCityId(cityId, apiKey, mode, units, language)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Exception("Error fetching weather data: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
