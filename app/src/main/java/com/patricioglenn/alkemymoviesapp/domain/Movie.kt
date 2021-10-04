package com.patricioglenn.alkemymoviesapp.domain

data class Movie(
    val id: Long = 0,
    val title: String = "",
    val overview: String = "",
    val release_date: String = "",
    val genres: List<Genre> = listOf(),
    val original_language: String = "",
    val poster_path: String = "",
    val backdrop_path: String = "",
    val vote_average: Float = 0F
)


