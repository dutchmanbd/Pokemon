package com.ticonsys.pokemon.data.remote.responses

import retrofit2.Response

const val UNKNOWN_ERROR = "Unknown error occurred"
const val UNKNOWN_CODE = 4444

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: String?, code: Int = UNKNOWN_CODE): ApiErrorResponse<T> {
            return ApiErrorResponse(error ?: UNKNOWN_ERROR, code)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {

            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse(UNKNOWN_ERROR, response.code())
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val code = response.code()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    UNKNOWN_ERROR
                }
                ApiErrorResponse(errorMsg, code)
            }
        }
    }

}


class ApiEmptyResponse<T>(val errorMessage: String, val code: Int) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String, val code: Int) : ApiResponse<T>()