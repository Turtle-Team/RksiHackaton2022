package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.EventService

import com.turtleteam.myapp.data.model.event.Events

import javax.inject.Inject

class EventRepository @Inject constructor(private val apiService: EventService) {

    suspend fun getAllEvent(): List<Events> = apiService.getAllEvents()

    suspend fun getEvent(id: Int) = println()

    suspend fun createEvent(eventModel: String) = apiService.createEvent(eventModel = eventModel)

    suspend fun deleteEvent(id: Int) = apiService.deleteEvent(id = id)
}