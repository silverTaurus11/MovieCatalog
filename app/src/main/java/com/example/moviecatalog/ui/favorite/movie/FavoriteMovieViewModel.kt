package com.example.moviecatalog.ui.favorite.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.domain.usecase.MoviesUseCase

class FavoriteMovieViewModel @ViewModelInject constructor(moviesUseCase: MoviesUseCase) : ViewModel() {
    val favoriteMovies = moviesUseCase.getFavoriteMovie()
}