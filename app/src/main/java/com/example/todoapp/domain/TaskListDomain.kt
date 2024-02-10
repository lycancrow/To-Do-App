package com.example.todoapp.domain

data class TaskListDomain (
    val id: Int?,
    val task: String,
    val date: String,
    val personalOrWork:String
)