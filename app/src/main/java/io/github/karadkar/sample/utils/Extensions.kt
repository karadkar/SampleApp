package io.github.karadkar.sample.utils

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

inline fun <reified T : ViewDataBinding> Int.createBinding(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean = false
): T {
    return DataBindingUtil.inflate(layoutInflater, this, parent, attachToParent)
}

val Int.DpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.PxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()