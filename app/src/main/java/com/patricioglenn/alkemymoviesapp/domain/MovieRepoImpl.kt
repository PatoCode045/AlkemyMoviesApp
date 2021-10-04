package com.patricioglenn.alkemymoviesapp.domain

import com.patricioglenn.alkemymoviesapp.data.ApiSesion
import com.patricioglenn.alkemymoviesapp.data.Movie
import com.patricioglenn.alkemymoviesapp.data.Rate
import com.patricioglenn.alkemymoviesapp.network.MovieApi


class MovieRepoImpl(private val api: MovieApi): MovieRepo {
    override suspend fun getGuessSession(): ApiSesion = api.retrofitService.getGuessSesion()
    override suspend fun getPopularMovies(page: Int): List<Movie> = api.retrofitService.getPopularMovies(page).results
    override suspend fun getSelectedMovie(movieId: Int): Movie = api.retrofitService.getSelectedMovie(movieId)
    override suspend fun rateMovie(movieId: Int, rate: Rate, session: String) = api.retrofitService.rateMovie(movieId, rate, session)
}