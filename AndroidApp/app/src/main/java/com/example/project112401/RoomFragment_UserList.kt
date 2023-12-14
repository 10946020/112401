package com.example.project112401

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RoomFragment_UserList : Fragment() {
    private lateinit var userListView : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room__user_list, container, false)
        //roomUserList = view.findViewById(R.id.roomInfoFrag_userList)
        //val data = arguments
        //roomUserList.text = data!!.get("string").toString()
        //roomUserList.text = loggedInUser.getUserRoom()[0].usersInThere.toString()
        //roomUserList.text = "用戶數量 : ${loggedInUser.getUserRoom()[0].usersCount}"

        userListView = view.findViewById(R.id.roomUserList_recycleView)  //裝所有資訊的recycleView

        //排版設定
        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        userListView.layoutManager = linearLayoutManager  //套用layout設定到recycleView

        //使用者查看的房間中, 全部在該房間內的使用者清單
        val roomUserList = loggedInUser.getUserRoom()[0].usersInThere
        //用自行寫的Adapter名稱, 參數填使用者列表, 再套用到recycleView
        userListView.adapter = RoomUserListAdapter(roomUserList)

        return view
    }
}