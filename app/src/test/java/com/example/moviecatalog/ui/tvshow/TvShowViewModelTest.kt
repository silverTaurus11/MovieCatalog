package com.example.moviecatalog.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.remote.response.TvShowEntity
import com.example.moviecatalog.domain.usecase.TvShowInteractor
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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowUseCase: TvShowInteractor

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)

    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getTvShows_Success(){
        val dummyTvShow = PagedListUtil.mockPagedList(DataDummy.getTvShowsListDummy())
        `when`(tvShowUseCase.getTvShows()).thenReturn(flowOf(Resource.Success(dummyTvShow)).asLiveData())
        viewModel = TvShowViewModel(tvShowUseCase)

        verify(tvShowUseCase).getTvShows()
        viewModel.tvShows.observeForTesting{
            assertTrue(viewModel.tvShows.value is Resource.Success<PagedList<TvShowEntity>>)
            val moviesEntities = viewModel.tvShows.value?.data
            assertNotNull(moviesEntities)
            assertEquals(dummyTvShow.size, moviesEntities?.size ?: 0)
        }
    }

    @Test
    fun getTvShowsOrderByRating_Success(){
        val dummyTvShow = PagedListUtil.mockPagedList(DataDummy.getTvShowsListDummy().sortedBy { it.rating })
        `when`(tvShowUseCase.getTvShowOrderByRating()).thenReturn(flowOf(Resource.Success(dummyTvShow)).asLiveData())
        viewModel = TvShowViewModel(tvShowUseCase)
        val orderLiveData = viewModel.orderByRating()
        verify(tvShowUseCase).getTvShows()
        orderLiveData.observeForTesting{
            assertTrue(orderLiveData.value is Resource.Success<PagedList<TvShowEntity>>)
            val moviesEntities = orderLiveData.value?.data
            assertNotNull(moviesEntities)
            assertEquals(dummyTvShow.size, moviesEntities?.size ?: 0)
        }
    }

    @Test
    fun getTvShows_Error(){
        val errorMessage = "Data Error"
        val liveData = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        liveData.value = Resource.Error(errorMessage)
        `when`(tvShowUseCase.getTvShows()).thenReturn(liveData)
        viewModel = TvShowViewModel(tvShowUseCase)

        verify(tvShowUseCase).getTvShows()
        viewModel.tvShows.observeForTesting{
            assertTrue(viewModel.tvShows.value is Resource.Error<PagedList<TvShowEntity>>)
            assertEquals(errorMessage, viewModel.tvShows.value?.message?:"")
        }
    }
}