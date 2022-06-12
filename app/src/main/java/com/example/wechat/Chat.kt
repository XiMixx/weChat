package com.example.wechat

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.chatTitleLayout
import kotlinx.android.synthetic.main.activity_title_layout.*
import kotlinx.android.synthetic.main.activity_title_layout.view.*
import kotlinx.android.synthetic.main.activity_title_layout.view.title
import kotlinx.android.synthetic.main.activity_user_info.*

class Chat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()
        //标题名改为好友名
        val friend = intent.getSerializableExtra("userInfo") as?Friend
        //show.text = oneUser?.name
        chatTitleLayout.title.text = friend?.name

        send.setOnClickListener(){
            val message = edit_message.text.toString()
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
            val llContainer = LinearLayout(this)
            llContainer.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            llContainer.setOrientation(LinearLayout.HORIZONTAL)
            llContainer.setGravity(Gravity.RIGHT)

            val tv = TextView(this)
            // setting height and width
            tv.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            // setting text
            tv.setText(message)
            tv.setBackgroundColor(Color.GREEN)
            tv.setPadding(20,20,20,20)
            tv.setBackgroundResource(R.drawable.radius)
            /*tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
            tv.setTextColor(Color.MAGENTA)*/
            llContainer.addView(tv)
            ll?.addView(llContainer)
            edit_message.setText(null)
        }
    }
}