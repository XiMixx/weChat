package com.example.wechat.show

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wechat.R
import kotlinx.android.synthetic.main.activity_fb.*
import kotlinx.android.synthetic.main.activity_show_data.*
import java.io.*
import java.lang.StringBuilder

class Fb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fb)

        val inputText = load()
        if(inputText.isNotEmpty()){
            tv_fb.setText(inputText)
        }
    }

    private fun load(): String {
        val content = StringBuilder()
        try {
            val input = openFileInput("FileData")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine { content.append(it) }
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
        return content.toString()
    }

}