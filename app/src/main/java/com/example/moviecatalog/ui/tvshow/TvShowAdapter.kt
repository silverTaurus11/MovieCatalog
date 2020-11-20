package com.example.moviecatalog.ui.tvshow

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
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import com.example.moviecatalog.ui.detail.DetailTvShowActivity
import com.example.moviecatalog.utils.MovieCatalogueHelper
import kotlinx.android.synthetic.main.item_movie.view.*

class TvShowAdapter internal constructor() : PagedListAdapter<TvShowEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShowItem = getItem(position) as TvShowEntity
        holder.bin(tvShowItem)
    }

    class TvShowViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bin(tvShowItem: TvShowEntity){
            with(itemView){
                title_text_view.text = tvShowItem.name
                description_text_view.text = tvShowItem.description
                rating_bar.rating = MovieCatalogueHelper.reCalculateRating(tvShowItem.rating)
                Glide.with(itemView.context)
                    .load(tvShowItem.fullPosterUrl)
                    .into(movie_image_view)
                setOnClickListener { context.startActivity(Intent(context, DetailTvShowActivity::class.java).apply {
                    putExtra(DetailTvShowActivity.ID_KEY, tvShowItem.id)
                }) }
            }
        }
    }
}