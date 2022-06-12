package com.example.wechat

import android.annotation.SuppressLint
import com.example.wechat.contentProvider.ContentProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wechat.contentProvider.MainActivity
import com.example.wechat.show.ShowData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.clear
import kotlinx.android.synthetic.main.activity_main.edit_pwd
import kotlinx.android.synthetic.main.activity_main.edit_user
import kotlinx.android.synthetic.main.activity_main.login
import kotlinx.android.synthetic.main.activity_main.register
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.alterdialog_yes_no.view.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(){

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
       /*
        val oneUser = intent.getSerializableExtra("userInfo") as?User
        edit_user.setText(oneUser?.userId)
        edit_pwd.setText(oneUser?.pwd)*/
        /*val user = getSharedPreferences("user",Context.MODE_PRIVATE)
        val name = user.getString("name","")
        val pwd = user.getString("pwd","")

        edit_user.setText(name)
        edit_pwd.setText(pwd)*/

        val preferences = getSharedPreferences("data",Context.MODE_PRIVATE)
        if(preferences.getBoolean("isRemember",false)){
            edit_user.setText(preferences.getString("name",null))
            edit_pwd.setText(preferences.getString("pwd",null))
            cb.isChecked = true
            if(preferences.getBoolean("isAutomatic",false)){
                zddl.isChecked = true
                val intent = Intent(this, ShowData::class.java)
                startActivity(intent)
            }

        }
        val editor = preferences.edit()

        zddl.setOnCheckedChangeListener { compoundButton, b ->
            System.out.println(b)
            if(zddl.isChecked){
                editor.putBoolean("isAutomatic",true)
            }else{
                editor.putBoolean("isAutomatic",false)
            }
        }

        cb.setOnCheckedChangeListener { compoundButton, b ->
            if(cb.isChecked){
                editor.putBoolean("isRemember",true)
            }else{
                editor.putBoolean("isRemember",true)
            }
        }
        val dbHelper = MyDatabaseHelper(this, "Chat.db",3)
        login.setOnClickListener(){
            var userId = ""
            if(edit_user.text.toString() != null){
                userId = edit_user.text.toString()
            }
            val pwd = edit_pwd.text.toString()
            val db = dbHelper.writableDatabase
            val cursor = db.query("User", null,"username='"+userId+"'",null,null,null,null)
            if(cursor.moveToFirst()){
                do {
                    val username = cursor.getString(cursor.getColumnIndex("username"))
                    val password = cursor.getString(cursor.getColumnIndex("pwd"))
                    if(pwd.length == 0){
                        edit_pwd.setError("用户密码不能为空！")
                    }else if(password != pwd){
                        edit_pwd.setError("密码错误！")
                    } else{
                        if(cb.isChecked){
                            editor.putBoolean("isRemember",true)
                            editor.putString("name",userId)
                            editor.putString("pwd",pwd)
                        }else{
                            editor.clear()
                        }
                        editor.apply()
                        val intent = Intent(this, UserInfo::class.java)
                        startActivity(intent)
                    }
                }while (cursor.moveToNext())
            }else{
                edit_user.setError("用户不存在！")
            }
            cursor.close()
        }

        clear.setOnClickListener(){
            edit_user.setText(null)
            edit_pwd.setText(null)
            cb.isChecked = false
            zddl.isChecked = false
        }

        register.setOnClickListener(){
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

/*
        tx_cp.setOnClickListener(){
            val intent = Intent(this,ContentProvider::class.java)
            startActivity(intent)
        }

        tx_lxr.setOnClickListener(){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
*/

    }




}