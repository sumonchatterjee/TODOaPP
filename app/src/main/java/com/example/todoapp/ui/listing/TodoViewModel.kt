package com.example.todoapp.ui.listing


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TODORepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.todoapp.model.Result
import com.example.todoapp.model.Todos

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository:TODORepository) :
    ViewModel() {

    private val _todoList = MutableLiveData<Result<List<Todos>?>?>()
    val todoList = _todoList

    init {
        fetchTodo()
    }

    private fun fetchTodo() {
        viewModelScope.launch {
            todoRepository.fetchTODOList().collect {
                _todoList.value = it
            }
        }
    }
}