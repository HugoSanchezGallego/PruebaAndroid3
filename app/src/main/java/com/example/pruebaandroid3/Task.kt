package com.example.pruebaandroid3

data class Task(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val priority: Boolean = false,
    val cost: Double = 0.0,
    val isDone: Boolean = false
)