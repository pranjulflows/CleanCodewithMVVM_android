package com.example.baseproject.data.resource

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.baseproject.R
import com.example.baseproject.data.error.ErrorBean
import com.example.baseproject.data.error.ErrorUtils
import com.example.baseproject.data.error.EmptyResponseBodyException
import com.example.baseproject.data.error.UnauthorizedException
import com.example.baseproject.data.error.ValidationException
import com.example.baseproject.app.AppConstant
import com.example.baseproject.app.MyApp
import com.example.baseproject.utils.asString
import org.json.JSONObject
import retrofit2.Response

class ResponseHandler(private val errorUtils: ErrorUtils) {

    fun <T : Any> handleResponse(
        response: Response<T>, isAutoHandleError: Boolean = true
    ): Resource<T> {
        if (response.isSuccessful) {
            val responseBody = response.body()
            return if (response.code() == 204 || response.code() == 201) {
                Resource.success(responseBody)
            } else {
                if (responseBody != null) {
                    Resource.success(responseBody)
                } else {
                    Resource.error(errorUtils.handleError(EmptyResponseBodyException(), ""))
                }
            }

        } else if (response.code() == 400) {
            val errorBody = response.errorBody()
            val error = JSONObject(errorBody?.string() ?: "").toString()
            return if (errorBody != null) {
                if (isAutoHandleError) {
                    Resource.error(errorUtils.handleError(ValidationException(error), ""))
                } else {
                    Resource.error(ErrorBean(errorMessage = error))
                }
            } else {
                Resource.error(errorUtils.handleError(null, ""))
            }

        } else if (response.code() == 401 || response.code() == 400 || response.code() == 403 || response.code() == 404 || response.code() == 423) {
            val errorBody = response.errorBody()
            val body: ErrorBean = if (errorBody != null) {
                val jsonObject = JSONObject(errorBody.string())
                if (jsonObject.has("detail")) errorUtils.handleError(
                    UnauthorizedException(jsonObject.getString("detail")), ""
                )
                else if (jsonObject.has("non_field_errors")) {
                    errorUtils.handleError(
                        UnauthorizedException(jsonObject.getString("non_field_errors")), ""
                    )
                } else {
                    errorUtils.handleError(null, "")
                }
            } else {
                errorUtils.handleError(null, "")
            }

            return if (response.code() == 401) {
                val intent = Intent(AppConstant.SESSION_BROADCAST) //action: "msg"
                intent.putExtra(AppConstant.DATA_TYPE, body.errorMessage)
                LocalBroadcastManager.getInstance(MyApp.getAppInstance()).sendBroadcast(intent)
                Resource.error(body)
            } else if (response.code() == 404) {
                body.errorCode = 404
                Resource.error(body)
            } else {
                Resource.error(body)
            }


        } else if (response.code() == 500) {
            return Resource.error(
                errorUtils.handleError(
                    ValidationException(R.string.something_went_wrong.asString()), ""
                )
            )
        } else {
            return Resource.error(errorUtils.handleError(null, ""))
        }

    }

    fun <T : Any> handleException(e: Exception?): Resource<T> {
        return Resource.error(errorUtils.handleError(e, ""))
    }
}
