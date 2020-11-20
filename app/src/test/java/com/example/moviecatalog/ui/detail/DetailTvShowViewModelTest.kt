package com.example.moviecatalog.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.domain.usecase.TvShowInteractor
import com.example.moviecatalog.util.LiveDataTestUtil.observeForTesting
import com.example.moviecatalog.utils.DataDummy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowUseCase: TvShowInteractor

    private val dummyId = DataDummy.getTvShowsListDummy()[0].id
    private val dummyTvShow = DataDummy.getTvShow(dummyId?:0)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = DetailTvShowViewModel(tvShowUseCase)
    }

    @Test
    fun getTvShowDetail_Success() {
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
        `when`(tvShowUseCase.getTvShowDetail(any())).thenReturn(flowOf(Resource.Success(dummyRoomTvShow)))
        val tvShowDetail = viewModel.getTvShowDetail()
        verify(tvShowUseCase).getTvShowDetail(any())
        tvShowDetail.observeForTesting{
            assertTrue(tvShowDetail.value is Resource.Success<RoomTvShowEntity>)
            val tvShowEntities = tvShowDetail.value?.data
            assertNotNull(tvShowEntities)
            assertEquals(dummyRoomTvShow.id, tvShowEntities?.id)
            assertEquals(dummyRoomTvShow.name, tvShowEntities?.name)
            assertEquals(dummyRoomTvShow.description, tvShowEntities?.description)
            assertEquals(dummyRoomTvShow.rating, tvShowEntities?.rating)
            assertEquals(dummyRoomTvShow.firstAirDate, tvShowEntities?.firstAirDate)
            assertEquals(dummyRoomTvShow.lastAirDate, tvShowEntities?.lastAirDate)
            assertEquals(dummyRoomTvShow.episodeTotal, tvShowEntities?.episodeTotal)
            assertEquals(dummyRoomTvShow.genre, tvShowEntities?.genre)
            assertEquals(dummyRoomTvShow.language, tvShowEntities?.language)
            assertEquals(dummyRoomTvShow.posterPath, tvShowEntities?.posterPath)
            assertEquals(dummyRoomTvShow.fullPosterPath, tvShowEntities?.fullPosterPath)
        }
    }

    @Test
    fun getTvShowDetail_Error() {
        val errorMessage = "Data Error"
        `when`(tvShowUseCase.getTvShowDetail(any()))
            .thenReturn(flowOf(Resource.Error(errorMessage)))
        val tvShowDetail = viewModel.getTvShowDetail()
        verify(tvShowUseCase).getTvShowDetail(any())
        tvShowDetail.observeForTesting{
            assertTrue(tvShowDetail.value is Resource.Error<RoomTvShowEntity>)
            assertEquals(errorMessage, tvShowDetail.value?.message?:"")
        }
    }

}