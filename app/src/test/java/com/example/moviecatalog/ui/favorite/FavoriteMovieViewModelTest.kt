package com.example.moviecatalog.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.example.moviecatalog.domain.usecase.MoviesInteractor
import com.example.moviecatalog.ui.favorite.movie.FavoriteMovieViewModel
import com.example.moviecatalog.util.LiveDataTestUtil.observeForTesting
import com.example.moviecatalog.util.PagedListUtil
import com.example.moviecatalog.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
class FavoriteMovieViewModelTest {
    private lateinit var viewModel: FavoriteMovieViewModel

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
    fun getFavoriteMovies_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(DataDummy.getMoviesListDummy())
        Mockito.`when`(moviesUseCase.getFavoriteMovie()).thenReturn(flowOf(dummyMovies).asLiveData())
        viewModel = FavoriteMovieViewModel(moviesUseCase)

        verify(moviesUseCase).getFavoriteMovie()
        viewModel.favoriteMovies.observeForTesting{
            val moviesEntities = viewModel.favoriteMovies.value
            Assert.assertNotNull(moviesEntities)
            Assert.assertEquals(dummyMovies.size, moviesEntities?.size ?: 0)
        }
    }

}