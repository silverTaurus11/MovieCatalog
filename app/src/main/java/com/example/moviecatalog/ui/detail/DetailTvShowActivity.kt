package com.example.moviecatalog.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.moviecatalog.R
import com.example.moviecatalog.data.Resource
import com.example.moviecatalog.data.source.local.entity.RoomTvShowEntity
import com.example.moviecatalog.utils.EspressoIdlingResource
import com.example.moviecatalog.utils.MovieCatalogueHelper
import com.example.moviecatalog.utils.MovieCatalogueHelper.getYearFromFullDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*

@AndroidEntryPoint
class DetailTvShowActivity: AppCompatActivity() {
    companion object{
        const val ID_KEY = "idKey"
    }

    private val detailViewModel: DetailTvShowViewModel by viewModels()
    private var shareIntentBuilder: ShareCompat.IntentBuilder?= null
    private var isFavoriteMovie = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initViewModel()
    }

    private fun initViewModel(){
        val id = intent.extras?.getInt(ID_KEY)?:0
        detailViewModel.setSelectedTvShow(id)
        EspressoIdlingResource.increment()
        detailViewModel.getTvShowDetail().observe(this, Observer{ tvShowDetail ->
            when(tvShowDetail){
                is Resource.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    view_detail.visibility = View.GONE
                    view_error.visibility = View.GONE
                }
                is Resource.Success -> {
                    initDetail(tvShowDetail.data)
                    progress_bar.visibility = View.GONE
                    view_detail.visibility = View.VISIBLE
                    view_error.visibility = View.GONE
                    //Memberitahukan bahwa tugas sudah selesai dijalankan
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.GONE
                    view_detail.visibility = View.GONE
                    view_error.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun initDetail(tvShowData: RoomTvShowEntity?){
        if(tvShowData == null) return
        Glide.with(this)
            .load(tvShowData.fullPosterPath)
            .into(img_cover_detail)
        Glide.with(this)
            .load(tvShowData.fullPosterPath)
            .into(img_photo_detail)
        detail_title_text_view.text = tvShowData.name
        rating_bar.rating = MovieCatalogueHelper.reCalculateRating(tvShowData.rating)
        genres_detail_text_view.text = tvShowData.genre
        year_detail_text_view.text = StringBuilder("${getYearFromFullDate(tvShowData.firstAirDate)} " +
                "- ${getYearFromFullDate(tvShowData.lastAirDate)}")
        language_text_view.text = tvShowData.language
        detail_description_text_view.text = tvShowData.description
        duration_label_text_view.text = getString(R.string.total_label)
        duration_text_view.text = getString(R.string.episode_template).replace("$1",
            tvShowData.episodeTotal.toString())
        initShareIntentBuilder(tvShowData)
        initFavoriteIcon(tvShowData.isFavorite)
    }

    private fun initFavoriteIcon(isFavorite: Boolean){
        this.isFavoriteMovie = isFavorite
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        val favoriteIcon = if(isFavoriteMovie) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
        menu?.findItem(R.id.favorite_item)?.icon = ContextCompat.getDrawable(this, favoriteIcon)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.share_item -> {
                shareIntentBuilder?.startChooser()
                true
            }
            R.id.favorite_item -> {
                EspressoIdlingResource.increment()
                detailViewModel.setToFavorite()
                val messageString = if(isFavoriteMovie) R.string.remove_to_favorite else R.string.add_to_favorite
                Toast.makeText(this, messageString, Toast.LENGTH_SHORT).show()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initShareIntentBuilder(entity: RoomTvShowEntity) {
        val mimeType = "text/plain"
        shareIntentBuilder =  ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle("Share to")
            .setText(entity.name)
    }
}