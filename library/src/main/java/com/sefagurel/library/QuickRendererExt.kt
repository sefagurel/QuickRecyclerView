/*
 * Created by Sefa Gürel on 23.08.2018 15:40
 * Copyright (c) 2018 . All rights reserved.
 */

package com.sefagurel.library

import android.view.View
import java.lang.reflect.Type

class QuickRendererExt<V : QuickModel>(resId: Int,
                                       private var kninit: (view: View, model: V) -> Unit,
                                       private var typ: Type) : QuickRenderer<V>(resId) {
    override fun getType(): Type {
        return typ
    }

    override fun bindView(holder: QuickViewHolder, model: V) {
        kninit(holder.rootView, model)
    }

}