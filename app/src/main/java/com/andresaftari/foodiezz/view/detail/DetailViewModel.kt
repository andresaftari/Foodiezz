package com.andresaftari.foodiezz.view.detail

import androidx.lifecycle.ViewModel
import com.andresaftari.core.domain.model.Category
import com.andresaftari.core.domain.usecase.IMealUseCase

class DetailViewModel(private val iMealUseCase: IMealUseCase) : ViewModel() {
    fun setFavorite(category: Category, state: Boolean) =
        iMealUseCase.setFavoriteCategories(category, state)
}