package com.patricioglenn.alkemymoviesapp.domain

import com.patricioglenn.alkemymoviesapp.data.Movie
import com.patricioglenn.alkemymoviesapp.network.MovieApi


class MovieRepoImpl(private val api: MovieApi): MovieRepo {
    override suspend fun getPopularMovies(): List<Movie> = api.retrofitService.getPopularMovies()?.results
    override suspend fun getSelectedMovie(id: Int): Movie = api.retrofitService.getSelectedMovie(id)
}