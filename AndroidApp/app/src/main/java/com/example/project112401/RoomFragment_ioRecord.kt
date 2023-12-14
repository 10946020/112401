package com.example.project112401

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomFragment_ioRecord : Fragment() {
    private lateinit var IORecordView : RecyclerView
    private lateinit var recordInThisRoom : MutableList<List<String>>  //紀錄所有該房間的進出data
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room_io_record, container, false)

        IORecordView = view.findViewById(R.id.roomIORecord_recycleView)
        recordInThisRoom = mutableListOf()

        //排版設定
        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        IORecordView.layoutManager = linearLayoutManager

        //處理資料
        //-----------------------------------------------------------------------------------------
        val url = "http://140.131.114.158/project/Open_Device_Information_Query.asp"

        GlobalScope.launch(Dispatchers.IO){  //非同步進行的動作, 避免連網路後執行續過載導致crash
            val document: Document = Jsoup.connect(url).get()  //連至後端程式, 調查DB資料的頁面

            val table = document.select("table").first()  //找到網頁中第一個table標籤
            val tbody = table?.select("tbody")?.first()  //table中第一個tbody標籤

            val resultLists = mutableListOf<List<String>>()  //儲存tbody所有資料List的List

            for(row in tbody!!.select("tr")){  //檢查每一行tr裡的元素
                val rowData = mutableListOf<String>()  //每一個橫行的資料
                for(column in row.select("td")) {  //檢查該tr裡每個td標籤內的資料
                    rowData.add(column.text())  //資料新增到list
                }
                //抓完每一行的資料後判斷資料是否符合需求再儲存
                if(rowData[3] == loggedInUser.getUserRoom()[0].deviceID.toString()){  //如果進出紀錄裡的deviceID跟該房間的設備ID符合
                    resultLists.add(rowData)  //最後再把list存到所有結果的list
                }
            }
            recordInThisRoom = resultLists  //資料轉移到另一個裝list的list, 提供之後更多資料處理功能使用

            withContext(Dispatchers.Main){  //切換回主執行緒進行套用Adapter等其他動作
                IORecordView.adapter = RoomIORecordAdapter(recordInThisRoom)  //套用Adapter到recyclerView
            }

            //----------------------------------------------------------------------?
            /*
            //每個資料分別獨立建成一個list做後續調用
            val roomIDList = mutableListOf<String>()
            val userIDList = mutableListOf<String>()
            val timeList = mutableListOf<String>()
            val deviceIDList = mutableListOf<String>()

            for(dataList in resultLists){  //list列表中每個list
                if(dataList != resultLists[0]){  //第一個row是資料的column名稱, 所以跳過
                    roomIDList.add(dataList[0])  //一個list裡第一筆資料為房間ID
                    userIDList.add(dataList[1])
                    timeList.add(dataList[2])
                    deviceIDList.add(dataList[3])
                }
            }

            //----------------------------------------------------------------------?

            //val ioRecordDataForAdapter = mutableListOf<>()
            //val recordInThisRoom = mutableListOf<List<String>>()  //紀錄所有該房間的進出data
            for(dataList in resultLists){
                if(dataList != resultLists[0]){  //忽略第一行
                    if(dataList[3] == "112401"){  //
                        recordInThisRoom.add(dataList)  //新增該筆資料到列表
                    }
                }
            }
            //testTxt.text = recordInThisRoom.toString()
            */

            //----------------------------------------------------------------------?
        }
        //-----------------------------------------------------------------------------------------

        return view
    }
}