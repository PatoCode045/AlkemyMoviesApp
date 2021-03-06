package com.patricioglenn.alkemymoviesapp.ui.movie_details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.patricioglenn.alkemymoviesapp.R
import com.patricioglenn.alkemymoviesapp.database.MovieDatabase
import com.patricioglenn.alkemymoviesapp.database.getDatabase
import com.patricioglenn.alkemymoviesapp.databinding.FragmentMovieBinding
import com.patricioglenn.alkemymoviesapp.repository.MovieRepoImpl
import com.patricioglenn.alkemymoviesapp.network.MovieApi
import com.patricioglenn.alkemymoviesapp.viewmodels.MovieViewModel
import com.patricioglenn.alkemymoviesapp.viewmodels.MovieViewModelFactory
import com.patricioglenn.alkemymoviesapp.util.MyUtils.formatDate
import com.patricioglenn.alkemymoviesapp.util.MyUtils.formatGenres
import com.patricioglenn.alkemymoviesapp.util.MyUtils.formatLanguage

class MovieFragment : Fragment(R.layout.fragment_movie) {

    lateinit var binding: FragmentMovieBinding
    private val viewmodel by activityViewModels<MovieViewModel> {
        MovieViewModelFactory(MovieRepoImpl(MovieApi, getDatabase(requireContext())))
    }
    private val args by navArgs<MovieFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        viewmodel.movieId = args.id
        viewmodel.getSelectedMovie()

        viewmodel.errorMessage.observe(viewLifecycleOwner, Observer{
            binding.tvErrorMovieDetails.text = it
        })

        viewmodel.requestState.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                "loading" -> {
                    binding.clMovieDetails.isVisible = false
                    binding.pbLoadingMovieDetails.isVisible = true
                    binding.tvErrorMovieDetails.isVisible = false
                }
                "error" ->{
                    binding.clMovieDetails.isVisible = false
                    binding.pbLoadingMovieDetails.isVisible = false
                    binding.tvErrorMovieDetails.isVisible = true
                }
            }
        })

        viewmodel.movie.observe(viewLifecycleOwner, { movie->
            binding.clMovieDetails.isVisible = true
            binding.pbLoadingMovieDetails.isVisible = false
            binding.tvErrorMovieDetails.isVisible = false

            Glide.with(requireContext())
                .load("https://www.themoviedb.org/t/p/w500/${movie.backdrop_path}")
                .into(binding.ivBackdrop)
            Glide.with(requireContext())
                .load("https://www.themoviedb.org/t/p/w200/${movie.poster_path}")
                .into(binding.ivPoster)
            binding.tvTitle.text = movie.title
            //binding.tvGenres.text = "Generos: ${formatGenres(movie.genres)}."
            binding.tvLanguage.text = "Idioma: ${formatLanguage(movie.original_language)}"
            binding.tvReleaseDate.text = "Lanzada: ${formatDate(movie.release_date)}"
            binding.tvRating.text = movie.vote_average.toString()
            binding.tvOverview.text = "Sinopsis: ${movie.overview}"
        })

        binding.ivRate.setOnClickListener {
            val dialog = RateMovieDialogFragment()
            dialog.show(parentFragmentManager, "tag")

        }

        viewmodel.showRateToast.observe(viewLifecycleOwner, {
            if (it){
                Toast.makeText(requireContext(), "El puntaje de guardo correctamente.", Toast.LENGTH_LONG).show()
                viewmodel.showRateToast.value = false
            }
        })

    }

}