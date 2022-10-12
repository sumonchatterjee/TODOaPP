package com.example.todoapp.util

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {
    val content: String
    init {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}


inline fun <reified T> loadFileText(
    caller: T,
    filePath: String
): String =
    T::class.java.getResource(filePath)?.readText() ?: throw IllegalArgumentException(
        "Could not find file $filePath. Make sure to put it in the correct resources folder for $caller's runtime."
    )