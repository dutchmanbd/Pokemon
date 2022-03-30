package com.ticonsys.pokemon.data.remote.middleware

import com.ticonsys.pokemon.data.remote.responses.ApiResponse
import org.json.JSONException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class SafeApiRequest {
    suspend fun <R : Any> apiRequest(call: suspend () -> Response<R>): ApiResponse<R> {
        return try {
            val response = call.invoke()
            ApiResponse.create(response)
        } catch (e: JSONException) {
            ApiResponse.create("Parsing error")
        } catch (e: IOException) {
            ApiResponse.create("Please check your internet")
        } catch (e: SocketTimeoutException) {
            ApiResponse.create("Please check your internet")
        } catch (e: Exception) {
            ApiResponse.create(e.message)
        }

    }
}