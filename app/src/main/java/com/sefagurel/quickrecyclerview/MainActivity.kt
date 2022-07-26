/*
 * Created by Sefa Gürel on 23.08.2018 15:15
 * Copyright (c) 2018 . All rights reserved.
 */

package com.sefagurel.quickrecyclerview

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sefagurel.library.*
import com.sefagurel.quickrecyclerview.models.DateModel
import com.sefagurel.quickrecyclerview.models.PersonModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//      val adapter = QuickAdapter()
        val adapter = QuickAdapter(quickDiff)

        adapter.addRenderer<DateModel>(R.layout.item1) { view, model ->
            view.findViewById<TextView>(R.id.txt1).text = model.dateText
        }

        adapter.addRenderer<PersonModel>(R.layout.item2) { view, model ->
            view.findViewById<TextView>(R.id.txt2).text = model.name
        }

        recyclerview.setVerticalLinearLayoutManager()
        recyclerview.setItemDivider(R.drawable.divider_vertical)
        recyclerview.adapter = adapter

        adapter.setItems(getList())

    }

    fun getList(): List<QuickModel> {
        val list = mutableListOf<QuickModel>()
        list.add(DateModel("Today"))
        list.add(PersonModel(2, "Sefa Gürel", 29))
        list.add(DateModel("Yesterday"))
        list.add(PersonModel(4, "Serkan Gürel", 21))
        list.add(PersonModel(4, "Selim TOKSAL", 25))
        return list
    }

    var quickDiff = object : QuickDiff {
        override fun areItemsTheSame(oldItem: QuickModel, newItem: QuickModel): Boolean {
            when {
                oldItem is DateModel && newItem is DateModel -> return oldItem.dateText == newItem.dateText
                oldItem is PersonModel && newItem is PersonModel -> return oldItem.id == newItem.id
                else -> return false
            }
        }

        override fun areContentsTheSame(oldItem: QuickModel, newItem: QuickModel): Boolean {
            when {
                oldItem is DateModel && newItem is DateModel -> return oldItem.dateText == newItem.dateText
                oldItem is PersonModel && newItem is PersonModel -> return oldItem.name == newItem.name
                else -> return false
            }
        }
    }
}
