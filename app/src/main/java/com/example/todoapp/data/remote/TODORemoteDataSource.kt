package com.example.todoapp.data.remote

import com.example.todoapp.model.Result
import com.example.todoapp.model.Todos
import com.example.todoapp.network.services.TODOService
import com.example.todoapp.utils.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class TODORemoteDataSource @Inject constructor(private val retrofit: Retrofit){

    suspend fun fetchTODOList(): Result<List<Todos>?> {
        val todoService = retrofit.create(TODOService::class.java);
        return getResponse(
            request = { todoService.getTODOList() },
            defaultErrorMessage = "Error fetching todo list")

    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}