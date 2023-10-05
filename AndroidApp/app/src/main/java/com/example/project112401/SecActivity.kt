package com.example.project112401

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        val returnBtn = findViewById<Button>(R.id.returnBtn1)
        val intent = Intent(this, MainActivity::class.java)

        returnBtn.setOnClickListener{
            startActivity(intent)
        }
    }
}