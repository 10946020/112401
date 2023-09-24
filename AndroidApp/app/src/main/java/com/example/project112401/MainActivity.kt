package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.sql.Timestamp
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.checkButton)  //暫時用來表示偵測身分的按鈕, 以後會用自動辨識功能來取代
        val time = findViewById<TextView>(R.id.daytime)  //這個TextView用來顯示 : 發生偵測動作當下的時間
        val calculator = findViewById<TextView>(R.id.last_check)  //這個TextView用來顯示 : 兩次偵測之間的時間差

        var lastTime : Long = 0  //暫放上次偵測時間資料的變數

        //自行寫的function, 用來把毫秒為單位的資料轉成直觀的時間
        fun datetimeExchanger (timeData : Long) : String{  //x : 以ms為單位的時間
            var result = ""  //最後要回傳的時間字串
            var x = timeData
            if (x / 1000 > 0){  //如果要轉換的時間大於1秒(1000ms)
                if (x / (1000 *60) > 0){  //大於1分鐘(60秒)
                    if (x / (1000 *60 *60) > 0){  //大於1小時(60分鐘)
                        if (x / (1000 *60 *60 *24) > 0){  //大於1天(24小時)
                            result += "${x / (1000 *60 *60 *24)}天 "
                            x %= (1000 *60 *60 *24)
                        }
                        result += "${x / (1000 *60 *60)}小時 "
                        x %= (1000 *60 *60)
                    }
                    result += "${x / (1000 *60)}分 "
                    x %= (1000 *60)
                }
                result += "${x / 1000}秒."
                x %= (1000)
            }
            else{
                result += "不到1秒"
            }
            return result
        }

        button1.setOnClickListener {  //當按下按鈕後發生的事, 以後是自動辨識到身分後就進行裡面的動作
            val now = System.currentTimeMillis()  //發生偵測動作當下的時間, 資料型態為long
            val timestamp = Timestamp(now)  //轉成時間戳
            val tf = SimpleDateFormat("yyyy/MM/dd EEE HH:mm:ss a")  //更改時間戳的顯示格式
            time.text = tf.format(timestamp)  //把顯示用的物件的text改成格式化後的當下時間

            if (lastTime != 0L) {  //如果不是第一次發生偵測動作, 即計算時間差並顯示
                calculator.text = "距離上次已經過了" + datetimeExchanger(now-lastTime)
                //當前時間 - 上次時間 = 經過了幾毫秒, 之後用自行寫的function來整理
            }
            lastTime = now  //把當下時間放到暫存用的變數裡
        }
    }
}