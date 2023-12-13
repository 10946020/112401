package com.example.project112401

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class RoomInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_information)

        val infoText = findViewById<TextView>(R.id.roomInfo_dataText)

        val navigator = findViewById<BottomNavigationView>(R.id.roomInfo_navigator)
        val backBtn = findViewById<Button>(R.id.roomInfo_backBtn)

        backBtn.setOnClickListener {  //右上角返回按鈕
            finish()
        }

        infoText.text = "房間名稱 : ${loggedInUser.getUserRoom()[0].roomName}"
        replaceFragment(RoomFragment_Info())  //預設先切換到顯示Info的Fragment

        navigator.setOnItemSelectedListener {  //底部按鈕選擇後的反應
            when(it.itemId){
                //預計優化方向 : 把登入的user跟房間相關data直接從這個activity導入, 再分別送到其他fragments
                R.id.roomNavBtn_info ->{  //第一個 : Info
                    infoText.text = "房間名稱 : ${loggedInUser.getUserRoom()[0].roomName}"
                    replaceFragment(RoomFragment_Info())
                }
                R.id.roomNavBtn_userList ->{
                    infoText.text = "用戶數量 : ${loggedInUser.getUserRoom()[0].usersCount}, Owner : ${loggedInUser.getUserRoom()[0].usersInThere[0].name}"
                    replaceFragment(RoomFragment_UserList())
                }
                R.id.roomNavBtn_ioRecords ->{
                    infoText.text = "目前綁定設備 : ${loggedInUser.getUserRoom()[0].deviceID}"
                    replaceFragment(RoomFragment_ioRecord())
                }
                R.id.roomNavBtn_setting ->{
                    infoText.text = "Setting"
                    replaceFragment(RoomFragment_Setting())
                }

                else -> { true }

                /*
                //待開發用的傳遞訊息方式跟語法

                R.id.roomNavBtn_info ->{  //第一個 : Info
                    val fragment = RoomFragment_Info()
                    val bundle = Bundle()
                    bundle.putString("string", loggedInUser.getUserRoom()[0].roomName)
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                }

                R.id.roomNavBtn_userList ->{  //第二個 : 用戶列表
                    val fragment = RoomFragment_UserList()
                    val bundle = Bundle()
                    bundle.putString("string", loggedInUser.getUserRoom()[0].usersInThere.toString())
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                }

                R.id.roomNavBtn_ioRecords ->{  //第三個 : 進出紀錄
                    val fragment = RoomFragment_ioRecord()
                    val bundle = Bundle()
                    bundle.putString("string", loggedInUser.getUserRoom()[0].deviceID.toString())
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                }

                R.id.roomNavBtn_setting ->{  //第四個 : 設定
                    val fragment = RoomFragment_Setting()
                    val bundle = Bundle()
                    bundle.putString("string", loggedInUser.getUserRoom()[0].roomNumber.toString())
                    fragment.arguments = bundle
                    loggedInUser.getUserRoom()
                    replaceFragment(fragment)
                }
                 */
            }
        }
    }

    private fun replaceFragment(fragment : Fragment): Boolean {  //更換fragment的function
        val fragmentManager = supportFragmentManager  //管理及監聽fragment
        val fragmentTransaction = fragmentManager.beginTransaction()  //可對fragment進行載入, 顯示, 隱藏等操作
        //把frameLayout裡面換成參數指定的fragment
        fragmentTransaction.replace(R.id.roomInfo_frameLayout, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }
}