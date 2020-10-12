package com.andresaftari.core.domain.repo

import com.andresaftari.core.data.source.Resource
import com.andresaftari.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface IMealRepository {
    fun getAllCategories(): Flow<Resource<List<Category>>>
    fun getFavoritedCategories(): Flow<List<Category>>
    fun setFavoriteCategories(category: Category, state: Boolean)
}