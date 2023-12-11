package com.example.project112401

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RoomFragment_Setting : Fragment() {
    private lateinit var roomSetting : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room__setting, container, false)
        roomSetting = view.findViewById(R.id.roomInfoFrag_setting)
        //val data = arguments
        //roomSetting.text = data!!.get("string").toString()
        //roomSetting.text = loggedInUser.getUserRoom()[0].roomNumber.toString()
        roomSetting.text = "setting, 待更新"
        return view
    }
}