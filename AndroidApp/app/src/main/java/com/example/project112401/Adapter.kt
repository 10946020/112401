package com.example.project112401

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Adapter是介於後端資料跟前端顯示之間溝通的角色
//這裡的前端是user_account_appearance.xml
//後端資料是NewAddedUser.kt的

class Adapter(private val newUserList : ArrayList<NewAddedUser>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //位於user_account_appearance.xml的物件
        val userName : TextView = itemView.findViewById(R.id.userList_userName)
        val userEmail : TextView = itemView.findViewById(R.id.userList_userEmail)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_account_appearance, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newUserList[position]
        holder.userName.text = currentItem.name
        holder.userEmail.text = currentItem.email
    }
    override fun getItemCount(): Int {
        return newUserList.size
    }
}