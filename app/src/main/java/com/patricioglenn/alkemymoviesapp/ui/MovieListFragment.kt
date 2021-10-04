package com.patricioglenn.alkemymoviesapp.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patricioglenn.alkemymoviesapp.R
import com.patricioglenn.alkemymoviesapp.data.Movie
import com.patricioglenn.alkemymoviesapp.databinding.FragmentMovieListBinding
import com.patricioglenn.alkemymoviesapp.domain.MovieRepoImpl
import com.patricioglenn.alkemymoviesapp.network.MovieApi
import com.patricioglenn.alkemymoviesapp.presentation.MovieListViewModel
import com.patricioglenn.alkemymoviesapp.presentation.MovieListViewModelFactory


class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListAdapter.OnItemClickListener{

    private lateinit var binding: FragmentMovieListBinding
    private val viewmodel by activityViewModels<MovieListViewModel> {
        MovieListViewModelFactory(MovieRepoImpl(MovieApi))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view)

        viewmodel.errorMessage.observe(viewLifecycleOwner, {
            binding.tvErrorMovieList.text = it
        })

        viewmodel.requestState.observe(viewLifecycleOwner, { state ->
            Log.d("state", state)
            when(state){
                "loading" -> {
                    binding.rvMovieList.isVisible = true
                    binding.pbLoadingMovieList.isVisible = true
                    binding.tvErrorMovieList.isVisible = false
                }
                "error" ->{
                    binding.rvMovieList.isVisible = false
                    binding.pbLoadingMovieList.isVisible = false
                    binding.tvErrorMovieList.isVisible = true

                }
            }
        })

        viewmodel.filteredMovieList.observe(viewLifecycleOwner, { movies->
            if (movies.isEmpty()){
                binding.rvMovieList.isVisible = false
                binding.pbLoadingMovieList.isVisible = false
                binding.tvErrorMovieList.isVisible = true
                binding.tvErrorMovieList.text = "No se encontraron peliculas."
            }else{
                binding.rvMovieList.adapter = MovieListAdapter(movies, this)
                binding.rvMovieList.isVisible = true
                binding.pbLoadingMovieList.isVisible = false
                binding.tvErrorMovieList.isVisible = false
            }
        })

        binding.bSearchMovies.setOnClickListener {
            viewmodel.searchMovie(binding.etSearchMovie.text.toString())
            if (view != it) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

    }

    override fun onItemClick(item: Movie?) {

        if (item != null){
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieFragment(
                item.id
            )
            findNavController().navigate(action)
        }else {
            viewmodel.getPopularMovies()
        }
    }
}