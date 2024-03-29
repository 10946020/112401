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
        /*
        val testTextView = findViewById<TextView>(R.id.test).apply {
            text =   //從主介面傳送來的時間資料
        }
        */
        //val lastTime = intent.getStringExtra("LastTime")
        val userInfo = findViewById<TextView>(R.id.userInfo).apply {
            if(!loggedInUser.checkStatus()){
                text = "尚未登入使用者"
            }
        }

        //-----------------------------------------------------------------------------------------

        //------回主介面相關--------------------------------------------------------------------------
        val returnBtn = findViewById<Button>(R.id.returnBtn1)  //回主介面的按鈕
        val intentBackToMain = Intent(this, MainActivity::class.java)  //回主介面用的intent

        returnBtn.setOnClickListener{
            intentBackToMain.putExtra("LastTime_fromSec", enterTime.getTime())  //設定傳送回去的資料
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
                    .setTitle("登出")
                    .setMessage("確定要登出嗎?")
                    .setNegativeButton("Yes"){dialog, which ->
                        loggedInUser.LogOut()
                        this.recreate()
                    }
                    .setNeutralButton("No"){dialog, which -> }
                    .show()
            }
            else{
                val intentToLogin = Intent(this, LoginVerify::class.java)
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
                val intentToRoom = Intent(this, RoomStatus::class.java).apply {
                    this.putExtra("CurrentUser", loggedInUser.getName())  //傳送當前登入的user的名稱至房間介面, 以達成頁面自動刷新
                }
                startActivityForResult(intentToRoom, 112401004)
            }
        }
        //-----------------------------------------------------------------------------------------

        //------還是TextView相關, 只是這段如果寫在太前面會發生莫名bug導致App當掉,這邊不要動-------------------
        if(loggedInUser.getName() != ""){  //如果有任何人登入, 則顯示目前使用者的名字
            userInfo.text = "歡迎回來, " + loggedInUser.getName()
            loginBtn.text = "登出"
        }
        //-----------------------------------------------------------------------------------------
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val userInfo = findViewById<TextView>(R.id.userInfo)
        //--------判定回傳的request code--------
        if(requestCode == 112401002){
            //requestCode傳回來時,顯示user資訊的物件根據resultCode的不同來顯示不同資訊
            if(resultCode == RESULT_OK){  //002:註冊

            }
        }
        else if(requestCode == 112401003){  //003:登入
            if(resultCode == RESULT_OK){
                userInfo.text = "歡迎回來, " + loggedInUser.getName()
                loginBtn.text = "登出"
            }
        }
        else if(requestCode == 112401004){  //004:房間相關資訊
            if(resultCode == RESULT_OK){

            }
            else if(resultCode == RESULT_CANCELED){

            }
        }
    }
}