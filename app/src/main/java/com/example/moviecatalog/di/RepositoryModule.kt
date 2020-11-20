package com.example.moviecatalog.di

import com.example.moviecatalog.data.repository.MoviesRepository
import com.example.moviecatalog.data.repository.TvShowsRepository
import com.example.moviecatalog.domain.repository.IMovieRepository
import com.example.moviecatalog.domain.repository.ITvShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTvShowRepository(tvShowsRepository: TvShowsRepository): ITvShowRepository

    @Binds
    abstract fun provideMovieRepository(moviesRepository: MoviesRepository): IMovieRepository

}