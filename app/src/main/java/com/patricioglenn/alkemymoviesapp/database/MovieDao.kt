package com.patricioglenn.alkemymoviesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("select * from movies where movie_id=:id")
    fun getMovie(id: Long): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie( movie: MovieEntity)

}