package com.andresaftari.core.domain.usecase

import com.andresaftari.core.data.source.Resource
import com.andresaftari.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface IMealUseCase {
    fun getAllCategories(): Flow<Resource<List<Category>>>
    fun getFavoriteCategories(): Flow<List<Category>>
    fun setFavoriteCategories(category: Category, state: Boolean)
}