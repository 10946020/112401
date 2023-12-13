package com.example.project112401

//data classes-------------------------------------------------------------------------------------
data class NewAddedUser(  //User的data格式
    val name : String,  //用戶名稱
    val email : String,  //用戶Email
    val password : String,  //用戶密碼
    val id : Int?,  //用戶ID
    val room : MutableList<RoomProperties>  //用戶所在的房間
)

data class RoomProperties(  //每個房間的屬性跟資料
    val roomNumber : Int,  //房間ID
    var roomName : String,  //房間名稱
    var roomPW : String,  //房間密碼
    var deviceID : Int?,  //綁定的硬體設備ID
    val usersInThere : MutableList<NewAddedUser>,  //儲存每一位使用者資訊的List
    var usersCount : Int  //計算使用者數量
)

data class DevicesProperties(  //每個裝置的屬性跟資料, 包括ID, 名稱, 還有綁定的房間物件
    val deviceID: Int,
    val deviceName : String,
    var boundRoom: RoomProperties?
)
//-------------------------------------------------------------------------------------------------
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
        private var user_ID : Int? = -1

        private var roomList : MutableList<RoomProperties> = mutableListOf(
            //該user目前已加入的房間, 房間的資料格式為RoomProperties.kt中的data class
        )

        private var isLoggedIn : Boolean = false  //是否已登入

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

        //------User ID------------------
        fun setUserID(id : Int?){
            this.user_ID = id
        }
        fun getUserID() : Int?{
            return user_ID
        }

        //------user待著的房間------------------
        fun setUserRoom(roomList : MutableList<RoomProperties>){
            this.roomList = roomList
        }
        fun getUserRoom() : MutableList<RoomProperties>{
            return roomList
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

        //------登入------------------
        fun logIn(user : NewAddedUser){
            setName(user.name)
            setEmail(user.email)
            setPassword(user.password)
            setUserID(user.id)
            setUserRoom(user.room)

            setStatusToLogin()
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
            return NewAddedUser(
                user_name,
                user_email,
                user_password,
                user_ID,
                roomList
            )
        }

        //------所屬房間------------------
        fun getRoomCount(): Int {
            return roomList.size
        }
        fun getRoomName() : String{
            return roomList[0].roomName
        }
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
            NewAddedUser("ItsZir","10946020@ntub.edu.tw", "114514", 1, mutableListOf()),
            NewAddedUser("user1", "user1@ntub.edu.tw", "000001", null, mutableListOf()),
            NewAddedUser("user2", "user2@ntub.edu.tw", "000002", null, mutableListOf()),
            NewAddedUser("user3", "user3@ntub.edu.tw", "000003", null, mutableListOf()),
            NewAddedUser("test", "test", "0", 0, mutableListOf())
        )

        val tempData = mutableListOf(
            //以下為假資料, 之後這裡要連線至database
            NewAddedUser("ItsZir","10946020@ntub.edu.tw", "114514", null, mutableListOf()),
            NewAddedUser("user1", "user1@ntub.edu.tw", "000001", null, mutableListOf()),
            NewAddedUser("user2", "user2@ntub.edu.tw", "000002", null, mutableListOf()),
            NewAddedUser("user3", "user3@ntub.edu.tw", "000003", null, mutableListOf())
        )

        fun addUserData(n : String, e: String, pw: String){
            list.add(NewAddedUser(n,e,pw, null, mutableListOf()))
        }

        fun removeUser(index : Int,n: String){
            for(user in list){
                if(user.name == n && list.indexOf(user) == index){
                    list.removeAt(index)
                }
            }
        }

        fun findUser(userID : Int) : NewAddedUser{  //用user ID找到人, 回傳人的物件
            return list.filter { it.id == userID }[0]
        }
    }

    class Room_Info{  //表示房間假資料的class
        val rooms : MutableList<RoomProperties> = mutableListOf(  //儲存假資料
            RoomProperties(
                112401,
                "testRoom",
                "114514",
                null,  //devices.findTheDevice(112401)[0].deviceID,  //儲存綁定的設備的ID
                mutableListOf(),
                0
            )
        )

        //用房間的編號跟密碼來鎖定要找的房間物件, 方便調用
        //此function回傳的是物件List(找到的房間)
        fun theRoom(roomID : Int) : MutableList<RoomProperties>{
            return rooms.filter { it.roomNumber == roomID }.toMutableList()
        }
        fun theRoom(num : Int, password : String) : MutableList<RoomProperties>{  //用房間編號跟密碼找
            return rooms.filter { it.roomNumber == num && it.roomPW == password }.toMutableList()
        }

        //------Methods
        fun createTheRoom(name : String, password : String, deviceID : Int){  //創建新房間
            if(loggedInUser.checkStatus()){  //只有登入使用者後才能操作
                //生成新的房間物件
                val newRoom = RoomProperties(
                    111111,  //要用隨機生成的數字作為ID
                    name,  //使用者輸入的房間名稱
                    password,  //使用者輸入的房間密碼
                    deviceID,  //使用者輸入的要綁定的設備ID
                    mutableListOf(),  //預設一個空List來存放使用者
                    0  //房間內的使用者數量
                )

                //將當前的使用者加進新房間的user List裡
                roomJoinTheUser(newRoom, loggedInUser.getDataFormat())

                //用設備ID來找到設備並綁定
                val device = devices.findTheDevice(deviceID)[0]
                devices.bindToTheRoom(device,newRoom)

                //之後再把新房間物件加到代表所有房間列表的List
                rooms.add(newRoom)

                //使用者個人已加入的房間List也要新增這個新房間進去
                // 23/12/08 筆記 : 如果這行在新房間儲存之前先執行的話會找不到房間, 導致切回頁面後顯示房間數量會出問題
                loggedInUser.userJoinToTheRoom(newRoom.roomNumber, newRoom.roomPW)
            }
        }
        private fun deleteTheRoom(num : Int, name : String){  //刪除房間
            val room = rooms.filter { (roomNum, roomName) -> roomNum == num && roomName == name }[0]
            rooms.remove(room)
        }

        fun roomJoinTheUser(room : RoomProperties, user: NewAddedUser){  //新增使用者進去房間
            val newEnteredUser = NewAddedUser(
                user.name,
                user.email,
                user.password,
                user.id,
                user.room
            )

            room.usersInThere.add(newEnteredUser)
            room.usersCount += 1
        }

        public fun RoomGetUserName(room : RoomProperties, index : Int) : String{
            return room.usersInThere[index].name
        }

        public fun RoomGetUserEmail(room : RoomProperties, index : Int) : String{
            return room.usersInThere[index].email
        }
    }

    class Devices{
        //用data class的格式成立的設備list
        val deviceList : MutableList<DevicesProperties> = mutableListOf(
            DevicesProperties(
                112401,
                "112401門禁裝置",
                null  //roomData.theRoom(112401)[0]  //設備綁定的房間
            )
        )

        //用id來找設備, 回傳list型態的資料, 如果檢查ID出來是已綁定的或不存在的設備則回傳空list
        fun findTheDevice(id : Int?) : MutableList<DevicesProperties>{
            if(id == null){  //如果ID是空的或有其他符號就不管
                return mutableListOf()
            }
            else{
                val device = deviceList.filter{ it.deviceID == id }.toMutableList()  //用ID找到已存在的設備, 資料型態還是list
                if(device.isNotEmpty()){  //如果有找到設備
                    if(device[0].boundRoom != null){  //如果該設備已有綁定的房間,則取消
                        device.removeAt(0)  //drop表示把list中的第幾個元素去掉, index寫1則表示把第1個消掉
                    }
                }
                return device
            }
        }

        fun bindToTheRoom(device : DevicesProperties, room : RoomProperties){
            device.boundRoom = room
        }
    }
}

val loggedInUser = GlobalVariables.Logged_In_Users()  //宣告一個物件, 作為已登入的使用者

val Users = GlobalVariables.User_Data()  //模擬資料庫裡所有的user

val roomData = GlobalVariables.Room_Info()  //模擬資料庫裡所有已存在的房間

val enterTime = GlobalVariables.EnterTime()  //當前辨識進出時的時間

val devices = GlobalVariables.Devices()  //資料庫存放的裝置列表