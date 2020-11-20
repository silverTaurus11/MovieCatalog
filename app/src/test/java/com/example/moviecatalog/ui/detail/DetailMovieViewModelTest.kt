package com.example.moviecatalog.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomMovieEntity
import com.example.moviecatalog.domain.usecase.MoviesInteractor
import com.example.moviecatalog.util.LiveDataTestUtil.observeForTesting
import com.example.moviecatalog.utils.DataDummy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesUseCase: MoviesInteractor

    private val dummyId = DataDummy.getMoviesListDummy()[0].id
    private val dummyMovie = DataDummy.getMovie(dummyId)

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = DetailMovieViewModel(moviesUseCase)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getMovieDetail_Success() {
        val dummyRoomMovieShow = RoomMovieEntity().apply {
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
        `when`(moviesUseCase.getMovieDetail(any())).thenReturn(flowOf(Resource.Success(dummyRoomMovieShow)))
        val movieDetail = viewModel.getMovieDetail()
        verify(moviesUseCase).getMovieDetail(any())
        movieDetail.observeForTesting{
            assertTrue(movieDetail.value is Resource.Success<RoomMovieEntity>)
            val moviesEntities = movieDetail.value?.data
            Assert.assertNotNull(moviesEntities)
            assertEquals(dummyRoomMovieShow.id, moviesEntities?.id)
            assertEquals(dummyRoomMovieShow.name, moviesEntities?.name)
            assertEquals(dummyRoomMovieShow.description, moviesEntities?.description)
            assertEquals(dummyRoomMovieShow.rating, moviesEntities?.rating)
            assertEquals(dummyRoomMovieShow.releaseDate, moviesEntities?.releaseDate)
            assertEquals(dummyRoomMovieShow.duration, moviesEntities?.duration)
            assertEquals(dummyRoomMovieShow.genre, moviesEntities?.genre)
            assertEquals(dummyRoomMovieShow.language, moviesEntities?.language)
            assertEquals(dummyRoomMovieShow.posterPath, moviesEntities?.posterPath)
            assertEquals(dummyRoomMovieShow.fullPosterPath, moviesEntities?.fullPosterPath)
        }
    }

    @Test
    fun getMovieDetail_Error() {
        val errorMessage = "Data Error"
        `when`(moviesUseCase.getMovieDetail(any())).thenReturn(flowOf(Resource.Error(errorMessage)))
        val movieDetail = viewModel.getMovieDetail()
        verify(moviesUseCase).getMovieDetail(any())
        movieDetail.observeForTesting{
            assertTrue(movieDetail.value is Resource.Error<RoomMovieEntity>)
            assertEquals(errorMessage, movieDetail.value?.message?:"")
        }
    }
}