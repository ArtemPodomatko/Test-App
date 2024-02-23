package ru.aapodomatko.testapp.models

data class NewsResponse(
    val articles: List<Article>,
    val totalArticles: Int
)