package com.patricioglenn.alkemymoviesapp.presentation

import androidx.lifecycle.*
import com.patricioglenn.alkemymoviesapp.domain.MovieRepo
import kotlinx.coroutines.Dispatchers


const val NO_NETWORK = "Unable to resolve host \"api.themoviedb.org\": No address associated with hostname"

class MovieListViewModel (private val repo: MovieRepo): ViewModel(){

    var requestState = MutableLiveData<String>()
    var errorMessage = MutableLiveData<String>()

    fun fetchPopularMovies() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        requestState.value = "loading"
        try {
            emit(repo.getPopularMovies())
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

class MovieListViewModelFactory(private val repo: MovieRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepo::class.java).newInstance(repo)
    }
}