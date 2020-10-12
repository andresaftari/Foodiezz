package com.andresaftari.foodiezz.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.andresaftari.core.adapter.ReusableAdapter
import com.andresaftari.core.data.source.Resource
import com.andresaftari.core.domain.model.Category
import com.andresaftari.foodiezz.R
import com.andresaftari.foodiezz.view.detail.DetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : Fragment() {
    private var listCategory = ArrayList<Category>()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val categoryAdapter = ReusableAdapter(
                R.layout.item_list,
                listCategory,
                { itemView, category ->
                    Glide.with(itemView.context)
                        .load(category.strCategoryThumb)
                        .into(itemView.iv_categoryThumb)

                    itemView.tv_categoryName?.text = category.strCategory
                },
                { _, selectedCategory ->
                    startActivity(
                        Intent(
                            requireContext(),
                            DetailActivity::class.java
                        ).putExtra(DetailActivity.EXTRA_CATEGORY, selectedCategory)
                    )
                }
            )

            homeViewModel.category.observe(viewLifecycleOwner, { categories ->
                if (categories != null) {
                    when (categories) {
                        is Resource.Loading -> showLoading()
                        is Resource.Success -> {
                            hideLoading()
                            categoryAdapter.setData(categories.data)
                        }
                        is Resource.Error -> {
                            hideLoading()
                            Toast.makeText(
                                requireContext(),
                                "Failed to load content",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })

            with(rv_category) {
                layoutManager = GridLayoutManager(
                    requireContext(),
                    2,
                    GridLayoutManager.VERTICAL,
                    false
                )
                adapter = categoryAdapter
                setHasFixedSize(true)
                isNestedScrollingEnabled = true
            }
        }
    }

    // Show shimmers when loading data
    private fun showLoading() {
        category_shimmer?.visibility = View.VISIBLE
    }

    // Hide shimmers when data is loaded
    private fun hideLoading() {
        category_shimmer?.visibility = View.GONE
    }
}