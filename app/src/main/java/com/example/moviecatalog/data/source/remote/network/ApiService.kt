package com.example.moviecatalog.data.source.remote.network

import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

@JvmSuppressWildcards
interface ApiService {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(@QueryMap queryMap: Map<String, Any>): BaseResponse.MovieResponse

    @GET("discover/tv")
    suspend fun getDiscoverTvShows(@QueryMap queryMap: Map<String, Any>): BaseResponse.TvShowResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieId: Int,
                       @QueryMap queryMap: Map<String, Any>): BaseDetailResponse.MovieDetailResponse

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetail(@Path("tv_id")tvId: Int,
                       @QueryMap queryMap: Map<String, Any>): BaseDetailResponse.TvShowDetailResponse
}