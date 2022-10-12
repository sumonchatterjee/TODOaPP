package com.example.todoapp.data.local

import androidx.room.*
import com.example.todoapp.model.Todos

@Dao
interface TODODao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todos: List<Todos>)

    @Query("SELECT * FROM todos")
    fun getAll(): List<Todos>?

    @Delete
    fun delete(todo: Todos)

    @Delete
    fun deleteAll(todos: List<Todos>)
}