package com.example.moviecatalog.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.repository.TvShowsRepository
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import com.example.moviecatalog.domain.repository.ITvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowInteractor @Inject constructor(private val tvShowsRepository: ITvShowRepository): TvShowUseCase {

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> =
        tvShowsRepository.getTvShow()

    override fun getTvShowDetail(id: Int): Flow<Resource<RoomTvShowEntity>> =
        tvShowsRepository.getTvShowDetail(id)

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> =
        tvShowsRepository.getFavoriteTvShow()

    override fun setFavoriteTvShow(tvShowEntity: RoomTvShowEntity, isFavorite: Boolean) {
        tvShowsRepository.setFavoriteTvShow(tvShowEntity, isFavorite)
    }

    override fun getTvShowOrderByRating(): LiveData<Resource<PagedList<TvShowEntity>>> =
        tvShowsRepository.getTvShowOrderByRating()

    override fun refreshTvShows() {
        tvShowsRepository.refreshTvShows()
    }

}