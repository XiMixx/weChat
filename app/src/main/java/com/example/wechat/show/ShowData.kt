package com.example.wechat.show

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wechat.R
import kotlinx.android.synthetic.main.activity_fb.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_data.*
import java.io.*
import java.lang.StringBuilder

class ShowData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "")
        val pwd = prefs.getString("pwd", "")
        tv_show.text = "用户名："+name+"\n密码："+pwd


        btn_fb.setOnClickListener(){
            val inputText = et_fb.text.toString()
            System.out.println("btn_fb.setOnClickListener:"+et_fb.text.toString())
            save(inputText)
            val intent = Intent(this, Fb::class.java)
            startActivity(intent)
        }
    }


    private fun save(inputText: String) {
        try {
            val output = openFileOutput("FileData", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(inputText)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }


}