package com.example.todoapp.todaoTest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todoapp.data.local.AppDatabase
import com.example.todoapp.data.local.TODODao
import com.example.todoapp.model.Todos
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TodoDaoTest {

    private lateinit var itemDao: TODODao
    private lateinit var itemDb: AppDatabase

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        itemDb = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        itemDao = itemDb.todoDao()
    }

    @After
    fun cleanup() {
        itemDb.close()
    }


    @Test
    fun addItem_shouldReturn_All_theItems() {
        var todoList: MutableList<Todos> = mutableListOf()
        val item1 = Todos(userId = 1, id = 2, title = "item 1", completed = true)
        val item2 = Todos(userId = 2, id = 3, title = "item 3", completed = true)
        todoList.add(item1)
        todoList.add(item2)

        itemDao.insertAll(todoList)

        itemDao.getAll()?.get(0)?.let {
            assert(it.title == "item 1")
        }

    }


    @Test
    fun delete_shouldReturn_EmptyList() {
        var todoList: MutableList<Todos> = mutableListOf()
        val item1 = Todos(userId = 1, id = 2, title = "item 1", completed = true)
        val item2 = Todos(userId = 2, id = 3, title = "item 3", completed = true)
        todoList.add(item1)
        todoList.add(item2)

        itemDao.insertAll(todoList)

        itemDao.deleteAll(todoList)

        itemDao.getAll()?.isEmpty()?.let { assert(it) }


    }
}