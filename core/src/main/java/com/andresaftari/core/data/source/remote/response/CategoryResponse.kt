package com.andresaftari.core.data.source.remote.response

import java.io.Serializable

data class CategoryResponse(
    var idCategory: String = "1",
    val strCategory: String?,
    val strCategoryDescription: String?,
    val strCategoryThumb: String,
    val isFavorited: Boolean
) : Serializable