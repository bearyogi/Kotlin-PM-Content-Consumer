package com.example.contentprovidertaker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.contentprovidertaker.R
import com.example.contentprovidertaker.objects.UserData

class UserDataAdapter(context: Context, userDatas: ArrayList<UserData>) :
    ArrayAdapter<UserData?>(context, 0, userDatas as List<UserData?>) {
    private lateinit var loginTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var currentUserData: UserData

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        initUI(listItemView!!)

        currentUserData = getItem(position)!!
        loginTextView.text = currentUserData.login
        passwordTextView.text = currentUserData.password

        return listItemView
    }

    private fun initUI(listItemView: View){
        loginTextView = listItemView.findViewById(R.id.login) as TextView
        passwordTextView = listItemView.findViewById(R.id.password) as TextView
    }
}