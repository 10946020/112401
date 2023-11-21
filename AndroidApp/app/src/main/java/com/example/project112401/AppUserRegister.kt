package com.example.project112401

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

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
                warningMessage.text = "Please Enter your name!"
            }
            else if(userEmail.text.toString() == ""){
                warningMessage.text = "Please Enter your E-mail!"
            }
            else if(userPassword.text.toString() == ""){
                warningMessage.text = "Please Enter your Password!"
            }
            else if(checkPassword.text.toString() == ""){
                warningMessage.text = "Please Enter your Password again!"
            }
            else if(userPassword.text.toString() != checkPassword.text.toString()){
                warningMessage.text = "Please Enter the same password twice!"
            }
            else if(userPassword.text.toString().length < 6){
                warningMessage.text = "Your password was too short!"
            }
            else if(userPassword.text.toString().length > 16){
                warningMessage.text = "Your password was too long!"
            }
            else{
                //這邊預定要開發的功能 : 送出使用者的資料到Database
                val un = userName.text.toString()
                val ue = userEmail.text.toString()
                val upw = userPassword.text.toString()
                Users.addUserData(un, ue, upw)
                intentBackToSec.putExtra("user_Name", userName.text.toString())
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