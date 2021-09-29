package com.retrofitcoroutines.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.retrofitcoroutines.example.model.Profiles
import com.retrofitcoroutines.example.model.User
import com.retrofitcoroutines.example.remote.UserService
import kotlinx.coroutines.*

class MyViewModel : ViewModel() {
    private val userService = UserService().getUsersService()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val usersLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }
    private val profiles: MutableLiveData<List<Profiles>> by lazy() {
        MutableLiveData<List<Profiles>>().also {
            loadProfiles()
        }
    }

    fun refresh() {
        loadUsers()
    }

    fun refreshProfile() {
        loadProfiles()
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    fun getProfiles(): LiveData<List<Profiles>> {
        return profiles
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

    private fun loadProfiles() {
        // Do an asynchronous operation to fetch users.
        val parameter = JsonObject()
        parameter.addProperty("user_id", "97897")
        parameter.addProperty(
            "user_looking_for",
            "Male"
        )

        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = userService.getProfiles(parameter)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    profiles.value = response.body()?.activeUser
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
