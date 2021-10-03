package com.patricioglenn.alkemymoviesapp.data

data class ApiResult(
    val page: Int = 0,
    val results: List<Movie> = listOf(),
    val total_results: Int = 0,
    val total_pages: Int = 0
)