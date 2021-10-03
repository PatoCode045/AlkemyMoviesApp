package com.patricioglenn.alkemymoviesapp.presentation

import androidx.lifecycle.*
import com.patricioglenn.alkemymoviesapp.domain.MovieRepo
import kotlinx.coroutines.Dispatchers

class MovieViewModel (private val repo: MovieRepo): ViewModel(){

    var requestState = MutableLiveData<String>()
    var errorMessage = MutableLiveData<String>()

    fun getSelectedMovie(id: Int) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        try {
            requestState.value = "loading"
            emit(repo.getSelectedMovie(id))
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

class MovieViewModelFactory(private val repo: MovieRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepo::class.java).newInstance(repo)
    }
}