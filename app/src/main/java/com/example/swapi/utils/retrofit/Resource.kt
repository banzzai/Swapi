package com.example.swapi.utils.retrofit

import com.example.swapi.utils.retrofit.RequestStatus.SUCCESS
import com.example.swapi.utils.retrofit.RequestStatus.ERROR
import com.example.swapi.utils.retrofit.RequestStatus.LOADING

data class Resource<out T>(val status: RequestStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = LOADING, data = data, message = null)
    }
}
