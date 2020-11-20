package com.example.moviecatalog.data.source.remote.response

import com.example.moviecatalog.utils.ServiceConst
import com.google.gson.annotations.SerializedName

data class TvShowEntity(
    @field:SerializedName("id")  val id: Int?= 0,
    @field:SerializedName("name") val name: String?= null,
    @field:SerializedName("overview") val description: String?= null,
    @field:SerializedName("vote_average") val rating: Float?= 0F,
    @field:SerializedName("poster_path") val posterPath: String?= null,
    @field:SerializedName("first_air_date") val firstAirDate: String?= null
){
    val fullPosterUrl: String get() =
        if(posterPath.isNullOrEmpty()) ServiceConst.EMPTY_IMAGE_URL else ServiceConst.IMAGE_BASE_URL + posterPath

}