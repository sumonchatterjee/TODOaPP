package com.example.todoapp.data

import com.example.todoapp.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

import com.example.todoapp.data.local.TODODao
import com.example.todoapp.data.remote.TODORemoteDataSource
import com.example.todoapp.model.Todos
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn


class TODORepositoryImpl @Inject constructor(private val todoRemoteDataSource: TODORemoteDataSource,
                                             private val todoDao: TODODao) : TODORepository  {


    private fun fetchTodosCached(): Result<List<Todos>>? =
        todoDao.getAll()?.let {
            Result.success(it)
        }


    override fun fetchTODOList(): Flow<Result<List<Todos>?>?> {
        return flow {
            emit(fetchTodosCached())
            emit(Result.loading())
            val result = todoRemoteDataSource.fetchTODOList()

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { it ->
                    todoDao.deleteAll(it)
                    todoDao.insertAll(it)
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}