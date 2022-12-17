package com.turtleteam.myapp.ui.fragments.create_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.EventRequestBody
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(private val repository: EventRepository) : ViewModel(){

    private val _result = MutableLiveData<Result<Throwable>>()
    val result: LiveData<Result<Throwable>> = _result

    fun createEvent(eventModel: EventRequestBody, token: String) = viewModelScope.launch(Dispatchers.IO) {
        _result.postValue(repository.createEvent(eventModel,token))
    }
}