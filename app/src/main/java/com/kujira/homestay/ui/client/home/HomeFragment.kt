package com.kujira.homestay.ui.client.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.databinding.FragmentHomeBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.client.map.MapActivity
import com.kujira.homestay.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(), IClickOnList {
    override fun createViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        activity.btn_home.setTextColor(context.getColor(R.color.rdc))
        activity.btn_manager.setTextColor(context.getColor(R.color.black))
        activity.btn_account.setTextColor(context.getColor(R.color.black))
        activity.linear_on_main.visibility = View.VISIBLE
        with(dataBinding.rcyListProvince) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeAdapter(mutableListOf(), this@HomeFragment)
        }

    }

    override fun bindViewModel() {

        viewModel.getListProvince()
        viewModel.listProvince.observe(this, {
            (rcy_list_province.adapter as HomeAdapter).setList(it)
        })


        viewModel.listener.observe(this, {
            when (it) {
                HomeViewModel.SEARCH_MAP -> {
                    dataBinding.tvSearchMap.isEnabled = false
                    activity.startActivity(Intent(context, MapActivity::class.java))
                }
            }
        })
    }

//    private fun checkNetWork() {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val netInfo = cm.activeNetworkInfo
//        //should check null because in airplane mode it will be null
//        //should check null because in airplane mode it will be null
//        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
//        val downSpeed = nc?.linkDownstreamBandwidthKbps
//        Log.d("downSpeed", "$downSpeed")
//        val upSpeed = nc?.linkUpstreamBandwidthKbps
//        Log.d("upSpeed", "$upSpeed")
//        Toast.makeText(context, upSpeed.toString() + "ok" + downSpeed.toString(), Toast.LENGTH_LONG)
//            .show()
//    }


    override fun clickItem(model: Provinces) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.KEY_ID, model)
        navigators.navigate(R.id.travelAll_fragment, bundle)
    }
}