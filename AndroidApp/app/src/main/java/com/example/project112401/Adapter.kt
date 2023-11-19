package com.example.project112401

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

//Adapter是介於資料跟顯示介面之間溝通的角色
//這裡的顯示介面是user_account_appearance.xml
//資料來自NewAddedUser.kt

class Adapter(private val newUserList : ArrayList<NewAddedUser>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //定義位於user_account_appearance.xml的物件
        val icon : ImageView = itemView.findViewById(R.id.userList_icon)
        val userName : TextView = itemView.findViewById(R.id.userList_userName)
        val userEmail : TextView = itemView.findViewById(R.id.userList_userEmail)
        val deleteBtn : ImageView = itemView.findViewById(R.id.userList_delete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //啟用顯示物件
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_account_appearance, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //以position來逐一顯示資料
        val currentItem = newUserList[position]
        holder.userName.text = currentItem.name
        holder.userEmail.text = currentItem.email

        holder.icon.setOnClickListener{
            AlertDialog.Builder(holder.icon.context)
                .setTitle("Login")
                .setMessage("Login as this user?")
                .setNegativeButton("Yes"){dialog, which ->
                    //傳送該使用者的名稱回SecActivity
                    //設定成為已登入的user, loggedInUser為global variable
                    loggedInUser.setName(holder.userName.text.toString())
                        //.setEmail(holder.userEmail.text.toString())
                        //.setPassword(holder.userName.text.toString())
                }
                .setNeutralButton("No"){dialog, which -> }
                .show()
        }

        holder.deleteBtn.setOnClickListener{
            AlertDialog.Builder(holder.deleteBtn.context)
                .setTitle("Delete the user")
                .setMessage("Do you really want to delete this user?")
                .setNegativeButton("Yes"){dialog, which ->
                    newUserList.removeAt(position)
                    this.notifyDataSetChanged()
                }
                .setNeutralButton("No"){dialog, which -> }
                .show()
        }
    }
    override fun getItemCount(): Int {
        return newUserList.size
    }
}