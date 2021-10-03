package com.patricioglenn.alkemymoviesapp.domain

import com.patricioglenn.alkemymoviesapp.data.Movie
import com.patricioglenn.alkemymoviesapp.network.MovieApi


class MovieRepoImpl(private val api: MovieApi): MovieRepo {
    override suspend fun getPopularMovies(page: Int): List<Movie> = api.retrofitService.getPopularMovies(page).results
    override suspend fun getSelectedMovie(id: Int): Movie = api.retrofitService.getSelectedMovie(id)
}