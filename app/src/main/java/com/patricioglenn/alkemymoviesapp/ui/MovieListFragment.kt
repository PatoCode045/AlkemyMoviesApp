package com.patricioglenn.alkemymoviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
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

        viewmodel.errorMessage.observe(viewLifecycleOwner, Observer{
            binding.tvErrorMovieList.text = it
        })

        viewmodel.requestState.observe(viewLifecycleOwner, Observer { state ->
            Log.d("state", state)
            when(state){
                "loading" -> {
                    binding.rvMovieList.isVisible = false
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

        viewmodel.fetchPopularMovies().observe(viewLifecycleOwner, Observer { movies->
            Log.d("state", "se usa")
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
    }

    override fun onItemClick(item: Movie) {
        Log.d("title", item.title)
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieFragment(
            item.id
        )
        findNavController().navigate(action)
    }
}