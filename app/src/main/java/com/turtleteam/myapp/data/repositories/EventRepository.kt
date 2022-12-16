package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.ApiService
import com.turtleteam.myapp.data.api.EventService

import com.turtleteam.myapp.data.model.event.Events

import javax.inject.Inject

class EventRepository @Inject constructor(private val apiService: EventService) {

    suspend fun getAllEvent(): List<Events> = apiService.getAllEvents()

    suspend fun getEvent(id: Int) = println()
}