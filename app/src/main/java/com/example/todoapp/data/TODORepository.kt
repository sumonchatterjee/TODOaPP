package com.example.todoapp.data

import com.example.todoapp.model.Result
import com.example.todoapp.model.Todos
import kotlinx.coroutines.flow.Flow

interface TODORepository{
    fun fetchTODOList():Flow<Result<List<Todos>?>?>
}

