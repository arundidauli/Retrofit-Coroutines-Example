# Retrofit-Coroutines-Example
Retrofit Coroutines Example By Arun Android

# ViewModel Class

````
    private val userService = UserService().getUsersService()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val users = MutableLiveData<List<User>>()
    val usersLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchUsers()
    }

    private fun fetchUsers() {
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

````

#Main Activity

````
private fun observeViewModel() {
        viewModel.users.observe(this, Observer {countries ->
            countries?.let {
                usersList.visibility = View.VISIBLE
                userListAdapter.updateCountries(it) }
        })

        viewModel.usersLoadError.observe(this, Observer { isError ->
            listError.visibility = if(isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listError.visibility = View.GONE
                    usersList.visibility = View.GONE
                }
            }
        })
    }
    
````
# dependencies

```
  // Retrofit dependency
    implementation 'com.squareup.retrofit2:retrofit:2.7.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.0'
    // Lifecycle dependency
    implementation 'android.arch.lifecycle:extensions:1.1.1'
    // Coroutines dependency
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
    
```
