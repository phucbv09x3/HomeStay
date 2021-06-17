package com.kujira.homestay.ui.list

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.list_fragment.*
import com.kujira.homestay.R
import com.kujira.homestay.data.model.RepoModel
import com.kujira.homestay.databinding.ListFragmentBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.utils.widget.baseadapter.BaseAdapter

/**
 * Created by OpenYourEyes on 11/2/20
 */
class ListFragment : BaseFragment<ListViewModel, ListFragmentBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    override fun createViewModel(): Class<ListViewModel> {
        return ListViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.list_fragment
    }

    override fun initView() {
        swipeRefreshLayout.setOnRefreshListener(this)
        val listAdapter = BaseAdapter<RepoModel>(activity, R.layout.view_repo_list_item) {
            replaceFragment(R.id.aboutFragment)
        }
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }
    }

    override fun bindViewModel() {
        viewModel.listRepo.observe(viewLifecycleOwner, {
            val adapter = recyclerView.adapter as BaseAdapter<RepoModel>
            adapter.updateList(it)
        })
        if (!viewModel.listRepoHasValue()) {
            viewModel.getListRepo()
        }

    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = false
        viewModel.getListRepo()
    }
}