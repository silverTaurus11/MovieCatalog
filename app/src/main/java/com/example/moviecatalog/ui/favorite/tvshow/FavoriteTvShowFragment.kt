package com.example.moviecatalog.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.tvshow.TvShowAdapter
import com.example.moviecatalog.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*

@AndroidEntryPoint
class FavoriteTvShowFragment: Fragment() {
    companion object {
        const val TAG = "FavoriteTvShowFragment"
    }

    private val favoriteTvShowViewModel: FavoriteTvShowViewModel by viewModels()
    private val tvShowAdapter by lazy { TvShowAdapter() }

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
            rv_movies.adapter = tvShowAdapter
            rv_movies.contentDescription = TAG
            swipe_refresh.isEnabled = false
            view_empty.visibility = View.GONE
            rv_movies.visibility = View.GONE
            view_error.visibility = View.GONE
            initTvShowListObserve()
        }
    }

    private fun initTvShowListObserve() {
        EspressoIdlingResource.increment()
        progress_bar.visibility = View.VISIBLE
        favoriteTvShowViewModel.favoriteTvShow.observe(viewLifecycleOwner, Observer { tvShowData ->
            if(tvShowData.isNullOrEmpty()){
                view_empty.visibility = View.VISIBLE
            } else{
                view_empty.visibility = View.GONE
                tvShowAdapter.submitList(tvShowData)
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