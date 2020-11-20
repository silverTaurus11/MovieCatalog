package com.example.moviecatalog.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.domain.usecase.MoviesUseCase
import com.example.moviecatalog.ui.RefreshableLiveData

class MovieViewModel @ViewModelInject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    val movies = RefreshableLiveData {
        moviesUseCase.getMovies()
    }

    fun orderByRating() = RefreshableLiveData {
            moviesUseCase.getMovieOrderByRating()
        }

    fun refresh(){
        moviesUseCase.refreshMovies()
    }
}