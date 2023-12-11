package com.example.project112401

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RoomFragment_Info : Fragment() {
    private lateinit var roomName : TextView  //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room__info, container, false)
        roomName = view.findViewById(R.id.roomInfoFrag_name)

        //val data = this.arguments?.getString("string").toString()
        //roomName.text = data

        roomName.text = loggedInUser.getUserRoom()[0].roomName
        return view
    }
}