package com.andresaftari.core.domain.usecase

import com.andresaftari.core.domain.model.Category
import com.andresaftari.core.domain.repo.IMealRepository

class CoreInteractor(private val iMealRepository: IMealRepository) : IMealUseCase {
    override fun getAllCategories() = iMealRepository.getAllCategories()
    override fun getFavoriteCategories() = iMealRepository.getFavoritedCategories()
    override fun setFavoriteCategories(category: Category, state: Boolean) =
        iMealRepository.setFavoriteCategories(category, state)
}