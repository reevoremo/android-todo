package fr.epita.android.todo

data class ToDoObject (
    val userId : Int,
    val id : Int,
    val title : String,
    val completed : Boolean)