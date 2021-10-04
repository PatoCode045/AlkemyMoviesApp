package com.patricioglenn.alkemymoviesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.patricioglenn.alkemymoviesapp.database.MovieDatabase
import com.patricioglenn.alkemymoviesapp.database.MovieEntity
import com.patricioglenn.alkemymoviesapp.network.ApiSesion
import com.patricioglenn.alkemymoviesapp.domain.Movie
import com.patricioglenn.alkemymoviesapp.domain.Rate
import com.patricioglenn.alkemymoviesapp.network.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher


class MovieRepoImpl(private val api: MovieApi, private val db: MovieDatabase): MovieRepo {
    override suspend fun getGuessSession(): ApiSesion = api.retrofitService.getGuessSesion()
    override suspend fun getPopularMovies(page: Int): List<Movie> = api.retrofitService.getPopularMovies(page).results
    override suspend fun getSelectedMovie(movieId: Long): LiveData<MovieEntity> {
        Log.d("repo", "se llamo a la funcion con el id ${movieId}")

        val dbMovie = withContext(Dispatchers.IO) {
            val movie = db.movieDao.getMovie(movieId )

            if (movie?.movie_id == movieId){
                Log.d("repo", "se llamo  base")
                movie
            }else{
                Log.d("repo", "se llamo  api")
                val apiMovie = api.retrofitService.getSelectedMovie(movieId)
                val dbMovie = MovieEntity(apiMovie.id,
                    apiMovie.title, apiMovie.overview, apiMovie.release_date, apiMovie.original_language,
                    apiMovie.poster_path, apiMovie.backdrop_path, apiMovie.vote_average)
                db.movieDao.insertMovie(dbMovie)
                dbMovie
            }
        }
        return MutableLiveData(dbMovie)
    }
    override suspend fun rateMovie(movieId: Long, rate: Rate, session: String) = api.retrofitService.rateMovie(movieId, rate, session)
}