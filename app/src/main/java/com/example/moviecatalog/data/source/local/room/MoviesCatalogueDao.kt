package com.example.moviecatalog.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesCatalogueDao {
    @Query("SELECT * FROM movies ORDER BY date(release_date) DESC")
    fun getAllMovies(): DataSource.Factory<Int, RoomMovieEntity>

    @Query("SELECT * FROM movies where isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, RoomMovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: List<RoomMovieEntity>)

    @Update
    fun updateFavoriteMovie(movie: RoomMovieEntity)

    @Query("SELECT * FROM tv_shows ORDER BY date(first_air_date) DESC")
    fun getAllTvShows(): DataSource.Factory<Int, RoomTvShowEntity>

    @Query("SELECT * FROM tv_shows where isFavorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, RoomTvShowEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShow(movie: List<RoomTvShowEntity>)

    @Update
    fun updateFavoriteTvShow(movie: RoomTvShowEntity)

    @Query("SELECT * FROM movies where id= :id")
    fun getDetailMovie(id: Int): Flow<RoomMovieEntity>

    @Query("SELECT * FROM tv_shows where id= :id")
    fun getDetailTvShow(id: Int): Flow<RoomTvShowEntity>

    @Query("UPDATE movies SET genres = :genre, original_language = :language, release_date = :releaseDate, runtime = :duration WHERE id = :id")
    suspend fun updateDetailMovie(id: Int?, genre: String?, language: String?, releaseDate: String?, duration: Int?)

    @Query("UPDATE tv_shows SET genres = :genre, original_language = :language, first_air_date = :firstAirDate, last_air_date = :lastAirDate, number_of_episodes = :episodeTotal WHERE id = :id")
    suspend fun updateDetailTvShow(id: Int?, genre: String?, language: String?, firstAirDate: String?,
                           lastAirDate: String?, episodeTotal: Int? )

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    fun getMovieOrderByRating():  DataSource.Factory<Int, RoomMovieEntity>

    @Query("SELECT * FROM tv_shows ORDER BY vote_average DESC")
    fun getTvShowOrderByRating(): DataSource.Factory<Int, RoomTvShowEntity>

}