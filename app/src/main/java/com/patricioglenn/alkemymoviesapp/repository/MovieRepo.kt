package com.patricioglenn.alkemymoviesapp.repository

import androidx.lifecycle.LiveData
import com.patricioglenn.alkemymoviesapp.database.MovieEntity
import com.patricioglenn.alkemymoviesapp.network.ApiSesion
import com.patricioglenn.alkemymoviesapp.domain.Movie
import com.patricioglenn.alkemymoviesapp.domain.Rate


interface MovieRepo {
    suspend fun getGuessSession(): ApiSesion
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getSelectedMovie(movieId: Long): LiveData<MovieEntity>
    suspend fun rateMovie(movieId: Long, rate: Rate, session: String)
}