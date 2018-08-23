/*
 * Created by Sefa Gürel on 23.08.2018 15:36
 * Copyright (c) 2018 . All rights reserved.
 */

package com.sefagurel.library

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Type

class QuickAdapter(var quickDiff: QuickDiff? = null) : RecyclerView.Adapter<QuickViewHolder>() {

    private val mItems = ArrayList<QuickModel>()
    private val mRenderers = ArrayList<QuickRenderer<QuickModel>>()
    private var dataVersion = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickViewHolder {
        val renderer = mRenderers.get(viewType)
        if (renderer != null) {
            return renderer.createViewHolder(parent)
        }
        throw RuntimeException("Not supported Item View Type: $viewType")
    }

    fun <K : QuickModel, R : QuickRenderer<K>> registerRenderer(renderer: R) {
        val type = renderer.getType()
        if (!isHaveType(type)) {
            mRenderers.add(renderer as QuickRenderer<QuickModel>)
        } else {
            throw RuntimeException("SMRenderer already exist with this type: $type")
        }
    }

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int) {
        val item = getItem(position)
        val index = getTypeIndex(item.javaClass)
        val renderer = mRenderers.get(index)
        if (renderer != null) {
            renderer.bindView(holder, item)
        } else {
            throw RuntimeException("Not supported View Holder: ${holder.javaClass.simpleName}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getTypeIndex(getItem(position).javaClass)
    }

    fun getItem(position: Int): QuickModel {
        return mItems[position]
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun setItems(items: List<QuickModel>) {
        replace(items)
    }

    fun isHaveType(type: Type): Boolean {
        return getTypeIndex(type) > -1
    }

    fun getTypeIndex(type: Type): Int {
        mRenderers.forEachIndexed { index, viewRenderer ->
            if (viewRenderer.getType().equals(type)) {
                return index
            }
        }
        return -1
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private fun replace(update: List<QuickModel>?) {

        dataVersion++

        if (update == null) {

            val oldSize = mItems.size
            mItems.clear()
            notifyItemRangeRemoved(0, oldSize)

        } else {

            val startVersion = dataVersion
            val oldItems = mItems

            object : AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                override fun doInBackground(vararg voids: Void): DiffUtil.DiffResult {

                    return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                        override fun getOldListSize(): Int {
                            return oldItems.size
                        }

                        override fun getNewListSize(): Int {
                            return update.size
                        }

                        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                            val oldItem = oldItems[oldItemPosition]
                            val newItem = update[newItemPosition]
                            return quickDiff?.areItemsTheSame(oldItem, newItem)
                                    ?: oldItem.equals(newItem)
                        }

                        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                            val oldItem = oldItems[oldItemPosition]
                            val newItem = update[newItemPosition]
                            return quickDiff?.areContentsTheSame(oldItem, newItem)
                                    ?: oldItem.equals(newItem)
                        }
                    })
                }

                override fun onPostExecute(diffResult: DiffUtil.DiffResult) {
                    if (startVersion != dataVersion) {
                        return
                    }
                    mItems.clear()
                    mItems.addAll(update)
                    diffResult.dispatchUpdatesTo(this@QuickAdapter)
                }
            }.execute()
        }
    }

}