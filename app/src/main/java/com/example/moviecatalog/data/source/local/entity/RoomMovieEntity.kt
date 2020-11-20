package com.example.moviecatalog.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class RoomMovieEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int?= 0,

    @ColumnInfo(name = "title")
    var name: String?= null,

    @ColumnInfo(name = "overview")
    var description: String?= null,

    @ColumnInfo(name = "vote_average")
    var rating: Float?= 0F,

    @ColumnInfo(name = "genres")
    var genre: String?= null,

    @ColumnInfo(name = "original_language")
    var language: String?= null,

    @ColumnInfo(name = "release_date")
    var releaseDate: String?= null,

    @ColumnInfo(name = "runtime")
    var duration: Int?= null,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?= null,

    @ColumnInfo(name = "full_poster_url")
    var fullPosterPath: String?= null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)