package com.patricioglenn.alkemymoviesapp.network

import com.patricioglenn.alkemymoviesapp.data.ApiResult
import com.patricioglenn.alkemymoviesapp.data.ApiSesion
import com.patricioglenn.alkemymoviesapp.data.Movie
import com.patricioglenn.alkemymoviesapp.data.Rate
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "422660eb4dadeacdbf836544e9a23b65"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface MovieApiService {

    @GET("authentication/guest_session/new?api_key=${API_KEY}")
    suspend fun getGuessSesion(): ApiSesion

    @GET("movie/popular?api_key=${API_KEY}&language=es-ES")
    suspend fun getPopularMovies(@Query("page")page:Int): ApiResult

    @GET("movie/{movie_id}?api_key=${API_KEY}&language=es-ES")
    suspend fun getSelectedMovie(@Path("movie_id") id: Int): Movie

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating?api_key=${API_KEY}")
    suspend fun rateMovie(@Path("movie_id") movieId: Int, @Body rate: Rate, @Query("guest_session_id")session:String)

}


object MovieApi {
    val retrofitService : MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}
