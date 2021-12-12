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
import com.kujira.homestay.utils.printLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit


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

        listenerCallBack()
    }
    private fun listenerCallBack(){
        dataManager.listenerHomeClientCallBack
            .throttleFirst(3, TimeUnit.SECONDS)
            .subscribe {
                printLog("callFragmentCallback $it")
                when(it){
                    1->{
                        navigators.navigate(R.id.listRoom)
                    }
                    2->{
                        navigators.navigate(R.id.travelAll_fragment,bundle = null)
                    }
                    3->{
                        activity.startActivity(Intent(context, MapActivity::class.java))
                    }
                    4->{
                        navigators.navigate(R.id.weather_fragment)
                    }
                }
            }.addDisposable()
    }
    override fun bindViewModel() {

        viewModel.getListProvince()
        viewModel.listProvince.observe(this, {
            (rcy_list_province.adapter as HomeAdapter).setList(it)
        })

    }



    override fun clickItem(model: Provinces) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.KEY_ID, model)
        navigators.navigate(R.id.travelAll_fragment, bundle)
    }
}