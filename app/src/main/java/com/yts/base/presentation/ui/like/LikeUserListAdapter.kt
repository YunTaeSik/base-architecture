package com.yts.base.presentation.ui.like

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yts.base.R
import com.yts.base.databinding.ItemLikeUserBinding
import com.yts.base.databinding.ItemUserBinding
import com.yts.base.presentation.ui.user.UserListViewModel
import com.yts.base.util.CommonDiffUtil
import com.yts.domain.entity.User
import javax.inject.Inject

class LikeUserListAdapter(val model: LikeUserListViewModel) :
    ListAdapter<User, LikeUserListAdapter.UserViewHolder>(CommonDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val item = DataBindingUtil.inflate<ItemLikeUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_like_user,
            parent,
            false
        )
        return UserViewHolder(item)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(var binding: ItemLikeUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            binding.model = model
            binding.user = data
        }


    }

}