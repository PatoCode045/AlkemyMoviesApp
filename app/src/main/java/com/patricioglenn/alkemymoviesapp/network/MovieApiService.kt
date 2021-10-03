package com.patricioglenn.alkemymoviesapp.network

import com.patricioglenn.alkemymoviesapp.data.ApiResult
import com.patricioglenn.alkemymoviesapp.data.Movie
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
private const val API_KEY = "422660eb4dadeacdbf836544e9a23b65"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface MovieApiService {

    @GET("popular?api_key=${API_KEY}&language=es-ES")
    suspend fun getPopularMovies(): ApiResult

    @GET("{movie_id}?api_key=${API_KEY}&language=es-ES")
    suspend fun getSelectedMovie(@Path("movie_id") id: Int): Movie
}


object MovieApi {
    val retrofitService : MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}
