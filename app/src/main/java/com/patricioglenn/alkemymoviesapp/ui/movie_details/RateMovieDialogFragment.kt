package com.patricioglenn.alkemymoviesapp.ui.movie_details

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patricioglenn.alkemymoviesapp.database.getDatabase
import com.patricioglenn.alkemymoviesapp.databinding.FragmentRateMovieDialogBinding
import com.patricioglenn.alkemymoviesapp.repository.MovieRepoImpl
import com.patricioglenn.alkemymoviesapp.network.MovieApi
import com.patricioglenn.alkemymoviesapp.viewmodels.MovieViewModel
import com.patricioglenn.alkemymoviesapp.viewmodels.MovieViewModelFactory

class RateMovieDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRateMovieDialogBinding
    private val viewmodel by activityViewModels<MovieViewModel> {
        MovieViewModelFactory(MovieRepoImpl(MovieApi, getDatabase(requireContext())))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRateMovieDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        viewmodel.rateMovie( binding.ratingBar.rating)
        super.onDismiss(dialog)
    }
}