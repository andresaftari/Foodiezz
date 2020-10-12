package com.andresaftari.core.data.source.remote.api

import com.andresaftari.core.data.source.remote.response.ListCategoryResponse
import retrofit2.http.GET

interface ApiService {
    // getCategories will gather every available Meal Categories in the API
    @GET("categories.php")
    suspend fun getCategories(): ListCategoryResponse
}