package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.GenreConverters
import com.example.todoapp.model.Todos


@Database(entities = [Todos::class], version = 1)
@TypeConverters(GenreConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoDao(): TODODao
}