/**
 * Created by Sefa Gürel on $today
 * Copyright (c) $today.year . All rights reserved.
 */

package com.sefagurel.library

interface QuickDiff {


    fun areItemsTheSame(oldItem: QuickModel, newItem: QuickModel): Boolean

    fun areContentsTheSame(oldItem: QuickModel, newItem: QuickModel): Boolean

}