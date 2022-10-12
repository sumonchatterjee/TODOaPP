package com.example.todoapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todos(
    @NonNull
    @PrimaryKey
    val userId: Int,
    val id: Int,
    val title: String?,
    val completed: Boolean
)
