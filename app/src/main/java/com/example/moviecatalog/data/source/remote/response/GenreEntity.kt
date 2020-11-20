package com.example.moviecatalog.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreEntity(
    @field:SerializedName("id") val id: Int?= 0,
    @field:SerializedName("name") val name: String?= null
)