package com.turtleteam.myapp.ui.fragments.auth.registerfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.users.AuthRequestBody
import com.turtleteam.myapp.data.model.users.UserId
import com.turtleteam.myapp.data.repositories.AuthRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _userId = MutableLiveData<com.turtleteam.myapp.data.wrapper.Result<UserId>>()
    var userId: LiveData<com.turtleteam.myapp.data.wrapper.Result<UserId>> = _userId

    fun register(userModel: AuthRequestBody) = viewModelScope.launch(Dispatchers.IO) {
        _userId.postValue(repository.register(userModel))
    }
}