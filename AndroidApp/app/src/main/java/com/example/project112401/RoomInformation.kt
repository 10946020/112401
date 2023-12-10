package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RoomInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_information)

        val backBtn = findViewById<Button>(R.id.roomInfo_backBtn)

        backBtn.setOnClickListener {
            finish()
        }
    }
}