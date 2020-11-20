package com.example.moviecatalog.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.DataSource
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.RemoteDataSource
import com.example.moviecatalog.data.source.local.LocalDataSource
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.data.source.remote.network.ApiResponse
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import com.example.moviecatalog.util.LiveDataTestUtil
import com.example.moviecatalog.util.LiveDataTestUtil.observeForTesting
import com.example.moviecatalog.util.PagedListUtil
import com.example.moviecatalog.utils.AppExecutors
import com.example.moviecatalog.utils.DataDummy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.times

@ExperimentalCoroutinesApi
class TvShowRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val tvShowRepository = TvShowsRepository(remote, local, appExecutors)

    private val tvShowResponses = DataDummy.getTvShowsListDummy()
    private val tvShowId = tvShowResponses[0].id

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getTvShowTest_Success(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        Mockito.`when`(local.getTvShows()).thenReturn(dataSourceFactory)
        val movieEntity = Resource.Success(PagedListUtil.mockPagedList(tvShowResponses))
        tvShowRepository.getTvShow()
        verify(local).getTvShows()
        assertNotNull(movieEntity)
        assertEquals(tvShowResponses.size, movieEntity.data?.size ?: 0)
    }

    @Test
    fun getTvShowDetailTest_Success() = runBlocking{
        val dummyTvShow = DataDummy.getTvShow(tvShowId?:0)
        val dummyRoomTvShow = RoomTvShowEntity().apply {
            id = dummyTvShow.id
            name = dummyTvShow.name
            description = dummyTvShow.description
            rating = dummyTvShow.rating
            firstAirDate = dummyTvShow.firstAirDate
            lastAirDate = dummyTvShow.lastAirDate
            episodeTotal = dummyTvShow.episodeTotal
            genre = dummyTvShow.genre?.map { it.name }?.joinToString(", ")
            language = dummyTvShow.language
            posterPath = dummyTvShow.posterPath
            fullPosterPath = dummyTvShow.fullPosterUrl
        }
        doAnswer {
            flowOf(dummyRoomTvShow)
        }.`when`(local).getSelectedTvShowDetail(any())
        doAnswer {
            flowOf(ApiResponse.Success(dummyTvShow))
        }.`when`(remote).getTvShowDetail(anyInt())
        val tvShowLiveData = tvShowRepository.getTvShowDetail(tvShowId?:0).asLiveData()
        val tvShowLiveDataValue = LiveDataTestUtil.getValue(tvShowLiveData)
        verify(remote).getTvShowDetail(anyInt())
        verify(local, times(2)).getSelectedTvShowDetail(anyInt())
        assertTrue(tvShowLiveDataValue is Resource.Loading<RoomTvShowEntity>)
        tvShowLiveData.observeForTesting {
            assertTrue(tvShowLiveData.value is Resource.Success<RoomTvShowEntity>)
        }
        val tvShowEntity = tvShowLiveData.value?.data
        assertNotNull(tvShowEntity)
        assertEquals(dummyRoomTvShow.id, tvShowEntity?.id)
        assertEquals(dummyRoomTvShow.name, tvShowEntity?.name)
        assertEquals(dummyRoomTvShow.description, tvShowEntity?.description)
        assertEquals(dummyRoomTvShow.rating, tvShowEntity?.rating)
        assertEquals(dummyRoomTvShow.firstAirDate, tvShowEntity?.firstAirDate)
        assertEquals(dummyRoomTvShow.lastAirDate, tvShowEntity?.lastAirDate)
        assertEquals(dummyRoomTvShow.episodeTotal, tvShowEntity?.episodeTotal)
        assertEquals(dummyRoomTvShow.genre, tvShowEntity?.genre)
        assertEquals(dummyRoomTvShow.language, tvShowEntity?.language)
        assertEquals(dummyRoomTvShow.posterPath, tvShowEntity?.posterPath)
        assertEquals(dummyRoomTvShow.fullPosterPath, tvShowEntity?.fullPosterPath)
    }

    @Test
    fun getTvShowDetailTest_Error() = runBlocking{
        val errorMessage = "Data Error"
        doAnswer {
            flowOf(RoomTvShowEntity())
        }.`when`(local).getSelectedTvShowDetail(anyInt())
        doAnswer {
            flowOf(ApiResponse.Error(errorMessage))
        }.`when`(remote).getTvShowDetail(anyInt())
        val tvShowLiveData = tvShowRepository.getTvShowDetail(tvShowId?:0).asLiveData()
        val tvShowLiveDataValue = LiveDataTestUtil.getValue(tvShowLiveData)
        verify(remote).getTvShowDetail(Mockito.anyInt())
        assertTrue(tvShowLiveDataValue is Resource.Loading<RoomTvShowEntity>)
        tvShowLiveData.observeForTesting {
            assertTrue(tvShowLiveData.value is Resource.Error<RoomTvShowEntity>)
            assertEquals(errorMessage, tvShowLiveData.value?.message?:"")
        }
    }

    @Test
    fun getFavoriteTvShowTest(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java)
                as DataSource.Factory<Int, TvShowEntity>
        Mockito.`when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        val favoriteMovies = Resource.Success(PagedListUtil.mockPagedList(tvShowResponses))
        tvShowRepository.getFavoriteTvShow()
        verify(local).getFavoriteTvShows()
        assertNotNull(favoriteMovies)
        assertEquals(tvShowResponses.size, favoriteMovies.data?.size?:0)
    }

    @Test
    fun getOrderByRating(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java)
                as DataSource.Factory<Int, TvShowEntity>
        Mockito.`when`(local.getTvShowsOrderByRating()).thenReturn(dataSourceFactory)
        val orderMovies = Resource.Success(PagedListUtil.mockPagedList(tvShowResponses.sortedBy { it.rating }))
        tvShowRepository.getTvShowOrderByRating()
        verify(local).getTvShowsOrderByRating()
        assertNotNull(orderMovies)
        assertEquals(tvShowResponses.size, orderMovies.data?.size?:0)
    }
}