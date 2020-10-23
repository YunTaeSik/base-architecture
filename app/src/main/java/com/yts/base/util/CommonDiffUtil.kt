package com.yts.base.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class CommonDiffUtil<B> : DiffUtil.ItemCallback<B>() {

    override fun areItemsTheSame(oldItem: B, newItem: B): Boolean {
/*        if (newItem is Book && oldItem is Book) {
            return oldItem.url == newItem.url
        }*/
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: B, newItem: B): Boolean {
        return oldItem == newItem
    }


}