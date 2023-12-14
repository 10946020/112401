package com.example.project112401

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RoomStatus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_status)

        val userName = findViewById<TextView>(R.id.room_userName)
        userName.text = "User : " + intent.getStringExtra("CurrentUser")  //從SecActivity傳送過來的user名稱

        val roomUserHave = findViewById<TextView>(R.id.room_userHave)  //顯示該使用者目前進入了多少房間
        roomUserHave.text = "您目前正在 ${loggedInUser.getRoomCount()} 個房間裡"

        val roomCount = findViewById<TextView>(R.id.room_roomCount)
        roomCount.text = ""

        val warningMsg = findViewById<TextView>(R.id.room_warning)  //警示訊息
        val checkRoomsBtn = findViewById<Button>(R.id.room_checkMyRooms)  //檢視用戶已加入的房間
        val createBtn = findViewById<Button>(R.id.room_create_btn)  //創建新房間
        val joinBtn = findViewById<Button>(R.id.room_join_btn)  //加入房間

        val backBtn = findViewById<Button>(R.id.roomBackBtn)  //返回
        backBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        joinBtn.setOnClickListener {
            //如果找到存在房間,則加到user所屬的房間列表, 同時該房間裡的user列表也新增該用戶進去
            val intentToJoinTheRoom = Intent(this, JoinTheRoom::class.java)
            startActivityForResult(intentToJoinTheRoom, 112401006)
        }

        createBtn.setOnClickListener {   //建立新房間
            //手動設定房間名稱, 密碼, 硬體編號(硬體ID不可重複)
            //自動生成唯一的房間ID
            val intentToCreateTheRoom = Intent(this, CreateTheRoom::class.java)
            startActivityForResult(intentToCreateTheRoom, 112401005)
        }

        checkRoomsBtn.setOnClickListener {
            if(loggedInUser.getRoomCount() <= 0){
                warningMsg.text = "請先加入房間才能查看房間內的資訊!"
            }
            else{
                //顯示房間資訊跟出入紀錄等, 紀錄用adapter弄
                val intentToCheckRooms = Intent(this, RoomInformation::class.java)
                startActivityForResult(intentToCheckRooms, 12401007)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val roomCount = findViewById<TextView>(R.id.room_roomCount)

        val roomUserHave = findViewById<TextView>(R.id.room_userHave)  //顯示該使用者目前進入了多少房間
        roomUserHave.text = "您目前正在 ${loggedInUser.getRoomCount()} 個房間裡"

        //--------判定回傳的request code--------
        if(requestCode == 112401005){  //創建房間的動作
            roomUserHave.text = "您目前正在 ${loggedInUser.getRoomCount()} 個房間裡"
        }
        else if(requestCode == 112401006){  //加入房間
            roomUserHave.text = "您目前正在 ${loggedInUser.getRoomCount()} 個房間裡"
        }
        else if(requestCode == 112401007){  //查看房間

        }
    }
}