package com.andresaftari.core.data.source.remote

import android.util.Log
import com.andresaftari.core.data.source.remote.api.ApiResponse
import com.andresaftari.core.data.source.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        const val TAG = "RemoteDataSource"
    }

    suspend fun getAllCategories() = flow {
        try {
            val response = apiService.getCategories()
            val listCategory = response.categories

            if (listCategory.isNotEmpty()) emit(ApiResponse.Success(response.categories))
            else emit(ApiResponse.Empty)

        } catch (e: RuntimeException) {
            Log.i(TAG, "${e.message} --- ${e.printStackTrace()}")
        } catch (e: Exception) {
            Log.i(TAG, "${e.message} --- ${e.printStackTrace()}")
        }
    }.flowOn(Dispatchers.IO)
}