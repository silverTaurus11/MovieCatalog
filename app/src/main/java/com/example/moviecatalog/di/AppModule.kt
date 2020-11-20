package com.example.moviecatalog.di

import com.example.moviecatalog.domain.usecase.MoviesInteractor
import com.example.moviecatalog.domain.usecase.MoviesUseCase
import com.example.moviecatalog.domain.usecase.TvShowInteractor
import com.example.moviecatalog.domain.usecase.TvShowUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideTvShowUseCase(tvShowInteractor: TvShowInteractor): TvShowUseCase

    @Binds
    abstract fun provideMoviesUseCase(moviesInteractor: MoviesInteractor): MoviesUseCase

}