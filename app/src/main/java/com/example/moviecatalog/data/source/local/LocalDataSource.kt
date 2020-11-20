package com.example.moviecatalog.data.source.local

import androidx.paging.DataSource
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.data.source.local.room.MoviesCatalogueDao
import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val moviesCatalogueDao: MoviesCatalogueDao) {

    fun getTvShows(): DataSource.Factory<Int, TvShowEntity> = moviesCatalogueDao.getAllTvShows().map {
        tvShow -> TvShowEntity(tvShow.id, tvShow.name, tvShow.description, tvShow.rating, tvShow.posterPath)
    }

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> = moviesCatalogueDao.getFavoriteTvShows().map {
            tvShow -> TvShowEntity(tvShow.id, tvShow.name, tvShow.description, tvShow.rating, tvShow.posterPath)
    }

    fun insertTvShows(tvShowEntityList: List<TvShowEntity>){
        moviesCatalogueDao.insertTvShow(tvShowEntityList.map{
            RoomTvShowEntity(it.id!!, it.name!!, it.description!!, it.rating!!,
                posterPath = it.posterPath?:"", fullPosterPath = it.fullPosterUrl, firstAirDate = it.firstAirDate)
        })
    }

    fun getMovies(): DataSource.Factory<Int, MovieEntity>  = moviesCatalogueDao.getAllMovies().map { movie ->
        MovieEntity(movie.id, movie.name, movie.description, movie.rating, movie.posterPath)
    }

    fun insertMovies(movies: List<MovieEntity>){
        moviesCatalogueDao.insertMovie(movies.map {
            RoomMovieEntity(it.id!!, it.name!!, it.description!!, it.rating!!,
                posterPath = it.posterPath?:"", fullPosterPath = it.fullPosterUrl, releaseDate = it.releaseDate)
        })
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = moviesCatalogueDao.getFavoriteMovies().map {
        movie -> MovieEntity(movie.id, movie.name, movie.description, movie.rating, movie.posterPath)
    }

    fun getSelectedTvShowDetail(id: Int): Flow<RoomTvShowEntity> = moviesCatalogueDao.getDetailTvShow(id)

    suspend fun updateSelectedTvShowDetail(raw: BaseDetailResponse.TvShowDetailResponse){
        moviesCatalogueDao.updateDetailTvShow(raw.id,
            raw.genre?.map{it.name}?.joinToString(", "),
            raw.language,
            raw.firstAirDate,
            raw.lastAirDate,
            raw.episodeTotal)
    }

    fun getSelectedMovieDetail(id: Int): Flow<RoomMovieEntity> = moviesCatalogueDao.getDetailMovie(id)

    suspend fun updateSelectedMovieDetail(raw: BaseDetailResponse.MovieDetailResponse){
        moviesCatalogueDao.updateDetailMovie(raw.id,
            raw.genre?.map{it.name}?.joinToString(", "),
            raw.language,
            raw.releaseDate,
            raw.duration)
    }

    fun updateFavoriteMovie(movies: RoomMovieEntity, isFavorite: Boolean){
        movies.isFavorite = isFavorite
        moviesCatalogueDao.updateFavoriteMovie(movies)
    }

    fun updateFavoriteTvShow(tvShow: RoomTvShowEntity, isFavorite: Boolean){
        tvShow.isFavorite = isFavorite
        moviesCatalogueDao.updateFavoriteTvShow(tvShow)
    }

    fun getTvShowsOrderByRating(): DataSource.Factory<Int, TvShowEntity> = moviesCatalogueDao.getTvShowOrderByRating()
        .map { tvShow -> TvShowEntity(tvShow.id, tvShow.name, tvShow.description, tvShow.rating, tvShow.posterPath) }

    fun getMoviesOrderByRating(): DataSource.Factory<Int, MovieEntity> = moviesCatalogueDao.getMovieOrderByRating()
        .map { movie -> MovieEntity(movie.id, movie.name, movie.description, movie.rating, movie.posterPath) }

}