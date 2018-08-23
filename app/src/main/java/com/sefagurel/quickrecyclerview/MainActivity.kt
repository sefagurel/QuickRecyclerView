/*
 * Created by Sefa Gürel on 23.08.2018 15:15
 * Copyright (c) 2018 . All rights reserved.
 */

package com.sefagurel.quickrecyclerview

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sefagurel.library.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//      val adapter = QuickAdapter(quickDiff)
        val adapter = QuickAdapter()

        adapter.addRenderer<Model1>(R.layout.item1) { view, model ->
            view.findViewById<TextView>(R.id.txt1).text = model.title
        }

        adapter.addRenderer<Model2>(R.layout.item2) { view, model ->
            view.findViewById<TextView>(R.id.txt2).text = model.name
        }

        recyclerview.setVerticalLinearLayoutManager()
        recyclerview.adapter = adapter

        adapter.setItems(getList())

    }

    fun getList(): List<QuickModel> {
        val list = mutableListOf<QuickModel>()
        list.add(Model1(1, "sefa"))
        list.add(Model2(2, "sefa", 29))
        list.add(Model1(3, "serkan"))
        list.add(Model2(4, "serkan", 21))
        return list
    }

    var quickDiff = object : QuickDiff {
        override fun areItemsTheSame(oldItem: QuickModel, newItem: QuickModel): Boolean {
            when {
                oldItem is Model1 && newItem is Model1 -> return oldItem.id == newItem.id
                oldItem is Model2 && newItem is Model2 -> return oldItem.id == newItem.id
                else -> return false
            }
        }

        override fun areContentsTheSame(oldItem: QuickModel, newItem: QuickModel): Boolean {
            when {
                oldItem is Model1 && newItem is Model1 -> return oldItem.title == newItem.title
                oldItem is Model2 && newItem is Model2 -> return oldItem.name == newItem.name
                else -> return false
            }
        }
    }
}
