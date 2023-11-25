package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class RoomStatus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_status)

        val userName = findViewById<TextView>(R.id.room_userName)
        userName.text = "User : " + intent.getStringExtra("CurrentUser")  //從SecActivity傳送過來的user名稱

        val roomUserHave = findViewById<TextView>(R.id.room_userHave)  //顯示該使用者目前進入了多少房間
        roomUserHave.text = "您目前正在 ${loggedInUser.roomList.size} 個房間裡"

        val createBtn = findViewById<Button>(R.id.room_create_btn)  //創建新房間
        val joinBtn = findViewById<Button>(R.id.room_join_btn)  //加入房間

        val backBtn = findViewById<Button>(R.id.roomBackBtn)  //返回
        backBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        joinBtn.setOnClickListener {
            //如果找到存在房間,則加到user所屬的房間列表, 同時該房間裡的user列表也新增該用戶進去
        }

        //顯示房間資訊跟出入紀錄等, 紀錄用adapter弄
    }
}