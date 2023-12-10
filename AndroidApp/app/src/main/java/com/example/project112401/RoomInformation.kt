package com.example.project112401

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class RoomInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_information)

        val navigator = findViewById<BottomNavigationView>(R.id.roomInfo_navigator)

        val backBtn = findViewById<Button>(R.id.roomInfo_backBtn)

        backBtn.setOnClickListener {  //右上角返回按鈕
            finish()
        }
        replaceFragment(RoomFragment_Info())

        navigator.setOnItemSelectedListener {
            when(it.itemId){

                R.id.roomNavBtn_info -> replaceFragment(RoomFragment_Info())
                R.id.roomNavBtn_userList -> replaceFragment(RoomFragment_UserList())
                R.id.roomNavBtn_ioRecords -> replaceFragment(RoomFragment_ioRecord())
                R.id.roomNavBtn_setting -> replaceFragment(RoomFragment_Setting())

                else -> { true }
            }
        }
    }

    private fun replaceFragment(fragment : Fragment): Boolean {  //更換fragment的function
        val fragmentManager = supportFragmentManager  //管理及監聽fragment
        val fragmentTransaction = fragmentManager.beginTransaction()  //可對fragment進行載入, 顯示, 隱藏等操作
        fragmentTransaction.replace(R.id.roomInfo_frameLayout, fragment)  //把frameLayout裡面換成指定的fragment
        fragmentTransaction.commit()
        return true
    }
}