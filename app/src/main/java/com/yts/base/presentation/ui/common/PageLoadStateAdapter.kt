package com.yts.base.presentation.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greencar.fieldworksystem.presentation.common.OnPageLoadStateRefreshListener
import com.yts.base.R
import com.yts.base.databinding.ItemNetworkStateBinding

class PageLoadStateAdapter(
    private val listener: OnPageLoadStateRefreshListener
) : LoadStateAdapter<PageLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val item = DataBindingUtil.inflate<ItemNetworkStateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_network_state,
            parent,
            false
        )
        return NetworkStateItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    inner class NetworkStateItemViewHolder(var binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bindTo(loadState: LoadState) {
            binding.hasError = loadState is LoadState.Error
            binding.retryButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.retryButton -> {
                    listener.refresh()
                }
            }
        }
    }

}