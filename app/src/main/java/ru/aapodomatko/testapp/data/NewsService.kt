package ru.aapodomatko.testapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.aapodomatko.testapp.models.NewsResponse
import ru.aapodomatko.testapp.utils.Constants.Companion.API_KEY

interface NewsService {

    @GET("v4/top-headlines")
    suspend fun getHeadLines(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("category") category: String
    ): Response<NewsResponse>

    @GET("v4/search")
    suspend fun searchNews(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("q") query: String,
        @Query("sortby") sortBy: String = "publishedAt"
    ): Response<NewsResponse>
}