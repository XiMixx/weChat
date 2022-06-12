package com.example.wechat

import java.io.Serializable

data class Friend(val name:String, val imgId:Int, val introduce:String, val state:String):
    Serializable