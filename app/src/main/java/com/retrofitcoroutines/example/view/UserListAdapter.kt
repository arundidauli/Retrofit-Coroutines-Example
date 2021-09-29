package com.retrofitcoroutines.example.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.retrofitcoroutines.example.databinding.ItemUserBinding
import com.retrofitcoroutines.example.model.User
import com.retrofitcoroutines.example.utils.loadImage


class UserListAdapter(private var users: List<User>) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    fun updateCountries(newUsers: List<User>) {
        users.toMutableList().clear()
        users.toMutableList().addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.userFullName.text =
            users[position].first_name + " " + users[position].last_name
        holder.binding.userEmail.text = users[position].email
        holder.binding.userAvatar.loadImage(users[position].avatar)
    }

    class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
/*
    class UserViewHolder(view: @NonNull ItemUserBinding): RecyclerView.ViewHolder(view) {

        private val imageView = view.userAvatar
        private val userName = view.userFullName
        private val userEmail = view.userEmail


        fun bind(user: User) {
            userName.text = user.first_name + " "+ user.last_name
            userEmail.text = user.email
            imageView.loadImage(user.avatar)
        }
    }*/
}



