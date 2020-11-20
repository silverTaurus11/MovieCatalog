package com.example.moviecatalog.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.domain.usecase.MoviesUseCase

class DetailMovieViewModel @ViewModelInject constructor(private val moviesUseCase: MoviesUseCase): ViewModel() {
    private var movieId: Int = 0
    private lateinit var movieDetail: LiveData<Resource<RoomMovieEntity>>

    fun setSelectedMovie(movieId: Int) {
        this.movieId = movieId
    }

    fun getMovieDetail(): LiveData<Resource<RoomMovieEntity>> {
        movieDetail = moviesUseCase.getMovieDetail(movieId).asLiveData()
        return movieDetail
    }

    fun setToFavorite(){
        val movieResource = movieDetail.value
        if(movieResource is Resource.Success){
            movieResource.data?.apply {
                moviesUseCase.setFavoriteMovie(this, !this.isFavorite)
            }
        }
    }
}