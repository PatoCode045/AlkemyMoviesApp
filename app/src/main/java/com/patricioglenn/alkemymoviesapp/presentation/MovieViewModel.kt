package com.patricioglenn.alkemymoviesapp.presentation

import android.util.Log
import androidx.lifecycle.*
import com.patricioglenn.alkemymoviesapp.data.Rate
import com.patricioglenn.alkemymoviesapp.domain.MovieRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel (private val repo: MovieRepo): ViewModel(){

    var requestState = MutableLiveData<String>()
    var errorMessage = MutableLiveData<String>()
    var showRateToast = MutableLiveData(false)

    var movieId = 0
    var session = ""

    init {
        getSession()
    }

    private fun getSession() {
        viewModelScope.launch {
            val newSession = repo.getGuessSession()
            session = newSession.guest_session_id
        }
    }

    fun getSelectedMovie(id: Int) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        movieId = id
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

    fun rateMovie(rateValue: Float){
        if (rateValue>0){
            viewModelScope.launch {
                try {
                    repo.rateMovie(movieId, Rate(rateValue), session)
                    showRateToast.value = true
                }catch (e: Exception){
                    Log.d("rate", e.toString())
                }
            }
        }
    }

}

class MovieViewModelFactory(private val repo: MovieRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepo::class.java).newInstance(repo)
    }
}