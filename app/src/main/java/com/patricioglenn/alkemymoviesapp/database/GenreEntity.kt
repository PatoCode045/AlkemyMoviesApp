package com.patricioglenn.alkemymoviesapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patricioglenn.alkemymoviesapp.domain.Genre


@Entity
data class GenreEntity(
    @PrimaryKey
    val genre_id: Long,
    val name: String
)