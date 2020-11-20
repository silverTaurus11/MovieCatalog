package com.example.moviecatalog.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>>
    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>
    fun setFavoriteMovie(movieEntity: RoomMovieEntity, isFavorite: Boolean)
    fun getMoviesOrderByRating(): LiveData<Resource<PagedList<MovieEntity>>>
    fun refreshMovies()
}