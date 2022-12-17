package com.turtleteam.myapp.ui.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {

    private val _events =
        MutableLiveData<Result<List<Events>>>(com.turtleteam.myapp.data.wrapper.Result.Loading)
    val events: LiveData<Result<List<Events>>> = _events

    init {
        getAllEvents()
    }

    fun getAllEvents() = viewModelScope.launch(Dispatchers.IO) {
        _events.postValue(com.turtleteam.myapp.data.wrapper.Result.Loading)
        _events.postValue(repository.getAllEvent())
    }

    fun deleteEvent(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteEvent(id)
    }


}