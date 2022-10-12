package com.example.todoapp


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todoapp.data.TODORepositoryImpl
import com.example.todoapp.data.local.TODODao
import com.example.todoapp.data.remote.TODORemoteDataSource
import com.example.todoapp.util.MockResponseFileReader
import com.google.gson.GsonBuilder
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection


@RunWith(JUnit4::class)
class GetTodoResponseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val server = MockWebServer()

    private lateinit var repository: TODORepositoryImpl
    private lateinit var mockedResponse: String
    private lateinit var todoDao: TODODao

    private val gson = GsonBuilder()
        .setLenient()
        .create()


    @Before
    fun init() {
        server.start(8000)
        var BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        todoDao = Mockito.mock(TODODao::class.java)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
        val todoRemoteDataSource = TODORemoteDataSource(retrofit)
        repository = TODORepositoryImpl(todoRemoteDataSource, todoDao)
    }


    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("success.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch details and check response Code 200 returned`() = runTest {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success.json").content)
        server.enqueue(response)
        // Act
        val flow = runBlocking {repository.fetchTODOList()}
        launch {
            flow.collect {
                //it?.data?.isEmpty()?.let { it1 -> Assert.assertTrue(it1) }
               // assertEquals(response.toString().contains("200"), it.toString().contains("200"))

            }
        }

    }


    @Test
    fun `test if response is not null`() {
        mockedResponse = MockResponseFileReader("success.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { repository.fetchTODOList() }
        val json = gson.toJson(response)
        Assert.assertNotNull(response)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check if 400 response results in an empty list`() = runTest {
        val response = MockResponse()
            .setBody("The client messed this up")
            .setResponseCode(400)

        server.enqueue(response)

        val flow = repository.fetchTODOList()
        launch {
            flow.collect {
                it?.data?.isEmpty()?.let { it1 -> Assert.assertTrue(it1) }
            }
        }
    }

/*
    @Test
    fun `check if response is success`()= runTest {
        mockedResponse = MockResponseFileReader("success.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val flow = repository.fetchTODOList()
        launch {
            flow.collect {
                assertTrue(it!!.data?.get(0)?.title == "delectus aut autem")
            }
        }

    }*/


    @After
    fun tearDown() {
        server.shutdown()
    }
}