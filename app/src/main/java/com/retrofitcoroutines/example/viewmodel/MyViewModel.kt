package com.retrofitcoroutines.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.retrofitcoroutines.example.model.User
import com.retrofitcoroutines.example.remote.UserService
import kotlinx.coroutines.*

class MyViewModel : ViewModel() {
    private val userService = UserService().getUsersService()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val usersLoadError = MutableLiveData<String?>()
    private val loading = MutableLiveData<Boolean>()

    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = userService.getUsers()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    users.value = response.body()?.data
                    usersLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        usersLoadError.value = ""
        loading.value = false

    }

    private fun onError(message: String) {
        usersLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
