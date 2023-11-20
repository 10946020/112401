package com.example.project112401

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoginList : AppCompatActivity() {
    private lateinit var userList : RecyclerView
    private val userData = ArrayList<NewAddedUser>()  //NewAddedUser.kt的data class
    lateinit var userName : Array<String>
    lateinit var userEmail : Array<String>
    lateinit var userPassword : Array<String>

    lateinit var count : TextView  //顯示user數量的TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_list)

        count = findViewById(R.id.data)
        userList = findViewById(R.id.added_userList)  //RecyclerView, 用來顯示Adapter抓來的每筆資料

        //這邊預定要開發的功能 : 讀取Database的所有使用者
        getData()

        //userList.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        userList.layoutManager = linearLayoutManager

        val backBtn = findViewById<Button>(R.id.userList_back)  //藍色的回到上一頁按鈕
        val registerBtn = findViewById<Button>(R.id.userList_register)  //綠色的註冊按鈕

        backBtn.setOnClickListener {
            //判斷全域變數class中已登入的user名稱是否為空
            if(loggedInUser.getName() != ""){
                val toast = Toast.makeText(this, "Logged in as " + loggedInUser.getName(), Toast.LENGTH_SHORT)
                    toast.show()
                setResult(RESULT_OK)
                finish()
            }
            else if(loggedInUser.getName() == ""){
                setResult(RESULT_CANCELED)
                finish()
            }
        }

        registerBtn.setOnClickListener {
            val intentToRegister = Intent(this, AppUserRegister::class.java)
            startActivity(intentToRegister)
        }
    }

    private fun getData(){  //輸入假資料
        val all_userList = GlobalVariables.User_Data().list  //全域變數的user data list
        for(user in all_userList){
            userData.add(user)
        }
        //刷新頁面
        userList.adapter = Adapter(userData)  //調用Adapter.kt的class
        count.text = userData.size.toString()  //顯示目前有多少user
    }
}