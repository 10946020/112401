package com.example.project112401

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlin.math.log

class RoomFragment_ioRecord : Fragment() {
    //private lateinit var roomRecord : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room_io_record, container, false)
        //roomRecord = view.findViewById(R.id.roomInfoFrag_record)
        //val data = arguments
        //roomRecord.text = data!!.get("string").toString()
        /*
        val room_device_ID = loggedInUser.getUserRoom()[0].deviceID

        roomRecord.text = if(room_device_ID == null){
            "錯誤,此房間並未綁定設備!"
        }
        else{
            room_device_ID.toString()
        }
         */

        //另類寫法 :
        //roomRecord.text = room_device_ID?.toString() ?: "錯誤,此房間並未綁定設備!"

        //roomRecord.text = "目前綁定設備 : ${loggedInUser.getUserRoom()[0].deviceID}"

        return view
    }
}