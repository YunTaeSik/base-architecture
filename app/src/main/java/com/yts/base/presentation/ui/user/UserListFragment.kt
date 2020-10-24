package com.yts.base.presentation.ui.user

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greencar.fieldworksystem.presentation.common.OnPageLoadStateRefreshListener
import com.yts.base.R
import com.yts.base.databinding.FragmentUserListBinding
import com.yts.base.presentation.base.BaseFragment
import com.yts.base.presentation.ui.MainViewModel
import com.yts.base.presentation.ui.common.PageLoadStateAdapter
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserListFragment : BaseFragment<FragmentUserListBinding>(), OnPageLoadStateRefreshListener {
    private val model: UserListViewModel by sharedViewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private val userListPagingAdapter: UserListPagingAdapter by inject() { parametersOf(model) }

    override fun onLayoutId(): Int = R.layout.fragment_user_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.model = model
        initAdapter()
    }

    private fun initAdapter() {
        list_user.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                userListPagingAdapter.withLoadStateFooter(PageLoadStateAdapter(this@UserListFragment))
        }
        userListPagingAdapter.addLoadStateListener {
            model.loadingUserList(it.prepend is LoadState.Loading || it.append is LoadState.Loading || it.refresh is LoadState.Loading)
        }
    }

    override fun observer() {
        mainViewModel.query.observe(viewLifecycleOwner, {
            model.getUserList(it)
        })
        model.userList.observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenCreated {
                userListPagingAdapter.submitData(it)
            }
        })
    }

    override fun refresh() {
        userListPagingAdapter.refresh()
    }
}