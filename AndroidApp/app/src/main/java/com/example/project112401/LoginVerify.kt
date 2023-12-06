package com.example.project112401

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginVerify : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_verify)

        val userEmail = findViewById<EditText>(R.id.login_email)  //給使用者輸入Email登入
        val userPassword = findViewById<EditText>(R.id.login_password)  //輸入的密碼

        val confirmBtn = findViewById<Button>(R.id.login_confirm)  //確認登入按鈕
        val backBtn = findViewById<Button>(R.id.login_backBtn)  //返回按鈕
        val regBtn = findViewById<Button>(R.id.login_regBtn)  //註冊按鈕
        val warningMsg = findViewById<TextView>(R.id.login_warningText)  //警告訊息

        backBtn.setOnClickListener {
            finish()
        }

        regBtn.setOnClickListener {
            val intentToRegister = Intent(this, AppUserRegister::class.java)
            startActivity(intentToRegister)
        }
    }
}