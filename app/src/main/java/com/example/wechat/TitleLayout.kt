package com.example.wechat

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wechat.R
import kotlinx.android.synthetic.main.activity_title_layout.view.*

class TitleLayout(context: Context, attrs:AttributeSet) : LinearLayout(context,attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.activity_title_layout,this)
        title.text = "聊天APP"
        btn_back.setOnClickListener{
            val activity = context as Activity
            activity.finish()
        }
    }
}