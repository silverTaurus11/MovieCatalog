package com.example.moviecatalog.di

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.moviecatalog.data.source.local.room.MoviesCatalogueDao
import com.example.moviecatalog.data.source.local.room.MoviesCatalogueDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviesCatalogueDatabase = databaseBuilder(
        context,
        MoviesCatalogueDatabase::class.java, "MovieCatalogue.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieCatalogue(database: MoviesCatalogueDatabase): MoviesCatalogueDao =
        database.moviesCatalogueDao()
}