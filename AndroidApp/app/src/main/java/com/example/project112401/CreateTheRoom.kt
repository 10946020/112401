package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateTheRoom : AppCompatActivity() {
    lateinit var roomDeviceID : EditText
    lateinit var roomName : EditText
    lateinit var roomPW : EditText
    lateinit var checkRoomPW : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_the_room)

        roomDeviceID = findViewById(R.id.createRoom_deviceID)  //輸入的設備ID
        roomName = findViewById(R.id.createRoom_roomName)  //輸入的房間名稱
        roomPW = findViewById(R.id.createRoom_roomPW)  //輸入的房間密碼
        checkRoomPW = findViewById(R.id.createRoom_checkRoomPW)  //確認密碼

        val confirmBtn = findViewById<Button>(R.id.createRoom_confirmBtn)
        val backBtn = findViewById<Button>(R.id.createRoom_backBtn)

        backBtn.setOnClickListener {
            finish()
        }
    }
}