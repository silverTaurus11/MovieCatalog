package com.example.moviecatalog.ui.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.ui.detail.DetailMovieActivity
import com.example.moviecatalog.utils.MovieCatalogueHelper
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter internal constructor() : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK)  {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position) as MovieEntity
        holder.bin(movieItem)
    }

    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bin(movieItem: MovieEntity){
            with(itemView){
                title_text_view.text = movieItem.name
                description_text_view.text = movieItem.description
                rating_bar.rating = MovieCatalogueHelper.reCalculateRating(movieItem.rating)
                Glide.with(itemView.context)
                    .load(movieItem.fullPosterUrl)
                    .into(movie_image_view)
                setOnClickListener { context.startActivity(Intent(context, DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.ID_KEY, movieItem.id)
                }) }
            }
        }
    }

}