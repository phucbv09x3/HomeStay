package com.kujira.homestay.ui.travel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.databinding.FragmentListTravelBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.home.HomeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class ListTravelFragment : BaseFragment<ListTravelViewModel, FragmentListTravelBinding>() {
    override fun createViewModel(): Class<ListTravelViewModel> {
        return ListTravelViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_list_travel
    }

    override fun initView() {

        with(dataBinding.rcyListTravel) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = TravelAdapter(mutableListOf())
        }
        val bundle = arguments
        val data = bundle?.getParcelable<Provinces>("keyId") as Provinces
        data?.let { data ->
            dataBinding.tvNameProvinceList.text = data.name
            viewModel.getListTravelId(data.name)
            viewModel.listTravels.observe(this, {
                Log.d("list","$it")
                (dataBinding.rcyListTravel.adapter as TravelAdapter).setList(it)
            })
        }

    }

    override fun bindViewModel() {

    }
}