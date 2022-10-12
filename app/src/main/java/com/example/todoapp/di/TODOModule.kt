package com.example.todoapp.di

import com.example.todoapp.data.TODORepository
import com.example.todoapp.data.TODORepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class) // Scope our dependencies
@Module
abstract class TODOModule {

    @Binds
    abstract fun getTodoSource(repo: TODORepositoryImpl): TODORepository
}