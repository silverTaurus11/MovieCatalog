package com.example.moviecatalog.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.domain.usecase.TvShowUseCase

class DetailTvShowViewModel @ViewModelInject constructor(private val tvShowUseCase: TvShowUseCase): ViewModel() {
    private var tvShowId: Int = 0
    private lateinit var tvShowDetail: LiveData<Resource<RoomTvShowEntity>>

    fun setSelectedTvShow(tvShowId: Int) {
        this.tvShowId = tvShowId
    }

    fun getTvShowDetail(): LiveData<Resource<RoomTvShowEntity>>{
        tvShowDetail =  tvShowUseCase.getTvShowDetail(tvShowId).asLiveData()
        return tvShowDetail
    }

    fun setToFavorite(){
        val tvShowResource = tvShowDetail.value
        if(tvShowResource is Resource.Success){
            tvShowResource.data?.apply {
                tvShowUseCase.setFavoriteTvShow(this, !this.isFavorite)
            }
        }
    }
}