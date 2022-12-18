package com.turtleteam.myapp.ui.fragments.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import com.turtleteam.myapp.data.preferences.UserPreferences
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: EventRepository,
    private val userPrefs: UserPreferences,
) : ViewModel() {

    private val _events = MutableLiveData<Result<List<Events>>>(Result.Loading)
    var events: LiveData<Result<List<Events>>> = _events

    private var _user = MutableLiveData<Result<AuthRequestBody>>(Result.Loading)
    var user: LiveData<Result<AuthRequestBody>> = _user

    init {
        userPrefs.setUserToken()?.let { getUser(it) }
        getAllEvents()
    }
    fun getUser(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(repository.getUser(token))
    }

    fun getAllEvents() = viewModelScope.launch(Dispatchers.IO) {
        _events.postValue(repository.getAllEvent())
    }

    fun deleteEvent(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        runCatching { repository.deleteEvent(id) }
    }
}