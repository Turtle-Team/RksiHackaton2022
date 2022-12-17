package com.turtleteam.myapp.ui.fragments.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {

    private val _events = MutableLiveData<Result<List<Events>>>(Result.Loading)
    var events: LiveData<Result<List<Events>>> = _events

    private val _user = MutableLiveData<Response<AuthRequestBody>?>()
    var user: LiveData<Response<AuthRequestBody>?> = _user

    init {
        getAllEvents()
    }

    fun getUser(token: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _user.postValue(repository.getUser(token))
        } catch (e: Throwable) {
            _user.postValue(null)
        }
    }

    fun getAllEvents() = viewModelScope.launch(Dispatchers.IO) {
        _events.postValue(repository.getAllEvent())
    }

    fun deleteEvent(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        runCatching { repository.deleteEvent(id) }
    }
}