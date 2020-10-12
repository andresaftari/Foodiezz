package com.andresaftari.core.data.source

import com.andresaftari.core.data.source.local.LocalDataSource
import com.andresaftari.core.data.source.remote.RemoteDataSource
import com.andresaftari.core.data.source.remote.response.CategoryResponse
import com.andresaftari.core.domain.model.Category
import com.andresaftari.core.domain.repo.IMealRepository
import com.andresaftari.core.utils.CoreExecutor
import com.andresaftari.core.utils.DataMapper
import kotlinx.coroutines.flow.map

class MealRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
    private val appExecutors: CoreExecutor
) : IMealRepository {
    override fun getAllCategories() = object :
        NetworkBoundRes<List<Category>, List<CategoryResponse>>() {
        override fun loadFromDB() =
            local.getAllCategories().map { DataMapper.mapCategoryEntityToDomain(it) }

        override fun shouldFetch(data: List<Category>?) = data.isNullOrEmpty()

        override suspend fun createCall() = remote.getAllCategories()

        override suspend fun saveCallResult(data: List<CategoryResponse>) {
            val response = DataMapper.mapCategoryResponseToCategoryEntity(data)
            local.insertCategories(response)
        }
    }.asFlow()

    override fun getFavoritedCategories() =
        local.getFavoritedCategories().map { DataMapper.mapCategoryEntityToDomain(it) }

    override fun setFavoriteCategories(category: Category, state: Boolean) {
        val favorited = DataMapper.mapDomainToCategoryEntity(category)
        appExecutors.diskIO().execute { local.setFavoriteCategories(favorited, state) }
    }
}
