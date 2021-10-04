package com.patricioglenn.alkemymoviesapp.domain

import com.patricioglenn.alkemymoviesapp.data.ApiSesion
import com.patricioglenn.alkemymoviesapp.data.Movie
import com.patricioglenn.alkemymoviesapp.data.Rate


interface MovieRepo {
    suspend fun getGuessSession(): ApiSesion
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getSelectedMovie(movieId: Int): Movie
    suspend fun rateMovie(movieId: Int, rate: Rate, session: String)
}