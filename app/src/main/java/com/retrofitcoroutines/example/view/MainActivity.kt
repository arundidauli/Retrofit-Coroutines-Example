package com.retrofitcoroutines.example.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.retrofitcoroutines.example.databinding.ActivityMainBinding
import com.retrofitcoroutines.example.model.Profiles
import com.retrofitcoroutines.example.viewmodel.ListViewModel
import com.retrofitcoroutines.example.viewmodel.MyViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var profilesAdapter: ProfilesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userListAdapter = UserListAdapter(this)
        profilesAdapter = ProfilesAdapter(this)
        // viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        // viewModel.refresh()


        val parameter = JsonObject()
        parameter.addProperty("user_id", "97897")
        parameter.addProperty(
            "user_looking_for",
            "Male"
        )



        binding.usersList.layoutManager = LinearLayoutManager(this)


        val model: MyViewModel by viewModels()
        model.refreshProfile()


        model.getProfiles().observe(this, Observer<List<Profiles>> {
            // update UI
            binding.usersList.visibility = View.VISIBLE

            profilesAdapter.updateCountries(it)
            binding.usersList.adapter = profilesAdapter

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

        binding.fabAdd.setOnClickListener {
            model.refreshProfile()
        }
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
