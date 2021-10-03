package com.patricioglenn.alkemymoviesapp.domain

import com.patricioglenn.alkemymoviesapp.data.Movie


interface MovieRepo {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getSelectedMovie(id: Int): Movie
}