package com.kujira.homestay.ui.travelAll

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentTravelAllBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.travel.TravelAdapter

class TravelAllFragment :BaseFragment<TravelAllViewModel,FragmentTravelAllBinding>() {
    override fun createViewModel(): Class<TravelAllViewModel> {
        return TravelAllViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_travel_all

    override fun initView() {
        with(dataBinding.rcyListTravelAll) {
            layoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = TravelAdapter(mutableListOf())
        }
    }

    override fun bindViewModel() {
        viewModel.getListTravelAll()
        viewModel.listTravels.observe(this, {
            Log.d("list", "$it")
            (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
        })

        viewModel.listenerBack.observe(this,{
            navigators.navigateUp()
        })
    }
}