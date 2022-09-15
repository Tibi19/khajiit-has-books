package com.tam.tesbooks.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T? = null): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)

    inline fun onError(doOnError: (message: String?) -> Unit) {
        if(this is Error) {
            doOnError(message)
        }
    }

    inline fun onSuccess(doOnSuccess: (data: T?) -> Unit) {
        if(this is Success) {
            doOnSuccess(data)
        }
    }

    inline fun onResource(
        onSuccess: (data: T?) -> Unit,
        onError: (message: String?) -> Unit,
        onLoading: (isLoading: Boolean) -> Unit = {}
    ) =
        when(this) {
            is Success -> onSuccess(data)
            is Error -> onError(message)
            is Loading -> onLoading(isLoading)
        }
}