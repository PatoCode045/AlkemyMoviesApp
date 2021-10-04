package com.patricioglenn.alkemymoviesapp.database

import androidx.room.Entity

@Entity(primaryKeys = ["genre_id", "movie_id"])
data class GenreMovieCrossRefEntity(
    val genre_id: Long,
    val movie_id: Long
)
