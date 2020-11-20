package com.example.moviecatalog.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface ITvShowRepository {
    fun getTvShow(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getTvShowDetail(id: Int): Flow<Resource<RoomTvShowEntity>>
    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>>
    fun setFavoriteTvShow(roomTvShowEntity: RoomTvShowEntity, isFavorite: Boolean)
    fun getTvShowOrderByRating(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun refreshTvShows()
}