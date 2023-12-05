package com.example.project112401

data class RoomProperties(  //每個房間的屬性跟資料
    val roomNumber : Int,  //房間ID
    var roomName : String,  //房間名稱
    var roomPW : String,  //房間密碼
    var deviceID : Int?,  //綁定的硬體設備ID
    val usersInThere : MutableList<NewAddedUser>,  //儲存每一位使用者資訊的List
    var usersCount : Int  //計算使用者數量
)