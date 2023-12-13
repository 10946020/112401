package com.example.project112401

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoomUserListAdapter(private val roomUserList : MutableList<NewAddedUser>) : RecyclerView.Adapter<RoomUserListAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //定義物件, 套用的檔案名稱 : room_user_list_appearance.xml
        val icon : ImageView = itemView.findViewById(R.id.roomUserList_icon)
        val userName : TextView = itemView.findViewById(R.id.roomUserList_userName)
        val userEmail : TextView = itemView.findViewById(R.id.roomUserList_userEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.room_user_list_appearance, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eachUserInTheRoom = roomUserList[position]  //每一個user
        holder.userName.text = eachUserInTheRoom.name
        holder.userEmail.text = eachUserInTheRoom.email
    }

    override fun getItemCount(): Int {
        return loggedInUser.getUserRoom()[0].usersInThere.size
    }
}