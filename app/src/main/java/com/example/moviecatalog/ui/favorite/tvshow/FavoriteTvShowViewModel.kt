package com.example.moviecatalog.ui.favorite.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.domain.usecase.TvShowUseCase

class FavoriteTvShowViewModel @ViewModelInject constructor(tvShowUseCase: TvShowUseCase): ViewModel(){
    val favoriteTvShow = tvShowUseCase.getFavoriteTvShows()
}