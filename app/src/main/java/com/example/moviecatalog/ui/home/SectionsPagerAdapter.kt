package com.example.moviecatalog.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.movie.MovieFragment
import com.example.moviecatalog.ui.tvshow.TvShowFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie_title, R.string.tv_show_title)
    }

    private val menuList by lazy { listOf(MovieFragment(), TvShowFragment()) }

    override fun getItem(position: Int): Fragment = menuList[position] as Fragment

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = menuList.size

    fun doSortMovieByRating(){
        menuList.forEach {
            if(it is SortMoviesListener){
                it.doSortMovieByRating()
            }
        }
    }
}