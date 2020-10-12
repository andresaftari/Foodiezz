package com.andresaftari.foodiezz.view.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.andresaftari.core.domain.model.Category
import com.andresaftari.foodiezz.MainActivity
import com.andresaftari.foodiezz.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.component_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        pb_loading_detail?.visibility = View.VISIBLE
        setupActionBar()

        if (intent.hasExtra(EXTRA_CATEGORY)) {
            val detailMeals = intent.getParcelableExtra<Category>(EXTRA_CATEGORY)

            showDetailMeals(detailMeals)
            Log.i("Detail", "data $detailMeals")
        }
    }

    private fun showDetailMeals(detailMeals: Category?) {
        pb_loading_detail.visibility = View.GONE
        detailMeals?.let {
            collapsing_toolbar?.title = detailMeals.strCategory
            tv_detail_description?.text = detailMeals.strCategoryDescription
                ?.replace("\r\n\r\n", "\n\n")
                ?.replace("\r\n", "\n\n")

            Glide.with(this)
                .load(detailMeals.strCategoryThumb)
                .into(iv_mealThumbDetail)

            var setFavorite = detailMeals.isFavorited
            setFavoriteStatus(setFavorite)

            fab_favorite.setOnClickListener {
                setFavorite = !setFavorite
                detailViewModel.setFavorite(detailMeals, setFavorite)
                setFavoriteStatus(setFavorite)

                if (setFavorite) Snackbar.make(
                    fab_favorite,
                    "${detailMeals.strCategory} set as favorited!",
                    Snackbar.LENGTH_SHORT
                ).show()

                val uri = Uri.parse("foodiezz://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setFavoriteStatus(state: Boolean) {
        if (state) fab_favorite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_favorite_on
            )
        ) else fab_favorite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_favorite_off
            )
        )
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // set the CollapsingToolbar when collapse change colors
        collapsing_toolbar.apply {
            setContentScrimColor(
                ContextCompat.getColor(
                    this@DetailActivity,
                    R.color.colorWhite
                )
            )
            setCollapsedTitleTextColor(
                ContextCompat.getColor(
                    this@DetailActivity,
                    R.color.colorPrimary
                )
            )
            setExpandedTitleColor(
                ContextCompat.getColor(
                    this@DetailActivity,
                    R.color.colorWhite
                )
            )
        }
    }

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
    }
}