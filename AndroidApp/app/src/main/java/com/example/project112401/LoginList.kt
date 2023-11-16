package com.example.project112401

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        userData.add(NewAddedUser("Zir", "114514", "1919810"))

        //測試用的假資料
        userName = arrayOf("a","b","c")
        userEmail = arrayOf("a@123","b@456","c@789")
        userPassword = arrayOf("a","b","c")

        //這邊預定要開發的功能 : 讀取Database的所有使用者
        getData()

        //userList.setHasFixedSize(true)
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

    private fun getData(){  //輸入假資料
        for(i in userName.indices){
            val dataset = NewAddedUser(userName[i], userEmail[i], userPassword[i])
            userData.add(dataset)
        }
        //刷新頁面
        userList.adapter = Adapter(userData)  //調用Adapter.kt的class
        count.text = userData.size.toString()  //顯示目前有多少user
    }
}