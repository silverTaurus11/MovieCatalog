package com.example.moviecatalog.data.source.remote.response

import com.google.gson.annotations.SerializedName

sealed class BaseResponse<T>(
    @field:SerializedName("id") val id: Int = 0,
    @field:SerializedName("page") val page: Int = 0,
    @field:SerializedName("total_results") val totalResults: Int = 0,
    @field:SerializedName("total_pages") val totalPages: Int = 0,
    @field:SerializedName("results") val results: List<T> = listOf()){
    class MovieResponse(): BaseResponse<MovieEntity>()
    class TvShowResponse(): BaseResponse<TvShowEntity>()
}