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

        //顯示儲存的最後一次辨識的時間
        val testTextView = findViewById<TextView>(R.id.test).apply {
            text = intent.getStringExtra("LastTime")  //從主介面傳送來的時間資料
        }

        val connectBtn =  findViewById<Button>(R.id.connectBtn1)  //送出http request用的按鈕
        val espData = findViewById<TextView>(R.id.data1)  //顯示接收到的訊息

        returnBtn.setOnClickListener{
            intent2.putExtra("LastTime_fromSec", testTextView.text)  //設定傳送回去的資料
            setResult(RESULT_OK,intent2)  //回傳表示執行成功的結果
            finish()  //回到主界面
        }

        connectBtn.setOnClickListener{

        }
    }
}