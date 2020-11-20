package com.example.moviecatalog.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.favorite.FavoriteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val sectionsPagerAdapter by lazy { SectionsPagerAdapter(this, supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorite_item -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            R.id.sort_item -> {
                sectionsPagerAdapter.doSortMovieByRating()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}