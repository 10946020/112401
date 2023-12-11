package com.example.project112401

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RoomFragment_UserList : Fragment() {
    private lateinit var roomUserList : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room__user_list, container, false)
        roomUserList = view.findViewById(R.id.roomInfoFrag_userList)
        //val data = arguments
        //roomUserList.text = data!!.get("string").toString()
        //roomUserList.text = loggedInUser.getUserRoom()[0].usersInThere.toString()

        roomUserList.text = "用戶數量 : ${loggedInUser.getUserRoom()[0].usersCount}"

        return view
    }
}