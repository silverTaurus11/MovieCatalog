package com.example.moviecatalog.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.movie.MovieAdapter
import com.example.moviecatalog.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*

@AndroidEntryPoint
class FavoriteMovieFragment: Fragment() {
    companion object {
        const val TAG = "FavoriteMovieFragment"
    }

    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModels()
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            rv_movies.layoutManager = LinearLayoutManager(context)
            rv_movies.setHasFixedSize(true)
            rv_movies.adapter = movieAdapter
            rv_movies.contentDescription = TAG
            swipe_refresh.isEnabled = false
            view_empty.visibility = View.GONE
            rv_movies.visibility = View.GONE
            view_error.visibility = View.GONE
            initMoviesListObserve()
        }
    }

    private fun initMoviesListObserve() {
        EspressoIdlingResource.increment()
        progress_bar.visibility = View.VISIBLE
        favoriteMovieViewModel.favoriteMovies.observe(viewLifecycleOwner, Observer { movieData ->
            if(movieData.isNullOrEmpty()){
                view_empty.visibility = View.VISIBLE
            } else{
                view_empty.visibility = View.GONE
                movieAdapter.submitList(movieData)
            }
            rv_movies.visibility = View.VISIBLE
            view_error.visibility = View.GONE
            progress_bar.visibility = View.GONE

            //Memberitahukan bahwa tugas sudah selesai dijalankan
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })
    }
}