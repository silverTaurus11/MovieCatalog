package com.example.moviecatalog.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>>
    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>
    fun setFavoriteMovie(movie: RoomMovieEntity, isFavorite: Boolean)
    fun getMovieOrderByRating(): LiveData<Resource<PagedList<MovieEntity>>>
    fun refreshMovies()
}