package com.example.wechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class FriendsAdapter(
    context: Context,
    private val resourceId:Int,
    data: ArrayList<Friend>
):ArrayAdapter<Friend>(context,resourceId,data) {
    inner class ViewHolder(val friendImg:ImageView, val friendName:TextView, val friendIntroduce:TextView, val friendState:TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val viewHolder : ViewHolder
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId,parent,false)
            val friendImg:ImageView = view.findViewById(R.id.friendImg)
            val friendName: TextView = view.findViewById(R.id.friendName)
            val friendIntroduce: TextView = view.findViewById(R.id.friendIntroduce)
            val friendState: TextView = view.findViewById(R.id.friendState)
            viewHolder = ViewHolder(friendImg,friendName,friendIntroduce,friendState)
            view.tag = viewHolder

        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val friend = getItem(position)
        if(friend != null){
            viewHolder.friendImg.setImageResource(friend.imgId)
            viewHolder.friendName.text = friend.name
            viewHolder.friendIntroduce.text = friend.introduce
            viewHolder.friendState.text = friend.state
        }

        return view
    }
}