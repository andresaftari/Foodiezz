package com.andresaftari.core.utils

import com.andresaftari.core.data.source.local.entity.CategoryEntity
import com.andresaftari.core.data.source.remote.response.CategoryResponse
import com.andresaftari.core.domain.model.Category

object DataMapper {
    fun mapCategoryResponseToCategoryEntity(listResponse: List<CategoryResponse>): List<CategoryEntity> {
        val list = ArrayList<CategoryEntity>()

        listResponse.map {
            val category = CategoryEntity(
                it.idCategory,
                it.strCategory,
                it.strCategoryDescription,
                it.strCategoryThumb,
                it.isFavorited
            )
            list.add(category)
        }
        return list
    }

    fun mapCategoryEntityToDomain(listEntity: List<CategoryEntity>) = listEntity.map {
        Category(
            it.idCategory,
            it.strCategory,
            it.strCategoryDescription,
            it.strCategoryThumb,
            it.isFavorited
        )
    }

    fun mapDomainToCategoryEntity(category: Category) = CategoryEntity(
        category.idCategory,
        category.strCategory,
        category.strCategoryDescription,
        category.strCategoryThumb,
        category.isFavorited
    )
}