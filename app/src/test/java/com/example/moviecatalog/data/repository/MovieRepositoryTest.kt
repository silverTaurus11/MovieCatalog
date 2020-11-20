package com.example.moviecatalog.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.DataSource
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.RemoteDataSource
import com.example.moviecatalog.data.source.local.LocalDataSource
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.data.source.remote.network.ApiResponse
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.util.LiveDataTestUtil
import com.example.moviecatalog.util.LiveDataTestUtil.observeForTesting
import com.example.moviecatalog.util.PagedListUtil
import com.example.moviecatalog.utils.AppExecutors
import com.example.moviecatalog.utils.DataDummy
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
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
class MovieRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val moviesRepository = MoviesRepository(remote, local, appExecutors)

    private val moviesResponses = DataDummy.getMoviesListDummy()
    private val movieId = moviesResponses[0].id

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getMoviesTest_Success() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies()).thenReturn(dataSourceFactory)
        val movieEntity = Resource.Success(PagedListUtil.mockPagedList(moviesResponses))
        moviesRepository.getMovies()
        verify(local).getMovies()
        assertNotNull(movieEntity)
        assertEquals(moviesResponses.size, movieEntity.data?.size?:0)
    }

    @Test
    fun getMovieDetailTest_Success() = runBlocking{
        val dummyMovie = DataDummy.getMovie(movieId)
        val dummyRoomMovie = RoomMovieEntity().apply {
            id = dummyMovie.id
            name = dummyMovie.name
            description = dummyMovie.description
            rating = dummyMovie.rating
            releaseDate = dummyMovie.releaseDate
            duration = dummyMovie.duration
            genre = dummyMovie.genre?.map { it.name }?.joinToString(", ")
            language = dummyMovie.language
            posterPath = dummyMovie.posterPath
            fullPosterPath = dummyMovie.fullPosterUrl
        }
        doAnswer {
            flowOf(dummyRoomMovie)
        }.`when`(local).getSelectedMovieDetail(anyInt())
        doAnswer {
            flowOf(ApiResponse.Success(dummyMovie))
        }.`when`(remote).getMovieDetail(anyInt())
        val moviesLiveData = moviesRepository.getMovieDetail(movieId?:0).asLiveData()
        val moviesLiveDataValue = LiveDataTestUtil.getValue(moviesLiveData)
        verify(remote).getMovieDetail(anyInt())
        verify(local, times(2)).getSelectedMovieDetail(anyInt())
        assertTrue(moviesLiveDataValue is Resource.Loading<RoomMovieEntity>)
        moviesLiveData.observeForTesting {
            assertTrue(moviesLiveData.value is Resource.Success<RoomMovieEntity>)
        }
        val movieEntity = moviesLiveData.value?.data
        assertNotNull(movieEntity)
        assertEquals(dummyRoomMovie.id, movieEntity?.id)
        assertEquals(dummyRoomMovie.name, movieEntity?.name)
        assertEquals(dummyRoomMovie.description, movieEntity?.description)
        assertEquals(dummyRoomMovie.rating, movieEntity?.rating)
        assertEquals(dummyRoomMovie.releaseDate, movieEntity?.releaseDate)
        assertEquals(dummyRoomMovie.duration, movieEntity?.duration)
        assertEquals(dummyRoomMovie.genre, movieEntity?.genre)
        assertEquals(dummyRoomMovie.language, movieEntity?.language)
        assertEquals(dummyRoomMovie.posterPath, movieEntity?.posterPath)
        assertEquals(dummyRoomMovie.fullPosterPath, movieEntity?.fullPosterPath)
    }

    @Test
    fun getMovieDetailTest_Error() = runBlocking{
        val errorMessage = "Data Error"
        doAnswer {
            flowOf(RoomMovieEntity())
        }.`when`(local).getSelectedMovieDetail(anyInt())
        doAnswer {
            flowOf(ApiResponse.Error(errorMessage))
        }.`when`(remote).getMovieDetail(anyInt())
        val moviesLiveData = moviesRepository.getMovieDetail(movieId?:0).asLiveData()
        val moviesLiveDataValue = LiveDataTestUtil.getValue(moviesLiveData)
        verify(remote).getMovieDetail(anyInt())
        assertTrue(moviesLiveDataValue is Resource.Loading<RoomMovieEntity>)
        moviesLiveData.observeForTesting {
            assertTrue(moviesLiveData.value is Resource.Error<RoomMovieEntity>)
            assertEquals(errorMessage, moviesLiveData.value?.message?:"")
        }
    }

    @Test
    fun getFavoriteMovieTest(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        val favoriteMovies = Resource.Success(PagedListUtil.mockPagedList(moviesResponses))
        moviesRepository.getFavoriteMovies()
        verify(local).getFavoriteMovies()
        assertNotNull(favoriteMovies)
        assertEquals(moviesResponses.size, favoriteMovies.data?.size?:0)
    }

    @Test
    fun getOrderByRating(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMoviesOrderByRating()).thenReturn(dataSourceFactory)
        val orderMovies = Resource.Success(PagedListUtil
            .mockPagedList(moviesResponses.sortedBy { it.rating }))
        moviesRepository.getMoviesOrderByRating()
        verify(local).getMoviesOrderByRating()
        assertNotNull(orderMovies)
        assertEquals(moviesResponses.size, orderMovies.data?.size?:0)
    }

}