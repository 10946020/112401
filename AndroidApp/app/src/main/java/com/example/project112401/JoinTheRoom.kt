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

class JoinTheRoom : AppCompatActivity() {
    lateinit var roomID : EditText
    lateinit var roomPW : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_the_room)

        roomID = findViewById(R.id.joinTheRoom_roomID)  //輸入的房間id
        roomPW = findViewById(R.id.joinTheRoom_roomPW)  //輸入的房間密碼

        val warningMsg = findViewById<TextView>(R.id.joinTheRoom_warning)  //警示訊息

        val confirmBtn = findViewById<Button>(R.id.joinTheRoom_confirmBtn)
        val backBtn = findViewById<Button>(R.id.joinTheRoom_backBtn)

        backBtn.setOnClickListener {
            finish()
        }

        confirmBtn.setOnClickListener {
            //是否有填寫每一欄
            if(roomID.text.toString() == ""){
                warningMsg.text = "請輸入房間ID!"
            }
            else if(roomPW.text.toString() == ""){
                warningMsg.text = "請輸入房間密碼!"
            }
            //資料是否正確
            else if(roomID.text.toString().toIntOrNull() == null){  //如果輸入的房間ID不是全數字
                warningMsg.text = "房間ID只能是數字, 不能有其他符號!"
            }
            else{  //如果輸入的房間ID都是數字
                val urlToCheckRooms = "http://140.131.114.158/project/Room_Information_Query.asp"
                GlobalScope.launch(Dispatchers.IO){
                    val document: Document = Jsoup.connect(urlToCheckRooms).get()
                    val table = document.select("table").first()  //找到網頁中第一個table標籤
                    val tbody = table?.select("tbody")?.first()  //table中第一個tbody標籤

                    for(row in tbody!!.select("tr")) {  //檢查每一行tr裡的元素
                        val rowData = mutableListOf<String>()  //每一個橫行的資料
                        for (column in row.select("td")) {  //檢查該tr裡每個td標籤內的資料
                            rowData.add(column.text())  //資料新增到list
                        }
                        if(rowData[0].toIntOrNull() != null){
                            roomData.rooms.add(  //App的資料表載入房間資訊
                                RoomProperties(
                                    rowData[0].toInt(),
                                    rowData[2],
                                    rowData[5],
                                    rowData[4].toInt(),
                                    mutableListOf(),
                                    rowData[3].toInt(),
                                )
                            )
                        }
                    }
                    val ID = roomID.text.toString().toInt()
                    if(roomData.theRoom(ID).isEmpty()){  //如果房間ID不存在
                        warningMsg.text = "該房間不存在, 請輸入已存在的房間ID!"
                    }
                    else{  //如果房間ID存在
                        withContext(Dispatchers.Main){
                            if(roomData.theRoom(ID, roomPW.text.toString()).isEmpty()){  //如果輸入的已存在房間密碼錯誤
                                warningMsg.text = "房間密碼錯誤!"
                            }
                            else{
                                warningMsg.text = "成功"

                                val room = roomData.theRoom(ID, roomPW.text.toString())[0]
                                //分別找到房間物件跟使用者物件, 型態都是以自行寫的data class的格式作為參數
                                roomData.roomJoinTheUser(room, loggedInUser.getDataFormat())
                                //使用者加入
                                loggedInUser.userJoinToTheRoom(ID,roomPW.text.toString())
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