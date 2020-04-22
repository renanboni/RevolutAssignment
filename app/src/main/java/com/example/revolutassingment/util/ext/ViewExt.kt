package com.example.revolutassingment.util.ext

import android.view.View

fun View.getDrawableFromName(name: String, defaultDrawable: Int = 0): Int {
    val id = resources.getIdentifier(
        name,
        "drawable",
        context.packageName
    )

    return if (id == 0) {
        defaultDrawable
    } else {
        id
    }
}