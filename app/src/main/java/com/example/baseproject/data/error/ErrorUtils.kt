package com.example.baseproject.data.error

import com.example.baseproject.R
import com.example.baseproject.utils.asString
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ErrorUtils {

    fun handleError(e: Exception?, key: String?): ErrorBean {
        return if (e != null) {
            when (e) {
                is IllegalStateException -> {
                    ErrorBean(Error.SERVER_ERROR, e.localizedMessage, key)
                }

                is SocketTimeoutException -> {
                    ErrorBean(
                        Error.SERVER_ERROR,
                        R.string.connection_timeout.asString(),
                        key
                    )
                }

                is ConnectException, is UnknownHostException -> {
                    ErrorBean(
                        Error.INTERNET_ERROR,
                        R.string.no_internet.asString(),
                        key
                    )
                }

                is JsonParseException -> {
                    ErrorBean(
                        Error.SERVER_ERROR,
                        R.string.something_went_wrong.asString(),
                        key
                    )
                }

                else -> {
                    handleMapError(e.message ?: "")
                }
            }

        } else ErrorBean(
            Error.UNKNOWN_ERROR,
            R.string.unknown_error.asString(),
            key
        )

    }

    private fun handleMapError(errorString: String): ErrorBean {
        try {
            val yourHashMap =
                Gson().fromJson(errorString, HashMap::class.java)

            try {
                if (yourHashMap != null) {
                    val keys: MutableSet<out Any> = yourHashMap.keys
                    for (key in keys) {
                        if (yourHashMap[key] != null && yourHashMap[key].toString().isNotEmpty()) {
                            try {
                                val messageList: ArrayList<String> = Gson().fromJson(
                                    Gson().toJson(yourHashMap[key]),
                                    object : TypeToken<ArrayList<String>>() {}.type
                                )

                                return ErrorBean(
                                    Error.UNKNOWN_ERROR,
                                    messageList[0],
                                    key.toString()
                                )

                            } catch (exception: Exception) {
                                val messageList: HashMap<*, *> = Gson().fromJson(
                                    Gson().toJson(yourHashMap["error"]),
                                    object : TypeToken<HashMap<*, *>>() {}.type
                                )

                                return ErrorBean(
                                    Error.UNKNOWN_ERROR,
                                    messageList[0].toString(),
                                    key.toString()
                                )
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                return ErrorBean(
                    Error.UNKNOWN_ERROR,
                    R.string.something_went_wrong.asString(),
                    ""
                )
            }
        } catch (exception: Exception) {
            return ErrorBean(Error.UNKNOWN_ERROR, errorString, "")
        }
        return ErrorBean(
            Error.UNKNOWN_ERROR,
            R.string.something_went_wrong.asString(),
            ""
        )
    }

}