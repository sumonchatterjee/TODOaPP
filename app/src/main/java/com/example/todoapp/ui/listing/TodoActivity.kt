package com.example.todoapp.ui.listing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class TodoActivity:AppCompatActivity() {

    private lateinit var binding: TodoActivityBinding


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TodoActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, TodoFragment.newInstance())
            .addToBackStack("todoFragment")
            .commit()
    }
}