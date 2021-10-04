package com.patricioglenn.alkemymoviesapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patricioglenn.alkemymoviesapp.domain.Genre

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val movie_id: Long,
    val title: String,
    val overview: String,
    val release_date: String,
    val original_language: String,
    val poster_path: String,
    val backdrop_path: String ,
    val vote_average: Float
)
