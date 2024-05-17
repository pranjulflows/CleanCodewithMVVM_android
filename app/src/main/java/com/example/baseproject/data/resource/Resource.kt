package com.example.baseproject.data.resource

import com.example.baseproject.data.error.ErrorBean


data class Resource<out T>(
    val status: Status,
    val data: T?,
    val error: ErrorBean?,
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: ErrorBean): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }


        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}