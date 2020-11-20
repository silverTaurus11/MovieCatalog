package com.example.moviecatalog.ui.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.domain.usecase.TvShowUseCase
import com.example.moviecatalog.ui.RefreshableLiveData

class TvShowViewModel @ViewModelInject constructor(private val tvShowUseCase: TvShowUseCase) : ViewModel() {

    val tvShows = RefreshableLiveData {
        tvShowUseCase.getTvShows()
    }

    fun orderByRating() = RefreshableLiveData {
        tvShowUseCase.getTvShowOrderByRating()
    }

    fun refresh(){
        tvShowUseCase.refreshTvShows()
    }

}