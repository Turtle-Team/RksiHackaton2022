package com.turtleteam.myapp.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.model.profile.Profile
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import com.turtleteam.myapp.data.repositories.AuthRepository
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {

    private val _user = MutableLiveData<Result<AuthRequestBody>>()
    var user: LiveData<Result<AuthRequestBody>> = _user

    fun getUser(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(repository.getUser(token))
    }
}