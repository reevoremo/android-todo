package fr.epita.android.todo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {

    @GET ("todos")
    fun listToDos(): Call<List<ToDoObject>>

}