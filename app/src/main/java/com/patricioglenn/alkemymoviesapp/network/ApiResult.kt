package com.patricioglenn.alkemymoviesapp.network

import com.patricioglenn.alkemymoviesapp.domain.Movie

data class ApiResult(
    val page: Int = 0,
    val results: List<Movie> = listOf(),
    val total_results: Int = 0,
    val total_pages: Int = 0
)