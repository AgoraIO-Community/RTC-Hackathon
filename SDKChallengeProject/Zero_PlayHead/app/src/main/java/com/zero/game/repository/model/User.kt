package com.zero.game.repository.model

data class User(val code:Int,val msg:String,val data:Data)

data class Data(val session_key:String)

data class UserId(val uuid:String,val name:String)