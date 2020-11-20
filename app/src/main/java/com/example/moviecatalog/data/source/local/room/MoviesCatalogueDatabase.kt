package com.example.moviecatalog.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity

@Database(entities = [RoomMovieEntity::class, RoomTvShowEntity::class], version = 1, exportSchema = false)
abstract class MoviesCatalogueDatabase : RoomDatabase() {
    abstract fun moviesCatalogueDao(): MoviesCatalogueDao
}