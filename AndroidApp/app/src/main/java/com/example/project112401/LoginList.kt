package com.example.project112401

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoginList : AppCompatActivity() {
    private lateinit var userList : RecyclerView
    private val userData = ArrayList<NewAddedUser>()  //NewAddedUser.kt的data class
    lateinit var userName : Array<String>
    lateinit var userEmail : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_list)

        userList = findViewById(R.id.added_userList)  //RecyclerView, 用來顯示Adapter抓來的每筆資料
        userData.add(NewAddedUser("Zir", "114514", "1919810"))
        userList.adapter = Adapter(userData)  //Adapter.kt的class

        userList.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        userList.layoutManager = linearLayoutManager

        val backBtn = findViewById<Button>(R.id.userList_back)  //藍色的回到上一頁按鈕
        val registerBtn = findViewById<Button>(R.id.userList_register)  //綠色的註冊按鈕

        backBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        registerBtn.setOnClickListener {
            val intentToRegister = Intent(this, AppUserRegister::class.java)
            startActivity(intentToRegister)
        }
    }
}