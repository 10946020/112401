package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoginList : AppCompatActivity() {
    private lateinit var userData: ArrayList<NewAddedUser>  //NewAddedUser.kt的data class
    lateinit var userName : Array<String>
    lateinit var userEmail : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_list)

        userData.add(NewAddedUser("Zir", "114514@", "1919810"))

        val userList = findViewById<RecyclerView>(R.id.added_userList)  //RecyclerView, 用來顯示Adapter抓來的每筆資料
        userList.adapter = Adapter(userData)  //Adapter.kt的class

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        userList.layoutManager = linearLayoutManager
        //userList.setHasFixedSize(true)

        val backBtn = findViewById<Button>(R.id.userList_back)  //藍色的回到上一頁按鈕
        val registerBtn = findViewById<Button>(R.id.userList_register)  //綠色的註冊按鈕

        backBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}