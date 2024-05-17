package com.example.baseproject.di

import android.content.Context
import android.content.SharedPreferences
import com.example.baseproject.api.AppApi
import com.example.baseproject.app.AppConstant
import com.example.baseproject.data.error.ErrorUtils
import com.example.baseproject.data.resource.ResponseHandler
import com.example.baseproject.reposotory.APIRepository
import com.example.baseproject.utils.PrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@Module
@OptIn(ExperimentalCoroutinesApi::class)
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAPIRepository(api: AppApi, responseHandler: ResponseHandler, prefsUtils: PrefUtils): APIRepository {
        return APIRepository(api, responseHandler, prefsUtils)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstant.PREF_KEY, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providePrefUtils(preferences: SharedPreferences): PrefUtils {
        return PrefUtils(preferences)
    }

    @Singleton
    @Provides
    fun provideErrorUtils(): ErrorUtils {
        return ErrorUtils()
    }

    @Singleton
    @Provides
    fun provideResponseHandler(errorUtils: ErrorUtils): ResponseHandler {
        return ResponseHandler(errorUtils)
    }

}