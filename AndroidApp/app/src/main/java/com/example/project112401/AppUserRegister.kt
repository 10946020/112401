package com.example.project112401

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

private class AppUser(var name : String, var email : String, var password : String){
    init {
        this.name = name
        this.email = email
        this.password = password
    }
}
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

        //設定按下按鈕後會觸發的動作
        registerBtn.setOnClickListener {
            val newUser = AppUser(userName.text.toString(),userEmail.text.toString(), userPassword.text.toString())
            if(newUser.name == ""){
                warningMessage.text = "Please Enter your name!"
            }
            else if(newUser.name != ""){
                intent.putExtra("user_Name", newUser.name)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        backBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}