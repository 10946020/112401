package com.example.project112401

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class AppUserRegister : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    //宣告給使用者輸入物件的type
    private lateinit var userName : EditText
    private lateinit var userEmail : EditText
    private lateinit var userPassword : EditText
    private lateinit var checkPassword : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_user_register)

        //依id來設定每個物件
        userName = findViewById(R.id.appUserRegister_Name)  //使用者輸入的名字
        userEmail = findViewById(R.id.appUserRegister_Email)  //使用者輸入的Email
        userPassword = findViewById(R.id.appUserRegister_Password)  //使用者輸入的密碼
        checkPassword = findViewById(R.id.appUserRegister_CheckPW)  //重複確認使用者輸入的密碼

        val warningMessage = findViewById<TextView>(R.id.warning_sign)
        val registerBtn =  findViewById<Button>(R.id.button_toRegister)  //註冊按鈕
        val backBtn = findViewById<Button>(R.id.button_back)  //取消按鈕

        val intentBackToSec = Intent(this, SecActivity::class.java)  //註冊結束後一律用這個intent回到SecActivity

        //設定按下按鈕後會觸發的動作
        registerBtn.setOnClickListener {
            //檢查輸入資訊是否正確及適當
            if(userName.text.toString() == ""){
                warningMessage.text = "請輸入您的使用者名稱!"
            }
            else if(userEmail.text.toString() == ""){
                warningMessage.text = "請填寫您的E-mail!"
            }
            else if(userPassword.text.toString() == ""){
                warningMessage.text = "請設定密碼!"
            }
            else if(checkPassword.text.toString() == ""){
                warningMessage.text = "請重複輸入一次您的密碼!"
            }
            //每一欄必要資訊都有填寫的話, 開始檢查輸入的資料是否適當
            else if(userName.text.toString().length > 30){
                warningMessage.text = "您的使用者名稱太長了"
            }
            else if(userPassword.text.toString() != checkPassword.text.toString()){
                warningMessage.text = "兩次輸入的密碼不相同!"
            }
            else if(userPassword.text.toString().length < 6){
                warningMessage.text = "您的密碼太短了!"
            }
            else if(userPassword.text.toString().length > 16){
                warningMessage.text = "您的密碼太長了!"
            }
            else{
                val un = userName.text.toString()
                val ue = userEmail.text.toString()
                val upw = userPassword.text.toString()

                val urlToRegister = "http://140.131.114.158/project/User_Information_Add_Save.asp"
                GlobalScope.launch(Dispatchers.IO){
                    Jsoup.connect(urlToRegister)
                        .data("UserMail", ue)
                        .data("UserName", un)
                        .data("UserPassword", upw)
                        .data("UserNickname", (10..127).random().toString())  //隨機給ID
                        .execute()
                    withContext(Dispatchers.Main){
                        Users.addUserData(un, ue, upw)
                    }
                }
                //intentBackToSec.putExtra("user_Name", userName.text.toString())
                setResult(RESULT_OK, intentBackToSec)
                finish()
            }
        }

        backBtn.setOnClickListener {
            setResult(RESULT_CANCELED, intentBackToSec)
            finish()
        }
    }
}