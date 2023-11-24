package com.example.project112401

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class SecActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var loginBtn : Button  //登入用的button

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
        loginBtn =  findViewById(R.id.button_login)  //登入用的button

        val joinBtn =  findViewById<Button>(R.id.button_joinTheRoom)  //加入房間用的button
        val reg_log_status = findViewById<TextView>(R.id.reg_log_status)  //顯示登入或註冊的狀態

        val userInfo = findViewById<TextView>(R.id.userInfo)
        if(loggedInUser.getName() != ""){  //如果有任何人登入, 則顯示目前使用者的名字
            userInfo.text = "Welcome back, " + loggedInUser.getName()
            loginBtn.text = "Logout"
        }

        //設定按下按鈕後會觸發的動作
        returnBtn.setOnClickListener{
            intent2.putExtra("LastTime_fromSec", testTextView.text)  //設定傳送回去的資料
            intent2.putExtra("CurrentUser", loggedInUser.getName())
            setResult(RESULT_OK,intent2)  //回傳表示執行成功的結果
            finish()
        }

        registerBtn.setOnClickListener {
            val intentToRegister = Intent(this, AppUserRegister::class.java)
            startActivityForResult(intentToRegister, 112401002)
        }

        loginBtn.setOnClickListener {
            if(loggedInUser.getName() != "" && loggedInUser.checkStatus()){
                AlertDialog.Builder(loginBtn.context)
                    .setTitle("Logout")
                    .setMessage("Do you really want to logout?")
                    .setNegativeButton("Yes"){dialog, which ->
                        loggedInUser.LogOut()
                        this.recreate()
                    }
                    .setNeutralButton("No"){dialog, which -> }
                    .show()
            }
            else{
                val intentToLogin = Intent(this, LoginList::class.java)
                startActivityForResult(intentToLogin, 112401003)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val userInfo = findViewById<TextView>(R.id.userInfo)
        val reg_log_status = findViewById<TextView>(R.id.reg_log_status)  //顯示登入或註冊的狀態
        //--------判定回傳的request code--------
        if(requestCode == 112401002){
            //requestCode傳回來時,顯示user資訊的物件根據resultCode的不同來顯示不同資訊
            if(resultCode == RESULT_OK){
                reg_log_status.text = "Register succeeded"
            }
            else if(resultCode == RESULT_CANCELED){
                reg_log_status.text = "Register canceled"
            }
            else{
                reg_log_status.text = "ERROR"
            }
        }
        else if(requestCode == 112401003){
            if(resultCode == RESULT_OK){
                userInfo.text = "Welcome back, " + loggedInUser.getName()
                loginBtn.text = "Logout"
            }
            else if(resultCode == RESULT_CANCELED){
                if(!loggedInUser.checkStatus()){
                    reg_log_status.text = "Login canceled"
                }
            }
            else{
                reg_log_status.text = "ERROR"
            }
        }
    }
}