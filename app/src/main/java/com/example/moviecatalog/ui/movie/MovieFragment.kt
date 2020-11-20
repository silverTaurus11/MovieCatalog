package com.example.moviecatalog.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalog.R
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.ui.home.SortMoviesListener
import com.example.moviecatalog.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*

@AndroidEntryPoint
class MovieFragment: Fragment(), SortMoviesListener {
    companion object{
        const val TAG = "MovieFragment"
    }

    private val movieViewModel: MovieViewModel by viewModels()
    private val movieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            initMoviesListObserve()

            rv_movies.layoutManager = LinearLayoutManager(context)
            rv_movies.setHasFixedSize(true)
            rv_movies.adapter = movieAdapter
            rv_movies.contentDescription = TAG
            rv_movies.visibility = View.GONE

            swipe_refresh.setOnRefreshListener {
                swipe_refresh.isRefreshing = false
                movieViewModel.refresh()
            }
        }
    }

    private fun initMoviesListObserve(){
        EspressoIdlingResource.increment()
        movieViewModel.movies.observe(viewLifecycleOwner, Observer { movieData ->
            setUpObserver(movieData)
        })
    }

    private fun setUpObserver(movieData: Resource<PagedList<MovieEntity>>){
        when(movieData){
            is Resource.Loading -> {
                if(isDataListVisible()){
                    progress_bar.visibility = View.GONE
                } else{
                    progress_bar.visibility = View.VISIBLE
                }
                view_empty.visibility = View.GONE
                view_error.visibility = View.GONE
            }
            is Resource.Success -> {
                showDataList(movieData.data)
            }
            is Resource.Error -> {
                if(isDataListVisible()){
                    Toast.makeText(activity, R.string.something_wrong, Toast.LENGTH_SHORT).show()
                } else{
                    view_empty.visibility = View.GONE
                    rv_movies.visibility = View.GONE
                    progress_bar.visibility = View.GONE
                    view_error.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showDataList(data: PagedList<MovieEntity>?){
        if(data.isNullOrEmpty()){
            view_empty.visibility = View.VISIBLE
        } else{
            view_empty.visibility = View.GONE
            movieAdapter.submitList(data)
        }
        rv_movies.visibility = View.VISIBLE
        view_error.visibility = View.GONE
        progress_bar.visibility = View.GONE

        //Memberitahukan bahwa tugas sudah selesai dijalankan
        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }

    private fun isDataListVisible(): Boolean = rv_movies.visibility == View.VISIBLE

    override fun doSortMovieByRating() {
        if(isDataListVisible()){
            EspressoIdlingResource.increment()
            movieViewModel.orderByRating().observe(viewLifecycleOwner, Observer { movieData ->
                setUpObserver(movieData)
            })
        }
    }
}