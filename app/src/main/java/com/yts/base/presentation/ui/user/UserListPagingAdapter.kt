package com.yts.base.presentation.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yts.base.R
import com.yts.base.databinding.ItemUserBinding
import com.yts.base.util.CommonDiffUtil
import com.yts.domain.entity.User
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListPagingAdapter(val model: UserListViewModel) :
    PagingDataAdapter<User, UserListPagingAdapter.UserViewHolder>(CommonDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val item = DataBindingUtil.inflate<ItemUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_user,
            parent,
            false
        )
        return UserViewHolder(item)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User?) {
            binding.model = model
            data?.let {
                binding.user = it
            }
            checkedUser(data)
        }

        private fun checkedUser(user: User?) {
            with(itemView) {
                model.viewModelScope.launch {
                    check_user.isChecked = if (user != null) {
                        model.getLikeUser(user.id)
                    } else {
                        false
                    }
                }
            }
        }
    }

}