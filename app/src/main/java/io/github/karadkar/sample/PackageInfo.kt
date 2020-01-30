package io.github.karadkar.sample

import android.graphics.drawable.Drawable

data class PackageInfo(
    val label: String,
    val packageName: String,
    val iconDrawable: Drawable
)