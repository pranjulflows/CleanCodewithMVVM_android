package com.example.baseproject.api

import com.example.baseproject.data.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface AppApi {

    @GET("products")
    suspend fun getProductList(): Response<ProductResponse>


}