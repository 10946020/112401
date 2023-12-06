package com.example.project112401

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginVerify : AppCompatActivity() {

    lateinit var userEmail : EditText
    lateinit var userPassword : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_verify)

        userEmail = findViewById(R.id.login_email)  //給使用者輸入Email登入
        userPassword = findViewById(R.id.login_password)  //輸入的密碼

        val confirmBtn = findViewById<Button>(R.id.login_confirm)  //確認登入按鈕
        val backBtn = findViewById<Button>(R.id.login_backBtn)  //返回按鈕
        val regBtn = findViewById<Button>(R.id.login_regBtn)  //註冊按鈕
        val warningMsg = findViewById<TextView>(R.id.login_warningText)  //警告訊息

        fun findTheUser(email : String, password : String) : List<NewAddedUser> {  //找到是否有這個使用者
            //尋找符合的使用者物件, 資料型態為自行寫的data class物件
            //用filter檢查後回傳的型態還是list, 所以要找到第一筆資料
            return Users.list.filter { it.email == email && it.password == password }
        }


        confirmBtn.setOnClickListener {
            val userEnteredEmail = userEmail.text.toString()  //轉換資料型態
            val userEnteredPassword = userPassword.text.toString()

            if(findTheUser(userEnteredEmail, userEnteredPassword).isNullOrEmpty()){  //如果回傳的物件list是空的(即找不到用戶)
                warningMsg.text = "您輸入的帳號或密碼有誤!"
            }
            else{  //帳號密碼都正確
                val verifyingUser = findTheUser(userEnteredEmail, userEnteredPassword)[0]  //找到使用者物件
                //設定成為已登入的user, loggedInUser為global variable
                loggedInUser.setName(verifyingUser.name)
                loggedInUser.setEmail(verifyingUser.email)
                loggedInUser.setPassword(verifyingUser.password)
                loggedInUser.setStatusToLogin()

                //顯示彈出訊息
                val toast = Toast.makeText(this, "以用戶身分 ${loggedInUser.getName()} 登入成功", Toast.LENGTH_SHORT)
                toast.show()

                //傳送成功狀態回Sec, 顯示使用者名稱的函式已經寫在Sec那邊了
                setResult(RESULT_OK)
                finish()
            }
        }

        backBtn.setOnClickListener {
            finish()
        }

        regBtn.setOnClickListener {
            val intentToRegister = Intent(this, AppUserRegister::class.java)
            startActivity(intentToRegister)
        }
    }
}