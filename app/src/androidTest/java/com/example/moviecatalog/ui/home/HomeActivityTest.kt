package com.example.moviecatalog.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.movie.MovieFragment
import com.example.moviecatalog.ui.tvshow.TvShowFragment
import com.example.moviecatalog.utils.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(MovieFragment.TAG)))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(MovieFragment.TAG)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadDetailMovies() {
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(MovieFragment.TAG)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()
            ))
        onView(withId(R.id.detail_title_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.genres_detail_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.main_scroll_view)).perform(swipeUp())
        onView(withId(R.id.year_detail_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.language_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.detail_description_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun loadTvShow() {
        onView(withText("Tv Shows")).perform(click())
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(TvShowFragment.TAG)))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(TvShowFragment.TAG)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("Tv Shows")).perform(click())
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(TvShowFragment.TAG)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.detail_title_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.genres_detail_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.main_scroll_view)).perform(swipeUp())
        onView(withId(R.id.year_detail_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.language_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.detail_description_text_view))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun sortMoviesByRating(){
        try {
            onView(withId(R.id.sort_item)).perform(click())
        } catch (e: NoMatchingViewException) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.order_by_rating)).perform(click())
        }
    }

    @Test
    fun sortTvShowByRating(){
        onView(withText("Tv Shows")).perform(click())
        try {
            onView(withId(R.id.sort_item)).perform(click())
        } catch (e: NoMatchingViewException) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.order_by_rating)).perform(click())
        }
    }

    @Test
    fun showFavoriteMovies(){
        try {
            onView(withId(R.id.favorite_item)).perform(click())
        } catch (e: NoMatchingViewException) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.favorite_list)).perform(click())
        }
    }

    @Test
    fun showFavoriteTvShows(){
        try {
            onView(withId(R.id.favorite_item)).perform(click())
        } catch (e: NoMatchingViewException) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.favorite_list)).perform(click())
        }
        onView(withText("Tv Shows")).perform(click())
    }

    @Test
    fun setFavoriteMovieShow() {
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(MovieFragment.TAG)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        try {
            onView(withId(R.id.favorite_item)).perform(click())
        } catch (e: NoMatchingViewException) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.add_to_favorite)).perform(click())
        }
    }

    @Test
    fun setFavoriteTvShow() {
        onView(withText("Tv Shows")).perform(click())
        onView(Matchers.allOf(withId(R.id.rv_movies), withContentDescription(TvShowFragment.TAG)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        try {
            onView(withId(R.id.favorite_item)).perform(click())
        } catch (e: NoMatchingViewException) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.add_to_favorite)).perform(click())
        }
    }
}