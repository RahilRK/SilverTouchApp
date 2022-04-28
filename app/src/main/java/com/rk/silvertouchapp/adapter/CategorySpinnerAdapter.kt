package com.rk.silvertouchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.rk.silvertouchapp.R
import com.rk.silvertouchapp.model.Category

class
CategorySpinnerAdapter(
    private val context: Context,
    private val arrayList: java.util.ArrayList<Category>
    ) : BaseAdapter() {

//    var inflter: LayoutInflater? = null

//    init {
//        this.inflter = LayoutInflater.from(context)
//        this.arrayList = list
//    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.category_spinner_layout, parent, false)
        val categoryName = convertView!!.findViewById<TextView>(R.id.categoryName)
        categoryName.text = arrayList!![position].categoryName
        return convertView
    }
}