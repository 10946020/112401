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

        //------TextView相關-------------------------------------------------------------------------
        //(接收前一個頁面傳來的資料) 顯示儲存的最後一次辨識的時間
        val testTextView = findViewById<TextView>(R.id.test).apply {
            text = intent.getStringExtra("LastTime")  //從主介面傳送來的時間資料
        }
        val userInfo = findViewById<TextView>(R.id.userInfo)
        //-----------------------------------------------------------------------------------------

        //------回主介面相關--------------------------------------------------------------------------
        val returnBtn = findViewById<Button>(R.id.returnBtn1)  //回主介面的按鈕
        val intentBackToMain = Intent(this, MainActivity::class.java)  //回主介面用的intent

        returnBtn.setOnClickListener{
            intentBackToMain.putExtra("LastTime_fromSec", testTextView.text)  //設定傳送回去的資料
            intentBackToMain.putExtra("CurrentUser", loggedInUser.getName())
            setResult(RESULT_OK,intentBackToMain)  //回傳表示執行成功的結果
            finish()
        }
        //-----------------------------------------------------------------------------------------

        //------註冊相關-----------------------------------------------------------------------------
        val registerBtn =  findViewById<Button>(R.id.button_appUser)  //註冊帳號用的button

        registerBtn.setOnClickListener {
            val intentToRegister = Intent(this, AppUserRegister::class.java)
            startActivityForResult(intentToRegister, 112401002)
        }
        //-----------------------------------------------------------------------------------------

        //------登入身分相關--------------------------------------------------------------------------
        loginBtn =  findViewById(R.id.button_login)  //登入用的button
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
        //-----------------------------------------------------------------------------------------

        //------加入房間相關--------------------------------------------------------------------------
        val joinBtn =  findViewById<Button>(R.id.button_joinTheRoom)  //加入房間用的button

        joinBtn.setOnClickListener {
            if(!loggedInUser.checkStatus()){  //如果沒登入則出現提示訊息
                AlertDialog.Builder(joinBtn.context)
                    .setTitle("尚未登入")
                    .setMessage("請先登入帳號才能加入或查看房間!")
                    .setNeutralButton("ok"){dialog, which ->}
                    .show()
            }
            else{
                val intentToRoom = Intent(this, RoomStatus::class.java)
                startActivityForResult(intentToRoom, 112401004)
            }
        }
        //-----------------------------------------------------------------------------------------

        //------還是TextView相關, 只是這段如果寫在太前面會發生莫名bug導致App當掉,這邊不要動-------------------
        if(loggedInUser.getName() != ""){  //如果有任何人登入, 則顯示目前使用者的名字
            userInfo.text = "Welcome back, " + loggedInUser.getName()
            loginBtn.text = "Logout"
        }
        //-----------------------------------------------------------------------------------------
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val userInfo = findViewById<TextView>(R.id.userInfo)
        val reg_log_status = findViewById<TextView>(R.id.reg_log_status)  //顯示登入或註冊的狀態
        //--------判定回傳的request code--------
        if(requestCode == 112401002){
            //requestCode傳回來時,顯示user資訊的物件根據resultCode的不同來顯示不同資訊
            if(resultCode == RESULT_OK){  //002:註冊
                reg_log_status.text = "Register succeeded"
            }
            else if(resultCode == RESULT_CANCELED){
                reg_log_status.text = "Register canceled"
            }
            else{
                reg_log_status.text = "REGISTER ERROR"
            }
        }
        else if(requestCode == 112401003){  //003:登入
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
                reg_log_status.text = "LOGIN ERROR"
            }
        }
        else if(requestCode == 112401004){  //004:房間相關資訊
            if(resultCode == RESULT_OK){

            }
            else if(resultCode == RESULT_CANCELED){

            }
            else{
                reg_log_status.text == "ROOM ERROR"
            }
        }
    }
}