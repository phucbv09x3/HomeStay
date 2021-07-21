package com.kujira.homestay.ui.travelAll

import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentTravelAllBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.travel.TravelAdapter

class TravelAllFragment : BaseFragment<TravelAllViewModel, FragmentTravelAllBinding>() {
    override fun createViewModel(): Class<TravelAllViewModel> {
        return TravelAllViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_travel_all

    override fun initView() {
        with(dataBinding.rcyListTravelAll) {
            layoutManager = LinearLayoutManager(
                context,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            adapter = TravelAdapter(mutableListOf())
        }
        search()
    }

    override fun bindViewModel() {
        viewModel.getListTravelAll()
        viewModel.listTravels.observe(this, {
            Log.d("list", "$it")
            (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
        })

        viewModel.listenerBack.observe(this, {
            navigators.navigateUp()
        })
    }

    private fun search() {
        dataBinding.svNameProvinceList.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!TextUtils.isEmpty(query?.trim())) {
                    viewModel.searchUser(query)
                    viewModel.listTravels.observe(this@TravelAllFragment, {
                        Log.d("list", "$it")
                        (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
                    })


                } else {
                   viewModel.getListTravelAll()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!TextUtils.isEmpty(newText?.trim())) {
                    viewModel.searchUser(newText)
                    viewModel.listTravels.observe(this@TravelAllFragment, {
                        Log.d("list", "$it")
                        (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
                    })
                } else {
                    viewModel.getListTravelAll()
                }
                return false
            }

        })
    }
}