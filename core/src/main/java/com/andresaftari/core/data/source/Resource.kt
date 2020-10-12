package com.andresaftari.core.data.source

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    open class Success<T>(data: T) : Resource<T>(data)
    open class Loading<T>(data: T? = null) : Resource<T>(data)
    open class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}