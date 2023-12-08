package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

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

        val warningMsg = findViewById<TextView>(R.id.createRoom_warning)  //警告訊息

        val confirmBtn = findViewById<Button>(R.id.createRoom_confirmBtn)  //確認按鈕
        val backBtn = findViewById<Button>(R.id.createRoom_backBtn)  //返回按鈕

        backBtn.setOnClickListener {
            finish()
        }

        confirmBtn.setOnClickListener {
            val enteredDeviceID : Int? = roomDeviceID.text.toString().toIntOrNull()  //輸入的設備ID轉換成Int的資料型態

            //------檢查是否每一欄都有填
            if(roomDeviceID.text.toString() == ""){
                warningMsg.text = "請輸入設備ID!"
            }
            else if(roomName.text.toString() == ""){
                warningMsg.text = "請設定房間名稱!"
            }
            else if(roomPW.text.toString() == ""){
                warningMsg.text = "請設定房間密碼!"
            }
            else if(checkRoomPW.text.toString() == ""){
                warningMsg.text = "請重複確認房間密碼!"
            }
            //------檢查輸入資訊是否正確及適當
            //設備ID存在與否
            else if(enteredDeviceID == null){  //如果輸入的設備ID不是數字
                warningMsg.text = "設備ID只能是數字, 不能有其他符號!"
            }
            else{
                if(devices.findTheDevice(enteredDeviceID).isEmpty()){  //如果找不到可使用的設備
                    warningMsg.text = "請輸入未被使用的設備ID!"
                }
                else{
                    roomData.createTheRoom(  //填入參數並創建房間
                        roomName.text.toString(),
                        roomPW.text.toString(),
                        enteredDeviceID
                    )
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }
}