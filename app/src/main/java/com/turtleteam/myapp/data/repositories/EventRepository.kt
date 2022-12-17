package com.turtleteam.myapp.data.repositories

import com.turtleteam.myapp.data.api.EventService
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.wrapper.Result
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.wrapper.NetworkResultWrapper

import javax.inject.Inject

class EventRepository @Inject constructor(private val apiService: EventService) {

    suspend fun getAllEvent(): Result<List<Events>> = NetworkResultWrapper.wrapWithResult {apiService.getAllEvents()}

    suspend fun getEvent(id: Int) = println()

    suspend fun createEvent(eventModel: EventRequestBody, token: String): Result<Throwable> = NetworkResultWrapper.wrapWithResult {apiService.createEvent(eventModel = eventModel, token = token)}

    suspend fun deleteEvent(id: Int) = apiService.deleteEvent(id = id)
}