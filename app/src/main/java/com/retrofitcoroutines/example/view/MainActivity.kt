package com.retrofitcoroutines.example.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.retrofitcoroutines.example.databinding.ActivityMainBinding
import com.retrofitcoroutines.example.model.User
import com.retrofitcoroutines.example.viewmodel.ListViewModel
import com.retrofitcoroutines.example.viewmodel.MyViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userListAdapter = UserListAdapter(this)
        // viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        // viewModel.refresh()


        binding.usersList.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = userListAdapter
        }


        binding.usersList.layoutManager = LinearLayoutManager(this)


        val model: MyViewModel by viewModels()
        model.refresh()


        model.getUsers().observe(this, Observer<List<User>> {
            // update UI
            binding.usersList.visibility = View.VISIBLE

            userListAdapter.updateCountries(it)
            binding.usersList.adapter = userListAdapter

            Log.e(
                MainActivity::javaClass.name,
                "*************** viewModels Data" + Gson().toJson(it)
            )

        })

        model.usersLoadError.observe(this, Observer { isError ->
            binding.listError.visibility = if (isError == "") View.GONE else View.VISIBLE
        })

        model.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.usersList.visibility = View.GONE
                }
            }
        })

        // observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.users.observe(this, Observer { countries ->
            countries?.let {
                binding.usersList.visibility = View.VISIBLE
                // Log.e(MainActivity::javaClass.name,"*************** Data"+Gson().toJson(it))
                // userListAdapter.updateCountries(it)
            }
        })

        viewModel.usersLoadError.observe(this, Observer { isError ->
            binding.listError.visibility = if (isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.usersList.visibility = View.GONE
                }
            }
        })
    }
}
