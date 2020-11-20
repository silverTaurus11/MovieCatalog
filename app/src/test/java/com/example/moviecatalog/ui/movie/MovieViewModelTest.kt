package com.example.moviecatalog.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.domain.usecase.MoviesInteractor
import com.example.moviecatalog.util.LiveDataTestUtil.observeForTesting
import com.example.moviecatalog.util.PagedListUtil
import com.example.moviecatalog.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesUseCase: MoviesInteractor

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getMovies_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(DataDummy.getMoviesListDummy())
        `when`(moviesUseCase.getMovies()).thenReturn(flowOf(Resource.Success(dummyMovies)).asLiveData())
        viewModel = MovieViewModel(moviesUseCase)

        verify(moviesUseCase).getMovies()
        viewModel.movies.observeForTesting{
            assertTrue(viewModel.movies.value is Resource.Success<PagedList<MovieEntity>>)
            val moviesEntities = viewModel.movies.value?.data
            assertNotNull(moviesEntities)
            assertEquals(dummyMovies.size, moviesEntities?.size?:0)
        }
    }

    @Test
    fun getMoviesOrderByRating_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(DataDummy.getMoviesListDummy().sortedBy { it.rating })
        `when`(moviesUseCase.getMovieOrderByRating()).thenReturn(flowOf(Resource.Success(dummyMovies)).asLiveData())
        viewModel = MovieViewModel(moviesUseCase)
        val orderLiveData = viewModel.orderByRating()
        verify(moviesUseCase).getMovieOrderByRating()
        orderLiveData.observeForTesting{
            assertTrue(orderLiveData.value is Resource.Success<PagedList<MovieEntity>>)
            val moviesEntities = orderLiveData.value?.data
            assertNotNull(moviesEntities)
            assertEquals(dummyMovies.size, moviesEntities?.size?:0)
        }
    }

    @Test
    fun getMovies_Error(){
        val errorMessage = "Data Error"
        val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        liveData.value = Resource.Error(errorMessage)
        `when`(moviesUseCase.getMovies()).thenReturn(liveData)
        viewModel = MovieViewModel(moviesUseCase)

        verify(moviesUseCase).getMovies()
        viewModel.movies.observeForTesting{
            assertTrue(viewModel.movies.value is Resource.Error<PagedList<MovieEntity>>)
            assertEquals(errorMessage, viewModel.movies.value?.message?:"")
        }
    }

}