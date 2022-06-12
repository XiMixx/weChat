package com.example.wechat

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.example.wechat.R
import com.example.wechat.UserInfo
import com.example.wechat.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.clear
import kotlinx.android.synthetic.main.activity_register.edit_pwd
import kotlinx.android.synthetic.main.activity_register.edit_user
import kotlinx.android.synthetic.main.activity_register.register
import java.io.*
import java.lang.StringBuilder

class Register : AppCompatActivity() , View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        val schoolData= listOf("广东东软学院","广东中医药大学","广东工业大学","华南理工大学",
            "华南师范大学","华南农业大学")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,schoolData)
        actv_school.setAdapter(adapter)
        val cityData= listOf("佛山","广州","深圳","揭阳","汕头","潮州","惠州")
        val adapterCity = ArrayAdapter(this,android.R.layout.simple_list_item_1,cityData)
        sp_city.adapter = adapterCity
        val dbHelper = MyDatabaseHelper(this, "Chat.db",3)
/*
        val inputText = load()
        if(inputText.isNotEmpty()){
            edit_user.setText(inputText)
            edit_user.setSelection(inputText.length)
        }
*/
        //1、
        var sex = ""
        rg_sex.setOnCheckedChangeListener { radioGroup, i ->
            if(rb_girl.getId() == i){
                sex = rb_girl.text.toString()
            } else if(rb_boy.getId() == i){
                sex = rb_boy.text.toString()
            }
        }
        var birth = ""
        dp_birth.init(2000,1,1){dp,year,month,day ->
            birth = "${year}-${month + 1}-${day}"
        }
        System.out.println()
        System.out.println()
        register.setOnClickListener(){
            val userId = edit_user.text.toString()
            val pwd = edit_pwd.text.toString()
            val rpwd = edit_rpwd.text.toString()
            val email = edit_email.text.toString()
            val school = actv_school.text.toString()
            val city = sp_city.getItemAtPosition(sp_city.selectedItemPosition).toString()
            val emailStr = Regex("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
            System.out.println("email="+emailStr.matches(email))
//            System.out.println(userId+"\n"+pwd+"\n"+rpwd+"\n"+email+"\n"+sex+"\n"+school+"\n"+city+"\n"+birth)
            if(userId.length < 4 || userId.length > 10){
                edit_user.setError("用户账号必须为4-10个字符！")
            }else if(userId.contains(" ")){
                edit_user.setError("用户账号不能包含空格！")
            }else if(pwd.length == 0){
                edit_pwd.setError("密码不能为空！")
            }else if(pwd != rpwd){
                edit_rpwd.setError("两次密码不一致！")
            /*}else if(emailStr.matches(email)==false){
                edit_email.setError("邮箱格式不正确！")
                //emailImg.setImageResource(R.drawable.ic_launcher_background)
*/            }else {
                val db = dbHelper.writableDatabase
                val values1 = ContentValues().apply {
                    put("username",userId)
                    put("pwd",pwd)
                    put("email",email)
                    put("sex",sex)
                    put("school",school)
                    put("city",city)
                    put("birth",birth)
                }
                db.insert("User",null, values1)
                val intent = Intent(this, MainActivity::class.java)
                //intent.putExtra("userInfo",userId+pwd+email)
                val oneUser = User(userId,pwd)
                intent.putExtra("userInfo",oneUser)
                startActivity(intent)
            }
        }
        clear.setOnClickListener(this)
    }

/*
    private fun load(): String {
        val content = StringBuilder()
        try {
            val input = openFileInput("openFileOutput")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine { content.append(it) }
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
        return content.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        val inputText = edit_user.text.toString()
        save(inputText)
    }

    private fun save(inputText: String) {
        try {
            val output = openFileOutput("openFileOutput",Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(inputText)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
*/

    //2、实现接口，重写抽象方法 onclick() 带有参数 View?
    override fun onClick(p0: View?) {
        //TODO("Not yet implemented")
        when(p0?.id){
            R.id.clear -> {
                //Toast.makeText(this,"clear",Toast.LENGTH_SHORT).show()
                edit_user.setText(null)
                edit_pwd.setText(null)
                edit_rpwd.setText(null)
                edit_email.setText(null)
                actv_school.setText(null)
            }
        }
    }

    //3、在其按钮添加一个属性：onclick（属性名直接用于自定义函数名，函数记得带 View? 参数）
    fun userLogin(v:View?){
        when(v?.id){
            R.id.login -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}