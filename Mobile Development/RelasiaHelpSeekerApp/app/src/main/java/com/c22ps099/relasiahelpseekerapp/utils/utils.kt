package com.c22ps099.relasiahelpseekerapp.utils

import android.view.View

fun visibility(visible: Boolean): Int {
    return if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}