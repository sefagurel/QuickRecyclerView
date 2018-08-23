/*
 * Created by Sefa Gürel on 23.08.2018 15:27
 * Copyright (c) 2018 . All rights reserved.
 */

package com.sefagurel.library

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Type


fun RecyclerView.setVerticalLinearLayoutManager() {
    val llm = LinearLayoutManager(context)
    llm.orientation = LinearLayoutManager.VERTICAL
    setLayoutManager(llm)
}

fun RecyclerView.setHorizontalLinearLayoutManager() {
    val llm = LinearLayoutManager(context)
    llm.orientation = LinearLayoutManager.HORIZONTAL
    setLayoutManager(llm)
}

inline fun <reified KN : QuickModel> QuickAdapter.addRenderer(@LayoutRes resId: Int,
                                                              noinline kninit: (view: View, model: KN) -> Unit): QuickAdapter {
    val typ: Type = KN::class.javaObjectType
    val render = QuickRendererExt(resId, kninit, typ)
    registerRenderer(render)
    return this
}


inline fun <reified KN : QuickModel> myRenderer(@LayoutRes resId: Int,
                                                noinline kninit: (view: View, model: KN) -> Unit): QuickRendererExt<KN> {
    val typ: Type = KN::class.javaObjectType
    return QuickRendererExt(resId, kninit, typ)
}