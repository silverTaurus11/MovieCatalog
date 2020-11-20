package com.example.moviecatalog.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesInteractor @Inject constructor(private val moviesRepository: IMovieRepository): MoviesUseCase {

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        moviesRepository.getMovies()

    override fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>> =
        moviesRepository.getMovieDetail(id)

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> =
        moviesRepository.getFavoriteMovies()

    override fun setFavoriteMovie(movie: RoomMovieEntity, isFavorite: Boolean) {
        moviesRepository.setFavoriteMovie(movie, isFavorite)
    }

    override fun getMovieOrderByRating(): LiveData<Resource<PagedList<MovieEntity>>> =
        moviesRepository.getMoviesOrderByRating()

    override fun refreshMovies() {
        moviesRepository.refreshMovies()
    }

}