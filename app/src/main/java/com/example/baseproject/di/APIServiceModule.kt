package com.example.baseproject.di

import com.example.baseproject.api.AppApi
import com.example.baseproject.app.AppConstant
import com.example.baseproject.utils.PrefUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class APIServiceModule {

    @Provides
    fun provideAPIService(prefsUtils: PrefUtils): AppApi {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        val gson = gsonBuilder.create()
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val token = prefsUtils.getAuthToken()
            val requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        httpClient.interceptors().add(Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response.code
            response
        })
        val okHttpClient = httpClient.addInterceptor(interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .cache(null).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
        return retrofit.build().create(AppApi::class.java)
    }
}