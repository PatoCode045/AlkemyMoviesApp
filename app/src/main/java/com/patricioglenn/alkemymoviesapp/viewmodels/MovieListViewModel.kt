package com.patricioglenn.alkemymoviesapp.viewmodels

import androidx.lifecycle.*
import com.patricioglenn.alkemymoviesapp.domain.Movie
import com.patricioglenn.alkemymoviesapp.repository.MovieRepo
import kotlinx.coroutines.launch


const val NO_NETWORK = "Unable to resolve host \"api.themoviedb.org\": No address associated with hostname"

class MovieListViewModel (private val repo: MovieRepo): ViewModel(){

    var requestState = MutableLiveData<String>()
    var errorMessage = MutableLiveData<String>()
    var movies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    var filteredMovieList: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    var page = 1

    init {
        getPopularMovies()
    }

    fun getPopularMovies(){
        requestState.value = "loading"
        viewModelScope.launch {
            try {
                var newList: MutableList<Movie> = mutableListOf()
                movies.value?.let { newList = movies.value!! }
                newList.addAll(repo.getPopularMovies(page))
                movies.value = newList
                filteredMovieList.value = newList
                page++
            }catch (e: Exception){
                requestState.value = "error"

                if(e.message == NO_NETWORK){
                    errorMessage.value = "Ha ocurrido un error inesperado, verifique su conexion a internet e intentelo nuevamente."
                }else{
                    errorMessage.value = "Ha ocurrido un error inesperado, por favor, pongase en contacto con el creador de esta aplicacion."
                }
            }
        }
    }

    fun searchMovie(text: String){
        if (text.trim().isNotBlank()){
            var filteredMovies: MutableList<Movie> = mutableListOf()
            if (text.trim().isNotBlank()){
                movies.value?.forEach {
                    if (it.title.lowercase().contains(text.lowercase())){
                        filteredMovies.add(it)
                    }
                }
            }
            filteredMovieList.value = filteredMovies
        }else{
            filteredMovieList.value = movies.value
        }
    }
}

class MovieListViewModelFactory(private val repo: MovieRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepo::class.java).newInstance(repo)
    }
}