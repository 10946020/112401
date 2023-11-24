package com.example.project112401

import android.app.Application

class GlobalVariables {  //存放全域變數
    class Logged_In_Users{
        private var user_name : String = ""
        private var user_email : String = ""
        private var user_password : String = ""
        private var isLoggedIn : Boolean = false

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
}

val loggedInUser = GlobalVariables.Logged_In_Users()  //宣告一個物件, 作為已登入的使用者

val Users = GlobalVariables.User_Data()
