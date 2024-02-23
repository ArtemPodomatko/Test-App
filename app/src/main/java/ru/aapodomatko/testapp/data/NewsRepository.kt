package ru.aapodomatko.testapp.data

import javax.inject.Inject


class NewsRepository @Inject constructor(private val newsService: NewsService){
    suspend fun getNews(category: String) = newsService.getHeadLines(category = category)

    suspend fun searchNews(query: String) = newsService.searchNews(query = query)
}