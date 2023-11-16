package com.example.project112401

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SecActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        val returnBtn = findViewById<Button>(R.id.returnBtn1)  //回主介面的按鈕
        val intent2 = Intent(this, MainActivity::class.java)  //回主介面用的intent

        //(接收前一個頁面傳來的資料) 顯示儲存的最後一次辨識的時間
        val testTextView = findViewById<TextView>(R.id.test).apply {
            text = intent.getStringExtra("LastTime")  //從主介面傳送來的時間資料
        }

        val registerBtn =  findViewById<Button>(R.id.button_appUser)  //註冊帳號用的button
        val loginBtn =  findViewById<Button>(R.id.button_login)  //登入用的button
        val joinBtn =  findViewById<Button>(R.id.button_joinTheRoom)  //加入房間用的button

        //設定按下按鈕後會觸發的動作
        returnBtn.setOnClickListener{
            intent2.putExtra("LastTime_fromSec", testTextView.text)  //設定傳送回去的資料
            setResult(RESULT_OK,intent2)  //回傳表示執行成功的結果
            finish()
        }

        registerBtn.setOnClickListener {
            val intentToRegister = Intent(this, AppUserRegister::class.java)
            startActivityForResult(intentToRegister, 112401002)
        }

        loginBtn.setOnClickListener {
            val intentToLogin = Intent(this, LoginList::class.java)
            startActivityForResult(intentToLogin, 112401003)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val userInfo = findViewById<TextView>(R.id.userInfo)
        if(requestCode == 112401002){
            userInfo.text = //requestCode傳回來時,顯示user資訊的物件根據resultCode的不同來顯示不同資訊
                if(resultCode == RESULT_OK){
                    "Welcome, " + data?.getStringExtra("user_Name")
                }
                else if(resultCode == RESULT_CANCELED){
                    "Register canceled"
                }
                else{
                    "ERROR"
                }
        }
        else if(requestCode == 112401003){
            userInfo.text =
                if(resultCode == RESULT_OK){
                    "Welcome back, " + data?.getStringExtra("user_Name")
                }
                else if(resultCode == RESULT_CANCELED){
                    "Login canceled"
                }
                else{
                    "ERROR"
                }
        }
    }
}