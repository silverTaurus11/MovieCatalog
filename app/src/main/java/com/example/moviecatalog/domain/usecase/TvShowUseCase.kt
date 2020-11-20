package com.example.moviecatalog.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface TvShowUseCase {
    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getTvShowDetail(id: Int): Flow<Resource<RoomTvShowEntity>>
    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>
    fun setFavoriteTvShow(tvShowEntity: RoomTvShowEntity, isFavorite: Boolean)
    fun getTvShowOrderByRating(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun refreshTvShows()
}