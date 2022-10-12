package com.example.todoapp.network.services

import com.example.todoapp.model.Todos
import retrofit2.Response
import retrofit2.http.GET

interface TODOService {

    @GET("/todos")
    suspend fun getTODOList() : Response<List<Todos>?>

}