package com.example.moviecatalog.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
class RoomTvShowEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int?= 0,

    @ColumnInfo(name = "name")
    var name: String?= null,

    @ColumnInfo(name = "overview")
    var description: String?= null,

    @ColumnInfo(name = "vote_average")
    var rating: Float?= 0F,

    @ColumnInfo(name = "genres")
    var genre: String?= null,

    @ColumnInfo(name = "original_language")
    var language: String?= null,

    @ColumnInfo(name = "first_air_date")
    var firstAirDate: String?= null,

    @ColumnInfo(name = "last_air_date")
    var lastAirDate: String?= null,

    @ColumnInfo(name = "number_of_episodes")
    var episodeTotal: Int?= null,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?= null,

    @ColumnInfo(name = "full_poster_url")
    var fullPosterPath: String?= null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)