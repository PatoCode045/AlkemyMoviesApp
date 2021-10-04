package com.patricioglenn.alkemymoviesapp.ui.movie_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patricioglenn.alkemymoviesapp.domain.Movie
import com.patricioglenn.alkemymoviesapp.databinding.ViewholderMovieListItemBinding
import com.patricioglenn.alkemymoviesapp.databinding.ViewholderMovieListLoadMoreItemsBinding

class MovieListAdapter (private val movieList: List<Movie>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Movie?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val binding = ViewholderMovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = MovieViewHolder(binding, parent.context)
            binding.root.setOnClickListener {
                val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
                itemClickListener.onItemClick(movieList[position])
            }
            return holder
        }else{
            val binding = ViewholderMovieListLoadMoreItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = LoadMoreMoviesViewHolder(binding)
            binding.bLoadMoreMovies.setOnClickListener {
                itemClickListener.onItemClick(null)
            }
            return holder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder){
            holder.bind(movieList[position])
        }else if (holder is LoadMoreMoviesViewHolder){
            holder.binding.bLoadMoreMovies
        }
    }

    override fun getItemCount(): Int = movieList.size + 1

    override fun getItemViewType(position: Int): Int = if(position < movieList.size) { 1 } else{ 2 }


    class MovieViewHolder(val binding: ViewholderMovieListItemBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.tvMovieName.text = item.title
            binding.tvMovieRating.text = "Puntaje: ${item.vote_average}"
            Glide.with(context).load("https://www.themoviedb.org/t/p/w200/${item.poster_path}")
                .centerCrop().into(binding.ivMoviePoster)
        }

    }
    class LoadMoreMoviesViewHolder(val binding: ViewholderMovieListLoadMoreItemsBinding): RecyclerView.ViewHolder(binding.root){

    }
}