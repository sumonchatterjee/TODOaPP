package com.example.todoapp.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoFragmentBinding
import com.example.todoapp.model.Result
import com.example.todoapp.model.Todos
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoFragment: Fragment() {

    private val list = ArrayList<Todos>()
    private val viewModel by viewModels<TodoViewModel>()

    private lateinit var binding: TodoFragmentBinding
    private lateinit var todoAdapter: TodoListAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TodoFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribeUi()
    }

    private fun init(){
        val layoutManager = LinearLayoutManager(binding.recyclerview.context)
        binding.recyclerview.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerview.context,
            layoutManager.orientation
        )

        binding.recyclerview.addItemDecoration(dividerItemDecoration)
        todoAdapter = TodoListAdapter( list)
        binding.recyclerview.adapter = todoAdapter
    }

    private fun subscribeUi() {

            viewModel.todoList.observe(viewLifecycleOwner) { result ->

                when (result?.status) {
                    Result.Status.SUCCESS -> {
                        result.data?.let { list ->
                            todoAdapter.updateData(list)
                        }
                        binding.loading.visibility = View.GONE
                    }

                    Result.Status.ERROR -> {
                        result.message?.let {
                            showError(it)
                        }
                        binding.loading.visibility = View.GONE
                    }

                    Result.Status.LOADING -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                }

            }


    }

    private fun showError(msg: String) {
        Snackbar.make(requireActivity().findViewById(R.id.root_container), msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TodoFragment()
    }
}