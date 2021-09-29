package com.retrofitcoroutines.example.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.retrofitcoroutines.example.databinding.ProfileItemRowBinding
import com.retrofitcoroutines.example.model.Profiles
import com.retrofitcoroutines.example.utils.loadImage


class ProfilesAdapter(context: Context) :
    RecyclerView.Adapter<ProfilesAdapter.ViewHolder>() {
    private val users = arrayListOf<Profiles>()

    fun updateCountries(newUsers: List<Profiles>) {
        users.clear()
        users.addAll(newUsers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        /* val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
         return ViewHolder(v)*/
        val binding =
            ProfileItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemNameAndAge.text =
            users[position].user_first_name + " " + users[position].age_count
        holder.binding.itemCity.text = users[position].user_location + " " + users.size
        holder.binding.itemImage.loadImage(users[position].user_profile_image)

        //holder.bind(users[position])
    }

    class ViewHolder(val binding: ProfileItemRowBinding) : RecyclerView.ViewHolder(binding.root)
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



