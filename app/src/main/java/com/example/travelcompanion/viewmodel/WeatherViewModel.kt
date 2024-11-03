// WeatherViewModel.kt
package com.example.travelcompanion.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelcompanion.api.OpenWeatherMapApi
import com.example.travelcompanion.model.WeatherResponse
import com.example.travelcompanion.repository.WeatherRepository
import com.example.travelcompanion.util.RetrofitInstance
import com.example.travelcompanion.util.LocationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository(RetrofitInstance.weatherApi)

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchWeatherByLocation(context: Context) {
        viewModelScope.launch {
            try {
                val locationHelper = LocationHelper(context)
                val location = locationHelper.getLastLocation()

                if (location != null) {
                    val (latitude, longitude) = location
                    weatherRepository.getWeatherByCoordinates(
                        latitude = latitude,
                        longitude = longitude,
                        apiKey = "1b8c44c2195c3e02989ed66724e777af",
                        mode = "json",
                        units = "metric"
                    ).catch { exception ->
                        _error.value = "Error fetching weather data: ${exception.message}"
                    }.collect { result ->
                        result.onSuccess { response ->
                            _weather.value = response
                        }.onFailure { exception ->
                            _error.value = "Failed to retrieve weather data: ${exception.message}"
                        }
                    }
                } else {
                    _error.value = "Unable to retrieve location."
                }
            } catch (e: Exception) {
                _error.value = "Unexpected error: ${e.message}"
            }
        }
    }

    fun fetchWeatherByCity(city: String) {
        viewModelScope.launch {
            weatherRepository.getWeatherByCity(
                city = city,
                apiKey = "1b8c44c2195c3e02989ed66724e777af",
                mode = "json",
                units = "metric"
            ).catch { exception ->
                _error.value = "Error fetching weather data: ${exception.message}"
            }.collect { result ->
                result.onSuccess { response ->
                    _weather.value = response
                }.onFailure { exception ->
                    _error.value = "Failed to retrieve weather data: ${exception.message}"
                }
            }
        }
    }
}
