package com.example.todoapp.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoItemsBinding
import com.example.todoapp.model.Todos

class TodoListAdapter( private val list: ArrayList<Todos>) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>()  {

    var binding:TodoItemsBinding? = null

    class TodoViewHolder(private val binding: TodoItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todos) {
            binding.title.text = todo.title
            binding.status.text = "Completed - ${todo.completed}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    fun updateData(newList: List<Todos>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}