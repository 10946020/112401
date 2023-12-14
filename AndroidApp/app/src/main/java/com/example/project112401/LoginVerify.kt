package com.example.project112401

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

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

        val url = "http://140.131.114.158/project/User_Information_Query.asp"  //查user資料的網頁
        GlobalScope.launch(Dispatchers.IO){  //非同步處理
            val document: Document = Jsoup.connect(url).get()  //連至後端程式, 調查user資料的頁面

            val table = document.select("table").first()  //找到網頁中第一個table標籤
            val tbody = table?.select("tbody")?.first()  //table中第一個tbody標籤

            for(row in tbody!!.select("tr")){  //檢查每一行tr裡的元素
                val rowData = mutableListOf<String>()  //每一個橫行的資料
                for(column in row.select("td")) {  //檢查該tr裡每個td標籤內的資料
                    rowData.add(column.text())  //資料新增到list
                }
                //抓完每一行的資料後判斷資料是否符合需求再儲存
                if(rowData[3].toIntOrNull() != null){
                    val user = NewAddedUser(rowData[1], rowData[0], rowData[2], rowData[3].toIntOrNull(), mutableListOf())
                    Users.list.add(user)  //導入該筆user資料到user list
                }
            }

            withContext(Dispatchers.Main){  //切換回主執行緒
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
                        loggedInUser.logIn(verifyingUser)

                        //傳送成功狀態回Sec, 顯示使用者名稱的函式已經寫在Sec那邊了
                        setResult(RESULT_OK)
                        finish()
                    }
                }
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