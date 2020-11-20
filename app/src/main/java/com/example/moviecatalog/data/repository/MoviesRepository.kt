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
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.remote.network.ApiResponse
import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.domain.repository.IMovieRepository
import com.example.moviecatalog.utils.AppExecutors
import com.example.moviecatalog.utils.ServiceConst
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors): IMovieRepository {

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(ServiceConst.PAGE_SIZE)
        .setPageSize(ServiceConst.PAGE_SIZE)
        .build()

    private var currentPage = 0

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>{
        return moviesLiveData.asLiveData()
    }

    private val moviesLiveData by lazy {
        object : NetworkBoundResourceLiveData<PagedList<MovieEntity>, List<MovieEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                return LivePagedListBuilder(localDataSource.getMovies(), config)
                    .setBoundaryCallback(MovieBoundaryCallback()).build()
            }

            public override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> {
                currentPage+=1
                return remoteDataSource.getMovies(currentPage)
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = true

            override fun saveCallResult(data: List<MovieEntity>) {
                localDataSource.insertMovies(data)
            }

            override fun onFetchFailed() {
                currentPage-=1
            }
        }
    }

    override fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>> =
        object : NetworkBoundResource<RoomMovieEntity, BaseDetailResponse.MovieDetailResponse>(){
            override fun loadFromDB(): Flow<RoomMovieEntity> =
                localDataSource.getSelectedMovieDetail(id)

            override suspend fun createCall(): Flow<ApiResponse<BaseDetailResponse.MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(id)

            override suspend fun saveCallResult(data: BaseDetailResponse.MovieDetailResponse) {
                localDataSource.updateSelectedMovieDetail(data)
            }

            override fun shouldFetch(data: RoomMovieEntity?): Boolean = true

            override fun shouldGetFromDataBaseWhenErrorResponse(data: RoomMovieEntity?): Boolean =
                data?.genre != null

        }.asFlow()

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun setFavoriteMovie(movieEntity: RoomMovieEntity, isFavorite: Boolean) {
        appExecutors.diskIO().execute { localDataSource.updateFavoriteMovie(movieEntity, isFavorite) }
    }

    override fun getMoviesOrderByRating(): LiveData<Resource<PagedList<MovieEntity>>> =
        object : NetworkBoundResourceLiveData<PagedList<MovieEntity>, List<MovieEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                return LivePagedListBuilder(localDataSource.getMoviesOrderByRating(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean = false

            override fun saveCallResult(data: List<MovieEntity>) {
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> = MediatorLiveData()

        }.asLiveData()

    override fun refreshMovies() {
        currentPage = 0
        moviesLiveData.fetchFromNetwork(true)
    }

    inner class MovieBoundaryCallback: PagedList.BoundaryCallback<MovieEntity>(){

        override fun onItemAtEndLoaded(itemAtEnd: MovieEntity) {
            moviesLiveData.fetchFromNetwork(true)
        }
    }

}