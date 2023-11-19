package com.example.project112401

import android.app.Application

class GlobalVariables {  //存放全域變數
    class Logged_In_Users{
        private var user_name : String = ""
        private var user_email : String = ""
        private var user_password : String = ""

        //基本變數的設定跟呼叫方法
        fun setName(name: String){
            this.user_name = name
        }
        fun getName(): String{
            return user_name
        }
        fun setEmail(e: String){
            this.user_email = e
        }
        fun getEmail(): String{
            return user_email
        }
        fun setPassword(pw: String){
            this.user_password = pw
        }
        fun getPassword(): String{
            return user_password
        }

        fun LogOut(){
            user_name = ""
            user_email = ""
            user_password = ""
        }
    }
}

val loggedInUser = GlobalVariables.Logged_In_Users()