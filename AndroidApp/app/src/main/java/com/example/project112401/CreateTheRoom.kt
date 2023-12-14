package com.example.project112401

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.Connection

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

        val urlToCheckDevice = "http://140.131.114.158/project/Device_Information_Query.asp"  //查設備資料的網頁
        GlobalScope.launch(Dispatchers.IO){
            val document: Document = Jsoup.connect(urlToCheckDevice).get()  //連至後端程式, 調查user資料的頁面

            val table = document.select("table").first()  //找到網頁中第一個table標籤
            val tbody = table?.select("tbody")?.first()  //table中第一個tbody標籤

            for(row in tbody!!.select("tr")) {  //檢查每一行tr裡的元素
                val rowData = mutableListOf<String>()  //每一個橫行的資料
                for (column in row.select("td")) {  //檢查該tr裡每個td標籤內的資料
                    rowData.add(column.text())  //資料新增到list
                }
                //抓完每一行的資料後判斷資料是否符合需求再儲存
                if(rowData[0].toIntOrNull() != null){  //deviceID必須是Int
                    val device = DevicesProperties(rowData[0].toInt(), rowData[1], null)
                    devices.deviceList.add(device)  //導入資料到設備list
                }
            }
            withContext(Dispatchers.Main){
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
                            var urlToCreateTheRoom = "http://140.131.114.158/project/Room_Information_Add_Save.asp"
                            val urlToCheckRooms = "http://140.131.114.158/project/Room_Information_Query.asp"
                            GlobalScope.launch(Dispatchers.IO){  //非同步處理
                                Jsoup.connect(urlToCreateTheRoom)
                                    //填入參數
                                    .data("UserMail", loggedInUser.getEmail())
                                    .data("RoomName", roomName.text.toString())
                                    .data("RoomHC", "0")
                                    .data("DeviceID", roomDeviceID.text.toString())
                                    .data("DeviceCode", roomPW.text.toString())
                                    .method(Connection.Method.GET)
                                    .execute()

                                val document: Document = Jsoup.connect(urlToCheckRooms).get()
                                val table = document.select("table").first()  //找到網頁中第一個table標籤
                                val tbody = table?.select("tbody")?.first()  //table中第一個tbody標籤

                                for(row in tbody!!.select("tr")) {  //檢查每一行tr裡的元素
                                    val rowData = mutableListOf<String>()  //每一個橫行的資料
                                    for (column in row.select("td")) {  //檢查該tr裡每個td標籤內的資料
                                        rowData.add(column.text())  //資料新增到list
                                    }
                                    if(rowData[4] == roomDeviceID.text.toString()) {  //用device核對, 找到剛剛創建的房間
                                        withContext(Dispatchers.Main) {
                                            roomData.createTheRoom(
                                                rowData[0]!!.toInt(),
                                                rowData[2],
                                                rowData[5],
                                                rowData[4].toInt()
                                            )
                                        }
                                    }
                                }
                            }
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                }
            }
        }


    }
}