package com.retrofitcoroutines.example.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.retrofitcoroutines.example.databinding.ItemUserBinding
import com.retrofitcoroutines.example.model.User
import com.retrofitcoroutines.example.utils.loadImage


class UserListAdapter(context: Context) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private val users = arrayListOf<User>()

    fun updateCountries(newUsers: List<User>) {
        users.clear()
        users.addAll(newUsers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        /* val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
         return ViewHolder(v)*/
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userFullName.text =
            users[position].first_name + " " + users[position].last_name
        holder.binding.userEmail.text = users[position].email
        holder.binding.userAvatar.loadImage(users[position].avatar)

        //holder.bind(users[position])
    }

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
    /*  class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

          private val imageView = view.findViewById<ImageView>(R.id.userAvatar)
          private val userName =view.findViewById<TextView>(R.id.userFullName)
          private val userEmail = view.findViewById<TextView>(R.id.userEmail)

          fun bind(user: User) {
              userName.text = user.first_name + " "+ user.last_name
              userEmail.text = user.email
              imageView.loadImage(user.avatar)
          }
      }*/
}



