package com.example.travelcompanion.repository

import com.example.travelcompanion.model.NewsResponse
import com.example.travelcompanion.util.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository {

    private val newsApi = RetrofitInstance.newsApi

    suspend fun getNews(
        query: String,
        apiKey: String,
        searchIn: String? = null,
        sources: String? = null,
        domains: String? = null,
        excludeDomains: String? = null,
        from: String? = null,
        to: String? = null,
        language: String? = null,
        sortBy: String? = null,
        pageSize: Int? = 100,
        page: Int? = 1
    ): Flow<Result<NewsResponse>> = flow {
        try {
            val response = newsApi.getNews(query, apiKey, searchIn, sources, domains, excludeDomains, from, to, language, sortBy, pageSize, page)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Exception("Error fetching news data: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getTopHeadlines(
        apiKey: String,
        country: String? = null,
        category: String? = null,
        sources: String? = null,
        query: String? = null,
        pageSize: Int? = 20,
        page: Int? = 1
    ): Flow<Result<NewsResponse>> = flow {
        try {
            val response = newsApi.getTopHeadlines(apiKey, country, category, sources, query, pageSize, page)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Exception("Error fetching top headlines: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
