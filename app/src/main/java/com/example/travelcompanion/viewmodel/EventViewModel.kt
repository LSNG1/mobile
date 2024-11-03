package com.example.travelcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelcompanion.model.Event
import com.example.travelcompanion.repository.EventRepository
import com.example.travelcompanion.util.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    private val eventRepository = EventRepository(RetrofitInstance.eventApi)

    private val _events = MutableStateFlow<List<Event>?>(null)
    val events: StateFlow<List<Event>?> = _events

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchEvents(bounds: String = "1.47217,48.08542,3.57056,49.21042") {
        viewModelScope.launch {
            eventRepository.getEvents(bounds = bounds)
                .catch { exception ->
                    _error.value = "Error fetching events: ${exception.message}"
                }
                .collect { result ->
                    result.onSuccess { data ->
                        _events.value = data
                    }.onFailure { exception ->
                        _error.value = "Failed to retrieve events: ${exception.message}"
                    }
                }
        }
    }
}
