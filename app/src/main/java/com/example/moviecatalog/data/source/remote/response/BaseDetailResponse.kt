package com.example.moviecatalog.data.source.remote.response

import com.example.moviecatalog.utils.ServiceConst
import com.google.gson.annotations.SerializedName

sealed class BaseDetailResponse(
    @field:SerializedName("status_message") val statusMessage: String?= null,
    @field:SerializedName("success") val status: Boolean?= false,
    @field:SerializedName("status_code") val statusCode: Int?= 0) {

    class MovieDetailResponse(
        @field:SerializedName("id") val id: Int?= 0,
        @field:SerializedName("title") val name: String?= null,
        @field:SerializedName("overview") val description: String?= null,
        @field:SerializedName("vote_average")val rating: Float?= 0F,
        @field:SerializedName("genres") val genre: List<GenreEntity> ?= null,
        @field:SerializedName("original_language") val language: String ?= null,
        @field:SerializedName("release_date") val releaseDate: String ?= null,
        @field:SerializedName("runtime") val duration: Int ?= 0,
        @field:SerializedName("poster_path") val posterPath: String?= null): BaseDetailResponse(){
            val fullPosterUrl: String get() = ServiceConst.IMAGE_BASE_URL + posterPath
            var isFavorite: Boolean = false
    }

    class TvShowDetailResponse(
        @field:SerializedName("id") val id: Int?= 0,
        @field:SerializedName("name") val name: String?= null,
        @field:SerializedName("overview") val description: String?= null,
        @field:SerializedName("vote_average")val rating: Float?= 0F,
        @field:SerializedName("genres") val genre: List<GenreEntity> ?= null,
        @field:SerializedName("original_language") val language: String ?= null,
        @field:SerializedName("first_air_date") val firstAirDate: String ?= null,
        @field:SerializedName("last_air_date") val lastAirDate: String ?= null,
        @field:SerializedName("number_of_episodes") val episodeTotal: Int ?= 0,
        @field:SerializedName("poster_path") val posterPath: String?= null): BaseDetailResponse(){
            val fullPosterUrl: String get() = ServiceConst.IMAGE_BASE_URL + posterPath
            val isFavorite: Boolean = false
    }
}