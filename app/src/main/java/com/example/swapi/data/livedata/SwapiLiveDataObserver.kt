package com.example.swapi.data.livedata

import androidx.lifecycle.Observer
import com.example.swapi.utils.retrofit.RequestStatus
import com.example.swapi.utils.retrofit.Resource

/**
 * A quick abstraction to simplify Observers that observe a Resource object.
 * Nothing is architecturally different, just streamlined common code.
 */
abstract class SwapiLiveDataObserver<T>: Observer<Resource<T?>> {
    override fun onChanged(it: Resource<T?>) {
        it.let { resource ->
            when (resource.status) {
                RequestStatus.SUCCESS -> {
                    onSuccess(resource.data)
                }
                RequestStatus.ERROR -> {
                    // This interface doesn't know this resource is coming from a error() call,
                    // in which the message isn't nullable.
                    onError(resource.message ?: "Unknown error")
                }
                RequestStatus.LOADING -> {
                    onLoading()
                }
            }
        }
    }

    abstract fun onSuccess(data: T?)
    abstract fun onError(errorMessage: String)
    open fun onLoading() {
        //Nothing by default
    }
}