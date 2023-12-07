package com.example.project112401

class GlobalVariables {  //存放全域變數
    class EnterTime{
        var recentTime : Long = 0

        fun refreshRecentTime(){
            recentTime = System.currentTimeMillis()
        }
        fun getTime() : Long{
            refreshRecentTime()
            return recentTime
        }
    }
    class Logged_In_Users{  //已登入的user, 以及其attributes, methods
        //基本帳號資訊
        private var user_name : String = ""
        private var user_email : String = ""
        private var user_password : String = ""

        //整理成自訂的data class格式, 方便調用
        private var format = NewAddedUser(user_name, user_email, user_password, null)

        private var isLoggedIn : Boolean = false  //是否已登入

        val roomList : MutableList<RoomProperties> = mutableListOf(
            //該user目前已加入的房間, 房間的資料格式為RoomProperties.kt中的data class
        )

        //基本變數的設定跟呼叫方法
        //------名稱------------------
        fun setName(name: String){
            this.user_name = name
        }
        fun getName(): String{
            return user_name
        }

        //------E-mail------------------
        fun setEmail(e: String){
            this.user_email = e
        }
        fun getEmail(): String{
            return user_email
        }

        //------密碼------------------
        fun setPassword(pw: String){
            this.user_password = pw
        }
        fun getPassword(): String{
            return user_password
        }

        //------設定登入狀態, 和檢查是否登入------------------
        fun setStatusToLogin(){
            this.isLoggedIn = true
        }
        fun setStatusToLogout(){
            this.isLoggedIn = false
        }
        fun checkStatus(): Boolean{
            return isLoggedIn
        }

        //------登出------------------
        fun LogOut(){
            user_name = ""
            user_email = ""
            user_password = ""
            setStatusToLogout()
        }

        //------調用data class格式------------------
        fun getDataFormat() : NewAddedUser{
            return format
        }

        //------所屬房間------------------
        fun userJoinToTheRoom(num: Int, password : String){  //使用者加入房間
            if(!roomData.theRoom(num, password).isNullOrEmpty()){  //如果有找到該房間
                val room = roomData.theRoom(num, password)[0]  //用編號跟密碼找到房間的物件
                roomList.add(room)  //把該房間加到使用者已經待著的房間列表
            }
        }
        fun userLeaveTheRoom(num: Int, password : String){  //使用者離開該房間
            if(!roomData.theRoom(num, password).isNullOrEmpty()){  //如果有找到該房間
                val room = roomData.theRoom(num, password)[0]  //用編號跟密碼找到房間的物件
                roomList.remove(room)  //把該房間從使用者已經待著的房間列表內移除
            }
        }
    }

    class User_Data{
        val list : MutableList<NewAddedUser> = mutableListOf(  //儲存user資料的list
            NewAddedUser("ItsZir","10946020@ntub.edu.tw", "114514", null),
            NewAddedUser("user1", "user1@ntub.edu.tw", "000001", null),
            NewAddedUser("user2", "user2@ntub.edu.tw", "000002", null),
            NewAddedUser("user3", "user3@ntub.edu.tw", "000003", null)
        )

        val tempData = mutableListOf(
            //以下為假資料, 之後這裡要連線至database
            NewAddedUser("ItsZir","10946020@ntub.edu.tw", "114514", null),
            NewAddedUser("user1", "user1@ntub.edu.tw", "000001", null),
            NewAddedUser("user2", "user2@ntub.edu.tw", "000002", null),
            NewAddedUser("user3", "user3@ntub.edu.tw", "000003", null)
        )

        fun addUserData(n : String, e: String, pw: String){
            list.add(NewAddedUser(n,e,pw, null))
        }

        fun removeUser(index : Int,n: String){
            for(user in list){
                if(user.name == n && list.indexOf(user) == index){
                    list.removeAt(index)
                }
            }
        }
    }

    class Room_Info{  //表示房間假資料的class
        private val rooms : MutableList<RoomProperties> = mutableListOf(  //儲存假資料
            RoomProperties(
                112401,
                "",
                "114514",
                null,
                mutableListOf(),
                0
            )
        )

        //用房間的編號跟密碼來鎖定要找的房間物件, 方便調用

        //此function回傳的是物件(找到的房間)
        fun theRoom(num: Int) : RoomProperties{  //只用房間編號找
            return rooms.filter { it.roomNumber == num}[0]
        }
        //此function回傳的是物件List(找到的房間)
        fun theRoom(num : Int, password : String) : List<RoomProperties>{  //用房間編號跟密碼找
            return rooms.filter { it.roomNumber == num && it.roomPW == password }
        }

        //------Methods
        fun isTheRoomExist(num : Int, name : String) : Boolean {  //尋找房間是否已存在
            val room = rooms.filter { (roomNum, roomName) -> roomNum == num && roomName == name }[0]  //只檢查房間的id跟名稱
            return rooms.contains(room)  //filter選出的data type為ArrayList, 所以要選擇其index為0(即第一個)的data
        }
        private fun createTheRoom(name : String, password : String, deviceID : Int){  //創建新房間
            if(loggedInUser.checkStatus()){  //只有登入使用者後才能操作
                //生成新的房間物件
                val newRoom = RoomProperties(
                    0,  //要用隨機生成的數字作為ID
                    name,  //使用者輸入的房間名稱
                    password,  //使用者輸入的房間密碼
                    deviceID,  //使用者輸入的要綁定的設備ID
                    mutableListOf(),  //預設一個空List來存放使用者
                    0  //房間內的使用者數量
                )

                //將當前的使用者加進新房間的user List裡
                roomJoinTheUser(newRoom, loggedInUser.getDataFormat())

                //使用者個人已加入的房間List也要新增這個新房間進去
                loggedInUser.userJoinToTheRoom(newRoom.roomNumber, newRoom.roomPW)

                //之後再把新房間物件加到代表所有房間列表的List
                rooms.add(newRoom)
            }
        }
        private fun deleteTheRoom(num : Int, name : String){  //刪除房間
            val room = rooms.filter { (roomNum, roomName) -> roomNum == num && roomName == name }[0]
            rooms.remove(room)
        }

        private fun roomJoinTheUser(room : RoomProperties, user: NewAddedUser){  //新增使用者進去房間
            room.usersInThere.add(user)
            room.usersCount += 1
        }
    }
}

val loggedInUser = GlobalVariables.Logged_In_Users()  //宣告一個物件, 作為已登入的使用者

val Users = GlobalVariables.User_Data()  //模擬資料庫裡所有的user

val roomData = GlobalVariables.Room_Info()  //模擬資料庫裡所有已存在的房間

val enterTime = GlobalVariables.EnterTime()  //當前辨識進出時的時間