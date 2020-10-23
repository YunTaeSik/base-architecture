package com.yts.base.extension

import android.content.Context
import android.widget.Toast

fun Context.makeToast(id: Int) {
    try {
        Toast.makeText(this, this.getString(id), Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
