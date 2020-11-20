package com.example.moviecatalog.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.moviecatalog.data.source.remote.network.ApiResponse
import com.example.moviecatalog.data.source.remote.network.ApiService
import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import com.example.moviecatalog.utils.MovieCatalogueHelper.getFilterDateString
import com.example.moviecatalog.utils.ServiceConst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    private val defaultConfig =  mutableMapOf<String, Any>(
        "api_key" to ServiceConst.API_KEY
    )

    fun getMovies(page: Int = 1): LiveData<ApiResponse<List<MovieEntity>>> {
        //get data from remote api
        return flow {
            try {
                val movieConfig = HashMap(defaultConfig)
                val response = apiService.getDiscoverMovies(movieConfig.apply{
                    put("page", page)
                    put("sort_by", ServiceConst.SORT_MOVIE_DATA_KEY)
                    put("release_date.lte",getFilterDateString())
                })
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<BaseDetailResponse.MovieDetailResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getMovieDetail(id, defaultConfig)
                val statusCode = response.statusCode
                if (statusCode != null){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShows(page: Int = 1): LiveData<ApiResponse<List<TvShowEntity>>> {
        //get data from remote api
        return flow {
            try {
                val tvShowConfig = HashMap(defaultConfig)
                val response = apiService.getDiscoverTvShows(tvShowConfig.apply{
                    put("page", page)
                    put("sort_by", ServiceConst.SORT_TV_SHOW_DATA_KEY)
                    put("first_air_date.lte",getFilterDateString())
                })
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }

    suspend fun getTvShowDetail(id: Int): Flow<ApiResponse<BaseDetailResponse.TvShowDetailResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getTvShowDetail(id, defaultConfig)
                val statusCode = response.statusCode
                if (statusCode != null){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}