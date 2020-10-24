package com.yts.base.presentation.ui.like

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.yts.base.R
import com.yts.base.databinding.FragmentLikeUserListBinding
import com.yts.base.extension.EventObserver
import com.yts.base.presentation.base.BaseFragment
import com.yts.base.presentation.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_like_user_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LikeUserListFragment : BaseFragment<FragmentLikeUserListBinding>() {
    private val model: LikeUserListViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private val likeUserListAdapter: LikeUserListAdapter by inject() { parametersOf(model) }

    override fun onLayoutId(): Int = R.layout.fragment_like_user_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.model = model
        initAdapter()
    }

    private fun initAdapter() {
        list_like.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = likeUserListAdapter
        }
    }

    override fun observer() {
        mainViewModel.query.observe(viewLifecycleOwner, {
            model.getLikeUserList(it)
        })
        model.deleteLikeUser.observe(viewLifecycleOwner, EventObserver {
            model.getLikeUserList(mainViewModel.query.value ?: "")
        })
        model.likeUserList.observe(viewLifecycleOwner, {
            likeUserListAdapter.submitList(it)
            model.setEmptyLikeUserList(it.size <= 0)
        })

    }
}