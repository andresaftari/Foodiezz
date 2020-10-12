package com.andresaftari.foodiezz.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresaftari.core.adapter.ReusableAdapter
import com.andresaftari.core.domain.model.Category
import com.andresaftari.foodiezz.MainActivity
import com.andresaftari.foodiezz.view.detail.DetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.item_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private val listFavorite = ArrayList<Category>()
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        loadKoinModules(favoriteModule)

        val favoriteAdapter = ReusableAdapter(
            R.layout.item_list,
            listFavorite,
            { itemView, category ->
                Glide.with(itemView.context)
                    .load(category.strCategoryThumb)
                    .into(itemView.iv_categoryThumb)

                itemView.tv_categoryName?.text = category.strCategory
            },
            { _, selectedCategory ->
                startActivity(
                    Intent(
                        this,
                        DetailActivity::class.java
                    ).putExtra(DetailActivity.EXTRA_CATEGORY, selectedCategory)
                )
            }
        )

        favoriteViewModel.getFavoriteList.observe(this, { categories ->
            favoriteAdapter.setData(categories)
            view_empty.visibility = if (categories.isNotEmpty()) View.GONE else View.VISIBLE
        })

        with(rv_favorite) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}