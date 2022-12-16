package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.ApiService

import com.turtleteam.myapp.data.model.event.Events

import javax.inject.Inject

class EventRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllEvent(): List<Events> = listOf(
        Events(title = "Заголовок 1", description = "Описание 1", date = "20.12.2022"),
        Events(title = "Заголовок 2", description = "Описание 2", date = "22.12.2022")
    )

    suspend fun getEvent(id: Int) = println()
}