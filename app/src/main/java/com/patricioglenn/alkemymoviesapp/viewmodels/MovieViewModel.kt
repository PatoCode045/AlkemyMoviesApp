package com.patricioglenn.alkemymoviesapp.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.patricioglenn.alkemymoviesapp.database.MovieDatabase
import com.patricioglenn.alkemymoviesapp.database.MovieEntity
import com.patricioglenn.alkemymoviesapp.domain.Movie
import com.patricioglenn.alkemymoviesapp.domain.Rate
import com.patricioglenn.alkemymoviesapp.repository.MovieRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel (private val repo: MovieRepo): ViewModel(){

    var requestState = MutableLiveData<String>()
    var errorMessage = MutableLiveData<String>()
    var showRateToast = MutableLiveData(false)

    var movieId = 0L
    var session = ""
    var movie: MutableLiveData<MovieEntity> = MutableLiveData()

    init {
        getSession()
    }

    private fun getSession() {
        viewModelScope.launch {
            val newSession = repo.getGuessSession()
            session = newSession.guest_session_id
        }
    }

    fun getSelectedMovie(){
        viewModelScope.launch {
            try {
                requestState.value = "loading"
                movie.value = repo.getSelectedMovie(movieId).value
            }catch (e: Exception){
                requestState.value = "error"
                Log.d("error", e.toString())

                if(e.message == NO_NETWORK){
                    errorMessage.value = "Ha ocurrido un error inesperado, verifique su conexion a internet e intentelo nuevamente."
                }else{
                    errorMessage.value = "Ha ocurrido un error inesperado, por favor, pongase en contacto con el creador de esta aplicacion."
                }
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