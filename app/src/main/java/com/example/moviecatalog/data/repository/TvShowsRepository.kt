package com.example.moviecatalog.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviecatalog.data.NetworkBoundResource
import com.example.moviecatalog.data.NetworkBoundResourceLiveData
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.RemoteDataSource
import com.example.moviecatalog.data.source.local.LocalDataSource
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.data.source.remote.network.ApiResponse
import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import com.example.moviecatalog.domain.repository.ITvShowRepository
import com.example.moviecatalog.utils.AppExecutors
import com.example.moviecatalog.utils.ServiceConst
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors): ITvShowRepository{

    private var currentPage = 0

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(ServiceConst.PAGE_SIZE)
        .setPageSize(ServiceConst.PAGE_SIZE)
        .build()

    private val tvShowNetworkResource by lazy {
        object : NetworkBoundResourceLiveData<PagedList<TvShowEntity>, List<TvShowEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                return LivePagedListBuilder(localDataSource.getTvShows(), config)
                    .setBoundaryCallback(TvShowBoundaryCallback()).build()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> {
                currentPage+=1
                return remoteDataSource.getTvShows(currentPage)
            }

            override fun saveCallResult(data: List<TvShowEntity>) {
                localDataSource.insertTvShows(data)
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean = true

            override fun onFetchFailed() {
                currentPage-=1
            }
        }
    }

    override fun getTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> =
        tvShowNetworkResource.asLiveData()

    override fun getTvShowDetail(id: Int): Flow<Resource<RoomTvShowEntity>> =
        object : NetworkBoundResource<RoomTvShowEntity, BaseDetailResponse.TvShowDetailResponse>(){
            override fun loadFromDB(): Flow<RoomTvShowEntity> =
                localDataSource.getSelectedTvShowDetail(id)

            override suspend fun createCall(): Flow<ApiResponse<BaseDetailResponse.TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(id)

            override suspend fun saveCallResult(data: BaseDetailResponse.TvShowDetailResponse) {
                localDataSource.updateSelectedTvShowDetail(data)
            }

            override fun shouldFetch(data: RoomTvShowEntity?): Boolean = true

            override fun shouldGetFromDataBaseWhenErrorResponse(data: RoomTvShowEntity?): Boolean =
                data?.genre != null

        }.asFlow()

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun setFavoriteTvShow(roomTvShowEntity: RoomTvShowEntity, isFavorite: Boolean) {
        appExecutors.diskIO().execute { localDataSource.updateFavoriteTvShow(roomTvShowEntity, isFavorite) }
    }

    override fun getTvShowOrderByRating(): LiveData<Resource<PagedList<TvShowEntity>>> =
        object : NetworkBoundResourceLiveData<PagedList<TvShowEntity>, List<TvShowEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                return LivePagedListBuilder(localDataSource.getTvShowsOrderByRating(), config).build()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> = MediatorLiveData()

            override fun saveCallResult(data: List<TvShowEntity>) {
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean = false

        }.asLiveData()

    override fun refreshTvShows() {
        currentPage = 0
        tvShowNetworkResource.fetchFromNetwork(true)
    }

    inner class TvShowBoundaryCallback: PagedList.BoundaryCallback<TvShowEntity>(){

        override fun onItemAtEndLoaded(itemAtEnd: TvShowEntity) {
            tvShowNetworkResource.fetchFromNetwork(true)
        }
    }
}
