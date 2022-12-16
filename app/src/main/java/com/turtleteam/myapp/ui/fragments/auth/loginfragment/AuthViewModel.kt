package com.turtleteam.myapp.ui.fragments.auth.loginfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtleteam.myapp.data.model.users.UserId
import com.turtleteam.myapp.data.repositories.AuthRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _userId = MutableLiveData<Result<UserId>>()
    var userId: LiveData<Result<UserId>> = _userId

    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _userId.postValue(repository.login(email, password))
    }
}