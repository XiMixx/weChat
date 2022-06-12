package com.example.wechat

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_title_layout.*
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.alterdialog_yes_no.*
import kotlinx.android.synthetic.main.alterdialog_yes_no.view.*
import kotlinx.android.synthetic.main.friend_item.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class UserInfo : AppCompatActivity() {
    private val friendList = ArrayList<Friend>()
    private var mAlertDialog: AlertDialog? = null
    private fun initStar(){
        friendList.add(Friend("钟南山", R.drawable.ic_launcher_background,"教育科研工作者、医生","在线"))
        friendList.add(Friend("申纪兰", R.drawable.ic_launcher_background,"全国劳动模范、全国三八红旗手、全国道德模范、第十三届全国人大代表、全国脱贫攻坚“奋进奖“","离线"))
/*        friendList.add(Friend("孙家栋", R.drawable.ic_launcher_background,"中科院院士，探月工程总设计师","在线"))
        friendList.add(Friend("李延年", R.drawable.ic_launcher_background,"军人","离线"))
        friendList.add(Friend("张富清", R.drawable.ic_launcher_background,"全国道德模范、时代楷模、全国模范退伍军人","离线"))
        friendList.add(Friend("袁隆平", R.drawable.ic_launcher_background,"杂交水稻专家","在线"))
        friendList.add(Friend("黄旭华", R.drawable.ic_launcher_background,"核潜艇设计师","在线"))
        friendList.add(Friend("屠呦呦", R.drawable.ic_launcher_background,"诺贝尔生理学或医学奖、创制抗疟药—青蒿素和双氢青蒿素","离线"))
        friendList.add(Friend("周兴铭", R.drawable.ic_launcher_background,"中国科学院院士","在线"))
        friendList.add(Friend("叶培建", R.drawable.ic_launcher_background,"教育科研工作者","离线"))
        friendList.add(Friend("王赤", R.drawable.ic_launcher_background,"中国工农红军、中国共产党员","离线"))
        friendList.add(Friend("戴永久", R.drawable.ic_launcher_background,"教育科研工作者","在线"))
        friendList.add(Friend("张学友", R.drawable.ic_launcher_background,"歌手、演员","在线"))
        friendList.add(Friend("刘德华", R.drawable.ic_launcher_background,"歌手、演员","离线"))
        friendList.add(Friend("孙俪", R.drawable.ic_launcher_background,"演员","离线"))
        friendList.add(Friend("古天乐", R.drawable.ic_launcher_background,"歌手","在线"))
        friendList.add(Friend("郭富强", R.drawable.ic_launcher_background,"歌手","离线"))
        friendList.add(Friend("张爱玲", R.drawable.ic_launcher_background,"歌手","在线"))
        friendList.add(Friend("胡歌", R.drawable.ic_launcher_background,"演员","离线"))*/
    }
    fun Context.showDialogYN( ) {
        if (mAlertDialog != null&& this == mAlertDialog!!.context && mAlertDialog!!.isShowing ) {
            //表示在同一个activity，已经显示了，就不再显示
            return
        }
        if (mAlertDialog == null || this != mAlertDialog!!.context) {
                mAlertDialog = AlertDialog.Builder(this, R.style.TransparentDialog).create()
        }
        val view1 = View.inflate(this, R.layout.alterdialog_yes_no, null)
        mAlertDialog!!.setView(view1)
        mAlertDialog!!.show()
        val stateData = listOf("在线","离线")
        val adapterState = ArrayAdapter(this,android.R.layout.simple_list_item_1,stateData)
        view1.addFriendState.adapter = adapterState
        var state = stateData[0]
        val dbHelper = MyDatabaseHelper(this, "Chat.db",1)
        view1.addFriendState.setOnItemSelectedListener( object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                state = stateData[position]
                System.out.println("状态1"+state)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                state = stateData[0]
            }
        })
        view1.btn_add.setOnClickListener {
            val name = view1.addFriendName.text.toString()
            val introduce = view1.addFriendIntroduce.text.toString()
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                put("friendName",name)
                put("friendIntroduce",introduce)
                put("friendState",state)
            }
            db.insert("Friends",null, values1)
            //friendList.add(Friend(name, R.drawable.ic_launcher_background,introduce,state))
            mAlertDialog!!.dismiss()
        }
        view1.btn_cancel.setOnClickListener {
            mAlertDialog!!.dismiss()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        supportActionBar?.hide()
        //添加添加朋友按钮
        val btn_add = Button(this)
        btn_add.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        btn_add.setText("＋")
        btn_add.setId(View.generateViewId())
//        titleLayout.addView(btn_add)

        /*val oneUser = intent.getSerializableExtra("userInfo") as?Users
        show_user_info.text = oneUser?.userId+","+oneUser?.pwd*/
        initStar()
        val adapter = FriendsAdapter(this,R.layout.friend_item,friendList)
        lv.adapter = adapter
        lv.setOnItemClickListener { adapterView, view, i, l ->
            val friend = friendList[i]
            val intent = Intent(this,Chat::class.java)
            val oneUser = Friend(friend.name,friend.imgId,friend.introduce,friend.state)
            intent.putExtra("userInfo",friend)
            startActivity(intent)
        }
        //添加按钮绑定事件
        if(btn_add != null){
            btn_add.setOnClickListener(){
                showDialogYN()
                val adapter = FriendsAdapter(this,R.layout.friend_item,friendList)
                lv.adapter = adapter
            }
        }
        //长按删除绑定事件
        lv.setOnItemLongClickListener { adapterView, view, i, l ->
            val friend = friendList[i]
            System.out.println("删除"+friend)
//            friendList.add(Friend("钟南山", R.drawable.ic_launcher_background,"教育科研工作者、医生","在线"))
            friendList.remove(friend)
            val adapter = FriendsAdapter(this,R.layout.friend_item,friendList)
            lv.adapter = adapter
            System.out.println("删除后："+friendList)
            true
        }

        //强制下线
        Timer().schedule(object : TimerTask() {
            override fun run() {
                //需要执行的任务
                val intent=Intent("com.example.wechat.FORCE_OFFLINE")
                startActivity(intent)
            }
        }, 1800000)
    }

}