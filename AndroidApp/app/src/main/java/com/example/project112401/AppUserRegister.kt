package com.example.project112401

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AppUserRegister : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_user_register)

        val userName = findViewById<TextView>(R.id.appUserRegister_Name)  //使用者輸入的名字
        val userEmail = findViewById<TextView>(R.id.appUserRegister_Email)  //使用者輸入的Email
        val userPassword = findViewById<TextView>(R.id.appUserRegister_Password)  //使用者輸入的密碼
        val checkPassword = findViewById<TextView>(R.id.appUserRegister_CheckPW)  //重複確認使用者輸入的密碼

        val registerBtn =  findViewById<Button>(R.id.button_toRegister)  //註冊按鈕
        val backBtn = findViewById<Button>(R.id.button_back)  //取消按鈕

        registerBtn.setOnClickListener {

        }

        backBtn.setOnClickListener {
            val intentToMainList = Intent(this, SecActivity::class.java)
            startActivity(intentToMainList)
        }
    }
}