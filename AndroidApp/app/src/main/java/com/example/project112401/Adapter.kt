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
                .setTitle("登入")
                .setMessage("要用這個身分登入嗎?")
                .setNegativeButton("Yes"){dialog, which ->
                    //傳送該使用者的名稱回SecActivity
                    //設定成為已登入的user, loggedInUser為global variable
                    loggedInUser.setName(holder.userName.text.toString())
                    loggedInUser.setEmail(holder.userEmail.text.toString())
                    loggedInUser.setPassword(holder.userName.text.toString())
                    loggedInUser.setStatusToLogin()
                    //預計開發功能 : 自動跳轉回主畫面
                }
                .setNeutralButton("No"){dialog, which -> }
                .show()
        }

        holder.deleteBtn.setOnClickListener{
            AlertDialog.Builder(holder.deleteBtn.context)
                .setTitle("刪除使用者")
                .setMessage("確定要刪除這位使用者嗎?")
                .setNegativeButton("Yes"){dialog, which ->
                    newUserList.removeAt(position)
                    //刪除Users裡的資料(即使用者物件)
                    //Users.removeUser(position, holder.userName.text.toString())
                    Users.list.removeAt(position)
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