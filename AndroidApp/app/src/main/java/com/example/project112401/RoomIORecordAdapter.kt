package com.example.project112401

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoomIORecordAdapter(private val ioRecordLists : MutableList<List<String>>) : RecyclerView.Adapter<RoomIORecordAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val userID : TextView = itemView.findViewById(R.id.roomIORecord_user)
        val userIOTime : TextView = itemView.findViewById(R.id.roomIORecord_ioTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.room_io_record_appearance, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eachRecordDataList = ioRecordLists[position]  //所有進出紀錄中每一筆進出紀錄, 資料型態為List
        holder.userID.text = eachRecordDataList[1]
        holder.userIOTime.text = eachRecordDataList[2]
    }

    override fun getItemCount(): Int {
        return ioRecordLists.size
    }
}