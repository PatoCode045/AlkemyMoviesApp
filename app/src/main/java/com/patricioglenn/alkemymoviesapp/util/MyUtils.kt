package com.patricioglenn.alkemymoviesapp.util

import com.patricioglenn.alkemymoviesapp.domain.Genre

object MyUtils{

    fun formatGenres(genresList: List<Genre>): String{
        var formatedGenres = ""
        genresList.forEach{ genre->
            formatedGenres = "${formatedGenres}${genre.name}, "
        }
        formatedGenres = formatedGenres.subSequence(0, formatedGenres.length - 2).toString()
        return formatedGenres
    }

    fun formatLanguage(language: String): String{
        var formatedLanguage = when(language){
            "en" -> "Ingles"
            "es" -> "Español"
            "fr" -> "Frances"
            "ru" -> "Ruso"
            else -> "no disponible"
        }
        return formatedLanguage
    }

    fun formatDate(date: String): String{
        var year = date.subSequence(0, 4)
        var month = date.subSequence(5, 7)
        var day = date.subSequence(8, 10)
        return "${day}-${month}-${year}"
    }
}