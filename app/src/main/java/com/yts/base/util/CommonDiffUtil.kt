package com.yts.base.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.yts.domain.entity.User

class CommonDiffUtil<B> : DiffUtil.ItemCallback<B>() {

    override fun areItemsTheSame(oldItem: B, newItem: B): Boolean {
        if (newItem is User && oldItem is User) {
            return (oldItem.id == newItem.id) && (newItem.login == oldItem.login) && (newItem.like == oldItem.like) && (newItem.avatar_url == oldItem.avatar_url)
        }
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: B, newItem: B): Boolean {
        return oldItem == newItem
    }


}