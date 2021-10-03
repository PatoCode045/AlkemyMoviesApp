package com.patricioglenn.alkemymoviesapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patricioglenn.alkemymoviesapp.data.Movie
import com.patricioglenn.alkemymoviesapp.databinding.ViewholderMovieListItemBinding

class MovieListAdapter (private val movieList: List<Movie>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<MovieViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ViewholderMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(binding, parent.context)
        binding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onItemClick(movieList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}

class MovieViewHolder(val binding: ViewholderMovieListItemBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
    fun bind(item: Movie) {
        binding.tvMovieName.text = item.title
        binding.tvMovieRating.text = "Puntaje: ${item.vote_average}"
        Glide.with(context).load("https://www.themoviedb.org/t/p/w200/${item.poster_path}").centerCrop().into(binding.ivMoviePoster)
    }
}