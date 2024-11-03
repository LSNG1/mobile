package com.example.travelcompanion.repository

import com.example.travelcompanion.api.EventApi
import com.example.travelcompanion.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class EventRepository(private val eventApi: EventApi) {

    fun getEvents(
        limit: Int = 100,
        categories: String? = null,
        bounds: String
    ): Flow<Result<List<Event>>> = flow {
        try {
            val response = eventApi.getEvents(limit, categories, bounds)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it.data))
                } ?: throw HttpException(response)
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
