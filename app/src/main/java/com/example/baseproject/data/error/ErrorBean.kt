package com.example.baseproject.data.error

data class ErrorBean(
    var errorCode: Int = 0,
    val errorMessage: String? = "",
    val errorKey: String? = "",
)