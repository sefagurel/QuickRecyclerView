/*
 * Created by Sefa Gürel on 23.08.2018 15:40
 * Copyright (c) 2018 . All rights reserved.
 */

package com.sefagurel.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.lang.reflect.Type

abstract class QuickRenderer<M : QuickModel>(@LayoutRes val layoutId: Int) {

    abstract fun getType(): Type

    fun createViewHolder(parent: ViewGroup): QuickViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return QuickViewHolder(itemView)
    }

    abstract fun bindView(holder: QuickViewHolder, model: M)

}