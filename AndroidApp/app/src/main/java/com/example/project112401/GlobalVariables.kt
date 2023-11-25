package com.example.project112401

class GlobalVariables {  //存放全域變數
    class Logged_In_Users{  //已登入的user, 以及其attributes, methods
        //基本帳號資訊
        private var user_name : String = ""
        private var user_email : String = ""
        private var user_password : String = ""

        //整理成自訂的data class格式, 方便調用
        private var format = NewAddedUser(user_name, user_email, user_password)

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
        fun joinToTheRoom(num: Int, name : String){  //使用者加入房間
            val room = roomData.theRoom(num, name)
            if(roomData.isTheRoomExist(num, name)){
                roomList.add(room)
            }
        }
        fun leaveTheRoom(num: Int, name : String){  //使用者離開該房間
            val room = roomData.theRoom(num, name)
            if(roomList.contains(room)){
                roomList.remove(room)
            }
        }
    }

    class User_Data{
        val list : MutableList<NewAddedUser> = mutableListOf(  //儲存user資料的list
            NewAddedUser("ItsZir","10946020@ntub.edu.tw", "114514"),
            NewAddedUser("user1", "user1@ntub.edu.tw", "000001"),
            NewAddedUser("user2", "user2@ntub.edu.tw", "000002"),
            NewAddedUser("user3", "user3@ntub.edu.tw", "000003")
        )

        val tempData = mutableListOf(
            //以下為假資料, 之後這裡要連線至database
            NewAddedUser("ItsZir","10946020@ntub.edu.tw", "114514"),
            NewAddedUser("user1", "user1@ntub.edu.tw", "000001"),
            NewAddedUser("user2", "user2@ntub.edu.tw", "000002"),
            NewAddedUser("user3", "user3@ntub.edu.tw", "000003")
        )

        fun addUserData(n : String, e: String, pw: String){
            list.add(NewAddedUser(n,e,pw))
        }

        fun removeUser(index : Int,n: String){
            for(user in list){
                if(user.name == n && list.indexOf(user) == index){
                    list.removeAt(index)
                }
            }
        }
    }

    class Room_Numbers{  //表示房間假資料的class
        private val rooms : MutableList<RoomProperties> = mutableListOf(  //儲存假資料
            RoomProperties(114514, "Room_1", "114514", mutableListOf())
        )

        //用房間的編號跟名稱來鎖定要找的房間物件, 方便調用
        fun theRoom(num: Int) : RoomProperties{  //此function回傳的是物件(找到的房間)
            return rooms.filter { (roomNum) -> roomNum == num}[0]
        }
        fun theRoom(num : Int, name : String) : RoomProperties{  //此function回傳的是物件(找到的房間)
            return rooms.filter { (roomNum, roomName) -> roomNum == num && roomName == name }[0]
        }

        //------Methods
        fun isTheRoomExist(num : Int, name : String) : Boolean {  //尋找房間是否已存在
            val room = rooms.filter { (roomNum, roomName) -> roomNum == num && roomName == name }[0]  //只檢查房間的id跟名稱
            return rooms.contains(room)  //filter選出的data type為ArrayList, 所以要選擇其index為0(即第一個)的data
        }
        private fun createTheRoom(num : Int, name : String){  //創建新房間
            val room = rooms.filter { (roomNum, roomName) -> roomNum == num && roomName == name }[0]
            rooms.add(room)
        }
        private fun deleteTheRoom(num : Int, name : String){  //刪除房間
            val room = rooms.filter { (roomNum, roomName) -> roomNum == num && roomName == name }[0]
            rooms.remove(room)
        }

        fun joinTheUser(room : RoomProperties, user: NewAddedUser){
            room.usersInThere.add(user)
        }
    }
}

val loggedInUser = GlobalVariables.Logged_In_Users()  //宣告一個物件, 作為已登入的使用者

val Users = GlobalVariables.User_Data()  //模擬資料庫裡所有的user

val roomData = GlobalVariables.Room_Numbers()  //模擬資料庫裡所有已存在的房間