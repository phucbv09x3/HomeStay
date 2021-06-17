package com.kujira.homestay.ui.home

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentHomeBinding
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun createViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        with(dataBinding.rcyListProvince) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeAdapter(mutableListOf())
        }

    }

    override fun bindViewModel() {
        viewModel.getListProvince()
        viewModel.listProvince.observe(this, {
            Log.d("lisProvincesVMD1","$it")
            (rcy_list_province.adapter as HomeAdapter).setList(it)
        })
    }
}