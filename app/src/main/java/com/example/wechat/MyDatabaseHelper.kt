package com.example.wechat

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name:String, version:Int) : SQLiteOpenHelper(context,name,null,version) {

    private val createUser = "create table User (" +
            "id integer primary key autoincrement," +
            "username text," +
            "pwd text,"+
            "email text,"+
            "sex text,"+
            "school text,"+
            "city text,"+
            "birth text)"

    private val createFriend = "create table Friends (" +
            "id integer primary key autoincrement," +
            "friendName text," +
            "friendIntroduce text,"+
            "friendState text)"

    private val createChatMsg = "create table ChatMsg (" +
            "id integer primary key autoincrement," +
            "friendName text," +
            "friendIntroduce text,"+
            "friendState text)"

    private val createContacts = "create table Contacts (" +
            "id integer primary key autoincrement," +
            "name text," +
            "phoneNumber text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUser)
        db?.execSQL(createFriend)
        Toast.makeText(context, "create success", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists User")
        db?.execSQL("drop table if exists Friends")

        /*if(oldVersion<=2){
            db?.execSQL("alter table User add column email text")
            db?.execSQL("alter table User add column sex text")
            db?.execSQL("alter table User add column school text")
            db?.execSQL("alter table User add column city text")
            db?.execSQL("alter table User add column birth text")
        }*/
        if(oldVersion<=3){
            db?.execSQL(createContacts)
        }
        onCreate(db)
    }
}