package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RoomStatus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_status)

        val backBtn = findViewById<Button>(R.id.roomBackBtn)
        backBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        //顯示房間資訊跟出入紀錄等, 紀錄用adapter弄
    }
}